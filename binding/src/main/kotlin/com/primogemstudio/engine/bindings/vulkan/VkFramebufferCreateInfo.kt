package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.core.Vk10Funcs.VK_STRUCTURE_TYPE_FRAMEBUFFER_CREATE_INFO
import com.primogemstudio.engine.bindings.vulkan.core.VkImageView
import com.primogemstudio.engine.bindings.vulkan.core.VkRenderPass
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.HeapPointerArray
import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkFramebufferCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val renderPass: VkRenderPass,
    private val attachments: HeapPointerArray<VkImageView>? = null,
    private val width: Int,
    private val height: Int,
    private val layers: Int
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, OFFSETS[0], VK_STRUCTURE_TYPE_FRAMEBUFFER_CREATE_INFO)
        seg.set(ADDRESS, OFFSETS[1], next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, OFFSETS[2], flags)
        seg.set(ADDRESS, OFFSETS[3], renderPass.ref())
        seg.set(JAVA_INT, OFFSETS[4], attachments?.length ?: 0)
        seg.set(ADDRESS, OFFSETS[5], attachments?.ref() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, OFFSETS[6], width)
        seg.set(JAVA_INT, OFFSETS[7], height)
        seg.set(JAVA_INT, OFFSETS[8], layers)
    }
}