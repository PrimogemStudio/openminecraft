package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_MAPPED_MEMORY_RANGE
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.loader.Platform.sizetLength
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

data class VkMappedMemoryRange(
    private val next: IStruct? = null,
    private val memory: VkDeviceMemory,
    private val offset: Long,
    private val length: Long
) : IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        ADDRESS,
        JAVA_LONG,
        JAVA_LONG
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_MAPPED_MEMORY_RANGE)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, sizetLength() + 8L, memory.ref())
        seg.set(JAVA_LONG, sizetLength() * 2 + 8L, offset)
        seg.set(JAVA_LONG, sizetLength() * 2 + 16L, length)
    }
}