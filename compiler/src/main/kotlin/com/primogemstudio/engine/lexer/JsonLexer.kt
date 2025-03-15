package com.primogemstudio.engine.lexer

enum class JsonTokens {
    JsonObject,
    JsonArray,
    JsonNumber,
    JsonString
}

@Suppress("UNCHECKED_CAST")
class JsonLexer(text: String) : ILexer<JsonTokens>() {
    private var textProcessed = text.replace("\n", "").replace("\t", "")

    fun updateText(text: String) {
        textProcessed = text.replace("\n", "").replace("\t", "")
        index = 0
    }

    private var index = 0
    fun parseTree(): Map<String, Any> = parse(JsonTokens.JsonObject) as Map<String, Any>
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
                        continue
                    }
                    when (tx) {
                        '"' -> {
                            datas.add(parse(JsonTokens.JsonString))
                            checkArrayObjEnd()
                        }

                        '[' -> {
                            datas.add(parse(JsonTokens.JsonArray))
                            checkArrayObjEnd()
                        }

                        '{' -> {
                            datas.add(parse(JsonTokens.JsonObject))
                            checkArrayObjEnd()
                        }

                        ' ' -> index++
                        else -> TODO("Corrupt array format!")
                    }
                }
                index++

                return datas
            }

            JsonTokens.JsonObject -> {
                if (textProcessed[index] != '{') TODO("Not an object!")
                index++
                val datas = mutableMapOf<String, Any>()

                while (textProcessed[index] != '}') {
                    if (textProcessed[index] == ' ') {
                        index++
                        continue
                    }

                    if (textProcessed[index] != '"') TODO("Corrupt object format!")
                    val key = parse(JsonTokens.JsonString).toString()
                    while (textProcessed[index] == ' ') index++
                    if (textProcessed[index] != ':') TODO("Corrupt object format!")
                    index++
                    while (textProcessed[index] == ' ') index++

                    if (isNumber(textProcessed[index])) {
                        datas[key] = parse(JsonTokens.JsonNumber)
                        checkObjectObjEnd()
                        continue
                    }
                    var data: Any
                    when (textProcessed[index]) {
                        '"' -> {
                            data = parse(JsonTokens.JsonString)
                            checkObjectObjEnd()
                        }

                        '[' -> {
                            data = parse(JsonTokens.JsonArray)
                            checkObjectObjEnd()
                        }

                        '{' -> {
                            data = parse(JsonTokens.JsonObject)
                            checkObjectObjEnd()
                        }

                        else -> TODO("Corrupt object format!")
                    }

                    datas[key] = data
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
        if (textProcessed[index] == ',') index++ else if (textProcessed[index] == ']' || textProcessed[index] == ' ') return else TODO(
            "Not a valid array element end!"
        )
    }

    private fun checkObjectObjEnd() {
        if (textProcessed[index] == ',') index++ else if (textProcessed[index] == '}' || textProcessed[index] == ' ') return else TODO(
            "Not a valid object element end!"
        )
    }
}