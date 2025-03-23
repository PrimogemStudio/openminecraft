package com.primogemstudio.engine.bindings.harfbuzz

import com.primogemstudio.engine.foreign.NativeMethodCache.callPointerFunc
import com.primogemstudio.engine.foreign.NativeMethodCache.callVoidFunc
import com.primogemstudio.engine.foreign.NativeMethodCache.constructStub
import com.primogemstudio.engine.foreign.heap.HeapByteArray
import com.primogemstudio.engine.foreign.heap.IHeapObject
import com.primogemstudio.engine.foreign.toCString
import java.lang.foreign.MemorySegment

class hb_blob_t(data: MemorySegment) : IHeapObject(data)

object HarfBuzzFuncs {
    const val HB_MEMORY_MODE_DUPLICATE = 0
    const val HB_MEMORY_MODE_READONLY = 1
    const val HB_MEMORY_MODE_WRITABLE = 2
    const val HB_MEMORY_MODE_READONLY_MAY_MAKE_WRITABLE = 3

    fun hb_blob_create(data: HeapByteArray, mode: Int, userData: MemorySegment, destroy: hb_destroy_func_t): hb_blob_t =
        hb_blob_t(
            callPointerFunc(
                "hb_blob_create",
                data,
                data.length,
                mode,
                userData,
                constructStub(hb_destroy_func_t::class, destroy)
            )
        )

    fun hb_blob_create_or_fail(
        data: HeapByteArray,
        mode: Int,
        userData: MemorySegment,
        destroy: hb_destroy_func_t
    ): hb_blob_t =
        hb_blob_t(
            callPointerFunc(
                "hb_blob_create_or_fail",
                data,
                data.length,
                mode,
                userData,
                constructStub(hb_destroy_func_t::class, destroy)
            )
        )

    fun hb_blob_create_from_file(fileName: String): hb_blob_t =
        hb_blob_t(callPointerFunc("hb_blob_create_from_file", fileName.toCString()))

    fun hb_blob_create_from_file_or_fail(fileName: String): hb_blob_t =
        hb_blob_t(callPointerFunc("hb_blob_create_from_file_or_fail", fileName.toCString()))

    fun hb_blob_create_sub_blob(blob: hb_blob_t, offset: Int, length: Int): hb_blob_t =
        hb_blob_t(callPointerFunc("hb_blob_create_sub_blob", blob, offset, length))

    fun hb_blob_copy_writable_or_fail(data: hb_blob_t): hb_blob_t =
        hb_blob_t(callPointerFunc("hb_blob_copy_writable_or_fail", data))

    fun hb_blob_get_empty(): hb_blob_t = hb_blob_t(callPointerFunc("hb_blob_get_empty"))
    fun hb_blob_reference(blob: hb_blob_t): hb_blob_t = hb_blob_t(callPointerFunc("hb_blob_reference", blob))
    fun hb_blob_destroy(blob: hb_blob_t) = callVoidFunc("hb_blob_destroy", blob)

}