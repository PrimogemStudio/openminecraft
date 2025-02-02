package com.primogemstudio.engine.interfaces.heap

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_FLOAT

class HeapFloat : IHeapVar<Float> {
    private val arena = Arena.ofConfined()
    private val seg = arena.allocate(JAVA_FLOAT)
    override fun ref(): MemorySegment = seg
    override fun value(): Float = seg.get(JAVA_FLOAT, 0)

    override fun toString(): String = "${value()}"
    override fun close() = arena.close()
}