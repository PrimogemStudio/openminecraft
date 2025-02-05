package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.heap.IHeapVar
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_LONG

class VkSubresourceLayout(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val offset: Long get() = seg.get(JAVA_LONG, 0)
    val size: Long get() = seg.get(JAVA_LONG, 8)
    val rowPitch: Long get() = seg.get(JAVA_LONG, 16)
    val arrayPitch: Long get() = seg.get(JAVA_LONG, 24)
    val depthPitch: Long get() = seg.get(JAVA_LONG, 32)
}