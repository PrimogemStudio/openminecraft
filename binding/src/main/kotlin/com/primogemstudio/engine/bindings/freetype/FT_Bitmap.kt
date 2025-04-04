package com.primogemstudio.engine.bindings.freetype

import com.primogemstudio.engine.foreign.align
import com.primogemstudio.engine.foreign.cacheOffsets
import com.primogemstudio.engine.foreign.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.ADDRESS
import java.lang.foreign.ValueLayout.ADDRESS_UNALIGNED
import java.lang.foreign.ValueLayout.JAVA_BYTE
import java.lang.foreign.ValueLayout.JAVA_INT
import java.lang.foreign.ValueLayout.JAVA_INT_UNALIGNED
import java.lang.foreign.ValueLayout.JAVA_SHORT
import java.lang.foreign.ValueLayout.JAVA_SHORT_UNALIGNED

class FT_Bitmap(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_SHORT_UNALIGNED,
            JAVA_BYTE,
            JAVA_BYTE,
            ADDRESS_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var rows: Int
        get() = seg.get(JAVA_INT, OFFSETS[0])
        set(value) = seg.set(JAVA_INT, OFFSETS[0], value)
    var width: Int
        get() = seg.get(JAVA_INT, OFFSETS[1])
        set(value) = seg.set(JAVA_INT, OFFSETS[1], value)
    var pitch: Int
        get() = seg.get(JAVA_INT, OFFSETS[2])
        set(value) = seg.set(JAVA_INT, OFFSETS[2], value)
    var buffer: MemorySegment
        get() = seg.get(ADDRESS, OFFSETS[3])
        set(value) = seg.set(ADDRESS, OFFSETS[3], value)
    var numGrays: Short
        get() = seg.get(JAVA_SHORT, OFFSETS[4])
        set(value) = seg.set(JAVA_SHORT, OFFSETS[4], value)
    var pixelMode: Byte
        get() = seg.get(JAVA_BYTE, OFFSETS[5])
        set(value) = seg.set(JAVA_BYTE, OFFSETS[5], value)
    var paletteMode: Byte
        get() = seg.get(JAVA_BYTE, OFFSETS[6])
        set(value) = seg.set(JAVA_BYTE, OFFSETS[6], value)
    var palette: MemorySegment
        get() = seg.get(ADDRESS, OFFSETS[7])
        set(value) = seg.set(ADDRESS, OFFSETS[7], value)
}