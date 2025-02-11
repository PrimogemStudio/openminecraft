package com.primogemstudio.engine.interfaces.heap

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_LONG

class HeapLong(private val seg: MemorySegment) : IHeapVar<Long> {
    constructor() : this(Arena.ofAuto().allocate(JAVA_LONG))
    override fun ref(): MemorySegment = seg
    override fun value(): Long = seg.get(JAVA_LONG, 0)

    override fun toString(): String = "${value()}"
}
