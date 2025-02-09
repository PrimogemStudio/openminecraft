package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.core.Vk10Funcs.VK_STRUCTURE_TYPE_RENDER_PASS_CREATE_INFO
import com.primogemstudio.engine.bindings.vulkan.core.VkSubpassDescription
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.HeapStructArray
import com.primogemstudio.engine.interfaces.struct.ArrayStruct
import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkRenderPassCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val attachments: ArrayStruct<VkAttachmentDescription>? = null,
    private val subpasses: HeapStructArray<VkSubpassDescription>? = null,
    private val dependencies: ArrayStruct<VkSubpassDependency>? = null
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, OFFSETS[0], VK_STRUCTURE_TYPE_RENDER_PASS_CREATE_INFO)
        seg.set(ADDRESS, OFFSETS[1], next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, OFFSETS[2], flags)
        seg.set(JAVA_INT, OFFSETS[3], attachments?.arr?.size ?: 0)
        seg.set(ADDRESS, OFFSETS[4], attachments?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, OFFSETS[5], subpasses?.length ?: 0)
        seg.set(ADDRESS, OFFSETS[5], subpasses?.ref() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, OFFSETS[6], dependencies?.arr?.size ?: 0)
        seg.set(ADDRESS, OFFSETS[7], dependencies?.pointer() ?: MemorySegment.NULL)
    }
}