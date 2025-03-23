package com.primogemstudio.engine.foreign.heap

import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment

class HeapStructArray<T : IHeapVar<*>>(
    val length: Int,
    private val seg: MemorySegment,
    private val layout: MemoryLayout
) :
    IHeapVar<Array<IHeapVar<*>>> {
    override fun ref(): MemorySegment = seg
    override fun value(): Array<IHeapVar<*>> {
        TODO("Unsupported operation!")
    }

    constructor(layout: MemoryLayout, length: Int) : this(
        length,
        Arena.ofAuto().allocate(length.toLong() * layout.byteSize()),
        layout
    )

    operator fun get(idx: Int): MemorySegment =
        seg.reinterpret(layout.byteSize() * length).asSlice(layout.byteSize() * idx, layout.byteSize())
    operator fun set(idx: Int, data: IHeapVar<*>) {
        seg.reinterpret(layout.byteSize() * length).asSlice(layout.byteSize() * idx, layout.byteSize())
            .copyFrom(data.ref())
    }
}