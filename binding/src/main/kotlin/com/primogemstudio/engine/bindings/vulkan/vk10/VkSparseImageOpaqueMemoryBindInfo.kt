package com.primogemstudio.engine.bindings.vulkan.vk10

import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.HeapStructArray
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkSparseImageOpaqueMemoryBindInfo(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            ADDRESS_UNALIGNED
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var buffer: VkBuffer
        get() = VkBuffer(seg.get(ADDRESS, OFFSETS[0]))
        set(value) = seg.set(ADDRESS, OFFSETS[0], value.ref())
    var binds: HeapStructArray<VkSparseImageMemoryBind>
        get() = HeapStructArray(
            seg.get(JAVA_INT, OFFSETS[1]),
            seg.get(ADDRESS, OFFSETS[2]),
            VkSparseImageMemoryBind.LAYOUT
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[2], value.ref())
            seg.set(JAVA_INT, OFFSETS[1], value.length)
        }
}