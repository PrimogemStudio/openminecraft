package com.primogemstudio.engine.bindings.harfbuzz

import com.primogemstudio.engine.foreign.heap.IHeapObject
import java.lang.foreign.MemorySegment

class hb_language_t(data: MemorySegment) : IHeapObject(data)

object HarfBuzzCommonFuncs {
}