package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.cacheOffsets
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
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
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
        seg.set(JAVA_INT, OFFSETS[0], aspectMask)
        seg.set(JAVA_INT, OFFSETS[1], baseMipLevel)
        seg.set(JAVA_INT, OFFSETS[2], levelCount)
        seg.set(JAVA_INT, OFFSETS[3], baseArrayLayer)
        seg.set(JAVA_INT, OFFSETS[4], layerCount)
    }
}