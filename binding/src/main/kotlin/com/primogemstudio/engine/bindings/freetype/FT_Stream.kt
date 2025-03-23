package com.primogemstudio.engine.bindings.freetype

import com.primogemstudio.engine.foreign.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.ADDRESS
import java.lang.foreign.ValueLayout.ADDRESS_UNALIGNED
import java.lang.foreign.ValueLayout.JAVA_LONG
import java.lang.foreign.ValueLayout.JAVA_LONG_UNALIGNED

class FT_Stream(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.unionLayout(
            JAVA_LONG_UNALIGNED,
            ADDRESS_UNALIGNED
        )
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var value: Long
        get() = seg.get(JAVA_LONG, 0)
        set(value) = seg.set(JAVA_LONG, 0, value)
    var pointer: MemorySegment
        get() = seg.get(ADDRESS, 0)
        set(value) = seg.set(ADDRESS, 0, value)
}