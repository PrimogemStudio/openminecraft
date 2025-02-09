package com.primogemstudio.engine.bindings.vulkan.core

import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_FLOAT

class VkViewport(private val seg: MemorySegment) : IHeapObject(seg) {
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

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var x: Float
        get() = seg.get(JAVA_FLOAT, OFFSETS[0])
        set(value) = seg.set(JAVA_FLOAT, OFFSETS[0], value)
    var y: Float
        get() = seg.get(JAVA_FLOAT, OFFSETS[1])
        set(value) = seg.set(JAVA_FLOAT, OFFSETS[1], value)
    var width: Float
        get() = seg.get(JAVA_FLOAT, OFFSETS[2])
        set(value) = seg.set(JAVA_FLOAT, OFFSETS[2], value)
    var height: Float
        get() = seg.get(JAVA_FLOAT, OFFSETS[3])
        set(value) = seg.set(JAVA_FLOAT, OFFSETS[3], value)
    var minDepth: Float
        get() = seg.get(JAVA_FLOAT, OFFSETS[4])
        set(value) = seg.set(JAVA_FLOAT, OFFSETS[4], value)
    var maxDepth: Float
        get() = seg.get(JAVA_FLOAT, OFFSETS[5])
        set(value) = seg.set(JAVA_FLOAT, OFFSETS[5], value)
}