package com.primogemstudio.engine.bindings.harfbuzz

import com.primogemstudio.engine.foreign.NativeMethodCache.callFunc
import com.primogemstudio.engine.foreign.NativeMethodCache.callPointerFunc
import com.primogemstudio.engine.foreign.NativeMethodCache.callVoidFunc
import com.primogemstudio.engine.foreign.heap.HeapByteArray
import com.primogemstudio.engine.foreign.heap.HeapInt
import com.primogemstudio.engine.foreign.heap.HeapIntArray
import com.primogemstudio.engine.foreign.heap.HeapShortArray
import com.primogemstudio.engine.foreign.heap.HeapStructArray
import com.primogemstudio.engine.foreign.heap.IHeapObject
import com.primogemstudio.engine.foreign.toCString
import java.lang.foreign.MemorySegment

class hb_buffer_t(data: MemorySegment) : IHeapObject(data)
class hb_unicode_funcs_t(data: MemorySegment) : IHeapObject(data)

object HarfBuzzBufferFuncs {
    const val HB_BUFFER_CONTENT_TYPE_INVALID = 0
    const val HB_BUFFER_CONTENT_TYPE_UNICODE = 1
    const val HB_BUFFER_CONTENT_TYPE_GLYPHS = 2
    const val HB_BUFFER_FLAG_DEFAULT = 0
    const val HB_BUFFER_FLAG_BOT = 1
    const val HB_BUFFER_FLAG_EOT = 2
    const val HB_BUFFER_FLAG_PRESERVE_DEFAULT_IGNORABLES = 3
    const val HB_BUFFER_FLAG_REMOVE_DEFAULT_IGNORABLES = 4
    const val HB_BUFFER_FLAG_DO_NOT_INSERT_DOTTED_CIRCLE = 5
    const val HB_BUFFER_FLAG_VERIFY = 6
    const val HB_BUFFER_FLAG_PRODUCE_UNSAFE_TO_CONCAT = 7
    const val HB_BUFFER_FLAG_PRODUCE_SAFE_TO_INSERT_TATWEEL = 8
    const val HB_BUFFER_FLAG_DEFINED = 9
    const val HB_BUFFER_CLUSTER_LEVEL_MONOTONE_GRAPHEMES = 0
    const val HB_BUFFER_CLUSTER_LEVEL_MONOTONE_CHARACTERS = 1
    const val HB_BUFFER_CLUSTER_LEVEL_CHARACTERS = 2
    const val HB_BUFFER_CLUSTER_LEVEL_GRAPHEMES = 3
    const val HB_BUFFER_CLUSTER_LEVEL_DEFAULT = 4
    const val HB_GLYPH_FLAG_UNSAFE_TO_BREAK = 0
    const val HB_GLYPH_FLAG_UNSAFE_TO_CONCAT = 1
    const val HB_GLYPH_FLAG_SAFE_TO_INSERT_TATWEEL = 2
    const val HB_GLYPH_FLAG_DEFINED = 3
    fun hb_buffer_create(): hb_buffer_t = hb_buffer_t(callPointerFunc("hb_buffer_create"))
    fun hb_buffer_allocation_successful(buffer: hb_buffer_t): Boolean =
        callFunc("hb_buffer_allocation_successful", Boolean::class, buffer)

    fun hb_buffer_create(buffer: hb_buffer_t): hb_buffer_t = hb_buffer_t(callPointerFunc("hb_buffer_create", buffer))
    fun hb_buffer_get_empty(): hb_buffer_t = hb_buffer_t(callPointerFunc("hb_buffer_get_empty"))
    fun hb_buffer_reference(buffer: hb_buffer_t): hb_buffer_t =
        hb_buffer_t(callPointerFunc("hb_buffer_reference", buffer))

    fun hb_buffer_destroy(buffer: hb_buffer_t) = callVoidFunc("hb_buffer_destroy", buffer)
    fun hb_buffer_set_user_data(
        buffer: hb_buffer_t,
        key: MemorySegment,
        data: MemorySegment,
        destroy: hb_destroy_func_t,
        replace: Boolean
    ): Boolean = callFunc("hb_buffer_set_user_data", Boolean::class, buffer, key, data, destroy, replace)

    fun hb_buffer_get_user_data(buffer: hb_buffer_t, key: MemorySegment): MemorySegment =
        callPointerFunc("hb_buffer_get_user_data", buffer, key)

    fun hb_buffer_reset(buffer: hb_buffer_t) = callVoidFunc("hb_buffer_reset", buffer)
    fun hb_buffer_clear_contents(buffer: hb_buffer_t) = callVoidFunc("hb_buffer_clear_contents", buffer)
    fun hb_buffer_pre_allocate(buffer: hb_buffer_t, size: Int): Boolean =
        callFunc("hb_buffer_pre_allocate", Boolean::class, buffer, size)

    fun hb_buffer_add(buffer: hb_buffer_t, codepoint: Int, cluster: Int) =
        callVoidFunc("hb_buffer_add", buffer, codepoint, cluster)

    fun hb_buffer_add_codepoints(buffer: hb_buffer_t, text: HeapIntArray, itemOffset: Int, itemLength: Int) =
        callVoidFunc("hb_buffer_add_codepoints", buffer, text, text.length, itemOffset, itemLength)

    fun hb_buffer_add_utf32(buffer: hb_buffer_t, text: HeapIntArray, itemOffset: Int, itemLength: Int) =
        callVoidFunc("hb_buffer_add_utf32", buffer, text, text.length, itemOffset, itemLength)

