package com.primogemstudio.engine.bindings.vulkan

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
        JAVA_INT
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, failOp)
        seg.set(JAVA_INT, 4, passOp)
        seg.set(JAVA_INT, 8, depthFailOp)
        seg.set(JAVA_INT, 12, compareOp)
        seg.set(JAVA_INT, 16, compareMask)
        seg.set(JAVA_INT, 20, writeMask)
        seg.set(JAVA_INT, 24, reference)
    }
}