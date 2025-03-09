package com.primogemstudio.engine.lexer

import java.io.InputStream

class JsonLexer(i: InputStream) : AbstractLexer(i) {
    companion object {
        const val JSON_NULL = 0
        const val JSON_OBJECT_INTERNAL = 1
        const val JSON_NAME_QUOTE_START = 2
        const val JSON_NAME = 3
        const val JSON_NAME_QUOTE_END = 4
        const val JSON_VALUE_COMMA = 5
        const val JSON_VALUE_NUMBER = 6
        const val JSON_VALUE_NUMBER_POINT = 7
    }

    override fun nextToken() {
        val c = nextChr()
        print(c)
        if (c == ' ' || c == '\t' || c == '\n') return

        when (state) {
            JSON_NULL -> {
                if (c != '{') throw IllegalStateException()
                state = JSON_OBJECT_INTERNAL
                return
            }

            JSON_OBJECT_INTERNAL -> {
                if (c != '"') throw IllegalStateException()
                state = JSON_NAME_QUOTE_START
                return
            }

            JSON_NAME_QUOTE_START, JSON_NAME -> {
                state = if (c != '"') JSON_NAME else JSON_NAME_QUOTE_END
                return
            }

            JSON_NAME_QUOTE_END -> {
                if (c != ':') throw IllegalStateException()
                state = JSON_VALUE_COMMA
                return
            }

            JSON_VALUE_COMMA -> {
                if (c > '9' || c < '0') throw IllegalStateException()
                // Other cases!
                state = JSON_VALUE_NUMBER
                return
            }

            JSON_VALUE_NUMBER -> {
                if (c == ',') {
                    state = JSON_OBJECT_INTERNAL
                    return
                } else if (c == '}') state = JSON_NULL
                else if (c == '.') {
                    state = JSON_VALUE_NUMBER_POINT
                    return
                } else if (c > '9' || c < '0') throw IllegalStateException()
            }

            JSON_VALUE_NUMBER_POINT -> {
                if (c > '9' || c < '0') throw IllegalStateException()
                state = JSON_VALUE_NUMBER
                return
            }

            else -> TODO()
        }

        if (state == JSON_NULL) TODO("End!")
    }
}