package com.primogemstudio.engine.interfaces.heap

import com.primogemstudio.engine.loader.Platform.is32bits
import com.primogemstudio.engine.loader.Platform.sizetLength
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class HeapRefArray(private val length: Int) : IHeapVar<Array<MemorySegment>> {
    private val seg = Arena.ofConfined().allocate(sizetLength() * length * 1L)
    override fun ref(): MemorySegment = seg

    override fun value(): Array<MemorySegment> {
        return (0..<length).toList().map {
            if (is32bits()) seg.get(JAVA_INT, sizetLength() * 4L * it).toLong()
            else seg.get(JAVA_LONG, sizetLength() * 8L * it)
        }.map { MemorySegment.ofAddress(it) }.toTypedArray()
    }
    fun setAddr(i: Int, addr: MemorySegment) = seg.set(ADDRESS, sizetLength() * i * 1L, addr)
    fun set(pointers: Array<MemorySegment>) {
        if (pointers.size > length) return
        for (i in pointers.indices) {
            seg.set(ADDRESS, sizetLength() * i * 1L, pointers[i])
        }
    }
}