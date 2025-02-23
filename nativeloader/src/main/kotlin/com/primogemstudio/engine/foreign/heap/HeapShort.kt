package com.primogemstudio.engine.foreign.heap

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_SHORT

class HeapShort(private val seg: MemorySegment) : IHeapVar<Short> {
    constructor() : this(Arena.ofAuto().allocate(JAVA_SHORT))
    override fun ref(): MemorySegment = seg
    override fun value(): Short = seg.get(JAVA_SHORT, 0)

    override fun toString(): String = "${value()}"
}
