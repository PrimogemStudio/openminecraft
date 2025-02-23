package com.primogemstudio.engine.bindings.vulkan.vk10

import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO
import com.primogemstudio.engine.foreign.align
import com.primogemstudio.engine.foreign.cacheOffsets
import com.primogemstudio.engine.foreign.heap.HeapStructArray
import com.primogemstudio.engine.foreign.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkGraphicsPipelineCreateInfo(private val seg: MemorySegment) : IHeapObject(seg) {
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

    constructor() : this(Arena.ofAuto().allocate(LAYOUT)) {
        sType = VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO
    }

    var sType: Int
        get() = seg.get(JAVA_INT, OFFSETS[0])
        set(value) = seg.set(JAVA_INT, OFFSETS[0], value)
    var next: MemorySegment
        get() = seg.get(ADDRESS, OFFSETS[1]).reinterpret(Long.MAX_VALUE)
        set(value) = seg.set(ADDRESS, OFFSETS[1], value)
    var flag: Int
        get() = seg.get(JAVA_INT, OFFSETS[2])
        set(value) = seg.set(JAVA_INT, OFFSETS[2], value)
    var stages: HeapStructArray<VkPipelineShaderStageCreateInfo>
        get() = HeapStructArray(
            seg.get(JAVA_INT, OFFSETS[3]),
            seg.get(ADDRESS, OFFSETS[4]),
            VkPipelineShaderStageCreateInfo.LAYOUT
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[4], value.ref())
            seg.set(JAVA_INT, OFFSETS[3], value.length)
        }
    var vertex: VkPipelineVertexInputStateCreateInfo
        get() = VkPipelineVertexInputStateCreateInfo(seg.get(ADDRESS, OFFSETS[5]))
        set(value) = seg.set(ADDRESS, OFFSETS[5], value.ref())
    var inputAssembly: VkPipelineInputAssemblyStateCreateInfo
        get() = VkPipelineInputAssemblyStateCreateInfo(seg.get(ADDRESS, OFFSETS[6]))
        set(value) = seg.set(ADDRESS, OFFSETS[6], value.ref())
    var tessellation: VkPipelineTessellationStateCreateInfo
        get() = VkPipelineTessellationStateCreateInfo(seg.get(ADDRESS, OFFSETS[7]))
        set(value) = seg.set(ADDRESS, OFFSETS[7], value.ref())
    var viewport: VkPipelineViewportStateCreateInfo
        get() = VkPipelineViewportStateCreateInfo(seg.get(ADDRESS, OFFSETS[8]))
        set(value) = seg.set(ADDRESS, OFFSETS[8], value.ref())
    var rasterization: VkPipelineRasterizationStateCreateInfo
        get() = VkPipelineRasterizationStateCreateInfo(seg.get(ADDRESS, OFFSETS[9]))
        set(value) = seg.set(ADDRESS, OFFSETS[9], value.ref())
    var multisample: VkPipelineMultisampleStateCreateInfo
        get() = VkPipelineMultisampleStateCreateInfo(seg.get(ADDRESS, OFFSETS[10]))
        set(value) = seg.set(ADDRESS, OFFSETS[10], value.ref())
    var depthStencil: VkPipelineDepthStencilStateCreateInfo
        get() = VkPipelineDepthStencilStateCreateInfo(seg.get(ADDRESS, OFFSETS[11]))
        set(value) = seg.set(ADDRESS, OFFSETS[11], value.ref())
    var colorBlend: VkPipelineColorBlendStateCreateInfo
        get() = VkPipelineColorBlendStateCreateInfo(seg.get(ADDRESS, OFFSETS[12]))
        set(value) = seg.set(ADDRESS, OFFSETS[12], value.ref())
    var dynamic: VkPipelineDynamicStateCreateInfo
        get() = VkPipelineDynamicStateCreateInfo(seg.get(ADDRESS, OFFSETS[13]))
        set(value) = seg.set(ADDRESS, OFFSETS[13], value.ref())
    var layout: VkPipelineLayout
        get() = VkPipelineLayout(seg.get(ADDRESS, OFFSETS[14]))
        set(value) = seg.set(ADDRESS, OFFSETS[14], value.ref())
    var renderPass: VkRenderPass
        get() = VkRenderPass(seg.get(ADDRESS, OFFSETS[15]))
        set(value) = seg.set(ADDRESS, OFFSETS[15], value.ref())
    var subpass: Int
        get() = seg.get(JAVA_INT, OFFSETS[16])
        set(value) = seg.set(JAVA_INT, OFFSETS[16], value)
    var basePipeline: VkPipeline
        get() = VkPipeline(seg.get(ADDRESS, OFFSETS[17]))
        set(value) = seg.set(ADDRESS, OFFSETS[17], value.ref())
    var basePipelineIndex: Int
        get() = seg.get(JAVA_INT, OFFSETS[18])
        set(value) = seg.set(JAVA_INT, OFFSETS[18], value)
}