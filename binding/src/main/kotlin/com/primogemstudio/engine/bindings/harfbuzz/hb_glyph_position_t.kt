package com.primogemstudio.engine.bindings.harfbuzz

import com.primogemstudio.engine.foreign.align
import com.primogemstudio.engine.foreign.cacheOffsets
import com.primogemstudio.engine.foreign.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT
import java.lang.foreign.ValueLayout.JAVA_INT_UNALIGNED

class hb_glyph_position_t(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var xAdvance: Int
        get() = seg.get(JAVA_INT, OFFSETS[0])
        set(value) = seg.set(JAVA_INT, OFFSETS[0], value)
    var yAdvance: Int
        get() = seg.get(JAVA_INT, OFFSETS[1])
        set(value) = seg.set(JAVA_INT, OFFSETS[1], value)
    var xOffset: Int
        get() = seg.get(JAVA_INT, OFFSETS[2])
        set(value) = seg.set(JAVA_INT, OFFSETS[2], value)
    var yOffset: Int
        get() = seg.get(JAVA_INT, OFFSETS[3])
        set(value) = seg.set(JAVA_INT, OFFSETS[3], value)
}