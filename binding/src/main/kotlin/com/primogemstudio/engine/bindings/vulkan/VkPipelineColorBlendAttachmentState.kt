package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT

class VkPipelineColorBlendAttachmentState(
    private val blendEnable: Boolean,
    private val srcColorBlendFactor: Int,
    private val dstColorBlendFactor: Int,
    private val colorBlendOp: Int,
    private val srcAlphaBlendFactor: Int,
    private val dstAlphaBlendFactor: Int,
    private val alphaBlendOp: Int,
    private val colorWriteMask: Int
) : IStruct() {
    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, if (blendEnable) 1 else 0)
        seg.set(JAVA_INT, 4, srcColorBlendFactor)
        seg.set(JAVA_INT, 8, dstColorBlendFactor)
        seg.set(JAVA_INT, 12, colorBlendOp)
        seg.set(JAVA_INT, 16, srcAlphaBlendFactor)
        seg.set(JAVA_INT, 20, dstAlphaBlendFactor)
        seg.set(JAVA_INT, 24, alphaBlendOp)
        seg.set(JAVA_INT, 28, colorWriteMask)
    }
}