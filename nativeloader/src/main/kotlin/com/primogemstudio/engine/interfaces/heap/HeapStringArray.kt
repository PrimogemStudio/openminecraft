package com.primogemstudio.engine.interfaces.heap

import com.primogemstudio.engine.interfaces.fetchCString
import java.lang.foreign.MemorySegment

class HeapStringArray(length: Int) : IHeapVar<Array<String>> {
    private val refArray = HeapRefArray(length)
    override fun ref(): MemorySegment = refArray.ref()
    override fun value(): Array<String> = refArray.value().map { it.fetchCString() }.toTypedArray()
}