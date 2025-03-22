package com.primogemstudio.engine.bindings.freetype

import com.primogemstudio.engine.foreign.NativeMethodCache.callVoidFunc
import com.primogemstudio.engine.foreign.heap.HeapInt
import com.primogemstudio.engine.foreign.heap.IHeapObject
import com.primogemstudio.engine.foreign.toCString
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.ADDRESS

class FT_Library(data: MemorySegment) : IHeapObject(data)
class FT_Face(data: MemorySegment) : IHeapObject(data)

object FreeTypeFuncs {
    const val FT_FACE_FLAG_SCALABLE = 1L.shl(0)
    const val FT_FACE_FLAG_FIXED_SIZES = 1L.shl(1)
    const val FT_FACE_FLAG_FIXED_WIDTH = 1L.shl(2)
    const val FT_FACE_FLAG_SFNT = 1L.shl(3)
    const val FT_FACE_FLAG_HORIZONTAL = 1L.shl(4)
    const val FT_FACE_FLAG_VERTICAL = 1L.shl(5)
    const val FT_FACE_FLAG_KERNING = 1L.shl(6)
    const val FT_FACE_FLAG_FAST_GLYPHS = 1L.shl(7)
    const val FT_FACE_FLAG_MULTIPLE_MASTERS = 1L.shl(8)
    const val FT_FACE_FLAG_GLYPH_NAMES = 1L.shl(9)
    const val FT_FACE_FLAG_EXTERNAL_STREAM = 1L.shl(10)
    const val FT_FACE_FLAG_HINTER = 1L.shl(11)
    const val FT_FACE_FLAG_CID_KEYED = 1L.shl(12)
    const val FT_FACE_FLAG_TRICKY = 1L.shl(13)
    const val FT_FACE_FLAG_COLOR = 1L.shl(14)
    const val FT_FACE_FLAG_VARIATION = 1L.shl(15)
    const val FT_FACE_FLAG_SVG = 1L.shl(16)
    const val FT_FACE_FLAG_SBIX = 1L.shl(17)
    const val FT_FACE_FLAG_SBIX_OVERLAY = 1L.shl(18)

    const val FT_STYLE_FLAG_ITALIC = 1
    const val FT_STYLE_FLAG_BOLD = 2

    fun FT_Init_FreeType(): FT_Library {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        callVoidFunc("FT_Init_FreeType", seg)
        return FT_Library(seg.get(ADDRESS, 0))
    }

    fun FT_Done_FreeType(library: FT_Library) = callVoidFunc("FT_Done_FreeType", library)
    fun FT_Library_Version(library: FT_Library, major: HeapInt, minor: HeapInt, patch: HeapInt) =
        callVoidFunc("FT_Library_Version", library, major, minor, patch)

    fun FT_New_Face(library: FT_Library, filepath: String, index: Long): FT_Face {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        callVoidFunc("FT_New_Face", library, filepath.toCString(), index, seg)
        return FT_Face(seg.get(ADDRESS, 0))
    }
}