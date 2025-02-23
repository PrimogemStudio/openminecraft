package com.primogemstudio.engine.bindings.vulkan.vk10

import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO
import com.primogemstudio.engine.foreign.align
import com.primogemstudio.engine.foreign.cacheOffsets
import com.primogemstudio.engine.foreign.heap.IHeapObject
import org.joml.Vector4i
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkImageViewCreateInfo(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED,
            VkImageSubresourceRange.LAYOUT
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT)) {
        sType = VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO
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
    var image: VkImage
        get() = VkImage(seg.get(ADDRESS, OFFSETS[3]))
        set(value) = seg.set(ADDRESS, OFFSETS[3], value.ref())
    var viewType: Int
        get() = seg.get(JAVA_INT, OFFSETS[4])
        set(value) = seg.set(JAVA_INT, OFFSETS[4], value)
    var format: Int
        get() = seg.get(JAVA_INT, OFFSETS[5])
        set(value) = seg.set(JAVA_INT, OFFSETS[5], value)
    var components: Vector4i
        get() = Vector4i(
            seg.get(JAVA_INT, OFFSETS[6]),
            seg.get(JAVA_INT, OFFSETS[7]),
            seg.get(JAVA_INT, OFFSETS[8]),
            seg.get(JAVA_INT, OFFSETS[9])
        )
        set(value) {
            seg.set(JAVA_INT, OFFSETS[6], value[0])
            seg.set(JAVA_INT, OFFSETS[7], value[1])
            seg.set(JAVA_INT, OFFSETS[8], value[2])
            seg.set(JAVA_INT, OFFSETS[9], value[3])
        }
    var range: VkImageSubresourceRange
        get() = VkImageSubresourceRange(seg.asSlice(OFFSETS[10], VkImageSubresourceRange.LAYOUT.byteSize()))
        set(value) {
            seg.asSlice(OFFSETS[10], VkImageSubresourceRange.LAYOUT.byteSize()).copyFrom(value.ref())
        }
}