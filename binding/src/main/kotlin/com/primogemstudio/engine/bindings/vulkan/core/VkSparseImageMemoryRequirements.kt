package com.primogemstudio.engine.bindings.vulkan.core

import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkSparseImageMemoryRequirements(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            VkSparseImageFormatProperties.LAYOUT,
            JAVA_INT_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var formatProperties: VkSparseImageFormatProperties
        get() = VkSparseImageFormatProperties(seg.asSlice(OFFSETS[0], VkSparseImageFormatProperties.LAYOUT.byteSize()))
        set(value) {
            seg.asSlice(OFFSETS[0], VkSparseImageFormatProperties.LAYOUT.byteSize()).copyFrom(value.ref())
        }
    var imageMipTailFirstLod: Int
        get() = seg.get(JAVA_INT, OFFSETS[1])
        set(value) = seg.set(JAVA_INT, OFFSETS[1], value)
    var imageMipTailSize: Long
        get() = seg.get(JAVA_LONG, OFFSETS[2])
        set(value) = seg.set(JAVA_LONG, OFFSETS[2], value)
    var imageMipTailOffset: Long
        get() = seg.get(JAVA_LONG, OFFSETS[3])
        set(value) = seg.set(JAVA_LONG, OFFSETS[3], value)
    var imageMipTailStride: Long
        get() = seg.get(JAVA_LONG, OFFSETS[4])
        set(value) = seg.set(JAVA_LONG, OFFSETS[4], value)
}