package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.interfaces.toCString
import com.primogemstudio.engine.loader.Platform.sizetLength
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkPipelineShaderStageCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val stage: Int,
    private val module: VkShaderModule,
    private val name: String,
    private val specializationInfo: VkSpecializationInfo
) : IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        specializationInfo.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_INT,
        JAVA_INT,
        JAVA_LONG,
        ADDRESS,
        ADDRESS
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(JAVA_INT, sizetLength() + 12L, stage)
        seg.set(ADDRESS, sizetLength() + 16L, module.ref())
        seg.set(ADDRESS, sizetLength() + 24L, name.toCString())
        seg.set(ADDRESS, sizetLength() * 2 + 24L, specializationInfo.pointer())
    }
}