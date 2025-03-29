package com.primogemstudio.engine.bindings.harfbuzz

import com.primogemstudio.engine.foreign.NativeMethodCache.callFunc
import com.primogemstudio.engine.foreign.NativeMethodCache.callPointerFunc
import com.primogemstudio.engine.foreign.NativeMethodCache.callVoidFunc
import com.primogemstudio.engine.foreign.NativeMethodCache.constructStub
import com.primogemstudio.engine.foreign.heap.HeapByteArray
import com.primogemstudio.engine.foreign.heap.HeapInt
import com.primogemstudio.engine.foreign.heap.IHeapObject
import com.primogemstudio.engine.foreign.toCString
import java.lang.foreign.MemorySegment

class hb_blob_t(data: MemorySegment) : IHeapObject(data)

object HarfBuzzBlobFuncs {
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
    fun hb_blob_set_user_data(
        blob: hb_blob_t,
        key: MemorySegment,
        data: MemorySegment,
        destroy: hb_destroy_func_t,
        replace: Boolean
    ): Boolean = callFunc(
        "hb_blob_set_user_data",
        Int::class,
        blob,
        key,
        data,
        constructStub(hb_destroy_func_t::class, destroy),
        if (replace) 1 else 0
    ) != 0

    fun hb_blob_get_user_data(blob: hb_blob_t, key: MemorySegment): MemorySegment =
        callPointerFunc("hb_blob_get_user_data", blob, key)

    fun hb_blob_make_immutable(blob: hb_blob_t) = callVoidFunc("hb_blob_make_immutable", blob)
    fun hb_blob_is_immutable(blob: hb_blob_t): Boolean = callFunc("hb_blob_is_immutable", Int::class, blob) != 0
    fun hb_blob_get_data(blob: hb_blob_t): HeapByteArray {
        var length = HeapInt()
        var data = callPointerFunc("hb_blob_get_data", blob, length)
        return HeapByteArray(length.value(), data)
    }

    fun hb_blob_get_data_writable(blob: hb_blob_t): HeapByteArray {
        var length = HeapInt()
        var data = callPointerFunc("hb_blob_get_data_writable", blob, length)
        return HeapByteArray(length.value(), data)
    }

    fun hb_blob_get_length(blob: hb_blob_t): Int = callFunc("hb_blob_get_length", Int::class, blob)
}