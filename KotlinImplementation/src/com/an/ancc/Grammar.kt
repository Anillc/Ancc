package com.an.ancc

import java.util.*

class Grammar(val entrance: String) {
    val productions = mutableListOf<Production>()

    fun getProductions(token: Token.NonTerm): List<Production> {
        val res = mutableListOf<Production>()
        for (production in productions) {
            if (production.name == token.token) {
                res.add(production)
            }
        }
        return res
    }

    /**
     * @param token 此处传入的token为文法中输入的字符串，Ancc将使用此字符串来辨识该token
     * 使用时可以继承Term和NonTerm来传入更多的数据比如说字符串内容、数字等
     */
    sealed class Token(val token: String) {

        open class Term(term: String) : Token(term) {

            /**
             * 代表空串，用于first方法
             */
            constructor() : this(UUID.randomUUID().toString())

            override fun equals(other: Any?): Boolean {
                return other is Term && other.token == this.token
            }

            override fun hashCode(): Int {
                return token.hashCode()
            }

            override fun toString(): String {
                return token
            }
        }

        open class NonTerm(nonTerm: String) : Token(nonTerm) {
            override fun equals(other: Any?): Boolean {
                return other is NonTerm && other.token == this.token
            }

            override fun hashCode(): Int {
                return token.hashCode()
            }

            override fun toString(): String {
                return token
            }
        }
    }

    data class Production(val id: String, val name: String, val tokens: MutableList<Token>)

    infix fun String.p(name: String): Production {
        val production = Production(this, name, mutableListOf())
        productions.add(production)
        return production
    }

    infix fun Production.t(token: String): Production {
        tokens.add(Token.Term(token))
        return this
    }

    infix fun Production.n(token: String): Production {
        tokens.add(Token.NonTerm(token))
        return this
    }
}