package com.primogemstudio.engine.foreign.heap

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_FLOAT

class HeapFloatArray(val length: Int, private val seg: MemorySegment) : IHeapVar<FloatArray> {
    constructor(length: Int) : this(length, Arena.ofAuto().allocate(length * 4L))
    constructor(data: FloatArray) : this(data.size, Arena.ofAuto().allocate(data.size * 4L)) {
        for (i in data.indices) {
            this[i] = data[i]
        }
    }

    override fun ref(): MemorySegment = seg
    override fun value(): FloatArray = (0..<length).map { this[it] }.toFloatArray()

    operator fun get(idx: Int): Float = seg.get(JAVA_FLOAT, 4L * idx)
    operator fun set(idx: Int, value: Float) = seg.set(JAVA_FLOAT, 4L * idx, value)
}