    fun hb_buffer_add_utf16(buffer: hb_buffer_t, text: HeapShortArray, itemOffset: Int, itemLength: Int) =
        callVoidFunc("hb_buffer_add_utf16", buffer, text, text.length, itemOffset, itemLength)

    fun hb_buffer_add_utf8(buffer: hb_buffer_t, text: String, itemOffset: Int, itemLength: Int) =
        callVoidFunc("hb_buffer_add_utf8", buffer, text.toCString(), text.length, itemOffset, itemLength)

    fun hb_buffer_add_latin1(buffer: hb_buffer_t, text: HeapByteArray, itemOffset: Int, itemLength: Int) =
        callVoidFunc("hb_buffer_add_latin1", buffer, text, text.length, itemOffset, itemLength)

    fun hb_buffer_append(buffer: hb_buffer_t, source: hb_buffer_t, start: Int, end: Int) =
        callVoidFunc("hb_buffer_append", buffer, source, start, end)

    fun hb_buffer_set_content_type(buffer: hb_buffer_t, contentType: Int) =
        callVoidFunc("hb_buffer_set_content_type", buffer, contentType)

    fun hb_buffer_get_content_type(buffer: hb_buffer_t): Int =
        callFunc("hb_buffer_get_content_type", Int::class, buffer)

    fun hb_buffer_set_direction(buffer: hb_buffer_t, direction: Int) =
        callVoidFunc("hb_buffer_set_direction", buffer, direction)

    fun hb_buffer_get_direction(buffer: hb_buffer_t): Int = callFunc("hb_buffer_get_direction", Int::class, buffer)
    fun hb_buffer_set_script(buffer: hb_buffer_t, script: Int) = callVoidFunc("hb_buffer_set_script", buffer, script)
    fun hb_buffer_get_script(buffer: hb_buffer_t): Int = callFunc("hb_buffer_get_script", Int::class, buffer)
    fun hb_buffer_set_language(buffer: hb_buffer_t, language: hb_language_t) =
        callVoidFunc("hb_buffer_set_language", buffer, language)

    fun hb_buffer_get_language(buffer: hb_buffer_t): hb_language_t =
        hb_language_t(callPointerFunc("hb_buffer_get_language", buffer))

    fun hb_buffer_set_flags(buffer: hb_buffer_t, flags: Int) = callVoidFunc("hb_buffer_set_flags", buffer, flags)
    fun hb_buffer_get_flags(buffer: hb_buffer_t): Int = callFunc("hb_buffer_get_flags", Int::class, buffer)
    fun hb_buffer_set_cluster_level(buffer: hb_buffer_t, clusterLevel: Int) =
        callVoidFunc("hb_buffer_set_cluster_level", buffer, clusterLevel)

    fun hb_buffer_get_cluster_level(buffer: hb_buffer_t): Int =
        callFunc("hb_buffer_get_cluster_level", Int::class, buffer)

    fun hb_buffer_set_length(buffer: hb_buffer_t, length: Int): Boolean =
        callFunc("hb_buffer_set_length", Boolean::class, buffer, length)

    fun hb_buffer_get_length(buffer: hb_buffer_t): Int = callFunc("hb_buffer_get_length", Int::class, buffer)
    fun hb_buffer_set_segment_properties(buffer: hb_buffer_t, props: hb_segment_properties_t) =
        callVoidFunc("hb_buffer_set_segment_properties", buffer, props)

    fun hb_buffer_get_segment_properties(buffer: hb_buffer_t, props: hb_segment_properties_t) =
        callVoidFunc("hb_buffer_get_segment_properties", buffer, props)

    fun hb_buffer_guess_segment_properties(buffer: hb_buffer_t) =
        callVoidFunc("hb_buffer_guess_segment_properties", buffer)

    fun hb_buffer_set_unicode_funcs(buffer: hb_buffer_t, unicodeFuncs: hb_unicode_funcs_t) =
        callVoidFunc("hb_buffer_set_unicode_funcs", buffer, unicodeFuncs)

    fun hb_buffer_get_unicode_funcs(buffer: hb_buffer_t): hb_unicode_funcs_t =
        hb_unicode_funcs_t(callPointerFunc("hb_buffer_get_unicode_funcs", buffer))

    fun hb_buffer_get_glyph_infos(buffer: hb_buffer_t): HeapStructArray<hb_glyph_info_t> {
        val size = HeapInt()
        return HeapStructArray(
            size.value(),
            callPointerFunc(
                "hb_buffer_get_glyph_infos",
                buffer,
                size
            ).reinterpret(hb_glyph_info_t.LAYOUT.byteSize() * size.value()),
            hb_glyph_info_t.LAYOUT
        )
    }

    fun hb_glyph_info_get_glyph_flags(info: HeapStructArray<hb_glyph_info_t>): Int =
        callFunc("hb_glyph_info_get_glyph_flags", Int::class, info)

    fun hb_buffer_get_glyph_positions(buffer: hb_buffer_t): HeapStructArray<hb_glyph_position_t> {
        val size = HeapInt()
        return HeapStructArray(
            size.value(),
            callPointerFunc(
                "hb_buffer_get_glyph_positions",
                buffer,
                size
            ).reinterpret(hb_glyph_position_t.LAYOUT.byteSize() * size.value()),
            hb_glyph_position_t.LAYOUT
        )
    }

    fun hb_buffer_has_positions(buffer: hb_buffer_t): Boolean =
        callFunc("hb_buffer_has_positions", Boolean::class, buffer)

}