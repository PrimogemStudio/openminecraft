package com.primogemstudio.engine.interfaces.struct

import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.interfaces.heap.IHeapVar
import com.primogemstudio.engine.loader.Platform.sizetLength
import java.lang.foreign.MemoryLayout
import java.lang.foreign.ValueLayout.*
import java.lang.foreign.MemorySegment

class PointerArrayStruct<T: IHeapVar<*>>(val arr: Array<T>): IStruct(MemoryLayout.paddingLayout(arr.size * sizetLength() * 1L)) {
    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = MemoryLayout.paddingLayout(arr.size * sizetLength() * 1L)

    override fun construct(seg: MemorySegment) {
        var idx = 0
        arr.forEach {
            seg.set(ADDRESS, idx * sizetLength() * 1L, it.ref())
            idx++
        }
    }
}
