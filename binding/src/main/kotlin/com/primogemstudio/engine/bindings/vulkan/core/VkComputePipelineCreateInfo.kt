package com.primogemstudio.engine.bindings.vulkan.core

import com.primogemstudio.engine.bindings.vulkan.core.Vk10Funcs.VK_STRUCTURE_TYPE_COMPUTE_PIPELINE_CREATE_INFO
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkComputePipelineCreateInfo(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            VkPipelineShaderStageCreateInfo.LAYOUT,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT)) {
        sType = VK_STRUCTURE_TYPE_COMPUTE_PIPELINE_CREATE_INFO
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
    var stage: VkPipelineShaderStageCreateInfo
        get() = VkPipelineShaderStageCreateInfo(
            seg.asSlice(
                OFFSETS[3],
                VkPipelineShaderStageCreateInfo.LAYOUT.byteSize()
            )
        )
        set(value) {
            seg.asSlice(OFFSETS[3], VkPipelineShaderStageCreateInfo.LAYOUT.byteSize()).copyFrom(value.ref())
        }
    var layout: VkPipelineLayout
        get() = VkPipelineLayout(seg.get(ADDRESS, OFFSETS[4]))
        set(value) = seg.set(ADDRESS, OFFSETS[4], value.ref())
    var basePipelineHandle: VkPipeline
        get() = VkPipeline(seg.get(ADDRESS, OFFSETS[5]))
        set(value) = seg.set(ADDRESS, OFFSETS[5], value.ref())
    var basePipelineIndex: Int
        get() = seg.get(JAVA_INT, OFFSETS[6])
        set(value) = seg.set(JAVA_INT, OFFSETS[6], value)
}