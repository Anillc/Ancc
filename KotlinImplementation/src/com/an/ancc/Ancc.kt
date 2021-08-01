package com.an.ancc

class Ancc(private val entrance: String, private val genGrammar: Grammar.() -> Unit) {
    fun genTable(): MutableMap<String, MutableMap<String, Grammar.Production>> {
        val grammar = Grammar(entrance)
        genGrammar(grammar)
        val table = mutableMapOf<String, MutableMap<String, Grammar.Production>>()
        for (production in grammar.productions) {
            var iTable = table[production.name]
            if (iTable == null) {
                iTable = mutableMapOf()
                table[production.name] = iTable
            }

            val fi = first(grammar, production.tokens)
            var existsEmpty = false
            for (token in fi) {
                if (token != empty) {
                    iTable[token.token] = production
                } else {
                    existsEmpty = true
                }
            }
            if (existsEmpty) {
                val fo = follow(grammar, mutableListOf(), production.name)
                for (token in fo) {
                    iTable[token.token] = production
                }
            }
        }
        return table
    }
}