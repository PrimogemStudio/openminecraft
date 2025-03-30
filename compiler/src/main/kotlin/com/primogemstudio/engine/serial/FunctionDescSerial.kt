package com.primogemstudio.engine.serial

import com.primogemstudio.engine.lexer.ConstantDescData
import com.primogemstudio.engine.lexer.FunctionDescData
import com.primogemstudio.engine.lexer.PointerTypeDescData
import com.primogemstudio.engine.lexer.StructTypeDescData
import java.io.OutputStream
import java.io.PrintStream

class FunctionDescSerial(out: OutputStream) : PrintStream(out) {
    fun write(data: List<Any>) {
        data.forEach {
            if (it is FunctionDescData) {
                val a = if (it.argInvoke.isEmpty()) "" else ", " + it.argInvoke.joinToString(", ")

                if (it.retType == "") {
                    this.println(
                        "fun ${it.funcName}(${
                            it.args.map { "${it.key}: ${it.value}" }.joinToString(", ")
                        }) = callVoidFunc(\"${it.funcName}\"$a)"
                    )
                } else if (it.retIsPointer) {
                    this.println(
                        "fun ${it.funcName}(${
                            it.args.map { "${it.key}: ${it.value}" }.joinToString(", ")
                        }): ${it.retType} = ${it.retType}(callPointerFunc(\"${it.funcName}\"$a))"
                    )
                } else {
                    this.println(
                        "fun ${it.funcName}(${
                            it.args.map { "${it.key}: ${it.value}" }.joinToString(", ")
                        }): ${it.retType} = callFunc(\"${it.funcName}\", ${it.retType}::class$a)"
                    )
                }
            } else if (it is ConstantDescData) {
                if (it.value.toString().contains("(")) this.println("val ${it.name} = ${it.value}")
                else this.println("const val ${it.name} = ${it.value}")
            } else if (it is PointerTypeDescData) {
                this.println("class ${it.name}(data: MemorySegment): IHeapObject(data)")
            } else if (it is StructTypeDescData) {
                this.println("class ${it.name}(seg: MemorySegment): IHeapObject(seg) {")
                this.println("    companion object {")
                this.println("        val LAYOUT = MemoryLayout.structLayout(")
                this.println(it.accessTypes.map { "            ${it.value}_UNALIGNED" }.joinToString(",\n"))
                this.println("        ).align()")
                this.println("        private val OFFSETS = LAYOUT.cacheOffsets()")
                this.println("    }")
                this.println("")
                this.println("    constructor() : this(Arena.ofAuto().allocate(LAYOUT))")
                this.println("")
                var id = 0
                it.comps.keys.forEach { k ->
                    this.println("    var $k: ${it.comps[k]}")
                    if (it.comps[k] != "MemorySegment" && it.accessTypes[k] == "ADDRESS") {
                        this.println("        get() = ${it.comps[k]}(seg.get(${it.accessTypes[k]}, OFFSETS[$id]))")
                        this.println("        set(value) = seg.set(${it.accessTypes[k]}, OFFSETS[$id], value.ref())")
                    } else {
                        this.println("        get() = seg.get(${it.accessTypes[k]}, OFFSETS[$id])")
                        this.println("        set(value) = seg.set(${it.accessTypes[k]}, OFFSETS[$id], value)")
                    }
                    id++
                }
                this.println("}")
            } else {
                this.println(it)
            }
        }
    }
}