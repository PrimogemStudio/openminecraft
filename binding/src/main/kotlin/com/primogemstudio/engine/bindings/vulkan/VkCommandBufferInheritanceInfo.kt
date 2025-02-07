package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_COMMAND_BUFFER_INHERITANCE_INFO
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkCommandBufferInheritanceInfo(
    private val next: IStruct? = null,
    private val renderPass: VkRenderPass,
    private val subpass: Int,
    private val framebuffer: VkFramebuffer,
    private val occlusionQueryEnable: Boolean,
    private val queryFlags: Int,
    private val pipelineStatistics: Int
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
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

    override fun close() {
        next?.close()
        super.close()
    }

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, OFFSETS[0], VK_STRUCTURE_TYPE_COMMAND_BUFFER_INHERITANCE_INFO)
        seg.set(ADDRESS, OFFSETS[1], next?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, OFFSETS[2], renderPass.ref())
        seg.set(JAVA_INT, OFFSETS[3], subpass)
        seg.set(ADDRESS, OFFSETS[4], framebuffer.ref())
        seg.set(JAVA_INT, OFFSETS[5], if (occlusionQueryEnable) 1 else 0)
        seg.set(JAVA_INT, OFFSETS[6], queryFlags)
        seg.set(JAVA_INT, OFFSETS[7], pipelineStatistics)
    }
}