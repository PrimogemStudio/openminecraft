package com.primogemstudio.engine.lexer

abstract class ILexer<T : Enum<*>> {
    abstract fun <R> parse(type: T): R
}