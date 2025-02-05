package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_PIPELINE_COLOR_BLEND_STATE_CREATE_INFO
import com.primogemstudio.engine.interfaces.struct.ArrayStruct
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.loader.Platform.sizetLength
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
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        attachments.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        ADDRESS,
        JAVA_FLOAT,
        JAVA_FLOAT,
        JAVA_FLOAT,
        JAVA_FLOAT
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_PIPELINE_COLOR_BLEND_STATE_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(JAVA_INT, sizetLength() + 12L, if (logicOpEnable) 1 else 0)
        seg.set(JAVA_INT, sizetLength() + 16L, logicOp)
        seg.set(JAVA_INT, sizetLength() + 20L, attachments.arr.size)
        seg.set(ADDRESS, sizetLength() + 24L, attachments.pointer())
        seg.set(JAVA_FLOAT, sizetLength() * 2 + 24L, blendConstants.x)
        seg.set(JAVA_FLOAT, sizetLength() * 2 + 28L, blendConstants.y)
        seg.set(JAVA_FLOAT, sizetLength() * 2 + 32L, blendConstants.z)
        seg.set(JAVA_FLOAT, sizetLength() * 2 + 36L, blendConstants.w)
    }
}