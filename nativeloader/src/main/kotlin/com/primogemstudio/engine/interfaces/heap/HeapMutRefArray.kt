package com.primogemstudio.engine.interfaces.heap

import com.primogemstudio.engine.loader.Platform.is32bits
import com.primogemstudio.engine.loader.Platform.sizetLength
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT
import java.lang.foreign.ValueLayout.JAVA_LONG

class HeapMutRefArray(
    private val seg: MemorySegment,
    private val length: Int
) : IHeapVar<Array<MemorySegment>> {
    override fun ref(): MemorySegment = seg.reinterpret(sizetLength() * length * 1L)

    override fun value(): Array<MemorySegment> {
        return (0..<length).toList().map {
            if (is32bits()) ref().get(JAVA_INT, sizetLength() * it * 1L).toLong()
            else ref().get(JAVA_LONG, sizetLength() * it * 1L)
        }.map { MemorySegment.ofAddress(it) }.toTypedArray()
    }
}