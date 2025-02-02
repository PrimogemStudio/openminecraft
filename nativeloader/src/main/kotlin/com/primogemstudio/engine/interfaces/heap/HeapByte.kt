package com.primogemstudio.engine.interfaces.heap

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_BYTE

class HeapByte : IHeapVar<Byte> {
    private val arena = Arena.ofConfined()
    private val seg = arena.allocate(JAVA_BYTE)
    override fun ref(): MemorySegment = seg
    override fun value(): Byte = seg.get(JAVA_BYTE, 0)

    override fun toString(): String = "${value()}"
    fun close() = arena.close()
}
