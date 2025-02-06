package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT

class VkAttachmentDescription(
    private val flags: Int,
    private val format: Int,
    private val samples: Int,
    private val loadOp: Int,
    private val storeOp: Int,
    private val stencilLoadOp: Int,
    private val stencilStoreOp: Int,
    private val initialLayout: Int,
    private val finalLayout: Int,
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
        JAVA_INT,
        JAVA_INT
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, flags)
        seg.set(JAVA_INT, 4, format)
        seg.set(JAVA_INT, 8, samples)
        seg.set(JAVA_INT, 12, loadOp)
        seg.set(JAVA_INT, 16, storeOp)
        seg.set(JAVA_INT, 20, stencilLoadOp)
        seg.set(JAVA_INT, 24, stencilStoreOp)
        seg.set(JAVA_INT, 28, initialLayout)
        seg.set(JAVA_INT, 32, finalLayout)
    }
}