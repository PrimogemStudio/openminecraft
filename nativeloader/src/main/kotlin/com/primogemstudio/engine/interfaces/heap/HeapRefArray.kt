package com.primogemstudio.engine.interfaces.heap

import com.primogemstudio.engine.loader.Platform.is32bits
import com.primogemstudio.engine.loader.Platform.sizetLength
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT
import java.lang.foreign.ValueLayout.JAVA_LONG

class HeapRefArray(private val length: Int) : IHeapVar<Array<MemorySegment>> {
    private val seg = Arena.ofConfined().allocate(sizetLength() * length * 1L)
    override fun ref(): MemorySegment = seg

    override fun value(): Array<MemorySegment> {
        return (0..<length).toList().map {
            if (is32bits()) seg.get(JAVA_INT, sizetLength() * 4L * it).toLong()
            else seg.get(JAVA_LONG, sizetLength() * 8L * it)
        }.map { MemorySegment.ofAddress(it) }.toTypedArray()
    }
}