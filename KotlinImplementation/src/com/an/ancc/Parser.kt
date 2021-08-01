package com.an.ancc

class Parser(
    private val entrance: String,
    private val table: MutableMap<String, MutableMap<String, Grammar.Production>>,
    private val lexer: Lexer
) {
    fun parse(): Node {
        return genNode(entrance)
    }

    private fun genNode(name: String): Node {
        val nodes = mutableListOf<Any>()
        val production = table[name]!![lexer.peek!!.token]
        for (token in production!!.tokens) {
            when (token) {
                is Grammar.Token.NonTerm -> {
                    nodes.add(genNode(token.token))
                }
                is Grammar.Token.Term -> {
                    val peek = lexer.peek
                    if (token != peek) {
                        throw Exception()
                    }
                    nodes.add(peek)
                    lexer.next()
                }
            }
        }
        return Node(production.id, nodes)
    }
}