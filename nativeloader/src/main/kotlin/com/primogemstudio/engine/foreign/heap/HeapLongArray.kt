package com.primogemstudio.engine.foreign.heap

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_LONG

class HeapLongArray(val length: Int, private val seg: MemorySegment) : IHeapVar<LongArray> {
    constructor(length: Int) : this(length, Arena.ofAuto().allocate(length * 8L))
    constructor(data: LongArray) : this(data.size, Arena.ofAuto().allocate(data.size * 8L)) {
        for (i in data.indices) {
            this[i] = data[i]
        }
    }

    override fun ref(): MemorySegment = seg
    override fun value(): LongArray = (0..<length).map { this[it] }.toLongArray()

    operator fun get(idx: Int): Long = seg.get(JAVA_LONG, 8L * idx)
    operator fun set(idx: Int, value: Long) = seg.set(JAVA_LONG, 8L * idx, value)
}