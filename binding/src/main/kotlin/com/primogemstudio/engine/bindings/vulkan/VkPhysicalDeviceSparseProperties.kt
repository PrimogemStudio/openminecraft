package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.heap.IHeapVar
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT

class VkPhysicalDeviceSparseProperties(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val residencyStandard2DBlockShape: Boolean get() = seg.get(JAVA_INT, 0) != 0
    val residencyStandard2DMultisampleBlockShape: Boolean get() = seg.get(JAVA_INT, 4) != 0
    val residencyStandard3DBlockShape: Boolean get() = seg.get(JAVA_INT, 8) != 0
    val residencyAlignedMipSize: Boolean get() = seg.get(JAVA_INT, 12) != 0
    val residencyNonResidentStrict: Boolean get() = seg.get(JAVA_INT, 16) != 0
}