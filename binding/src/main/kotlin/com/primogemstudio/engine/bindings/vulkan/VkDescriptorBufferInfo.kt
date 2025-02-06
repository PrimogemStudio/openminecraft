package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.loader.Platform.sizetLength
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.ADDRESS
import java.lang.foreign.ValueLayout.JAVA_LONG

class VkDescriptorBufferInfo(
    private val buffer: VkBuffer,
    private val offset: Long,
    private val range: Long
) : IStruct() {
    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        ADDRESS,
        JAVA_LONG,
        JAVA_LONG
    )

    override fun construct(seg: MemorySegment) {
        seg.set(ADDRESS, 0, buffer.ref())
        seg.set(JAVA_LONG, sizetLength() * 1L, offset)
        seg.set(JAVA_LONG, sizetLength() + 8L, range)
    }
}