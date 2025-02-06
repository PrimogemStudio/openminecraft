package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.interfaces.toCString
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkPipelineShaderStageCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val stage: Int,
    private val module: VkShaderModule,
    private val name: String,
    private val specializationInfo: VkSpecializationInfo? = null
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        specializationInfo?.close()
        super.close()
    }

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, OFFSETS[0], VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO)
        seg.set(ADDRESS, OFFSETS[1], next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, OFFSETS[2], flags)
        seg.set(JAVA_INT, OFFSETS[3], stage)
        seg.set(ADDRESS, OFFSETS[4], module.ref())
        seg.set(ADDRESS, OFFSETS[5], name.toCString())
        seg.set(ADDRESS, OFFSETS[6], specializationInfo?.pointer() ?: MemorySegment.NULL)
    }
}