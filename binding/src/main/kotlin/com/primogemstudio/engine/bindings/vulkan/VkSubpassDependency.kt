package com.primogemstudio.engine.bindings.vulkan

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
        seg.set(JAVA_INT, 0, srcSubpass)
        seg.set(JAVA_INT, 4, dstSubpass)
        seg.set(JAVA_INT, 8, srcStageMask)
        seg.set(JAVA_INT, 12, dstStageMask)
        seg.set(JAVA_INT, 16, srcAccessMask)
        seg.set(JAVA_INT, 20, dstAccessMask)
        seg.set(JAVA_INT, 24, dependencyFlags)
    }
}