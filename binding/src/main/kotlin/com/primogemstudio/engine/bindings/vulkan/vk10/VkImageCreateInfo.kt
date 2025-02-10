package com.primogemstudio.engine.bindings.vulkan.vk10

import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_STRUCTURE_TYPE_IMAGE_CREATE_INFO
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.HeapIntArray
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import org.joml.Vector3i
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkImageCreateInfo(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT)) {
        sType = VK_STRUCTURE_TYPE_IMAGE_CREATE_INFO
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
    var imageType: Int
        get() = seg.get(JAVA_INT, OFFSETS[3])
        set(value) = seg.set(JAVA_INT, OFFSETS[3], value)
    var format: Int
        get() = seg.get(JAVA_INT, OFFSETS[4])
        set(value) = seg.set(JAVA_INT, OFFSETS[4], value)
    var extent: Vector3i
        get() = Vector3i(
            seg.get(JAVA_INT, OFFSETS[5]),
            seg.get(JAVA_INT, OFFSETS[6]),
            seg.get(JAVA_INT, OFFSETS[7])
        )
        set(value) {
            seg.set(JAVA_INT, OFFSETS[5], value[0])
            seg.set(JAVA_INT, OFFSETS[6], value[1])
            seg.set(JAVA_INT, OFFSETS[7], value[2])
        }
    var mipLevels: Int
        get() = seg.get(JAVA_INT, OFFSETS[8])
        set(value) = seg.set(JAVA_INT, OFFSETS[8], value)
    var arrayLayers: Int
        get() = seg.get(JAVA_INT, OFFSETS[9])
        set(value) = seg.set(JAVA_INT, OFFSETS[9], value)
    var samples: Int
        get() = seg.get(JAVA_INT, OFFSETS[10])
        set(value) = seg.set(JAVA_INT, OFFSETS[10], value)
    var tiling: Int
        get() = seg.get(JAVA_INT, OFFSETS[11])
        set(value) = seg.set(JAVA_INT, OFFSETS[11], value)
    var usage: Int
        get() = seg.get(JAVA_INT, OFFSETS[12])
        set(value) = seg.set(JAVA_INT, OFFSETS[12], value)
    var sharingMode: Int
        get() = seg.get(JAVA_INT, OFFSETS[13])
        set(value) = seg.set(JAVA_INT, OFFSETS[13], value)
    var queueFamilyIndices: HeapIntArray
        get() = HeapIntArray(
            seg.get(JAVA_INT, OFFSETS[14]),
            seg.get(ADDRESS, OFFSETS[15])
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[15], value.ref())
            seg.set(JAVA_INT, OFFSETS[14], value.length)
        }
    var initialLayout: Int
        get() = seg.get(JAVA_INT, OFFSETS[16])
        set(value) = seg.set(JAVA_INT, OFFSETS[16], value)
}