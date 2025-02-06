package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_PIPELINE_LAYOUT_CREATE_INFO
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.ArrayStruct
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.interfaces.struct.PointerArrayStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkPipelineLayoutCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val setLayouts: PointerArrayStruct<VkDescriptorSetLayout>? = null,
    private val pushConstantRanges: ArrayStruct<VkPushConstantRange>? = null
): IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED
        ).align()
        val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        super.close()
    }

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, OFFSETS[0], VK_STRUCTURE_TYPE_PIPELINE_LAYOUT_CREATE_INFO)
        seg.set(ADDRESS, OFFSETS[1], next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, OFFSETS[2], flags)
        seg.set(JAVA_INT, OFFSETS[3], setLayouts?.arr?.size ?: 0)
        seg.set(ADDRESS, OFFSETS[4], setLayouts?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, OFFSETS[5], pushConstantRanges?.arr?.size ?: 0)
        seg.set(ADDRESS, OFFSETS[6], pushConstantRanges?.pointer() ?: MemorySegment.NULL)
    }
}