package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT

class VkVertexInputBindingDescription(
    private val binding: Int,
    private val stride: Int,
    private val inputRate: Int
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
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
        seg.set(JAVA_INT, OFFSETS[0], binding)
        seg.set(JAVA_INT, OFFSETS[1], stride)
        seg.set(JAVA_INT, OFFSETS[2], inputRate)
    }
}