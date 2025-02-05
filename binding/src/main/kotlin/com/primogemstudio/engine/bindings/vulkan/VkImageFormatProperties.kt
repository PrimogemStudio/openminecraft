package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.heap.IHeapVar
import org.joml.Vector3i
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkImageFormatProperties : IHeapVar<MemorySegment> {
    private val seg = Arena.ofAuto().allocate(
        MemoryLayout.structLayout(
            JAVA_FLOAT, JAVA_FLOAT, JAVA_FLOAT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_LONG
        )
    )

    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val maxExtent: Vector3i get() = Vector3i(seg.get(JAVA_INT, 0), seg.get(JAVA_INT, 4), seg.get(JAVA_INT, 8))
    val maxMipLevels: UInt get() = seg.get(JAVA_INT, 12).toUInt()
    val maxArrayLayers: UInt get() = seg.get(JAVA_INT, 16).toUInt()
    val sampleCounts: Int get() = seg.get(JAVA_INT, 20)
    val maxResourceSize: Long get() = seg.get(JAVA_LONG, 24)
}