package com.primogemstudio.engine.bindings.harfbuzz

import com.primogemstudio.engine.foreign.align
import com.primogemstudio.engine.foreign.cacheOffsets
import com.primogemstudio.engine.foreign.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.ADDRESS
import java.lang.foreign.ValueLayout.ADDRESS_UNALIGNED
import java.lang.foreign.ValueLayout.JAVA_INT
import java.lang.foreign.ValueLayout.JAVA_INT_UNALIGNED

class hb_segment_properties_t(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var direction: Int
        get() = seg.get(JAVA_INT, OFFSETS[0])
        set(value) = seg.set(JAVA_INT, OFFSETS[0], value)
    var script: Int
        get() = seg.get(JAVA_INT, OFFSETS[1])
        set(value) = seg.set(JAVA_INT, OFFSETS[1], value)
    var language: hb_language_t
        get() = hb_language_t(seg.get(ADDRESS, OFFSETS[2]))
        set(value) = seg.set(ADDRESS, OFFSETS[2], value.ref())
}