package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
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
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, OFFSETS[0], flags)
        seg.set(JAVA_INT, OFFSETS[1], format)
        seg.set(JAVA_INT, OFFSETS[2], samples)
        seg.set(JAVA_INT, OFFSETS[3], loadOp)
        seg.set(JAVA_INT, OFFSETS[4], storeOp)
        seg.set(JAVA_INT, OFFSETS[5], stencilLoadOp)
        seg.set(JAVA_INT, OFFSETS[6], stencilStoreOp)
        seg.set(JAVA_INT, OFFSETS[7], initialLayout)
        seg.set(JAVA_INT, OFFSETS[8], finalLayout)
    }
}