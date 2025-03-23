package com.primogemstudio.engine.bindings.freetype

import com.primogemstudio.engine.foreign.align
import com.primogemstudio.engine.foreign.cacheOffsets
import com.primogemstudio.engine.foreign.heap.HeapPointerArray
import com.primogemstudio.engine.foreign.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.ADDRESS
import java.lang.foreign.ValueLayout.ADDRESS_UNALIGNED
import java.lang.foreign.ValueLayout.JAVA_INT
import java.lang.foreign.ValueLayout.JAVA_INT_UNALIGNED
import java.lang.foreign.ValueLayout.JAVA_LONG
import java.lang.foreign.ValueLayout.JAVA_LONG_UNALIGNED

class FT_GlyphSlot(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            FT_Generic.LAYOUT,
            FT_Glyph_Metrics.LAYOUT,
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            FT_Vector.LAYOUT,
            JAVA_LONG_UNALIGNED,
            FT_Bitmap.LAYOUT,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            FT_Outline.LAYOUT,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var library: FT_Library
        get() = FT_Library(seg.get(ADDRESS, OFFSETS[0]))
        set(value) = seg.set(ADDRESS, OFFSETS[0], value.ref())
    var face: FT_Face
        get() = FT_Face(seg.get(ADDRESS, OFFSETS[1]).reinterpret(FT_Face.LAYOUT.byteSize()))
        set(value) = seg.set(ADDRESS, OFFSETS[1], value.ref())
    var next: FT_GlyphSlot
        get() = FT_GlyphSlot(seg.get(ADDRESS, OFFSETS[2]).reinterpret(LAYOUT.byteSize()))
        set(value) = seg.set(ADDRESS, OFFSETS[2], value.ref())
    var glyphIndex: Int
        get() = seg.get(JAVA_INT, OFFSETS[3])
        set(value) = seg.set(JAVA_INT, OFFSETS[3], value)
    var generic: FT_Generic
        get() = FT_Generic(seg.asSlice(OFFSETS[4], FT_Generic.LAYOUT.byteSize()))
        set(value) {
            seg.asSlice(OFFSETS[4], FT_Generic.LAYOUT.byteSize()).copyFrom(value.ref())
        }
    var metrics: FT_Glyph_Metrics
        get() = FT_Glyph_Metrics(seg.asSlice(OFFSETS[5], FT_Glyph_Metrics.LAYOUT.byteSize()))
        set(value) {
            seg.asSlice(OFFSETS[5], FT_Glyph_Metrics.LAYOUT.byteSize()).copyFrom(value.ref())
        }
    var linearHoriAdvance: FT_Fixed
        get() = seg.get(JAVA_LONG, OFFSETS[6])
        set(value) = seg.set(JAVA_LONG, OFFSETS[6], value)
    var linearVertAdvance: FT_Fixed
        get() = seg.get(JAVA_LONG, OFFSETS[7])
        set(value) = seg.set(JAVA_LONG, OFFSETS[7], value)
    var advance: FT_Vector
        get() = FT_Vector(seg.asSlice(OFFSETS[8], FT_Vector.LAYOUT.byteSize()))
        set(value) {
            seg.asSlice(OFFSETS[8], FT_Vector.LAYOUT.byteSize()).copyFrom(value.ref())
        }
    var format: Long
        get() = seg.get(JAVA_LONG, OFFSETS[9])
        set(value) = seg.set(JAVA_LONG, OFFSETS[9], value)
    var bitmap: FT_Bitmap
        get() = FT_Bitmap(seg.asSlice(OFFSETS[10], FT_Bitmap.LAYOUT.byteSize()))
        set(value) {
            seg.asSlice(OFFSETS[10], FT_Bitmap.LAYOUT.byteSize()).copyFrom(value.ref())
        }
    var bitmapLeft: Int
        get() = seg.get(JAVA_INT, OFFSETS[11])
        set(value) = seg.set(JAVA_INT, OFFSETS[11], value)
    var bitmapTop: Int
        get() = seg.get(JAVA_INT, OFFSETS[12])
        set(value) = seg.set(JAVA_INT, OFFSETS[12], value)
    var outline: FT_Outline
        get() = FT_Outline(seg.asSlice(OFFSETS[13], FT_Outline.LAYOUT.byteSize()))
        set(value) {
            seg.asSlice(OFFSETS[13], FT_Outline.LAYOUT.byteSize()).copyFrom(value.ref())
        }
    var subglyphs: HeapPointerArray<FT_SubGlyph>
        get() = HeapPointerArray(
            seg.get(JAVA_INT, OFFSETS[14]),
            seg.get(ADDRESS, OFFSETS[15])
        ) { FT_SubGlyph(it) }
        set(value) {
            seg.set(ADDRESS, OFFSETS[15], value.ref())
            seg.set(JAVA_INT, OFFSETS[14], value.length)
        }
    var controlData: MemorySegment
        get() = seg.get(ADDRESS, OFFSETS[16])
        set(value) = seg.set(ADDRESS, OFFSETS[16], value)
    var controlLen: Long
        get() = seg.get(JAVA_LONG, OFFSETS[17])
        set(value) = seg.set(JAVA_LONG, OFFSETS[17], value)
    var lsbDelta: FT_Pos
        get() = seg.get(JAVA_LONG, OFFSETS[18])
        set(value) = seg.set(JAVA_LONG, OFFSETS[18], value)
    var rsbDelta: FT_Pos
        get() = seg.get(JAVA_LONG, OFFSETS[19])
        set(value) = seg.set(JAVA_LONG, OFFSETS[19], value)
    var other: MemorySegment
        get() = seg.get(ADDRESS, OFFSETS[20])
        set(value) = seg.set(ADDRESS, OFFSETS[20], value)
    var internal: FT_Slot_Internal
        get() = FT_Slot_Internal(seg.get(ADDRESS, OFFSETS[21]))
        set(value) = seg.set(ADDRESS, OFFSETS[21], value.ref())
}