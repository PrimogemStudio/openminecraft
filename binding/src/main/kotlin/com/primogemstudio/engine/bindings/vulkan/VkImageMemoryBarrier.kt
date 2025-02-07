package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_IMAGE_MEMORY_BARRIER
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkImageMemoryBarrier(
    private val next: IStruct? = null,
    private val srcAccessMask: Int,
    private val dstAccessMask: Int,
    private val oldLayout: Int,
    private val newLayout: Int,
    private val srcQueueFamilyIndex: Int,
    private val dstQueueFamilyIndex: Int,
    private val image: VkImage,
    private val subresourceRange: VkImageSubresourceRange
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            VkImageSubresourceRange.LAYOUT
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        subresourceRange.close()
        super.close()
    }

    override fun layout(): MemoryLayout = LAYOUT
    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, OFFSETS[0], VK_STRUCTURE_TYPE_IMAGE_MEMORY_BARRIER)
        seg.set(ADDRESS, OFFSETS[1], next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, OFFSETS[2], srcAccessMask)
        seg.set(JAVA_INT, OFFSETS[3], dstAccessMask)
        seg.set(JAVA_INT, OFFSETS[4], oldLayout)
        seg.set(JAVA_INT, OFFSETS[5], newLayout)
        seg.set(JAVA_INT, OFFSETS[6], srcQueueFamilyIndex)
        seg.set(JAVA_INT, OFFSETS[7], dstQueueFamilyIndex)
        seg.set(ADDRESS, OFFSETS[8], image.ref())
        subresourceRange.construct(seg.asSlice(OFFSETS[9], VkImageSubresourceRange.LAYOUT.byteSize()))
    }
}
