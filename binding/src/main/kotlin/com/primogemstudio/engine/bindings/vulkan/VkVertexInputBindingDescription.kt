package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT

class VkVertexInputBindingDescription(
    private val binding: Int,
    private val stride: Int,
    private val inputRate: Int
) : IStruct() {
    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_INT,
        JAVA_INT,
        JAVA_INT
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, binding)
        seg.set(JAVA_INT, 4, stride)
        seg.set(JAVA_INT, 8, inputRate)
    }
}