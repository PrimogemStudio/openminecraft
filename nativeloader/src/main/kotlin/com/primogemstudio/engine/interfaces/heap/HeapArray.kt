package com.primogemstudio.engine.interfaces.heap

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.ADDRESS
import com.primogemstudio.engine.loader.Platform.sizetLength

class HeapArray(private val length: Int) : IHeapVar<Array<MemorySegment>> {
    private val arena = Arena.ofConfined()
    private val seg = arena.allocate(sizetLength() * length * 1L)
    override fun ref(): MemorySegment = seg
    override fun value(): Array<MemorySegment> = (0 ..< length).map { seg.get(ADDRESS, sizetLength() * it * 1L) }.toTypedArray()

    override fun toString(): String = "${value()}"
    override fun close() = arena.close()
}