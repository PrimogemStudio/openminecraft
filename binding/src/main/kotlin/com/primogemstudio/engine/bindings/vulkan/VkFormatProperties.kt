package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.IHeapVar
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT

class VkFormatProperties : IHeapVar<MemorySegment> {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            *Array<MemoryLayout>(3) { _ -> JAVA_INT }
        )
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    private val seg = Arena.ofAuto().allocate(LAYOUT)

    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val linearTilingFeatures: Int get() = seg.get(JAVA_INT, OFFSETS[0])
    val optimalTilingFeatures: Int get() = seg.get(JAVA_INT, OFFSETS[1])
    val bufferFeatures: Int get() = seg.get(JAVA_INT, OFFSETS[2])
}