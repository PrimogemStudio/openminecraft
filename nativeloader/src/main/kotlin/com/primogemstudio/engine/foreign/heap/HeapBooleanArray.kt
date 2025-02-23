package com.primogemstudio.engine.foreign.heap

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_BYTE

class HeapBooleanArray(val length: Int, private val seg: MemorySegment) : IHeapVar<BooleanArray> {
    constructor(length: Int) : this(length, Arena.ofAuto().allocate(length * 1L))
    constructor(data: BooleanArray) : this(data.size, Arena.ofAuto().allocate(data.size * 1L)) {
        for (i in data.indices) {
            this[i] = data[i]
        }
    }

    override fun ref(): MemorySegment = seg
    override fun value(): BooleanArray = (0..<length).map { this[it] }.toBooleanArray()

    operator fun get(idx: Int): Boolean = seg.get(JAVA_BYTE, 1L * idx) != 0.toByte()
    operator fun set(idx: Int, value: Boolean) = seg.set(JAVA_BYTE, 1L * idx, if (value) 1 else 0)
}