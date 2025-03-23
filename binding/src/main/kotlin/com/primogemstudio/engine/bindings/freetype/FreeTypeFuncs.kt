package com.primogemstudio.engine.bindings.freetype

import com.primogemstudio.engine.foreign.NativeMethodCache.callVoidFunc
import com.primogemstudio.engine.foreign.heap.HeapByte
import com.primogemstudio.engine.foreign.heap.HeapInt
import com.primogemstudio.engine.foreign.heap.HeapStructArray
import com.primogemstudio.engine.foreign.heap.IHeapObject
import com.primogemstudio.engine.foreign.toCString
import com.primogemstudio.engine.foreign.unbox
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.ADDRESS

typealias FT_Pos = Long

class FT_Library(data: MemorySegment) : IHeapObject(data)
class FT_Module(data: MemorySegment) : IHeapObject(data)

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

    const val FT_OPEN_MEMORY = 0x1
    const val FT_OPEN_STREAM = 0x2
    const val FT_OPEN_PATHNAME = 0x4
    const val FT_OPEN_DRIVER = 0x8
    const val FT_OPEN_PARAMS = 0x10

    const val FT_ENCODE_NONE = 0
    val FT_ENCODING_MS_SYMBOL = FT_ENC_TAG('s', 'y', 'm', 'b')
    val FT_ENCODING_UNICODE = FT_ENC_TAG('u', 'n', 'i', 'c')
    val FT_ENCODING_SJIS = FT_ENC_TAG('s', 'j', 'i', 's')
    val FT_ENCODING_PRC = FT_ENC_TAG('g', 'b', 0.toChar(), 0.toChar())
    val FT_ENCODING_BIG5 = FT_ENC_TAG('b', 'i', 'g', '5')
    val FT_ENCODING_WANSUNG = FT_ENC_TAG('w', 'a', 'n', 's')
    val FT_ENCODING_JOHAB = FT_ENC_TAG('j', 'o', 'h', 'a')
    val FT_ENCODING_GB2312 = FT_ENCODING_PRC
    val FT_ENCODING_MS_SJIS = FT_ENCODING_SJIS
    val FT_ENCODING_MS_GB2312 = FT_ENCODING_PRC
    val FT_ENCODING_MS_BIG5 = FT_ENCODING_BIG5
    val FT_ENCODING_MS_WANSUNG = FT_ENCODING_WANSUNG
    val FT_ENCODING_MS_JOHAB = FT_ENCODING_JOHAB
    val FT_ENCODING_ADOBE_STANDARD = FT_ENC_TAG('A', 'D', 'O', 'B')
    val FT_ENCODING_ADOBE_EXPERT = FT_ENC_TAG('A', 'D', 'B', 'E')
    val FT_ENCODING_ADOBE_CUSTOM = FT_ENC_TAG('A', 'D', 'B', 'C')
    val FT_ENCODING_ADOBE_LATIN_1 = FT_ENC_TAG('l', 'a', 't', '1')
    val FT_ENCODING_OLD_LATIN_2 = FT_ENC_TAG('l', 'a', 't', '2')
    val FT_ENCODING_APPLE_ROMAN = FT_ENC_TAG('a', 'r', 'm', 'n')

    fun FT_ENC_TAG(a: Char, b: Char, c: Char, d: Char): Int =
        a.code.shl(24).and(b.code.shl(16).and(c.code.shl(8).and(d.code)))

    fun FT_Init_FreeType(): FT_Library {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        callVoidFunc("FT_Init_FreeType", seg)
        return FT_Library(seg.unbox())
    }

    fun FT_Done_FreeType(library: FT_Library) = callVoidFunc("FT_Done_FreeType", library)
    fun FT_Library_Version(library: FT_Library, major: HeapInt, minor: HeapInt, patch: HeapInt) =
        callVoidFunc("FT_Library_Version", library, major, minor, patch)

    fun FT_New_Face(library: FT_Library, filepath: String, index: Long): FT_Face {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        callVoidFunc("FT_New_Face", library, filepath.toCString(), index, seg)
        return FT_Face(seg.unbox(FT_Face.LAYOUT))
    }

    fun FT_Done_Face(face: FT_Face) = callVoidFunc("FT_Done_Face", face)
    fun FT_Reference_Face(face: FT_Face) = callVoidFunc("FT_Reference_Face", face)
    fun FT_New_Memory_Face(library: FT_Library, file: HeapByte, size: Long, index: Long): FT_Face {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        callVoidFunc("FT_New_Memory_Face", library, file, size, index, seg)
        return FT_Face(seg.unbox(FT_Face.LAYOUT))
    }

    fun FT_Face_Properties(face: FT_Face, properties: HeapStructArray<FT_Parameter>) = callVoidFunc(
        "FT_Face_Properties",
        face,
        properties.length,
        properties
    )

    fun FT_Open_Face(library: FT_Library, args: FT_Open_Args, index: Long): FT_Face {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        callVoidFunc("FT_Open_Face", library, args, index, seg)
        return FT_Face(seg.unbox(FT_Face.LAYOUT))
    }

    fun FT_Attach_File(face: FT_Face, filepath: String) = callVoidFunc("FT_Attach_File", face, filepath.toCString())
    fun FT_Attach_Stream(file: FT_Face, parameters: FT_Open_Args) = callVoidFunc("FT_Attach_Stream", file, parameters)

}