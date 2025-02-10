package com.primogemstudio.engine.bindings.vulkan.vk10

import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT

class VkAttachmentDescription(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var flags: Int
        get() = seg.get(JAVA_INT, OFFSETS[0])
        set(value) = seg.set(JAVA_INT, OFFSETS[0], value)
    var format: Int
        get() = seg.get(JAVA_INT, OFFSETS[1])
        set(value) = seg.set(JAVA_INT, OFFSETS[1], value)
    var samples: Int
        get() = seg.get(JAVA_INT, OFFSETS[2])
        set(value) = seg.set(JAVA_INT, OFFSETS[2], value)
    var loadOp: Int
        get() = seg.get(JAVA_INT, OFFSETS[3])
        set(value) = seg.set(JAVA_INT, OFFSETS[3], value)
    var storeOp: Int
        get() = seg.get(JAVA_INT, OFFSETS[4])
        set(value) = seg.set(JAVA_INT, OFFSETS[4], value)
    var stencilLoadOp: Int
        get() = seg.get(JAVA_INT, OFFSETS[5])
        set(value) = seg.set(JAVA_INT, OFFSETS[5], value)
    var stencilStoreOp: Int
        get() = seg.get(JAVA_INT, OFFSETS[6])
        set(value) = seg.set(JAVA_INT, OFFSETS[6], value)
    var initialLayout: Int
        get() = seg.get(JAVA_INT, OFFSETS[7])
        set(value) = seg.set(JAVA_INT, OFFSETS[7], value)
    var finalLayout: Int
        get() = seg.get(JAVA_INT, OFFSETS[8])
        set(value) = seg.set(JAVA_INT, OFFSETS[8], value)
}
