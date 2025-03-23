package com.primogemstudio.engine.bindings.harfbuzz

import com.primogemstudio.engine.foreign.heap.IHeapObject
import java.lang.foreign.MemorySegment

class hb_blob_t(data: MemorySegment) : IHeapObject(data)

object HarfBuzzFuncs {
    const val HB_MEMORY_MODE_DUPLICATE = 0
    const val HB_MEMORY_MODE_READONLY = 1
    const val HB_MEMORY_MODE_WRITABLE = 2
    const val HB_MEMORY_MODE_READONLY_MAY_MAKE_WRITABLE = 3

}