package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkDescriptorBufferInfo(
    private val buffer: VkBuffer,
    private val offset: Long,
    private val range: Long
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        seg.set(ADDRESS, OFFSETS[0], buffer.ref())
        seg.set(JAVA_LONG, OFFSETS[1], offset)
        seg.set(JAVA_LONG, OFFSETS[2], range)
    }
}