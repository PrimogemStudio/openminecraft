package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.core.*
import com.primogemstudio.engine.bindings.vulkan.core.Vk10Funcs.VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.HeapStructArray
import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkGraphicsPipelineCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val stages: HeapStructArray<VkPipelineShaderStageCreateInfo>? = null,
    private val vertex: VkPipelineVertexInputStateCreateInfo? = null,
    private val inputAssembly: VkPipelineInputAssemblyStateCreateInfo? = null,
    private val tessellation: VkPipelineTessellationStateCreateInfo? = null,
    private val viewport: VkPipelineViewportStateCreateInfo? = null,
    private val rasterization: VkPipelineRasterizationStateCreateInfo? = null,
    private val multisample: VkPipelineMultisampleStateCreateInfo? = null,
    private val depthStencil: VkPipelineDepthStencilStateCreateInfo? = null,
    private val colorBlend: VkPipelineColorBlendStateCreateInfo? = null,
    private val dynamic: VkPipelineDynamicStateCreateInfo? = null,
    private val layout: VkPipelineLayout,
    private val renderPass: VkRenderPass,
    private val subpass: Int,
    private val basePipeline: VkPipeline,
    private val basePipelineIndex: Int
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, OFFSETS[0], VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO)
        seg.set(ADDRESS, OFFSETS[1], next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, OFFSETS[2], flags)
        seg.set(JAVA_INT, OFFSETS[3], stages?.length ?: 0)
        seg.set(ADDRESS, OFFSETS[4], stages?.ref() ?: MemorySegment.NULL)
        seg.set(ADDRESS, OFFSETS[5], vertex?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, OFFSETS[6], inputAssembly?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, OFFSETS[7], tessellation?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, OFFSETS[8], viewport?.ref() ?: MemorySegment.NULL)
        seg.set(ADDRESS, OFFSETS[9], rasterization?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, OFFSETS[10], multisample?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, OFFSETS[11], depthStencil?.ref() ?: MemorySegment.NULL)
        seg.set(ADDRESS, OFFSETS[12], colorBlend?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, OFFSETS[13], dynamic?.ref() ?: MemorySegment.NULL)
        seg.set(ADDRESS, OFFSETS[14], layout.ref())
        seg.set(ADDRESS, OFFSETS[15], renderPass.ref())
        seg.set(JAVA_INT, OFFSETS[16], subpass)
        seg.set(ADDRESS, OFFSETS[17], basePipeline.ref())
        seg.set(JAVA_INT, OFFSETS[18], basePipelineIndex)
    }
}