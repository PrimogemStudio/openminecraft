package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT

class VkImageSubresourceRange(
    private val aspectMask: Int,
    private val baseMipLevel: Int,
    private val levelCount: Int,
    private val baseArrayLayer: Int,
    private val layerCount: Int
) : IStruct() {
    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, aspectMask)
        seg.set(JAVA_INT, 4, baseMipLevel)
        seg.set(JAVA_INT, 8, levelCount)
        seg.set(JAVA_INT, 12, baseArrayLayer)
        seg.set(JAVA_INT, 16, layerCount)
    }
}