package com.primogemstudio.engine.interfaces.heap

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_BYTE

class HeapByteArray(val length: Int, private val seg: MemorySegment) : IHeapVar<ByteArray> {
    constructor(length: Int) : this(length, Arena.ofAuto().allocate(length * 1L))
    constructor(data: ByteArray) : this(data.size, Arena.ofAuto().allocate(data.size * 1L)) {
        for (i in data.indices) {
            this[i] = data[i]
        }
    }

    override fun ref(): MemorySegment = seg
    override fun value(): ByteArray {
        TODO("Unsupported operation!")
    }

    operator fun get(idx: Int): Byte = seg.get(JAVA_BYTE, 1L * idx)
    operator fun set(idx: Int, value: Byte) = seg.set(JAVA_BYTE, 1L * idx, value)
}