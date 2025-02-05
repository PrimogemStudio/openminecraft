package com.primogemstudio.engine.interfaces.struct

import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.ValueLayout.*
import java.lang.foreign.MemorySegment

class ByteArrayStruct(val arr: ByteArray): IStruct(MemoryLayout.paddingLayout(arr.size * 1L)) {
    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = MemoryLayout.paddingLayout(arr.size * 1L)

    override fun construct(seg: MemorySegment) {
        var idx = 0
        arr.forEach {
            seg.set(JAVA_BYTE, idx * 1L, it)
            idx++
        }
    }
}
