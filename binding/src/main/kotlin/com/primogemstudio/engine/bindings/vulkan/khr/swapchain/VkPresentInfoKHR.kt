package com.primogemstudio.engine.bindings.vulkan.khr.swapchain

import com.primogemstudio.engine.bindings.vulkan.khr.swapchain.VkSwapchainKHRFuncs.VK_STRUCTURE_TYPE_PRESENT_INFO_KHR
import com.primogemstudio.engine.bindings.vulkan.vk10.VkSemaphore
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.HeapIntArray
import com.primogemstudio.engine.interfaces.heap.HeapPointerArray
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkPresentInfoKHR(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT)) {
        sType = VK_STRUCTURE_TYPE_PRESENT_INFO_KHR
    }

    var sType: Int
        get() = seg.get(JAVA_INT, OFFSETS[0])
        set(value) = seg.set(JAVA_INT, OFFSETS[0], value)
    var next: MemorySegment
        get() = seg.get(ADDRESS, OFFSETS[1]).reinterpret(Long.MAX_VALUE)
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
    var swapchains: HeapPointerArray<VkSwapchainKHR>
        get() = HeapPointerArray(
            seg.get(JAVA_INT, OFFSETS[4]),
            seg.get(ADDRESS, OFFSETS[5])
        ) { VkSwapchainKHR(it) }
        set(value) {
            seg.set(ADDRESS, OFFSETS[5], value.ref())
            seg.set(JAVA_INT, OFFSETS[4], value.length)
        }
    var imageIndices: HeapIntArray
        get() = HeapIntArray(
            seg.get(JAVA_INT, OFFSETS[4]),
            seg.get(ADDRESS, OFFSETS[6])
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[6], value.ref())
            seg.set(JAVA_INT, OFFSETS[4], value.length)
        }
    var results: HeapIntArray
        get() = HeapIntArray(
            seg.get(JAVA_INT, OFFSETS[4]),
            seg.get(ADDRESS, OFFSETS[7])
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[7], value.ref())
            seg.set(JAVA_INT, OFFSETS[4], value.length)
        }
}