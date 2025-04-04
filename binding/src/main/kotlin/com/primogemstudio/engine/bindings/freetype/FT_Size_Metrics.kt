package com.primogemstudio.engine.bindings.freetype

import com.primogemstudio.engine.foreign.align
import com.primogemstudio.engine.foreign.cacheOffsets
import com.primogemstudio.engine.foreign.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_LONG
import java.lang.foreign.ValueLayout.JAVA_LONG_UNALIGNED
import java.lang.foreign.ValueLayout.JAVA_SHORT
import java.lang.foreign.ValueLayout.JAVA_SHORT_UNALIGNED

class FT_Size_Metrics(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_SHORT_UNALIGNED,
            JAVA_SHORT_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var xppem: Short
        get() = seg.get(JAVA_SHORT, OFFSETS[0])
        set(value) = seg.set(JAVA_SHORT, OFFSETS[0], value)
    var yppem: Short
        get() = seg.get(JAVA_SHORT, OFFSETS[1])
        set(value) = seg.set(JAVA_SHORT, OFFSETS[1], value)
    var xscale: FT_Fixed
        get() = seg.get(JAVA_LONG, OFFSETS[2])
        set(value) = seg.set(JAVA_LONG, OFFSETS[2], value)
    var yscale: FT_Fixed
        get() = seg.get(JAVA_LONG, OFFSETS[3])
        set(value) = seg.set(JAVA_LONG, OFFSETS[3], value)
    var ascender: FT_Pos
        get() = seg.get(JAVA_LONG, OFFSETS[4])
        set(value) = seg.set(JAVA_LONG, OFFSETS[4], value)
    var descender: FT_Pos
        get() = seg.get(JAVA_LONG, OFFSETS[5])
        set(value) = seg.set(JAVA_LONG, OFFSETS[5], value)
    var height: FT_Pos
        get() = seg.get(JAVA_LONG, OFFSETS[6])
        set(value) = seg.set(JAVA_LONG, OFFSETS[6], value)
    var maxAdvance: FT_Pos
        get() = seg.get(JAVA_LONG, OFFSETS[7])
        set(value) = seg.set(JAVA_LONG, OFFSETS[7], value)
}