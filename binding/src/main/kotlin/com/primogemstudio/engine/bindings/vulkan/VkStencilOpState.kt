package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT

class VkStencilOpState(
    private val failOp: Int,
    private val passOp: Int,
    private val depthFailOp: Int,
    private val compareOp: Int,
    private val compareMask: Int,
    private val writeMask: Int,
    private val reference: Int
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
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
        seg.set(JAVA_INT, OFFSETS[0], failOp)
        seg.set(JAVA_INT, OFFSETS[1], passOp)
        seg.set(JAVA_INT, OFFSETS[2], depthFailOp)
        seg.set(JAVA_INT, OFFSETS[3], compareOp)
        seg.set(JAVA_INT, OFFSETS[4], compareMask)
        seg.set(JAVA_INT, OFFSETS[5], writeMask)
        seg.set(JAVA_INT, OFFSETS[6], reference)
    }
}