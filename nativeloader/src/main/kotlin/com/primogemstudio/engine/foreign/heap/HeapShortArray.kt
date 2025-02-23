package com.primogemstudio.engine.foreign.heap

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_SHORT

class HeapShortArray(val length: Int, private val seg: MemorySegment) : IHeapVar<ShortArray> {
    constructor(length: Int) : this(length, Arena.ofAuto().allocate(length * 2L))
    constructor(data: ShortArray) : this(data.size, Arena.ofAuto().allocate(data.size * 2L)) {
        for (i in data.indices) {
            this[i] = data[i]
        }
    }

    override fun ref(): MemorySegment = seg
    override fun value(): ShortArray = (0..<length).map { this[it] }.toShortArray()

    operator fun get(idx: Int): Short = seg.get(JAVA_SHORT, 2L * idx)
    operator fun set(idx: Int, value: Short) = seg.set(JAVA_SHORT, 2L * idx, value)
}