package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

data class VkSparseMemoryBind(
    private val resourceOffset: Long,
    private val size: Long,
    private val memory: VkDeviceMemory,
    private val memoryOffset: Long,
    private val flags: Int
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            JAVA_INT_UNALIGNED
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_LONG, OFFSETS[0], resourceOffset)
        seg.set(JAVA_LONG, OFFSETS[1], size)
        seg.set(ADDRESS, OFFSETS[2], memory.ref())
        seg.set(JAVA_LONG, OFFSETS[3], memoryOffset)
        seg.set(JAVA_INT, OFFSETS[4], flags)
    }
}