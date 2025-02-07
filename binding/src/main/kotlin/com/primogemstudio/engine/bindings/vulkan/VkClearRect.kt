package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT
import java.lang.foreign.ValueLayout.JAVA_INT_UNALIGNED

class VkClearRect(
    private val rect: VkRect2D,
    private val baseArrayLayer: Int,
    private val layerCount: Int
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            VkRect2D.LAYOUT,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = LAYOUT
    override fun construct(seg: MemorySegment) {
        rect.construct(seg.asSlice(OFFSETS[0], VkRect2D.LAYOUT.byteSize()))
        seg.set(JAVA_INT, OFFSETS[1], baseArrayLayer)
        seg.set(JAVA_INT, OFFSETS[2], layerCount)
    }
}