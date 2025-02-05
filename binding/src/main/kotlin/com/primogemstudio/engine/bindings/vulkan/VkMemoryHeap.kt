package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.heap.IHeapVar
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT
import java.lang.foreign.ValueLayout.JAVA_LONG

class VkMemoryHeap(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val size: Long get() = seg.get(JAVA_LONG, 0)
    val flags: Int get() = seg.get(JAVA_INT, 8)
}