package com.primogemstudio.engine.bindings.freetype

import com.primogemstudio.engine.foreign.align
import com.primogemstudio.engine.foreign.cacheOffsets
import com.primogemstudio.engine.foreign.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT
import java.lang.foreign.ValueLayout.JAVA_INT_UNALIGNED
import java.lang.foreign.ValueLayout.JAVA_LONG
import java.lang.foreign.ValueLayout.JAVA_LONG_UNALIGNED

class FT_Size_Request(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var type: Int
        get() = seg.get(JAVA_INT, OFFSETS[0])
        set(value) = seg.set(JAVA_INT, OFFSETS[0], value)
    var width: Long
        get() = seg.get(JAVA_LONG, OFFSETS[1])
        set(value) = seg.set(JAVA_LONG, OFFSETS[1], value)
    var height: Long
        get() = seg.get(JAVA_LONG, OFFSETS[2])
        set(value) = seg.set(JAVA_LONG, OFFSETS[2], value)
    var horiResolution: Int
        get() = seg.get(JAVA_INT, OFFSETS[3])
        set(value) = seg.set(JAVA_INT, OFFSETS[3], value)
    var vertResolution: Int
        get() = seg.get(JAVA_INT, OFFSETS[4])
        set(value) = seg.set(JAVA_INT, OFFSETS[4], value)
}