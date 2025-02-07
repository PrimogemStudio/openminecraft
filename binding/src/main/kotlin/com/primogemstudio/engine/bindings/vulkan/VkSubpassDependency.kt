package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT

class VkSubpassDependency(
    private val srcSubpass: Int,
    private val dstSubpass: Int,
    private val srcStageMask: Int,
    private val dstStageMask: Int,
    private val srcAccessMask: Int,
    private val dstAccessMask: Int,
    private val dependencyFlags: Int
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
        seg.set(JAVA_INT, OFFSETS[0], srcSubpass)
        seg.set(JAVA_INT, OFFSETS[1], dstSubpass)
        seg.set(JAVA_INT, OFFSETS[2], srcStageMask)
        seg.set(JAVA_INT, OFFSETS[3], dstStageMask)
        seg.set(JAVA_INT, OFFSETS[4], srcAccessMask)
        seg.set(JAVA_INT, OFFSETS[5], dstAccessMask)
        seg.set(JAVA_INT, OFFSETS[6], dependencyFlags)
    }
}