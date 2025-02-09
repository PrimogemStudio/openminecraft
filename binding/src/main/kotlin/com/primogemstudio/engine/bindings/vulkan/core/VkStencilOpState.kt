package com.primogemstudio.engine.bindings.vulkan.core

import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT

class VkStencilOpState(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT
        )
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var failOp: Int
        get() = seg.get(JAVA_INT, OFFSETS[0])
        set(value) = seg.set(JAVA_INT, OFFSETS[0], value)
    var passOp: Int
        get() = seg.get(JAVA_INT, OFFSETS[1])
        set(value) = seg.set(JAVA_INT, OFFSETS[1], value)
    var depthFailOp: Int
        get() = seg.get(JAVA_INT, OFFSETS[2])
        set(value) = seg.set(JAVA_INT, OFFSETS[2], value)
    var compareOp: Int
        get() = seg.get(JAVA_INT, OFFSETS[3])
        set(value) = seg.set(JAVA_INT, OFFSETS[3], value)
    var compareMask: Int
        get() = seg.get(JAVA_INT, OFFSETS[4])
        set(value) = seg.set(JAVA_INT, OFFSETS[4], value)
    var writeMask: Int
        get() = seg.get(JAVA_INT, OFFSETS[5])
        set(value) = seg.set(JAVA_INT, OFFSETS[5], value)
    var reference: Int
        get() = seg.get(JAVA_INT, OFFSETS[6])
        set(value) = seg.set(JAVA_INT, OFFSETS[6], value)
}