package com.primogemstudio.engine.bindings.vulkan.vk10

import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO
import com.primogemstudio.engine.foreign.*
import com.primogemstudio.engine.foreign.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkInstanceCreateInfo(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
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
        sType = VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO
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
    var appInfo: VkApplicationInfo
        get() = VkApplicationInfo(seg.get(ADDRESS, OFFSETS[3]).reinterpret(Long.MAX_VALUE))
        set(value) = seg.set(ADDRESS, OFFSETS[3], value.ref())
    var layers: Array<String>
        get() = seg.get(ADDRESS, OFFSETS[5]).toPointerArray(seg.get(JAVA_INT, OFFSETS[4])).map { it.fetchString() }
            .toTypedArray()
        set(value) {
            seg.set(JAVA_INT, OFFSETS[4], value.size)
            seg.set(ADDRESS, OFFSETS[5], value.toCStrArray())
        }
    var extensions: Array<String>
        get() = seg.get(ADDRESS, OFFSETS[7]).toPointerArray(seg.get(JAVA_INT, OFFSETS[6])).map { it.fetchString() }
            .toTypedArray()
        set(value) {
            seg.set(JAVA_INT, OFFSETS[6], value.size)
            seg.set(ADDRESS, OFFSETS[7], value.toCStrArray())
        }
}