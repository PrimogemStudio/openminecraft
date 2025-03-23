package com.primogemstudio.engine.bindings.freetype

import com.primogemstudio.engine.foreign.align
import com.primogemstudio.engine.foreign.cacheOffsets
import com.primogemstudio.engine.foreign.fetchString
import com.primogemstudio.engine.foreign.heap.HeapPointerArray
import com.primogemstudio.engine.foreign.heap.HeapStructArray
import com.primogemstudio.engine.foreign.heap.IHeapObject
import com.primogemstudio.engine.foreign.toCString
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.ADDRESS
import java.lang.foreign.ValueLayout.ADDRESS_UNALIGNED
import java.lang.foreign.ValueLayout.JAVA_INT
import java.lang.foreign.ValueLayout.JAVA_INT_UNALIGNED
import java.lang.foreign.ValueLayout.JAVA_LONG
import java.lang.foreign.ValueLayout.JAVA_LONG_UNALIGNED
import java.lang.foreign.ValueLayout.JAVA_SHORT
import java.lang.foreign.ValueLayout.JAVA_SHORT_UNALIGNED

class FT_Face(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            FT_Generic.LAYOUT,
            FT_BBox.LAYOUT,
            JAVA_SHORT_UNALIGNED,
            JAVA_SHORT_UNALIGNED,
            JAVA_SHORT_UNALIGNED,
            JAVA_SHORT_UNALIGNED,
            JAVA_SHORT_UNALIGNED,
            JAVA_SHORT_UNALIGNED,
            JAVA_SHORT_UNALIGNED,
            JAVA_SHORT_UNALIGNED,
            ADDRESS_UNALIGNED
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
    var familyName: String
        get() = seg.get(ADDRESS, OFFSETS[5]).fetchString()
        set(value) = seg.set(ADDRESS, OFFSETS[5], value.toCString())
    var styleName: String
        get() = seg.get(ADDRESS, OFFSETS[6]).fetchString()
        set(value) = seg.set(ADDRESS, OFFSETS[6], value.toCString())
    var availableSizes: HeapStructArray<FT_Bitmap_Size>
        get() = HeapStructArray(
            seg.get(JAVA_INT, OFFSETS[7]),
            seg.get(ADDRESS, OFFSETS[8]),
            FT_Bitmap_Size.LAYOUT
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[8], value.ref())
            seg.set(JAVA_INT, OFFSETS[7], value.length)
        }
    var charmaps: HeapPointerArray<FT_CharMap>
        get() = HeapPointerArray(
            seg.get(JAVA_INT, OFFSETS[9]),
            seg.get(ADDRESS, OFFSETS[10])
        ) { FT_CharMap(it.reinterpret(FT_CharMap.LAYOUT.byteSize())) }
        set(value) {
            seg.set(ADDRESS, OFFSETS[10], value.ref())
            seg.set(JAVA_INT, OFFSETS[9], value.length)
        }
    var generic: FT_Generic
        get() = FT_Generic(seg.asSlice(OFFSETS[11], FT_Generic.LAYOUT.byteSize()))
        set(value) {
            seg.asSlice(OFFSETS[11], FT_Generic.LAYOUT.byteSize()).copyFrom(value.ref())
        }
    var bbox: FT_BBox
        get() = FT_BBox(seg.asSlice(OFFSETS[12], FT_BBox.LAYOUT.byteSize()))
        set(value) {
            seg.asSlice(OFFSETS[12], FT_BBox.LAYOUT.byteSize()).copyFrom(value.ref())
        }
    var unitsPerEM: Short
        get() = seg.get(JAVA_SHORT, OFFSETS[13])
        set(value) = seg.set(JAVA_SHORT, OFFSETS[13], value)
    var ascender: Short
        get() = seg.get(JAVA_SHORT, OFFSETS[14])
        set(value) = seg.set(JAVA_SHORT, OFFSETS[14], value)
    var descender: Short
        get() = seg.get(JAVA_SHORT, OFFSETS[15])
        set(value) = seg.set(JAVA_SHORT, OFFSETS[15], value)
    var height: Short
        get() = seg.get(JAVA_SHORT, OFFSETS[16])
        set(value) = seg.set(JAVA_SHORT, OFFSETS[16], value)
    var maxAdvanceWidth: Short
        get() = seg.get(JAVA_SHORT, OFFSETS[17])
        set(value) = seg.set(JAVA_SHORT, OFFSETS[17], value)
    var maxAdvanceHeight: Short
        get() = seg.get(JAVA_SHORT, OFFSETS[18])
        set(value) = seg.set(JAVA_SHORT, OFFSETS[18], value)
    var underlinePosition: Short
        get() = seg.get(JAVA_SHORT, OFFSETS[19])
        set(value) = seg.set(JAVA_SHORT, OFFSETS[19], value)
    var underlineThickness: Short
        get() = seg.get(JAVA_SHORT, OFFSETS[20])
        set(value) = seg.set(JAVA_SHORT, OFFSETS[20], value)
    var glyph: FT_GlyphSlot
        get() = FT_GlyphSlot(seg.get(ADDRESS, OFFSETS[21]).reinterpret(FT_GlyphSlot.LAYOUT.byteSize()))
        set(value) = seg.set(ADDRESS, OFFSETS[21], value.ref())

    /*
    FT_Size           size;
    FT_CharMap        charmap;*/
}