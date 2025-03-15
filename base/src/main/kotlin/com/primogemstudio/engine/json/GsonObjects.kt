package com.primogemstudio.engine.json

import com.google.gson.GsonBuilder
import com.primogemstudio.engine.lexer.JsonLexer

object GsonObjects {
    val GSON = GsonBuilder().setPrettyPrinting().create()
    fun lexer(t: String): JsonLexer = JsonLexer(t)
}