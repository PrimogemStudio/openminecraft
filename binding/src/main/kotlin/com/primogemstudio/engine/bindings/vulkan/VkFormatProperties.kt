package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.heap.IHeapVar
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT

class VkFormatProperties : IHeapVar<MemorySegment> {
    private val seg = Arena.ofAuto().allocate(
        MemoryLayout.structLayout(
            *Array<MemoryLayout>(3) { _ -> JAVA_INT }
        ))

    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val linearTilingFeatures: Int get() = seg.get(JAVA_INT, 0)
    val optimalTilingFeatures: Int get() = seg.get(JAVA_INT, 4)
    val bufferFeatures: Int get() = seg.get(JAVA_INT, 8)
}