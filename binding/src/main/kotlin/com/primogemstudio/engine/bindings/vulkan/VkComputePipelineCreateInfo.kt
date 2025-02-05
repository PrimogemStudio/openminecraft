package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_COMPUTE_PIPELINE_CREATE_INFO
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.loader.Platform.sizetLength
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
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        stage?.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_INT,
        MemoryLayout.paddingLayout(48 + 4),
        ADDRESS,
        ADDRESS,
        JAVA_LONG
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_COMPUTE_PIPELINE_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        stage?.construct(seg.asSlice(sizetLength() + 12L, 48))
        seg.set(ADDRESS, sizetLength() + 64L, layout.ref())
        seg.set(ADDRESS, sizetLength() * 2 + 64L, basePipelineHandle.ref())
        seg.set(JAVA_INT, sizetLength() * 3 + 64L, basePipelineIndex)
    }
}