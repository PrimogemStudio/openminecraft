package com.primogemstudio.engine.json

import com.primogemstudio.engine.lexer.JsonLexer

object JsonWrap {
    fun lexer(t: String): JsonLexer = JsonLexer(t)
}