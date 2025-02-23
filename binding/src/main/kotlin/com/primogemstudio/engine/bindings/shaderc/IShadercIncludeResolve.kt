package com.primogemstudio.engine.bindings.shaderc

import com.primogemstudio.engine.interfaces.fetchString
import com.primogemstudio.engine.interfaces.stub.IStub
import java.lang.foreign.MemorySegment
import java.lang.invoke.MethodType

fun interface IShadercIncludeResolve : IStub {
    fun call(userData: MemorySegment, source: String, type: Int, reqSource: String, includeDepth: Long)
    fun call(
        userData: MemorySegment,
        source: MemorySegment,
        type: Int,
        reqSource: MemorySegment,
        includeDepth: MemorySegment
    ) = call(userData, source.fetchString(), type, reqSource.fetchString(), includeDepth.address())

    override fun register(): Pair<String, MethodType> =
        Pair(
            "call",
            MethodType.methodType(
                Void.TYPE,
                MemorySegment::class.java,
                MemorySegment::class.java,
                Int::class.java,
                MemorySegment::class.java,
                MemorySegment::class.java
            )
        )
}