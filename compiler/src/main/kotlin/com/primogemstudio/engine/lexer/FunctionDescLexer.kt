package com.primogemstudio.engine.lexer

enum class FunctionDescToken { Map, Type, FuncDesc, Args, TypeName }
enum class DescType { ReturnFunc, ArrayArgPostFunc, PointerType, ConstantMacro, StructType, FileSep }

data class FunctionDescData(
    val funcName: String,
    val retType: String,
    val retIsPointer: Boolean,
    val args: Map<String, String>,
    val argInvoke: List<String>
)

data class StructTypeDescData(
    val name: String,
    val comps: Map<String, String>,
    val accessTypes: Map<String, String>,
)

data class PointerTypeDescData(val name: String)
data class ConstantDescData(val name: String, val value: String)

fun mapType(s: String): String = when (s) {
    "b" -> "Byte"
    "c" -> "Char"
    "s" -> "Short"
    "i" -> "Int"
    "l" -> "Long"
    "f" -> "Float"
    "d" -> "Double"
    "z" -> "Boolean"
    "b*" -> "HeapByte"
    "c*" -> "HeapChar"
    "s*" -> "HeapShort"
    "i*" -> "HeapInt"
    "l*" -> "HeapLong"
    "f*" -> "HeapFloat"
    "d*" -> "HeapDouble"
    "z*" -> "HeapBoolean"
    "b[" -> "HeapByteArray"
    "c[" -> "HeapCharArray"
    "s[" -> "HeapShortArray"
    "i[" -> "HeapIntArray"
    "l[" -> "HeapLongArray"
    "f[" -> "HeapFloatArray"
    "d[" -> "HeapDoubleArray"
    "z[" -> "HeapBooleanArray"
    "*" -> "MemorySegment"
    "v" -> ""
    else -> s
}

fun isPointer(s: String): Boolean =
    s !in listOf("b", "c", "s", "i", "l", "f", "d", "z", "b*", "c*", "s*", "i*", "l*", "f*", "d*", "z*", "v")
fun isArray(s: String): Boolean =
    s in listOf("b[", "c[", "s[", "i[", "l[", "f[", "d[", "z[").map { mapType(it) } || s == "String"

fun stringToC(name: String, map: Map<String, String>): String = if (map[name] == "String") "$name.toCString()" else name

@Suppress("UNCHECKED_CAST")
class FunctionDescLexer(text: String) : ILexer<FunctionDescToken>() {
    private val textProcessed = text.replace("\t", "")
    private var index = 0

    override fun <R> parse(type: FunctionDescToken): R {
        return when (type) {
            FunctionDescToken.TypeName -> {
                index++
                var baseIndex = index
                while (index < textProcessed.length && textProcessed[index] != '\n') {
                    index++
                }
                return (textProcessed.substring(baseIndex, index) as R)
            }

            FunctionDescToken.Type -> {
                val baseIndex = index
                while (textProcessed[index] != ',') {
                    index++
                }

                when (textProcessed.substring(baseIndex, index)) {
                    "tp" -> DescType.PointerType
                    "fr" -> DescType.ReturnFunc
                    "fap" -> DescType.ArrayArgPostFunc
                    "cm" -> DescType.ConstantMacro
                    "tc" -> DescType.StructType
                    "fi" -> DescType.FileSep
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
                            val args = if (textProcessed[index] == '\n') mapOf() else parse<Map<String, String>>(
                                FunctionDescToken.Args
                            ).map { Pair(it.key, mapType(it.value)) }.toMap()
                            defs.add(
                                FunctionDescData(
                                    desc.first, mapType(desc.second), isPointer(desc.second),
                                    args,
                                    args.map { it.key }
                                        .map { stringToC(it, args) }
                                )
                            )
                            index++
                        }

                        DescType.ArrayArgPostFunc -> {
                            val desc = parse<Pair<String, String>>(FunctionDescToken.FuncDesc)
                            val args = if (textProcessed[index] == '\n') mapOf() else parse<Map<String, String>>(
                                FunctionDescToken.Args
                            ).map { Pair(it.key, mapType(it.value)) }.toMap()
                            defs.add(
                                FunctionDescData(
                                    desc.first, mapType(desc.second), isPointer(desc.second),
                                    args,
                                    args.map { it.key }
                                        .flatMap { if (isArray(args[it]!!)) listOf(it, "$it.length") else listOf(it) }
                                        .map { stringToC(it, args) }
                                )
                            )
                            index++
                        }

                        DescType.PointerType -> {
                            defs.add(PointerTypeDescData(parse<String>(FunctionDescToken.TypeName)))
                            index++
                        }

                        DescType.FileSep -> {
                            defs.add(parse<String>(FunctionDescToken.TypeName))
                            index++
                        }

                        DescType.ConstantMacro -> {
                            parse<Pair<String, String>>(FunctionDescToken.FuncDesc).apply {
                                defs.add(
                                    ConstantDescData(
                                        first,
                                        second
                                    )
                                )
                            }
                            index++
                        }

                        DescType.StructType -> {
                            val desc = parse<Pair<String, String>>(FunctionDescToken.FuncDesc)
                            val args = if (textProcessed[index] == '\n') mapOf() else parse<Map<String, String>>(
                                FunctionDescToken.Args
                            ).map { Pair(it.key, mapType(it.value)) }.toMap()
                            defs.add(StructTypeDescData(desc.first, args.mapKeys { mapType(it.key) }, args.mapValues {
                                when (it.value) {
                                    "Byte" -> "JAVA_BYTE"
                                    "Char" -> "JAVA_CHAR"
                                    "Short" -> "JAVA_SHORT"
                                    "Int" -> "JAVA_INT"
                                    "Long" -> "JAVA_LONG"
                                    "Float" -> "JAVA_FLOAT"
                                    "Double" -> "JAVA_DOUBLE"
                                    "Boolean" -> "JAVA_BOOLEAN"
                                    else -> "ADDRESS"
                                }
                            }))
                            index++
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