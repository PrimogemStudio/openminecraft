package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.cacheOffsets
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
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_FLOAT,
            JAVA_FLOAT,
            JAVA_FLOAT,
            JAVA_FLOAT,
            JAVA_FLOAT,
            JAVA_FLOAT
        )

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_FLOAT, OFFSETS[0], x)
        seg.set(JAVA_FLOAT, OFFSETS[1], y)
        seg.set(JAVA_FLOAT, OFFSETS[2], width)
        seg.set(JAVA_FLOAT, OFFSETS[3], height)
        seg.set(JAVA_FLOAT, OFFSETS[4], minDepth)
        seg.set(JAVA_FLOAT, OFFSETS[5], maxDepth)
    }
}