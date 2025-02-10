package com.primogemstudio.engine.bindings.vulkan.core

import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkCommandBufferInheritanceInfo(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var sType: Int
        get() = seg.get(JAVA_INT, OFFSETS[0])
        set(value) = seg.set(JAVA_INT, OFFSETS[0], value)
    var next: MemorySegment
        get() = seg.get(ADDRESS, OFFSETS[1]).reinterpret(Long.MAX_VALUE)
        set(value) = seg.set(ADDRESS, OFFSETS[1], value)
    var renderPass: VkRenderPass
        get() = VkRenderPass(seg.get(ADDRESS, OFFSETS[2]))
        set(value) = seg.set(ADDRESS, OFFSETS[2], value.ref())
    var subpass: Int
        get() = seg.get(JAVA_INT, OFFSETS[3])
        set(value) = seg.set(JAVA_INT, OFFSETS[3], value)
    var framebuffer: VkFramebuffer
        get() = VkFramebuffer(seg.get(ADDRESS, OFFSETS[4]))
        set(value) = seg.set(ADDRESS, OFFSETS[4], value.ref())
    var occlusionQueryEnable: Boolean
        get() = seg.get(JAVA_INT, OFFSETS[5]) != 0
        set(value) = seg.set(JAVA_INT, OFFSETS[5], if (value) 1 else 0)
    var queryFlags: Int
        get() = seg.get(JAVA_INT, OFFSETS[6])
        set(value) = seg.set(JAVA_INT, OFFSETS[6], value)
    var pipelineStatistics: Int
        get() = seg.get(JAVA_INT, OFFSETS[7])
        set(value) = seg.set(JAVA_INT, OFFSETS[7], value)
}