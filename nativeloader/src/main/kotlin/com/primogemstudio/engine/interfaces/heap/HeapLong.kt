package com.primogemstudio.engine.interfaces.heap

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_LONG

class HeapLong : IHeapVar<Long> {
    private val arena = Arena.ofConfined()
    private val seg = arena.allocate(JAVA_LONG)
    override fun ref(): MemorySegment = seg
    override fun value(): Long = seg.get(JAVA_LONG, 0)

    override fun toString(): String = "${value()}"
    fun close() = arena.close()
}
