package com.primogemstudio.engine.interfaces.struct

import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment

class ArrayStruct<T: IStruct>(val arr: Array<T>): IStruct(MemoryLayout.paddingLayout(arr.map { it.layout().byteSize() }.stream().mapToLong { it }.sum())) {
    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = MemoryLayout.paddingLayout(arr.map { it.layout().byteSize() }.stream().mapToLong { it }.sum())

    override fun construct(seg: MemorySegment) {
        var offset = 0L
        arr.forEach {
            seg.asSlice(offset, it.layout().byteSize()).copyFrom(it.pointer())
            offset += it.layout().byteSize()
        }
    }
}
