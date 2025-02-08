package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.core.Vk10Funcs.VK_STRUCTURE_TYPE_COMPUTE_PIPELINE_CREATE_INFO
import com.primogemstudio.engine.bindings.vulkan.core.VkPipeline
import com.primogemstudio.engine.bindings.vulkan.core.VkPipelineLayout
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkComputePipelineCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val stage: VkPipelineShaderStageCreateInfo? = null,
    private val layout: VkPipelineLayout,
    private val basePipelineHandle: VkPipeline,
    private val basePipelineIndex: Int
) : IStruct() {
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
        val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        stage?.close()
        super.close()
    }

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, OFFSETS[0], VK_STRUCTURE_TYPE_COMPUTE_PIPELINE_CREATE_INFO)
        seg.set(ADDRESS, OFFSETS[1], next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, OFFSETS[2], flags)
        stage?.construct(seg.asSlice(OFFSETS[3], VkPipelineShaderStageCreateInfo.LAYOUT.byteSize()))
        seg.set(ADDRESS, OFFSETS[4], layout.ref())
        seg.set(ADDRESS, OFFSETS[5], basePipelineHandle.ref())
        seg.set(JAVA_INT, OFFSETS[6], basePipelineIndex)
    }
}