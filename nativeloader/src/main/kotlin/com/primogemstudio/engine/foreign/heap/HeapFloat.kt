package com.primogemstudio.engine.foreign.heap

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_FLOAT

class HeapFloat(private val seg: MemorySegment) : IHeapVar<Float> {
    constructor() : this(Arena.ofAuto().allocate(JAVA_FLOAT))
    override fun ref(): MemorySegment = seg
    override fun value(): Float = seg.get(JAVA_FLOAT, 0)

    override fun toString(): String = "${value()}"
}
