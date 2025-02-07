package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkClearDepthStencilValue(
    private val depth: Float,
    private val stencil: Int
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_FLOAT_UNALIGNED,
            JAVA_INT_UNALIGNED
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = LAYOUT
    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_FLOAT, OFFSETS[0], depth)
        seg.set(JAVA_INT, OFFSETS[1], stencil)
    }
}