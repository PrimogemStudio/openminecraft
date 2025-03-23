package com.primogemstudio.engine.bindings.freetype

import com.primogemstudio.engine.foreign.align
import com.primogemstudio.engine.foreign.cacheOffsets
import com.primogemstudio.engine.foreign.heap.HeapByteArray
import com.primogemstudio.engine.foreign.heap.HeapShortArray
import com.primogemstudio.engine.foreign.heap.HeapStructArray
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

class FT_Outline(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_SHORT_UNALIGNED,
            JAVA_SHORT_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var points: HeapStructArray<FT_Vector>
        get() = HeapStructArray(
            seg.get(JAVA_SHORT, OFFSETS[1]).toInt(),
            seg.get(ADDRESS, OFFSETS[2]),
            FT_Vector.LAYOUT
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[2], value.ref())
            seg.set(JAVA_SHORT, OFFSETS[1], value.length.toShort())
        }
    var tags: HeapByteArray
        get() = HeapByteArray(
            seg.get(JAVA_SHORT, OFFSETS[1]).toInt(),
            seg.get(ADDRESS, OFFSETS[3])
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[3], value.ref())
            seg.set(JAVA_SHORT, OFFSETS[1], value.length.toShort())
        }
    var contours: HeapShortArray
        get() = HeapShortArray(
            seg.get(JAVA_SHORT, OFFSETS[0]).toInt(),
            seg.get(ADDRESS, OFFSETS[4])
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[4], value.ref())
            seg.set(JAVA_SHORT, OFFSETS[0], value.length.toShort())
        }
    var flags: Int
        get() = seg.get(JAVA_INT, OFFSETS[5])
        set(value) = seg.set(JAVA_INT, OFFSETS[5], value)
}