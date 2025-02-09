package com.primogemstudio.engine.bindings.vulkan.core

import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT

class VkClearAttachment(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT,
            JAVA_INT,
            VkClearValue.LAYOUT
        )

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var aspectMask: Int
        get() = seg.get(JAVA_INT, OFFSETS[0])
        set(value) = seg.set(JAVA_INT, OFFSETS[0], value)
    var colorAttachment: Int
        get() = seg.get(JAVA_INT, OFFSETS[1])
        set(value) = seg.set(JAVA_INT, OFFSETS[1], value)
    var clearValue: VkClearValue
        get() = VkClearValue(seg.asSlice(OFFSETS[2], VkClearValue.LAYOUT.byteSize()))
        set(value) {
            seg.asSlice(OFFSETS[2], VkClearValue.LAYOUT.byteSize()).copyFrom(value.ref())
        }
}