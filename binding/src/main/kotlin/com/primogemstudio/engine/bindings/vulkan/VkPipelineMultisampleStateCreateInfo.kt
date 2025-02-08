package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.core.Vk10Funcs.VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.interfaces.struct.IntArrayStruct
import com.primogemstudio.engine.loader.Platform.sizetLength
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkPipelineMultisampleStateCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val rasterizationSamples: Int,
    private val sampleShadingEnable: Boolean,
    private val minSampleShading: Float,
    private val sampleMask: IntArrayStruct,
    private val alphaToCoverageEnable: Boolean,
    private val alphaToOneEnable: Boolean
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_LONG,
            ADDRESS,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_FLOAT,
            ADDRESS,
            JAVA_INT,
            JAVA_INT
        ).align()
    }

    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        sampleMask.close()
        super.close()
    }

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(JAVA_INT, sizetLength() + 12L, rasterizationSamples)
        seg.set(JAVA_INT, sizetLength() + 16L, if (sampleShadingEnable) 1 else 0)
        seg.set(JAVA_FLOAT, sizetLength() + 20L, minSampleShading)
        seg.set(ADDRESS, sizetLength() + 24L, sampleMask.pointer())
        seg.set(JAVA_INT, sizetLength() * 2 + 24L, if (alphaToCoverageEnable) 1 else 0)
        seg.set(JAVA_INT, sizetLength() * 2 + 28L, if (alphaToOneEnable) 1 else 0)
    }
}