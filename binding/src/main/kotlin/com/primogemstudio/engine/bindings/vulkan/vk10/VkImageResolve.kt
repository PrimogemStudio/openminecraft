package com.primogemstudio.engine.bindings.vulkan.vk10

import com.primogemstudio.engine.foreign.align
import com.primogemstudio.engine.foreign.cacheOffsets
import com.primogemstudio.engine.foreign.heap.IHeapObject
import org.joml.Vector3i
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT
import java.lang.foreign.ValueLayout.JAVA_INT_UNALIGNED

class VkImageResolve(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            VkImageSubresourceLayers.LAYOUT,
            JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED,
            VkImageSubresourceLayers.LAYOUT,
            JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED,
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var srcSubresource: VkImageSubresourceLayers
        get() = VkImageSubresourceLayers(seg.asSlice(OFFSETS[0], VkImageSubresourceLayers.LAYOUT.byteSize()))
        set(value) {
            seg.asSlice(OFFSETS[0], VkImageSubresourceLayers.LAYOUT.byteSize()).copyFrom(value.ref())
        }
    var srcOffset: Vector3i
        get() = Vector3i(
            seg.get(JAVA_INT, OFFSETS[1]),
            seg.get(JAVA_INT, OFFSETS[2]),
            seg.get(JAVA_INT, OFFSETS[3]),
        )
        set(value) {
            seg.set(JAVA_INT, OFFSETS[1], value[0])
            seg.set(JAVA_INT, OFFSETS[2], value[1])
            seg.set(JAVA_INT, OFFSETS[3], value[2])
        }
    var dstSubresource: VkImageSubresourceLayers
        get() = VkImageSubresourceLayers(seg.asSlice(OFFSETS[4], VkImageSubresourceLayers.LAYOUT.byteSize()))
        set(value) {
            seg.asSlice(OFFSETS[4], VkImageSubresourceLayers.LAYOUT.byteSize()).copyFrom(value.ref())
        }
    var dstOffset: Vector3i
        get() = Vector3i(
            seg.get(JAVA_INT, OFFSETS[5]),
            seg.get(JAVA_INT, OFFSETS[6]),
            seg.get(JAVA_INT, OFFSETS[7]),
        )
        set(value) {
            seg.set(JAVA_INT, OFFSETS[5], value[0])
            seg.set(JAVA_INT, OFFSETS[6], value[1])
            seg.set(JAVA_INT, OFFSETS[7], value[2])
        }
}