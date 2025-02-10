package com.primogemstudio.engine.bindings.vulkan.vk10

import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkDescriptorBufferInfo(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var buffer: VkBuffer
        get() = VkBuffer(seg.get(ADDRESS, OFFSETS[0]))
        set(value) = seg.set(ADDRESS, OFFSETS[0], value.ref())
    var offset: Long
        get() = seg.get(JAVA_LONG, OFFSETS[1])
        set(value) = seg.set(JAVA_LONG, OFFSETS[1], value)
    var range: Long
        get() = seg.get(JAVA_LONG, OFFSETS[2])
        set(value) = seg.set(JAVA_LONG, OFFSETS[2], value)
}