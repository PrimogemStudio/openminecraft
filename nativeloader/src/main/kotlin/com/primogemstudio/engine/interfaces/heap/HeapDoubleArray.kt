package com.primogemstudio.engine.interfaces.heap

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_DOUBLE

class HeapDoubleArray(val length: Int, private val seg: MemorySegment) : IHeapVar<DoubleArray> {
    constructor(length: Int) : this(length, Arena.ofAuto().allocate(length * 8L))
    constructor(data: DoubleArray) : this(data.size, Arena.ofAuto().allocate(data.size * 8L)) {
        for (i in data.indices) {
            this[i] = data[i]
        }
    }

    override fun ref(): MemorySegment = seg
    override fun value(): DoubleArray = (0..<length).map { this[it] }.toDoubleArray()

    operator fun get(idx: Int): Double = seg.get(JAVA_DOUBLE, 8L * idx)
    operator fun set(idx: Int, value: Double) = seg.set(JAVA_DOUBLE, 8L * idx, value)
}