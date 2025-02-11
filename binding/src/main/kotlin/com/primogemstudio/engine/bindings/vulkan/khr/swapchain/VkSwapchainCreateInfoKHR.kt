package com.primogemstudio.engine.bindings.vulkan.khr.swapchain

import com.primogemstudio.engine.bindings.vulkan.khr.surface.VkSurfaceKHR
import com.primogemstudio.engine.bindings.vulkan.khr.swapchain.VkSwapchainKHRFuncs.VK_STRUCTURE_TYPE_SWAPCHAIN_CREATE_INFO_KHR
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.HeapIntArray
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import org.joml.Vector2i
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

// TODO Complete structure
class VkSwapchainCreateInfoKHR(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT)) {
        sType = VK_STRUCTURE_TYPE_SWAPCHAIN_CREATE_INFO_KHR
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
    var surface: VkSurfaceKHR
        get() = VkSurfaceKHR(seg.get(ADDRESS, OFFSETS[3]))
        set(value) = seg.set(ADDRESS, OFFSETS[3], value.ref())
    var minImageCount: Int
        get() = seg.get(JAVA_INT, OFFSETS[4])
        set(value) = seg.set(JAVA_INT, OFFSETS[4], value)
    var imageFormat: Int
        get() = seg.get(JAVA_INT, OFFSETS[5])
        set(value) = seg.set(JAVA_INT, OFFSETS[5], value)
    var imageColorSpace: Int
        get() = seg.get(JAVA_INT, OFFSETS[6])
        set(value) = seg.set(JAVA_INT, OFFSETS[6], value)
    var imageExtent: Vector2i
        get() = Vector2i(
            seg.get(JAVA_INT, OFFSETS[7]),
            seg.get(JAVA_INT, OFFSETS[8])
        )
        set(value) {
            seg.set(JAVA_INT, OFFSETS[7], value[0])
            seg.set(JAVA_INT, OFFSETS[8], value[1])
        }
    var imageArrayLayers: Int
        get() = seg.get(JAVA_INT, OFFSETS[9])
        set(value) = seg.set(JAVA_INT, OFFSETS[9], value)
    var imageUsage: Int
        get() = seg.get(JAVA_INT, OFFSETS[10])
        set(value) = seg.set(JAVA_INT, OFFSETS[10], value)
    var imageSharingMode: Int
        get() = seg.get(JAVA_INT, OFFSETS[11])
        set(value) = seg.set(JAVA_INT, OFFSETS[11], value)
    var queueFamilyIndices: HeapIntArray
        get() = HeapIntArray(
            seg.get(JAVA_INT, OFFSETS[12]),
            seg.get(ADDRESS, OFFSETS[13])
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[13], value.ref())
            seg.set(JAVA_INT, OFFSETS[12], value.length)
        }
    var preTransform: Int
        get() = seg.get(JAVA_INT, OFFSETS[14])
        set(value) = seg.set(JAVA_INT, OFFSETS[14], value)
    var compositeAlpha: Int
        get() = seg.get(JAVA_INT, OFFSETS[15])
        set(value) = seg.set(JAVA_INT, OFFSETS[15], value)
    var presentMode: Int
        get() = seg.get(JAVA_INT, OFFSETS[16])
        set(value) = seg.set(JAVA_INT, OFFSETS[16], value)
    var clipped: Boolean
        get() = seg.get(JAVA_INT, OFFSETS[17]) != 0
        set(value) = seg.set(JAVA_INT, OFFSETS[17], if (value) 1 else 0)
    var oldSwapchain: VkSwapchainKHR
        get() = VkSwapchainKHR(seg.get(ADDRESS, OFFSETS[18]))
        set(value) = seg.set(ADDRESS, OFFSETS[18], value.ref())
}