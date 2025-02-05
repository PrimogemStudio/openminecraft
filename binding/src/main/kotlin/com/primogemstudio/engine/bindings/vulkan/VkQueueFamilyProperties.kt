package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.heap.IHeapVar
import org.joml.Vector3i
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT

class VkQueueFamilyProperties(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val queueFlags: Int get() = seg.get(JAVA_INT, 0)
    val queueCount: Int get() = seg.get(JAVA_INT, 4)
    val timestampValidBits: Int get() = seg.get(JAVA_INT, 8)
    val minImageTransferGranularity: Vector3i
        get() = Vector3i(
            seg.get(JAVA_INT, 12),
            seg.get(JAVA_INT, 16),
            seg.get(JAVA_INT, 20)
        )
}