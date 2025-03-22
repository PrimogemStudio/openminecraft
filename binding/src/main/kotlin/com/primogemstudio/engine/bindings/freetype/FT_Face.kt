package com.primogemstudio.engine.bindings.freetype

import com.primogemstudio.engine.foreign.align
import com.primogemstudio.engine.foreign.cacheOffsets
import com.primogemstudio.engine.foreign.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_LONG

class FT_Face(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_LONG,
            JAVA_LONG,
            JAVA_LONG,
            JAVA_LONG,
            JAVA_LONG,
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var numFaces: Long
        get() = seg.get(JAVA_LONG, OFFSETS[0])
        set(value) = seg.set(JAVA_LONG, OFFSETS[0], value)
    var faceIndex: Long
        get() = seg.get(JAVA_LONG, OFFSETS[1])
        set(value) = seg.set(JAVA_LONG, OFFSETS[1], value)
    var faceFlags: Long
        get() = seg.get(JAVA_LONG, OFFSETS[2])
        set(value) = seg.set(JAVA_LONG, OFFSETS[2], value)
    var styleFlags: Long
        get() = seg.get(JAVA_LONG, OFFSETS[3])
        set(value) = seg.set(JAVA_LONG, OFFSETS[3], value)
    var numGlyphs: Long
        get() = seg.get(JAVA_LONG, OFFSETS[4])
        set(value) = seg.set(JAVA_LONG, OFFSETS[4], value)
}