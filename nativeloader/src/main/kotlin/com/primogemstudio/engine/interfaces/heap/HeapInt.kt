package com.primogemstudio.engine.interfaces.heap

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT

class HeapInt : IHeapVar<Int> {
    private val arena = Arena.ofAuto()
    private val seg = arena.allocate(JAVA_INT)
    override fun ref(): MemorySegment = seg
    override fun value(): Int = seg.get(JAVA_INT, 0)

    override fun toString(): String = "${value()}"
}
