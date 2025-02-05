package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_PIPELINE_LAYOUT_CREATE_INFO
import com.primogemstudio.engine.interfaces.struct.ArrayStruct
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.interfaces.struct.PointerArrayStruct
import com.primogemstudio.engine.loader.Platform.sizetLength
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkPipelineLayoutCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val setLayouts: PointerArrayStruct<VkDescriptorSetLayout>? = null,
    private val pushConstantRanges: ArrayStruct<VkPushConstantRange>? = null
): IStruct() {
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
        ADDRESS,
        JAVA_LONG,
        ADDRESS
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_PIPELINE_LAYOUT_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(JAVA_INT, sizetLength() + 12L, setLayouts?.arr?.size?: 0)
        seg.set(ADDRESS, sizetLength() + 16L, setLayouts?.pointer()?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() * 2 + 16L, pushConstantRanges?.arr?.size?: 0)
        seg.set(ADDRESS, sizetLength() * 2 + 24L, pushConstantRanges?.pointer()?: MemorySegment.NULL)
    }
}