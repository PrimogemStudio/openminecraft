package com.primogemstudio.engine.serial

import com.primogemstudio.engine.lexer.FunctionDescData
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
            } else {
                this.println("class $it(data: MemorySegment): IHeapObject(data)")
            }
        }
    }
}