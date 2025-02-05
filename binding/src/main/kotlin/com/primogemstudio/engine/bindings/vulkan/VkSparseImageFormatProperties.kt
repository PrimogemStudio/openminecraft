package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.heap.IHeapVar
import org.joml.Vector3i
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT

class VkSparseImageFormatProperties(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val aspectMask: Int get() = seg.get(JAVA_INT, 0)
    val imageGranularity: Vector3i get() = Vector3i(seg.get(JAVA_INT, 4), seg.get(JAVA_INT, 8), seg.get(JAVA_INT, 12))
    val flags: Int get() = seg.get(JAVA_INT, 16)
}