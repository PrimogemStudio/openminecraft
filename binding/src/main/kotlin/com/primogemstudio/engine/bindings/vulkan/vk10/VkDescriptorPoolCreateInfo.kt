package com.primogemstudio.engine.bindings.vulkan.vk10

import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_STRUCTURE_TYPE_DESCRIPTOR_POOL_CREATE_INFO
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.HeapStructArray
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkDescriptorPoolCreateInfo(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT)) {
        sType = VK_STRUCTURE_TYPE_DESCRIPTOR_POOL_CREATE_INFO
    }

    var sType: Int
        get() = seg.get(JAVA_INT, OFFSETS[0])
        set(value) = seg.set(JAVA_INT, OFFSETS[0], value)
    var next: MemorySegment
        get() = seg.get(ADDRESS, OFFSETS[1]).reinterpret(Long.MAX_VALUE)
        set(value) = seg.set(ADDRESS, OFFSETS[1], value)
    var flag: Int
        get() = seg.get(JAVA_INT, OFFSETS[2])
        set(value) = seg.set(JAVA_INT, OFFSETS[2], value)
    var maxSets: Int
        get() = seg.get(JAVA_INT, OFFSETS[3])
        set(value) = seg.set(JAVA_INT, OFFSETS[3], value)
    var poolSizes: HeapStructArray<VkDescriptorPoolSize>
        get() = HeapStructArray(
            seg.get(JAVA_INT, OFFSETS[4]),
            seg.get(ADDRESS, OFFSETS[5]),
            VkDescriptorPoolSize.LAYOUT
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[5], value.ref())
            seg.set(JAVA_INT, OFFSETS[4], value.length)
        }
}