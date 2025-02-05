package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_FLOAT

class VkViewport(
    private val x: Float,
    private val y: Float,
    private val width: Float,
    private val height: Float,
    private val minDepth: Float,
    private val maxDepth: Float
) : IStruct() {
    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_FLOAT,
        JAVA_FLOAT,
        JAVA_FLOAT,
        JAVA_FLOAT,
        JAVA_FLOAT,
        JAVA_FLOAT
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_FLOAT, 0, x)
        seg.set(JAVA_FLOAT, 4, y)
        seg.set(JAVA_FLOAT, 8, width)
        seg.set(JAVA_FLOAT, 12, height)
        seg.set(JAVA_FLOAT, 16, minDepth)
        seg.set(JAVA_FLOAT, 20, maxDepth)
    }
}