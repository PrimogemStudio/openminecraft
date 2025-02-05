package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT
import java.lang.foreign.ValueLayout.JAVA_LONG

class VkSpecializationMapEntry(
    private val constantId: Int,
    private val offset: Int,
    private val size: Long
) : IStruct() {
    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_INT,
        JAVA_INT,
        JAVA_LONG
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, constantId)
        seg.set(JAVA_INT, 4, offset)
        seg.set(JAVA_LONG, 8, size)
    }
}