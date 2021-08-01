package com.an.ancc

val empty = Grammar.Token.Term()

/*
这两个方法根据《编译原理》所写，有些变量名我确实取不出名了，
反正这俩方法的作用就跟编译原理写的一样，看不懂可以去康康书w
 */

fun first(
    grammar: Grammar,
    tokens: MutableList<Grammar.Token>
): HashSet<Grammar.Token.Term> {
    val res = hashSetOf<Grammar.Token.Term>()
    if (tokens.size == 0){
        res.add(empty)
        return res
    }
    loop@ for (i in 0 until tokens.size) {
        when (val e = tokens[i]) {
            is Grammar.Token.Term -> {
                res.add(e)
                break@loop
            }
            is Grammar.Token.NonTerm -> {
                val n = grammar.getProductions(e)
                var c = false
                for (ni in n) {
                    if (ni.tokens.size == 0) {
                        res.add(empty)
                        continue
                    }
                    val nif = first(grammar, ni.tokens)
                    for (fe in nif) {
                        if (fe == empty) {
                            c = true
                            if (i == tokens.size - 1) {
                                res.add(empty)
                            }
                        } else {
                            res.add(fe)
                        }
                    }
                }
                if (!c) {
                    break@loop
                }
            }
        }
    }
    return res
}

fun follow(grammar: Grammar, stack: MutableList<String>, token: String): HashSet<Grammar.Token.Term> {
    val res = hashSetOf<Grammar.Token.Term>()
    if (token == grammar.entrance){
        res.add(Grammar.Token.Term("$"))
    }
    for (production in grammar.productions) {
        if (production.name in stack) {
            continue
        }
        stack.add(production.name)
        val tokens = production.tokens
        for (i in 0 until tokens.size) {
            if (tokens[i].token == token) {
                if (i == tokens.size - 1) {
                    res.addAll(follow(grammar, stack, production.name))
                } else {
                    val n = first(grammar, tokens.subList(i + 1, tokens.size))
                    for (e in n) {
                        if (e != empty) {
                            res.add(e)
                        } else {
                            res.addAll(follow(grammar, stack, production.name))
                        }
                    }
                }
            }
        }
        stack.removeAt(stack.size - 1)
    }
    return res
}