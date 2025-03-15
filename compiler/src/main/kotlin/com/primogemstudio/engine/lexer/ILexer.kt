package com.primogemstudio.engine.lexer

abstract class ILexer<T : Enum<*>> {
    abstract fun parse(type: T): Any
}