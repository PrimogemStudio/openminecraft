package com.primogemstudio.engine.bindings.vulkan.vk10

import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_STRUCTURE_TYPE_BIND_SPARSE_INFO
import com.primogemstudio.engine.foreign.align
import com.primogemstudio.engine.foreign.cacheOffsets
import com.primogemstudio.engine.foreign.heap.HeapPointerArray
import com.primogemstudio.engine.foreign.heap.HeapStructArray
import com.primogemstudio.engine.foreign.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkBindSparseInfo(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT)) {
        sType = VK_STRUCTURE_TYPE_BIND_SPARSE_INFO
    }

    var sType: Int
        get() = seg.get(JAVA_INT, OFFSETS[0])
        set(value) = seg.set(JAVA_INT, OFFSETS[0], value)
    var next: MemorySegment
        get() = seg.get(ADDRESS, OFFSETS[1])
        set(value) = seg.set(ADDRESS, OFFSETS[1], value)
    var waitSemaphores: HeapPointerArray<VkSemaphore>
        get() = HeapPointerArray(
            seg.get(JAVA_INT, OFFSETS[2]),
            seg.get(ADDRESS, OFFSETS[3])
        ) { VkSemaphore(it) }
        set(value) {
            seg.set(ADDRESS, OFFSETS[3], value.ref())
            seg.set(JAVA_INT, OFFSETS[2], value.length)
        }
    var bufferBinds: HeapStructArray<VkSparseBufferMemoryBindInfo>
        get() = HeapStructArray(
            seg.get(JAVA_INT, OFFSETS[4]),
            seg.get(ADDRESS, OFFSETS[5]),
            VkSparseBufferMemoryBindInfo.LAYOUT
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[5], value.ref())
            seg.set(JAVA_INT, OFFSETS[4], value.length)
        }
    var imageOpaqueBinds: HeapStructArray<VkSparseImageOpaqueMemoryBindInfo>
        get() = HeapStructArray(
            seg.get(JAVA_INT, OFFSETS[6]),
            seg.get(ADDRESS, OFFSETS[7]),
            VkSparseImageOpaqueMemoryBindInfo.LAYOUT
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[7], value.ref())
            seg.set(JAVA_INT, OFFSETS[6], value.length)
        }
    var imageBinds: HeapStructArray<VkSparseImageMemoryBindInfo>
        get() = HeapStructArray(
            seg.get(JAVA_INT, OFFSETS[8]),
            seg.get(ADDRESS, OFFSETS[9]),
            VkSparseImageMemoryBindInfo.LAYOUT
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[9], value.ref())
            seg.set(JAVA_INT, OFFSETS[8], value.length)
        }
    var signalSemaphores: HeapPointerArray<VkSemaphore>
        get() = HeapPointerArray(
            seg.get(JAVA_INT, OFFSETS[10]),
            seg.get(ADDRESS, OFFSETS[11])
        ) { VkSemaphore(it) }
        set(value) {
            seg.set(ADDRESS, OFFSETS[11], value.ref())
            seg.set(JAVA_INT, OFFSETS[10], value.length)
        }
}