package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.IHeapVar
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkMemoryRequirements : IHeapVar<MemorySegment> {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            JAVA_INT_UNALIGNED
        )
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    private val seg: MemorySegment = Arena.ofAuto().allocate(LAYOUT)

    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val size: Long get() = seg.get(JAVA_LONG, OFFSETS[0])
    val alignment: Long get() = seg.get(JAVA_LONG, OFFSETS[1])
    val type: UInt get() = seg.get(JAVA_INT, OFFSETS[2]).toUInt()
}