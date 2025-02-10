package com.primogemstudio.engine.bindings.vulkan.vk10

import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import org.joml.Vector3i
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkSparseImageMemoryBind(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            VkImageSubresource.LAYOUT,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var subresource: VkImageSubresource
        get() = VkImageSubresource(seg.asSlice(OFFSETS[0], VkImageSubresource.LAYOUT.byteSize()))
        set(value) {
            seg.asSlice(OFFSETS[0], VkImageSubresource.LAYOUT.byteSize()).copyFrom(value.ref())
        }
    var offset: Vector3i
        get() = Vector3i(
            seg.get(JAVA_INT, OFFSETS[1]),
            seg.get(JAVA_INT, OFFSETS[2]),
            seg.get(JAVA_INT, OFFSETS[3])
        )
        set(value) {
            seg.set(JAVA_INT, OFFSETS[1], value[0])
            seg.set(JAVA_INT, OFFSETS[2], value[1])
            seg.set(JAVA_INT, OFFSETS[3], value[2])
        }
    var extent: Vector3i
        get() = Vector3i(
            seg.get(JAVA_INT, OFFSETS[4]),
            seg.get(JAVA_INT, OFFSETS[5]),
            seg.get(JAVA_INT, OFFSETS[6])
        )
        set(value) {
            seg.set(JAVA_INT, OFFSETS[4], value[0])
            seg.set(JAVA_INT, OFFSETS[5], value[1])
            seg.set(JAVA_INT, OFFSETS[6], value[2])
        }
    var memory: VkDeviceMemory
        get() = VkDeviceMemory(seg.get(ADDRESS, OFFSETS[7]))
        set(value) = seg.set(ADDRESS, OFFSETS[7], value.ref())
    var memoryOffset: Long
        get() = seg.get(JAVA_LONG, OFFSETS[8])
        set(value) = seg.set(JAVA_LONG, OFFSETS[8], value)
    var flags: Int
        get() = seg.get(JAVA_INT, OFFSETS[9])
        set(value) = seg.set(JAVA_INT, OFFSETS[9], value)
}
