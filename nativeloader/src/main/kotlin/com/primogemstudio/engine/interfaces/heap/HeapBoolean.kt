package com.primogemstudio.engine.interfaces.heap

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_BYTE

class HeapBoolean(private val seg: MemorySegment) : IHeapVar<Boolean> {
    constructor() : this(Arena.ofAuto().allocate(JAVA_BYTE))
    override fun ref(): MemorySegment = seg
    override fun value(): Boolean = seg.get(JAVA_BYTE, 0) != 0.toByte()

    override fun toString(): String = "${value()}"
}
