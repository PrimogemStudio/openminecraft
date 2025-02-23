package com.primogemstudio.engine.bindings.vulkan.vk10

import com.primogemstudio.engine.foreign.align
import com.primogemstudio.engine.foreign.cacheOffsets
import com.primogemstudio.engine.foreign.heap.IHeapObject
import org.joml.Vector3i
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkBufferImageCopy(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_LONG_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            VkImageSubresourceLayers.LAYOUT,
            JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED,
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var bufferOffset: Long
        get() = seg.get(JAVA_LONG, OFFSETS[0])
        set(value) = seg.set(JAVA_LONG, OFFSETS[0], value)
    var bufferRowLength: Int
        get() = seg.get(JAVA_INT, OFFSETS[1])
        set(value) = seg.set(JAVA_INT, OFFSETS[1], value)
    var bufferImageHeight: Int
        get() = seg.get(JAVA_INT, OFFSETS[2])
        set(value) = seg.set(JAVA_INT, OFFSETS[2], value)
    var imageSubresourceLayers: VkImageSubresourceLayers
        get() = VkImageSubresourceLayers(seg.asSlice(OFFSETS[3], VkImageSubresourceLayers.LAYOUT.byteSize()))
        set(value) {
            seg.asSlice(OFFSETS[3], VkImageSubresourceLayers.LAYOUT.byteSize()).copyFrom(value.ref())
        }
    var imageOffset: Vector3i
        get() = Vector3i(
            seg.get(JAVA_INT, OFFSETS[4]),
            seg.get(JAVA_INT, OFFSETS[5]),
            seg.get(JAVA_INT, OFFSETS[6]),
        )
        set(value) {
            seg.set(JAVA_INT, OFFSETS[4], value[0])
            seg.set(JAVA_INT, OFFSETS[5], value[1])
            seg.set(JAVA_INT, OFFSETS[6], value[2])
        }
    var imageExtent: Vector3i
        get() = Vector3i(
            seg.get(JAVA_INT, OFFSETS[7]),
            seg.get(JAVA_INT, OFFSETS[8]),
            seg.get(JAVA_INT, OFFSETS[9]),
        )
        set(value) {
            seg.set(JAVA_INT, OFFSETS[7], value[0])
            seg.set(JAVA_INT, OFFSETS[8], value[1])
            seg.set(JAVA_INT, OFFSETS[9], value[2])
        }
}