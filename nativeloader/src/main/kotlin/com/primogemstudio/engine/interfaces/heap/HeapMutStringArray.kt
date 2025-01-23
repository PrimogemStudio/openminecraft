package com.primogemstudio.engine.interfaces.heap

import com.primogemstudio.engine.interfaces.toCString
import com.primogemstudio.engine.loader.Platform.sizetLength
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.ADDRESS

class HeapMutStringArray(private val arr: Array<String>) : IHeapVar<Array<String>> {
    private val refArray = arr.map { it.toCString() }.let {
        val seg = Arena.ofConfined().allocate(sizetLength() * it.size * 1L)
        var i = 0
        it.forEach { s -> seg.set(ADDRESS, sizetLength() * i * 1L, s); i++ }
        seg
    }

    override fun ref(): MemorySegment = refArray
    override fun value(): Array<String> = arr
}