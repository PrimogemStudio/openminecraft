package com.primogemstudio.engine.lexer

enum class FunctionDescToken { Map, Type, FuncDesc, Args }
enum class DescType { ReturnFunc, PointerType }

data class FunctionDescData(
    private val funcName: String,
    private val retType: String,
    private val args: Map<String, String>
)

@Suppress("UNCHECKED_CAST")
class FunctionDescLexer(text: String) : ILexer<FunctionDescToken>() {
    private val textProcessed = text.replace("\t", "")
    private var index = 0

    override fun <R> parse(type: FunctionDescToken): R {
        return when (type) {
            FunctionDescToken.Type -> {
                val baseIndex = index
                while (textProcessed[index] != ',') {
                    index++
                }

                when (textProcessed.substring(baseIndex, index)) {
                    "tp" -> DescType.PointerType
                    "fr" -> DescType.ReturnFunc
                    else -> TODO("Invalid desc type!")
                } as R
            }

            FunctionDescToken.FuncDesc -> {
                index++
                var baseIndex = index
                while (textProcessed[index] != '.') {
                    index++
                }
                val funcName = textProcessed.substring(baseIndex, index)
                index++
                baseIndex = index
                while (textProcessed[index] != ':' && textProcessed[index] != '\n') {
                    index++
                }
                val retType = textProcessed.substring(baseIndex, index)
                return Pair(funcName, retType) as R
            }

            FunctionDescToken.Args -> {
                index++
                var baseIndex = index
                var argName = ""
                val argList = mutableMapOf<String, String>()
                while (index < textProcessed.length && textProcessed[index] != '\n') {
                    index++
                    if (textProcessed[index] == '.') {
                        argName = textProcessed.substring(baseIndex, index)
                        index++
                        baseIndex = index
                    } else if (textProcessed[index] == ',') {
                        argList[argName] = textProcessed.substring(baseIndex, index)
                        index++
                        baseIndex = index
                    }
                }
                if (argName != "") argList[argName] = textProcessed.substring(baseIndex, index)
                argList as R
            }

            FunctionDescToken.Map -> {
                val defs = mutableListOf<Any>()
                while (index < textProcessed.length - 1) {
                    when (parse<DescType>(FunctionDescToken.Type)) {
                        DescType.ReturnFunc -> {
                            val desc = parse<Pair<String, String>>(FunctionDescToken.FuncDesc)
                            defs.add(
                                FunctionDescData(
                                    desc.first, desc.second, parse<Map<String, String>>(FunctionDescToken.Args)
                                )
                            )
                        }

                        else -> TODO()
                    }
                }
                return defs as R
            }

            else -> TODO()
        }
    }
}