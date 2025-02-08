package com.primogemstudio.engine.interfaces.struct

import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_FLOAT

class FloatArrayStruct(val arr: FloatArray): IStruct(MemoryLayout.paddingLayout(arr.size * 4L)) {
    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = MemoryLayout.paddingLayout(arr.size * 4L)

    override fun construct(seg: MemorySegment) {
        var idx = 0
        arr.forEach {
            seg.set(JAVA_FLOAT, idx * 4L, it)
            idx++
        }
    }
}
