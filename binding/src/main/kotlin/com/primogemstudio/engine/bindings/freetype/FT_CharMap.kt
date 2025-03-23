package com.primogemstudio.engine.bindings.freetype

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
import java.lang.foreign.ValueLayout.JAVA_SHORT
import java.lang.foreign.ValueLayout.JAVA_SHORT_UNALIGNED

class FT_CharMap(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_SHORT_UNALIGNED,
            JAVA_SHORT_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var face: FT_Face
        get() = FT_Face(seg.get(ADDRESS, OFFSETS[0]).reinterpret(FT_Face.LAYOUT.byteSize()))
        set(value) = seg.set(ADDRESS, OFFSETS[0], value.ref())
    var encoding: Int
        get() = seg.get(JAVA_INT, OFFSETS[1])
        set(value) = seg.set(JAVA_INT, OFFSETS[1], value)
    var platformId: Short
        get() = seg.get(JAVA_SHORT, OFFSETS[2])
        set(value) = seg.set(JAVA_SHORT, OFFSETS[2], value)
    var encodingId: Short
        get() = seg.get(JAVA_SHORT, OFFSETS[3])
        set(value) = seg.set(JAVA_SHORT, OFFSETS[3], value)
}