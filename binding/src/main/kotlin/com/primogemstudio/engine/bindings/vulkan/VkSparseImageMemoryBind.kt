package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.core.VkDeviceMemory
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import org.joml.Vector3i
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

data class VkSparseImageMemoryBind(
    private val subresource: VkImageSubresource,
    private val offset: Vector3i,
    private val extent: Vector3i,
    private val memory: VkDeviceMemory,
    private val memoryOffset: Long,
    private val flags: Int
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            VkImageSubresource.LAYOUT,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        subresource.construct(seg.asSlice(OFFSETS[0], VkImageSubresource.LAYOUT.byteSize()))
        seg.set(JAVA_INT, OFFSETS[1], offset.x)
        seg.set(JAVA_INT, OFFSETS[2], offset.y)
        seg.set(JAVA_INT, OFFSETS[3], offset.z)
        seg.set(JAVA_INT, OFFSETS[4], extent.x)
        seg.set(JAVA_INT, OFFSETS[5], extent.y)
        seg.set(JAVA_INT, OFFSETS[6], extent.z)
        seg.set(ADDRESS, OFFSETS[7], memory.ref())
        seg.set(JAVA_LONG, OFFSETS[8], memoryOffset)
        seg.set(JAVA_INT, OFFSETS[9], flags)
    }
}