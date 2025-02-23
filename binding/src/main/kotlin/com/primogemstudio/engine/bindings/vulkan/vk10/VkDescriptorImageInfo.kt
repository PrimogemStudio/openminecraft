package com.primogemstudio.engine.bindings.vulkan.vk10

import com.primogemstudio.engine.foreign.align
import com.primogemstudio.engine.foreign.cacheOffsets
import com.primogemstudio.engine.foreign.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkDescriptorImageInfo(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var sampler: VkSampler
        get() = VkSampler(seg.get(ADDRESS, OFFSETS[0]))
        set(value) = seg.set(ADDRESS, OFFSETS[0], value.ref())
    var imageView: VkImageView
        get() = VkImageView(seg.get(ADDRESS, OFFSETS[1]))
        set(value) = seg.set(ADDRESS, OFFSETS[1], value.ref())
    var imageLayout: Int
        get() = seg.get(JAVA_INT, OFFSETS[2])
        set(value) = seg.set(JAVA_INT, OFFSETS[2], value)
}