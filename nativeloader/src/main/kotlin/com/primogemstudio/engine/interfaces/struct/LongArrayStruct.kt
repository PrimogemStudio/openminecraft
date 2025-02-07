package com.primogemstudio.engine.interfaces.struct

import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_LONG

class LongArrayStruct(val arr: LongArray) : IStruct(MemoryLayout.paddingLayout(arr.size * 8L)) {
    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = MemoryLayout.paddingLayout(arr.size * 8L)

    override fun construct(seg: MemorySegment) {
        var idx = 0
        arr.forEach {
            seg.set(JAVA_LONG, idx * 8L, it)
            idx++
        }
    }
}
