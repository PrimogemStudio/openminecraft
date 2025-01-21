package com.primogemstudio.engine.interfaces.heap

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_DOUBLE

class HeapDouble : IHeapVar<Double> {
    private val seg = Arena.ofConfined().allocate(JAVA_DOUBLE)
    override fun ref(): MemorySegment = seg
    override fun value(): Double = seg.get(JAVA_DOUBLE, 0)

    override fun toString(): String = "${value()}"
}