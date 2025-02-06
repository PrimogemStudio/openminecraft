package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_MAPPED_MEMORY_RANGE
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

data class VkMappedMemoryRange(
    private val next: IStruct? = null,
    private val memory: VkDeviceMemory,
    private val offset: Long,
    private val length: Long
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        super.close()
    }

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, OFFSETS[0], VK_STRUCTURE_TYPE_MAPPED_MEMORY_RANGE)
        seg.set(ADDRESS, OFFSETS[1], next?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, OFFSETS[2], memory.ref())
        seg.set(JAVA_LONG, OFFSETS[3], offset)
        seg.set(JAVA_LONG, OFFSETS[4], length)
    }
}