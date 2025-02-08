package com.primogemstudio.engine.interfaces.heap

import java.lang.foreign.MemorySegment

abstract class IHeapObject(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg
}