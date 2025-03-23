package com.primogemstudio.engine.bindings.freetype

import com.primogemstudio.engine.foreign.align
import com.primogemstudio.engine.foreign.cacheOffsets
import com.primogemstudio.engine.foreign.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.ADDRESS
import java.lang.foreign.ValueLayout.ADDRESS_UNALIGNED

class FT_Size(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            ADDRESS_UNALIGNED,
            FT_Generic.LAYOUT,
            FT_Size_Metrics.LAYOUT,
            ADDRESS_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var face: FT_Face
        get() = FT_Face(seg.get(ADDRESS, OFFSETS[0]).reinterpret(FT_Face.LAYOUT.byteSize()))
        set(value) = seg.set(ADDRESS, OFFSETS[0], value.ref())
    var generic: FT_Generic
        get() = FT_Generic(seg.asSlice(OFFSETS[1], FT_Generic.LAYOUT.byteSize()))
        set(value) {
            seg.asSlice(OFFSETS[1], FT_Generic.LAYOUT.byteSize()).copyFrom(value.ref())
        }
    var metrics: FT_Size_Metrics
        get() = FT_Size_Metrics(seg.asSlice(OFFSETS[2], FT_Size_Metrics.LAYOUT.byteSize()))
        set(value) {
            seg.asSlice(OFFSETS[2], FT_Size_Metrics.LAYOUT.byteSize()).copyFrom(value.ref())
        }
    var internal: FT_Size_Internal
        get() = FT_Size_Internal(seg.get(ADDRESS, OFFSETS[3]))
        set(value) = seg.set(ADDRESS, OFFSETS[3], value.ref())
}