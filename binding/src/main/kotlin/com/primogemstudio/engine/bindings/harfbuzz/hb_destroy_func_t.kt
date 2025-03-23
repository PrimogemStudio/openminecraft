package com.primogemstudio.engine.bindings.harfbuzz

import com.primogemstudio.engine.foreign.stub.IStub
import java.lang.foreign.MemorySegment
import java.lang.invoke.MethodType

fun interface hb_destroy_func_t : IStub {
    fun call(data: MemorySegment)
    override fun register(): Pair<String, MethodType> =
        Pair(
            "call",
            MethodType.methodType(
                Void.TYPE,
                MemorySegment::class.java
            )
        )
}