package com.primogemstudio.engine.interfaces.heap

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT

class HeapIntArray(val length: Int, private val seg: MemorySegment) : IHeapVar<IntArray> {
    constructor(length: Int) : this(length, Arena.ofAuto().allocate(length * 4L))
    constructor(data: IntArray) : this(data.size, Arena.ofAuto().allocate(data.size * 4L)) {
        for (i in data.indices) {
            this[i] = data[i]
        }
    }

    override fun ref(): MemorySegment = seg
    override fun value(): IntArray {
        TODO("Unsupported operation!")
    }

    operator fun get(idx: Int): Int = seg.get(JAVA_INT, 4L * idx)
    operator fun set(idx: Int, value: Int) = seg.set(JAVA_INT, 4L * idx, value)
}