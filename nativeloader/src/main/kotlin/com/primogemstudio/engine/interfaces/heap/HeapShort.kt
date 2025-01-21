package com.primogemstudio.engine.interfaces.heap

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_SHORT

class HeapShort : IHeapVar<Short> {
    private val seg = Arena.ofConfined().allocate(JAVA_SHORT)
    override fun ref(): MemorySegment = seg
    override fun value(): Short = seg.get(JAVA_SHORT, 0)

    override fun toString(): String = "${value()}"
}