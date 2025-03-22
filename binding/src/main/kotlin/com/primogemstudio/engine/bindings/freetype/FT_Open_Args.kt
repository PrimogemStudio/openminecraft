package com.primogemstudio.engine.bindings.freetype

import com.primogemstudio.engine.foreign.align
import com.primogemstudio.engine.foreign.cacheOffsets
import com.primogemstudio.engine.foreign.fetchString
import com.primogemstudio.engine.foreign.heap.HeapByteArray
import com.primogemstudio.engine.foreign.heap.HeapStructArray
import com.primogemstudio.engine.foreign.heap.IHeapObject
import com.primogemstudio.engine.foreign.toCString
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.ADDRESS
import java.lang.foreign.ValueLayout.JAVA_INT
import java.lang.foreign.ValueLayout.JAVA_LONG

class FT_Open_Args(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT,
            ADDRESS,
            JAVA_LONG,
            ADDRESS,
            ADDRESS,
            ADDRESS,
            JAVA_INT,
            ADDRESS
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var flags: Int
        get() = seg.get(JAVA_INT, OFFSETS[0])
        set(value) = seg.set(JAVA_INT, OFFSETS[0], value)
    var memory: HeapByteArray
        get() = HeapByteArray(
            seg.get(JAVA_LONG, OFFSETS[2]).toInt(),
            seg.get(ADDRESS, OFFSETS[1])
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[1], value.ref())
            seg.set(JAVA_LONG, OFFSETS[2], value.length.toLong())
        }
    var filename: String
        get() = seg.get(ADDRESS, OFFSETS[3]).fetchString()
        set(value) = seg.set(ADDRESS, OFFSETS[3], value.toCString())
    var stream: FT_Stream
        get() = FT_Stream(seg.get(ADDRESS, OFFSETS[4]))
        set(value) = seg.set(ADDRESS, OFFSETS[4], value.ref())
    var driver: FT_Module
        get() = FT_Module(seg.get(ADDRESS, OFFSETS[5]))
        set(value) = seg.set(ADDRESS, OFFSETS[5], value.ref())
    var params: HeapStructArray<FT_Parameter>
        get() = HeapStructArray(
            seg.get(JAVA_INT, OFFSETS[6]),
            seg.get(ADDRESS, OFFSETS[7]),
            FT_Parameter.LAYOUT
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[7], value.ref())
            seg.set(JAVA_INT, OFFSETS[6], value.length)
        }
}