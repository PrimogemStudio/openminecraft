package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_PIPELINE_RASTERIZATION_STATE_CREATE_INFO
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.loader.Platform.sizetLength
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
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_FLOAT,
        JAVA_FLOAT,
        JAVA_FLOAT,
        JAVA_FLOAT,
        MemoryLayout.paddingLayout(4)
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_PIPELINE_RASTERIZATION_STATE_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(JAVA_INT, sizetLength() + 12L, if (depthClampEnable) 1 else 0)
        seg.set(JAVA_INT, sizetLength() + 16L, if (rasterizerDiscardEnable) 1 else 0)
        seg.set(JAVA_INT, sizetLength() + 20L, polygonMode)
        seg.set(JAVA_INT, sizetLength() + 24L, cullMode)
        seg.set(JAVA_INT, sizetLength() + 28L, frontFace)
        seg.set(JAVA_INT, sizetLength() + 32L, if (depthBiasEnable) 1 else 0)
        seg.set(JAVA_FLOAT, sizetLength() + 36L, depthBiasConstantFactor)
        seg.set(JAVA_FLOAT, sizetLength() + 40L, depthBiasClamp)
        seg.set(JAVA_FLOAT, sizetLength() + 44L, depthBiasSlopeFactor)
        seg.set(JAVA_FLOAT, sizetLength() + 48L, lineWidth)
    }
}