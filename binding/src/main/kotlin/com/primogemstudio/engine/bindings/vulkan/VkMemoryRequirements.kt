package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.heap.IHeapVar
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT
import java.lang.foreign.ValueLayout.JAVA_LONG

class VkMemoryRequirements : IHeapVar<MemorySegment> {
    private val seg: MemorySegment = Arena.ofAuto().allocate(
        MemoryLayout.structLayout(
            JAVA_LONG,
            JAVA_LONG,
            JAVA_INT
        )
    )

    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val size: Long get() = seg.get(JAVA_LONG, 0)
    val alignment: Long get() = seg.get(JAVA_LONG, 8)
    val type: UInt get() = seg.get(JAVA_INT, 16).toUInt()
}