package com.an.ancc

abstract class Lexer {
    abstract fun next()
    abstract val peek: Grammar.Token.Term?
}