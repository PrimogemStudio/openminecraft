package com.primogemstudio.engine.bindings.vulkan.core

import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_LONG
import java.lang.foreign.ValueLayout.JAVA_LONG_UNALIGNED

class VkSubresourceLayout(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED,
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var offset: Long
        get() = seg.get(JAVA_LONG, OFFSETS[0])
        set(value) = seg.set(JAVA_LONG, OFFSETS[0], value)
    var size: Long
        get() = seg.get(JAVA_LONG, OFFSETS[1])
        set(value) = seg.set(JAVA_LONG, OFFSETS[1], value)
    var rowPitch: Long
        get() = seg.get(JAVA_LONG, OFFSETS[2])
        set(value) = seg.set(JAVA_LONG, OFFSETS[2], value)
    var arrayPitch: Long
        get() = seg.get(JAVA_LONG, OFFSETS[3])
        set(value) = seg.set(JAVA_LONG, OFFSETS[3], value)
    var depthPitch: Long
        get() = seg.get(JAVA_LONG, OFFSETS[4])
        set(value) = seg.set(JAVA_LONG, OFFSETS[4], value)
}