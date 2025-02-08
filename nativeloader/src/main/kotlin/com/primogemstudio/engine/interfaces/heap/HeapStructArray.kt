package com.primogemstudio.engine.interfaces.heap

import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment

class HeapStructArray(val length: Int, private val seg: MemorySegment, private val layout: MemoryLayout) :
    IHeapVar<Array<IHeapVar<*>>> {
    override fun ref(): MemorySegment = seg
    override fun value(): Array<IHeapVar<*>> {
        TODO("Unsupported operation!")
    }

    constructor(layout: MemoryLayout, length: Int) : this(
        length,
        Arena.ofAuto().allocate(MemoryLayout.sequenceLayout(length.toLong(), layout)),
        layout
    )

    operator fun get(idx: Int): MemorySegment = seg.asSlice(layout.byteSize() * idx, layout.byteSize())
    operator fun set(idx: Int, data: IHeapVar<*>) {
        seg.asSlice(layout.byteSize() * idx, layout.byteSize()).copyFrom(data.ref())
    }
}