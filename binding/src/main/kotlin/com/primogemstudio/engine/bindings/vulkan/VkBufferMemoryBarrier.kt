package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_BUFFER_MEMORY_BARRIER
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkBufferMemoryBarrier(
    private val next: IStruct? = null,
    private val srcAccessMask: Int,
    private val dstAccessMask: Int,
    private val srcQueueFamilyIndex: Int,
    private val dstQueueFamilyIndex: Int,
    private val buffer: VkBuffer,
    private val offset: Long,
    private val size: Long
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
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
        seg.set(JAVA_INT, OFFSETS[0], VK_STRUCTURE_TYPE_BUFFER_MEMORY_BARRIER)
        seg.set(ADDRESS, OFFSETS[1], next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, OFFSETS[2], srcAccessMask)
        seg.set(JAVA_INT, OFFSETS[3], dstAccessMask)
        seg.set(JAVA_INT, OFFSETS[4], srcQueueFamilyIndex)
        seg.set(JAVA_INT, OFFSETS[5], dstQueueFamilyIndex)
        seg.set(ADDRESS, OFFSETS[6], buffer.ref())
        seg.set(JAVA_LONG, OFFSETS[7], offset)
        seg.set(JAVA_LONG, OFFSETS[8], size)
    }
}
