package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT

class VkVertexInputAttributeDescription(
    private val location: Int,
    private val binding: Int,
    private val format: Int,
    private val position: Int
) : IStruct() {
    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, location)
        seg.set(JAVA_INT, 4, binding)
        seg.set(JAVA_INT, 8, format)
        seg.set(JAVA_INT, 12, position)
    }
}