package com.primogemstudio.engine.bindings.freetype

import com.primogemstudio.engine.foreign.NativeMethodCache.callFunc
import com.primogemstudio.engine.foreign.NativeMethodCache.callVoidFunc
import com.primogemstudio.engine.foreign.heap.HeapByte
import com.primogemstudio.engine.foreign.heap.HeapInt
import com.primogemstudio.engine.foreign.heap.HeapStructArray
import com.primogemstudio.engine.foreign.heap.IHeapObject
import com.primogemstudio.engine.foreign.toCString
import com.primogemstudio.engine.foreign.unbox
import com.primogemstudio.engine.types.Result
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.ADDRESS

typealias FT_Pos = Long
typealias FT_Fixed = Long
typealias FT_F26Dot6 = Long

class FT_Library(data: MemorySegment) : IHeapObject(data)
class FT_Module(data: MemorySegment) : IHeapObject(data)
class FT_SubGlyph(data: MemorySegment) : IHeapObject(data)
class FT_Slot_Internal(data: MemorySegment) : IHeapObject(data)
class FT_Size_Internal(data: MemorySegment) : IHeapObject(data)

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

    const val FT_GLYPH_FORMAT_NONE = 0L
    val FT_GLYPH_FORMAT_COMPOSITE = FT_IMAGE_TAG('c', 'o', 'm', 'p')
    val FT_GLYPH_FORMAT_BITMAP = FT_IMAGE_TAG('b', 'i', 't', 's')
    val FT_GLYPH_FORMAT_OUTLINE = FT_IMAGE_TAG('o', 'u', 't', 'l')
    val FT_GLYPH_FORMAT_PLOTTER = FT_IMAGE_TAG('p', 'l', 'o', 't')
    val FT_GLYPH_FORMAT_SVG = FT_IMAGE_TAG('S', 'V', 'G', ' ')

    const val FT_SIZE_REQUEST_TYPE_NOMINAL = 0
    const val FT_SIZE_REQUEST_TYPE_REAL_DIM = 1
    const val FT_SIZE_REQUEST_TYPE_BBOX = 2
    const val FT_SIZE_REQUEST_TYPE_CELL = 3
    const val FT_SIZE_REQUEST_TYPE_SCALES = 4
    const val FT_SIZE_REQUEST_TYPE_MAX = 5

    const val FT_ERROR_OK = 0x00
    const val FT_ERROR_CANNOT_OPEN_RESOURCE = 0x01
    const val FT_ERROR_UNKNOWN_FILE_FORMAT = 0x02
    const val FT_ERROR_INVALID_FILE_FORMAT = 0x03
    const val FT_ERROR_INVALID_VERSION = 0x04
    const val FT_ERROR_LOWER_MODULE_VERSION = 0x05
    const val FT_ERROR_INVALID_ARGUMENT = 0x06
    const val FT_ERROR_UNIMPLEMENTED_FEATURE = 0x07
    const val FT_ERROR_INVALID_TABLE = 0x08
    const val FT_ERROR_INVALID_OFFSET = 0x09
    const val FT_ERROR_ARRAY_TOO_LARGE = 0x0A
    const val FT_ERROR_MISSING_MODULE = 0x0B
    const val FT_ERROR_MISSING_PROPERTY = 0x0C
    const val FT_ERROR_INVALID_GLYPH_INDEX = 0x10
    const val FT_ERROR_INVALID_CHARACTER_CODE = 0x11
    const val FT_ERROR_INVALID_GLYPH_FORMAT = 0x12
    const val FT_ERROR_CANNOT_RENDER_GLYPH = 0x13
    const val FT_ERROR_INVALID_OUTLINE = 0x14
    const val FT_ERROR_INVALID_COMPOSITE = 0x15
    const val FT_ERROR_TOO_MANY_HINTS = 0x16
    const val FT_ERROR_INVALID_PIXEL_SIZE = 0x17
    const val FT_ERROR_INVALID_SVG_DOCUMENT = 0x18
    const val FT_ERROR_INVALID_HANDLE = 0x20
    const val FT_ERROR_INVALID_LIBRARY_HANDLE = 0x21
    const val FT_ERROR_INVALID_DRIVER_HANDLE = 0x22
    const val FT_ERROR_INVALID_FACE_HANDLE = 0x23
    const val FT_ERROR_INVALID_SIZE_HANDLE = 0x24
    const val FT_ERROR_INVALID_SLOT_HANDLE = 0x25
    const val FT_ERROR_INVALID_CHARMAP_HANDLE = 0x26
    const val FT_ERROR_INVALID_CACHE_HANDLE = 0x27
    const val FT_ERROR_INVALID_STREAM_HANDLE = 0x28
    const val FT_ERROR_TOO_MANY_DRIVERS = 0x30
    const val FT_ERROR_TOO_MANY_EXTENSIONS = 0x31
    const val FT_ERROR_OUT_OF_MEMORY = 0x40
    const val FT_ERROR_UNLISTED_OBJECT = 0x41
    const val FT_ERROR_CANNOT_OPEN_STREAM = 0x51
    const val FT_ERROR_INVALID_STREAM_SEEK = 0x52
    const val FT_ERROR_INVALID_STREAM_SKIP = 0x53
    const val FT_ERROR_INVALID_STREAM_READ = 0x54
    const val FT_ERROR_INVALID_STREAM_OPERATION = 0x55
    const val FT_ERROR_INVALID_FRAME_OPERATION = 0x56
    const val FT_ERROR_NESTED_FRAME_ACCESS = 0x57
    const val FT_ERROR_INVALID_FRAME_READ = 0x58
    const val FT_ERROR_RASTER_UNINITIALIZED = 0x60
    const val FT_ERROR_RASTER_CORRUPTED = 0x61
    const val FT_ERROR_RASTER_OVERFLOW = 0x62
    const val FT_ERROR_RASTER_NEGATIVE_HEIGHT = 0x63
    const val FT_ERROR_TOO_MANY_CACHES = 0x70
    const val FT_ERROR_INVALID_OPCODE = 0x80
    const val FT_ERROR_TOO_FEW_ARGUMENTS = 0x81
    const val FT_ERROR_STACK_OVERFLOW = 0x82
    const val FT_ERROR_CODE_OVERFLOW = 0x83
    const val FT_ERROR_BAD_ARGUMENT = 0x84
    const val FT_ERROR_DIVIDE_BY_ZERO = 0x85
    const val FT_ERROR_INVALID_REFERENCE = 0x86
    const val FT_ERROR_DEBUG_OPCODE = 0x87
    const val FT_ERROR_ENDF_IN_EXEC_STREAM = 0x88
    const val FT_ERROR_NESTED_DEFS = 0x89
    const val FT_ERROR_INVALID_CODERANGE = 0x8A
    const val FT_ERROR_EXECUTION_TOO_LONG = 0x8B
    const val FT_ERROR_TOO_MANY_FUNCTION_DEFS = 0x8C
    const val FT_ERROR_TOO_MANY_INSTRUCTION_DEFS = 0x8D
    const val FT_ERROR_TABLE_MISSING = 0x8E
    const val FT_ERROR_HORIZ_HEADER_MISSING = 0x8F
    const val FT_ERROR_LOCATIONS_MISSING = 0x90
    const val FT_ERROR_NAME_TABLE_MISSING = 0x91
    const val FT_ERROR_CMAP_TABLE_MISSING = 0x92
    const val FT_ERROR_HMTX_TABLE_MISSING = 0x93
    const val FT_ERROR_POST_TABLE_MISSING = 0x94
    const val FT_ERROR_INVALID_HORIZ_METRICS = 0x95
    const val FT_ERROR_INVALID_CHARMAP_FORMAT = 0x96
    const val FT_ERROR_INVALID_PPEM = 0x97
    const val FT_ERROR_INVALID_VERT_METRICS = 0x98
    const val FT_ERROR_COULD_NOT_FIND_CONTEXT = 0x99
    const val FT_ERROR_INVALID_POST_TABLE_FORMAT = 0x9A
    const val FT_ERROR_INVALID_POST_TABLE = 0x9B
    const val FT_ERROR_DEF_IN_GLYF_BYTECODE = 0x9C
    const val FT_ERROR_MISSING_BITMAP = 0x9D
    const val FT_ERROR_MISSING_SVG_HOOKS = 0x9E
    const val FT_ERROR_SYNTAX_ERROR = 0xA0
    const val FT_ERROR_STACK_UNDERFLOW = 0xA1
    const val FT_ERROR_IGNORE = 0xA2
    const val FT_ERROR_NO_UNICODE_GLYPH_NAME = 0xA3
    const val FT_ERROR_GLYPH_TOO_BIG = 0xA4
    const val FT_ERROR_MISSING_STARTFONT_FIELD = 0xB0
    const val FT_ERROR_MISSING_FONT_FIELD = 0xB1
    const val FT_ERROR_MISSING_SIZE_FIELD = 0xB2
    const val FT_ERROR_MISSING_FONTBOUNDINGBOX_FIELD = 0xB3
    const val FT_ERROR_MISSING_CHARS_FIELD = 0xB4
    const val FT_ERROR_MISSING_STARTCHAR_FIELD = 0xB5
    const val FT_ERROR_MISSING_ENCODING_FIELD = 0xB6
    const val FT_ERROR_MISSING_BBX_FIELD = 0xB7
    const val FT_ERROR_BBX_TOO_BIG = 0xB8
    const val FT_ERROR_CORRUPTED_FONT_HEADER = 0xB9
    const val FT_ERROR_CORRUPTED_FONT_GLYPHS = 0xBA

    fun FT_ENC_TAG(a: Char, b: Char, c: Char, d: Char): Int =
        a.code.shl(24).and(b.code.shl(16).and(c.code.shl(8).and(d.code)))
    fun FT_IMAGE_TAG(a: Char, b: Char, c: Char, d: Char): Long =
        a.code.shl(24).and(b.code.shl(16).and(c.code.shl(8).and(d.code))).toLong()

    fun FT_Init_FreeType(): Result<FT_Library, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc("FT_Init_FreeType", Int::class, seg)
        return if (retCode == 0) Result.success(FT_Library(seg.unbox())) else Result.fail(retCode)
    }

    fun FT_Done_FreeType(library: FT_Library): Int = callFunc("FT_Done_FreeType", Int::class, library)
    fun FT_Library_Version(library: FT_Library, major: HeapInt, minor: HeapInt, patch: HeapInt) =
        callVoidFunc("FT_Library_Version", library, major, minor, patch)

    fun FT_New_Face(library: FT_Library, filepath: String, index: Long): Result<FT_Face, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc("FT_New_Face", Int::class, library, filepath.toCString(), index, seg)
        return if (retCode == 0) Result.success(FT_Face(seg.unbox(FT_Face.LAYOUT))) else Result.fail(retCode)
    }

    fun FT_Done_Face(face: FT_Face): Int = callFunc("FT_Done_Face", Int::class, face)
    fun FT_Reference_Face(face: FT_Face): Int = callFunc("FT_Reference_Face", Int::class, face)
    fun FT_New_Memory_Face(library: FT_Library, file: HeapByte, size: Long, index: Long): Result<FT_Face, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc("FT_New_Memory_Face", Int::class, library, file, size, index, seg)
        return if (retCode == 0) Result.success(FT_Face(seg.unbox(FT_Face.LAYOUT))) else Result.fail(retCode)
    }

    fun FT_Face_Properties(face: FT_Face, properties: HeapStructArray<FT_Parameter>): Int = callFunc(
        "FT_Face_Properties",
        Int::class,
        face,
        properties.length,
        properties
    )

    fun FT_Open_Face(library: FT_Library, args: FT_Open_Args, index: Long): Result<FT_Face, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc("FT_Open_Face", Int::class, library, args, index, seg)
        return if (retCode == 0) Result.success(FT_Face(seg.unbox(FT_Face.LAYOUT))) else Result.fail(retCode)
    }

    fun FT_Attach_File(face: FT_Face, filepath: String): Int =
        callFunc("FT_Attach_File", Int::class, face, filepath.toCString())

    fun FT_Attach_Stream(file: FT_Face, parameters: FT_Open_Args): Int =
        callFunc("FT_Attach_Stream", Int::class, file, parameters)

    fun FT_HAS_HORIZONTAL(face: FT_Face): Boolean = face.faceFlags.and(FT_FACE_FLAG_HORIZONTAL) != 0L
    fun FT_HAS_VERTICAL(face: FT_Face): Boolean = face.faceFlags.and(FT_FACE_FLAG_VERTICAL) != 0L
    fun FT_HAS_KERNING(face: FT_Face): Boolean = face.faceFlags.and(FT_FACE_FLAG_KERNING) != 0L
    fun FT_HAS_FIXED_SIZES(face: FT_Face): Boolean = face.faceFlags.and(FT_FACE_FLAG_FIXED_SIZES) != 0L
    fun FT_HAS_GLYPH_NAMES(face: FT_Face): Boolean = face.faceFlags.and(FT_FACE_FLAG_GLYPH_NAMES) != 0L
    fun FT_HAS_COLOR(face: FT_Face): Boolean = face.faceFlags.and(FT_FACE_FLAG_COLOR) != 0L
    fun FT_HAS_MULTIPLE_MASTERS(face: FT_Face): Boolean = face.faceFlags.and(FT_FACE_FLAG_MULTIPLE_MASTERS) != 0L
    fun FT_HAS_SVG(face: FT_Face): Boolean = face.faceFlags.and(FT_FACE_FLAG_SVG) != 0L
    fun FT_HAS_SBIX(face: FT_Face): Boolean = face.faceFlags.and(FT_FACE_FLAG_SBIX) != 0L
    fun FT_HAS_SBIX_OVERLAY(face: FT_Face): Boolean = face.faceFlags.and(FT_FACE_FLAG_SBIX_OVERLAY) != 0L
    fun FT_IS_SFNT(face: FT_Face): Boolean = face.faceFlags.and(FT_FACE_FLAG_SFNT) != 0L
    fun FT_IS_SCALABLE(face: FT_Face): Boolean = face.faceFlags.and(FT_FACE_FLAG_SCALABLE) != 0L
    fun FT_IS_FIXED_WIDTH(face: FT_Face): Boolean = face.faceFlags.and(FT_FACE_FLAG_FIXED_WIDTH) != 0L
    fun FT_IS_CID_KEYED(face: FT_Face): Boolean = face.faceFlags.and(FT_FACE_FLAG_CID_KEYED) != 0L
    fun FT_IS_TRICKY(face: FT_Face): Boolean = face.faceFlags.and(FT_FACE_FLAG_TRICKY) != 0L
    fun FT_IS_NAMED_INSTANCE(face: FT_Face): Boolean = face.faceFlags.and(0x7FFF0000L) != 0L
    fun FT_IS_VARIATION(face: FT_Face): Boolean = face.faceFlags.and(FT_FACE_FLAG_VARIATION) != 0L

    fun FT_Set_Char_Size(charWidth: FT_F26Dot6, charHeight: FT_F26Dot6, horzResolution: Int, vertResolution: Int): Int =
        callFunc("FT_Set_Char_Size", Int::class, charWidth, charHeight, horzResolution, vertResolution)

    fun FT_Set_Pixel_Sizes(face: FT_Face, pixelWidth: Int, pixelHeight: Int): Int =
        callFunc("FT_Set_Pixel_Sizes", Int::class, face, pixelWidth, pixelHeight)

    fun FT_Request_Size(face: FT_Face, size: FT_Size_Request): Int = callFunc("FT_Request_Size", Int::class, face, size)
    fun FT_Select_Size(face: FT_Face, strikeIndex: Int): Int = callFunc("FT_Select_Size", Int::class, face, strikeIndex)
    fun FT_Set_Transform(face: FT_Face, matrix: FT_Matrix, delta: FT_Vector) =
        callVoidFunc("FT_Set_Transform", face, matrix, delta)

    fun FT_Get_Transform(face: FT_Face, matrix: FT_Matrix, delta: FT_Vector) =
        callVoidFunc("FT_Get_Transform", face, matrix, delta)
}