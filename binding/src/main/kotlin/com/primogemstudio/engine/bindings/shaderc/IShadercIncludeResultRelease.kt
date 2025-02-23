package com.primogemstudio.engine.bindings.shaderc

import com.primogemstudio.engine.interfaces.stub.IStub
import java.lang.foreign.MemorySegment
import java.lang.invoke.MethodType

fun interface IShadercIncludeResultRelease : IStub {
    fun call(userData: MemorySegment, result: ShadercIncludeResult)
    fun call(userData: MemorySegment, result: MemorySegment) = call(userData, ShadercIncludeResult(result))

    override fun register(): Pair<String, MethodType> =
        Pair(
            "call",
            MethodType.methodType(
                Void.TYPE,
                MemorySegment::class.java,
                MemorySegment::class.java
            )
        )
}