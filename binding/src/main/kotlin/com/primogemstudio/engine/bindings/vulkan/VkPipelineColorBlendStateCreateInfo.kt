package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.core.Vk10Funcs.VK_STRUCTURE_TYPE_PIPELINE_COLOR_BLEND_STATE_CREATE_INFO
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.ArrayStruct
import com.primogemstudio.engine.interfaces.struct.IStruct
import org.joml.Vector4f
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkPipelineColorBlendStateCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val logicOpEnable: Boolean,
    private val logicOp: Int,
    private val attachments: ArrayStruct<VkPipelineColorBlendAttachmentState>,
    private val blendConstants: Vector4f
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
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
        seg.set(JAVA_INT, OFFSETS[0], VK_STRUCTURE_TYPE_PIPELINE_COLOR_BLEND_STATE_CREATE_INFO)
        seg.set(ADDRESS, OFFSETS[1], next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, OFFSETS[2], flags)
        seg.set(JAVA_INT, OFFSETS[3], if (logicOpEnable) 1 else 0)
        seg.set(JAVA_INT, OFFSETS[4], logicOp)
        seg.set(JAVA_INT, OFFSETS[5], attachments.arr.size)
        seg.set(ADDRESS, OFFSETS[6], attachments.pointer())
        seg.set(JAVA_FLOAT, OFFSETS[7], blendConstants.x)
        seg.set(JAVA_FLOAT, OFFSETS[8], blendConstants.y)
        seg.set(JAVA_FLOAT, OFFSETS[9], blendConstants.z)
        seg.set(JAVA_FLOAT, OFFSETS[10], blendConstants.w)
    }
}