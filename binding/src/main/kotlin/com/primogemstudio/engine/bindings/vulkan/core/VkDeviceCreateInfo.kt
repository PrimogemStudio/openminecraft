package com.primogemstudio.engine.bindings.vulkan.core

import com.primogemstudio.engine.bindings.vulkan.core.Vk10Funcs.VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO
import com.primogemstudio.engine.interfaces.*
import com.primogemstudio.engine.interfaces.heap.HeapStructArray
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

// TODO complete define
class VkDeviceCreateInfo(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT)) {
        sType = VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO
    }

    var sType: Int
        get() = seg.get(JAVA_INT, OFFSETS[0])
        set(value) = seg.set(JAVA_INT, OFFSETS[0], value)
    var next: MemorySegment
        get() = seg.get(ADDRESS, OFFSETS[1])
        set(value) = seg.set(ADDRESS, OFFSETS[1], value)
    var flag: Int
        get() = seg.get(JAVA_INT, OFFSETS[2])
        set(value) = seg.set(JAVA_INT, OFFSETS[2], value)
    var queueCreateInfos: HeapStructArray<VkDeviceQueueCreateInfo>
        get() = HeapStructArray(
            seg.get(JAVA_INT, OFFSETS[3]),
            seg.get(ADDRESS, OFFSETS[4]),
            VkDeviceQueueCreateInfo.LAYOUT
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[4], value.ref())
            seg.set(JAVA_INT, OFFSETS[3], value.length)
        }
    var layers: Array<String>
        get() = seg.get(ADDRESS, OFFSETS[6]).toPointerArray(seg.get(JAVA_INT, OFFSETS[5])).map { it.fetchString() }
            .toTypedArray()
        set(value) {
            seg.set(JAVA_INT, OFFSETS[5], value.size)
            seg.set(ADDRESS, OFFSETS[6], value.toCStrArray())
        }
    var extensions: Array<String>
        get() = seg.get(ADDRESS, OFFSETS[8]).toPointerArray(seg.get(JAVA_INT, OFFSETS[7])).map { it.fetchString() }
            .toTypedArray()
        set(value) {
            seg.set(JAVA_INT, OFFSETS[7], value.size)
            seg.set(ADDRESS, OFFSETS[8], value.toCStrArray())
        }
    var features: VkPhysicalDeviceFeatures
        get() = VkPhysicalDeviceFeatures(seg.get(ADDRESS, OFFSETS[9]))
        set(value) = seg.set(ADDRESS, OFFSETS[9], value.ref())
}