package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO
import com.primogemstudio.engine.interfaces.struct.ArrayStruct
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.loader.Platform.sizetLength
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkGraphicsPipelineCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val stages: ArrayStruct<VkPipelineShaderStageCreateInfo>? = null,
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
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        stages?.close()
        vertex?.close()
        inputAssembly?.close()
        tessellation?.close()
        viewport?.close()
        rasterization?.close()
        multisample?.close()
        depthStencil?.close()
        colorBlend?.close()
        dynamic?.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_INT,
        JAVA_INT,
        ADDRESS,
        ADDRESS,
        ADDRESS,
        ADDRESS,
        ADDRESS,
        ADDRESS,
        ADDRESS,
        ADDRESS,
        ADDRESS,
        ADDRESS,
        JAVA_LONG,
        JAVA_LONG,
        JAVA_LONG,
        JAVA_LONG,
        JAVA_LONG
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(JAVA_INT, sizetLength() + 12L, stages?.arr?.size ?: 0)
        seg.set(ADDRESS, sizetLength() + 16L, stages?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, sizetLength() * 2 + 16L, vertex?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, sizetLength() * 3 + 16L, inputAssembly?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, sizetLength() * 4 + 16L, tessellation?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, sizetLength() * 5 + 16L, viewport?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, sizetLength() * 6 + 16L, rasterization?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, sizetLength() * 7 + 16L, multisample?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, sizetLength() * 8 + 16L, depthStencil?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, sizetLength() * 9 + 16L, colorBlend?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, sizetLength() * 10 + 16L, dynamic?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, sizetLength() * 11 + 16L, layout.ref())
        seg.set(ADDRESS, sizetLength() * 11 + 24L, renderPass.ref())
        seg.set(JAVA_INT, sizetLength() * 11 + 32L, subpass)
        seg.set(ADDRESS, sizetLength() * 11 + 40L, basePipeline.ref())
        seg.set(JAVA_INT, sizetLength() * 11 + 48L, basePipelineIndex)
    }
}