package com.primogemstudio.engine.bindings.vulkan.vk10

import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.HeapByteArray
import com.primogemstudio.engine.interfaces.heap.HeapStructArray
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkSpecializationInfo(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    var mapEntries: HeapStructArray<VkSpecializationMapEntry>
        get() = HeapStructArray(
            seg.get(JAVA_INT, OFFSETS[0]),
            seg.get(ADDRESS, OFFSETS[1]),
            VkSpecializationMapEntry.LAYOUT
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[1], value.ref())
            seg.set(JAVA_INT, OFFSETS[0], value.length)
        }
    var data: HeapByteArray
        get() = HeapByteArray(
            seg.get(JAVA_INT, OFFSETS[2]),
            seg.get(ADDRESS, OFFSETS[3])
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[3], value.ref())
            seg.set(JAVA_INT, OFFSETS[2], value.length)
        }
}