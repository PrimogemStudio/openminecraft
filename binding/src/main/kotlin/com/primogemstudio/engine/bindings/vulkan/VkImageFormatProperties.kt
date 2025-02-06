package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.IHeapVar
import org.joml.Vector3i
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkImageFormatProperties : IHeapVar<MemorySegment> {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_FLOAT, JAVA_FLOAT, JAVA_FLOAT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_LONG
        )
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    private val seg = Arena.ofAuto().allocate(LAYOUT)

    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val maxExtent: Vector3i
        get() = Vector3i(
            seg.get(JAVA_INT, OFFSETS[0]),
            seg.get(JAVA_INT, OFFSETS[1]),
            seg.get(JAVA_INT, OFFSETS[2])
        )
    val maxMipLevels: UInt get() = seg.get(JAVA_INT, OFFSETS[3]).toUInt()
    val maxArrayLayers: UInt get() = seg.get(JAVA_INT, OFFSETS[4]).toUInt()
    val sampleCounts: Int get() = seg.get(JAVA_INT, OFFSETS[5])
    val maxResourceSize: Long get() = seg.get(JAVA_LONG, OFFSETS[6])
}