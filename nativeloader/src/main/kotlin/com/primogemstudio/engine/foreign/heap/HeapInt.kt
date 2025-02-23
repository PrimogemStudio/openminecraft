package com.primogemstudio.engine.foreign.heap

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT

class HeapInt(private val seg: MemorySegment) : IHeapVar<Int> {
    constructor() : this(Arena.ofAuto().allocate(JAVA_INT))
    override fun ref(): MemorySegment = seg
    override fun value(): Int = seg.get(JAVA_INT, 0)

    override fun toString(): String = "${value()}"
}
