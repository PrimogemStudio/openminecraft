package com.primogemstudio.engine.lexer

enum class JsonTokens {
    JsonObject,
    JsonArray,
    JsonNumber,
    JsonString
}

class JsonLexer(text: String) : ILexer<JsonTokens>() {
    private val textProcessed = text.replace("\n", "").replace("\t", "")

    private var index = 0
    override fun parse(type: JsonTokens): Any {
        when (type) {
            JsonTokens.JsonString -> {
                if (textProcessed[index] != '"') TODO("Non string!")
                index++
                val st = index
                while (textProcessed[index] != '"') index++
                return textProcessed.substring(st, index).apply { index++ }
            }

            JsonTokens.JsonNumber -> {
                if (!isNumber(textProcessed[index])) TODO("Non number!")
                val st = index
                var numpoint = false
                while (index < textProcessed.length && isNumber(textProcessed[index])) {
                    val c = textProcessed[index]
                    if (c == '.') {
                        if (numpoint) TODO("Corrupt floating number format!")
                        numpoint = true
                    }
                    index++
                }
                val tx = textProcessed.substring(st, index)
                return if (numpoint) {
                    tx.toDouble()
                } else tx.toLong()
            }

            JsonTokens.JsonArray -> {
                if (textProcessed[index] != '[') TODO("Not an array!")
                index++
                val datas = mutableListOf<Any>()

                while (textProcessed[index] != ']') {
                    val tx = textProcessed[index]
                    if (isNumber(tx)) {
                        datas.add(parse(JsonTokens.JsonNumber))
                        checkArrayObjEnd()
                    } else if (tx == '"') {
                        datas.add(parse(JsonTokens.JsonString))
                        checkArrayObjEnd()
                    } else if (tx == '[') {
                        datas.add(parse(JsonTokens.JsonArray))
                        checkArrayObjEnd()
                    } else if (tx == ' ') {
                        index++
                    } else TODO("Corrupt array format!")
                }
                index++

                return datas
            }
            else -> TODO()
        }
        TODO()
    }

    private fun isNumber(c: Char): Boolean = ('0' <= c && c <= '9') || c == '.'
    private fun checkArrayObjEnd() {
        if (textProcessed[index] == ',') index++ else if (textProcessed[index] == ']') return else TODO("Not a valid array element end!")
    }
}