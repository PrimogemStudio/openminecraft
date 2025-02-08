package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.core.Vk10Funcs.VK_STRUCTURE_TYPE_PIPELINE_RASTERIZATION_STATE_CREATE_INFO
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkPipelineRasterizationStateCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val depthClampEnable: Boolean,
    private val rasterizerDiscardEnable: Boolean,
    private val polygonMode: Int,
    private val cullMode: Int,
    private val frontFace: Int,
    private val depthBiasEnable: Boolean,
    private val depthBiasConstantFactor: Float,
    private val depthBiasClamp: Float,
    private val depthBiasSlopeFactor: Float,
    private val lineWidth: Float
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_FLOAT_UNALIGNED,
            JAVA_FLOAT_UNALIGNED,
            JAVA_FLOAT_UNALIGNED,
            JAVA_FLOAT_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, OFFSETS[0], VK_STRUCTURE_TYPE_PIPELINE_RASTERIZATION_STATE_CREATE_INFO)
        seg.set(ADDRESS, OFFSETS[1], next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, OFFSETS[2], flags)
        seg.set(JAVA_INT, OFFSETS[3], if (depthClampEnable) 1 else 0)
        seg.set(JAVA_INT, OFFSETS[4], if (rasterizerDiscardEnable) 1 else 0)
        seg.set(JAVA_INT, OFFSETS[5], polygonMode)
        seg.set(JAVA_INT, OFFSETS[6], cullMode)
        seg.set(JAVA_INT, OFFSETS[7], frontFace)
        seg.set(JAVA_INT, OFFSETS[8], if (depthBiasEnable) 1 else 0)
        seg.set(JAVA_FLOAT, OFFSETS[9], depthBiasConstantFactor)
        seg.set(JAVA_FLOAT, OFFSETS[10], depthBiasClamp)
        seg.set(JAVA_FLOAT, OFFSETS[11], depthBiasSlopeFactor)
        seg.set(JAVA_FLOAT, OFFSETS[12], lineWidth)
    }
}