package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.cacheOffsets
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
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT
        )
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, OFFSETS[0], if (blendEnable) 1 else 0)
        seg.set(JAVA_INT, OFFSETS[1], srcColorBlendFactor)
        seg.set(JAVA_INT, OFFSETS[2], dstColorBlendFactor)
        seg.set(JAVA_INT, OFFSETS[3], colorBlendOp)
        seg.set(JAVA_INT, OFFSETS[4], srcAlphaBlendFactor)
        seg.set(JAVA_INT, OFFSETS[5], dstAlphaBlendFactor)
        seg.set(JAVA_INT, OFFSETS[6], alphaBlendOp)
        seg.set(JAVA_INT, OFFSETS[7], colorWriteMask)
    }
}