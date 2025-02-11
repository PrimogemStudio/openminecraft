package com.primogemstudio.engine.interfaces.heap

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_BYTE

class HeapByte(private val seg: MemorySegment) : IHeapVar<Byte> {
    constructor() : this(Arena.ofAuto().allocate(JAVA_BYTE))
    override fun ref(): MemorySegment = seg
    override fun value(): Byte = seg.get(JAVA_BYTE, 0)

    override fun toString(): String = "${value()}"
}
