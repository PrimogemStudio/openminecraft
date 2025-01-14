package com.primogemstudio.engine

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType

class TestNativeCall {
    fun fetchHandle(): MethodHandle {
        return MethodHandles.lookup()
            .findStatic(this::class.java, "test", MethodType.methodType(Void.TYPE, Int::class.java))
    }

    companion object {
        @JvmStatic
        fun test(i: Int) {
            println(i)
        }
    }
}