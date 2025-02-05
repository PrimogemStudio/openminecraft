package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.loader.Platform.sizetLength
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
    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG,
        JAVA_INT
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_LONG, 0, resourceOffset)
        seg.set(JAVA_LONG, 8, size)
        seg.set(ADDRESS, 16, memory.ref())
        seg.set(JAVA_LONG, sizetLength() + 16L, memoryOffset)
        seg.set(JAVA_INT, sizetLength() + 24L, flags)
    }
}