package com.primogemstudio.engine.bindings.vulkan.vk10

import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_STRUCTURE_TYPE_PIPELINE_DEPTH_STENCIL_STATE_CREATE_INFO
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkPipelineDepthStencilStateCreateInfo(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            VkStencilOpState.LAYOUT,
            VkStencilOpState.LAYOUT,
            JAVA_FLOAT_UNALIGNED,
            JAVA_FLOAT_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT)) {
        sType = VK_STRUCTURE_TYPE_PIPELINE_DEPTH_STENCIL_STATE_CREATE_INFO
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
    var depthTestEnable: Boolean
        get() = seg.get(JAVA_INT, OFFSETS[3]) != 0
        set(value) = seg.set(JAVA_INT, OFFSETS[3], if (value) 1 else 0)
    var depthWriteEnable: Boolean
        get() = seg.get(JAVA_INT, OFFSETS[4]) != 0
        set(value) = seg.set(JAVA_INT, OFFSETS[4], if (value) 1 else 0)
    var depthCompareOp: Int
        get() = seg.get(JAVA_INT, OFFSETS[5])
        set(value) = seg.set(JAVA_INT, OFFSETS[5], value)
    var depthBoundsTestEnable: Boolean
        get() = seg.get(JAVA_INT, OFFSETS[6]) != 0
        set(value) = seg.set(JAVA_INT, OFFSETS[6], if (value) 1 else 0)
    var stencilTestEnable: Boolean
        get() = seg.get(JAVA_INT, OFFSETS[7]) != 0
        set(value) = seg.set(JAVA_INT, OFFSETS[7], if (value) 1 else 0)
    var front: VkStencilOpState
        get() = VkStencilOpState(seg.asSlice(OFFSETS[8], VkStencilOpState.LAYOUT.byteSize()))
        set(value) {
            seg.asSlice(OFFSETS[8], VkStencilOpState.LAYOUT.byteSize()).copyFrom(value.ref())
        }
    var back: VkStencilOpState
        get() = VkStencilOpState(seg.asSlice(OFFSETS[9], VkStencilOpState.LAYOUT.byteSize()))
        set(value) {
            seg.asSlice(OFFSETS[9], VkStencilOpState.LAYOUT.byteSize()).copyFrom(value.ref())
        }
    var minDepthBounds: Float
        get() = seg.get(JAVA_FLOAT, OFFSETS[10])
        set(value) = seg.set(JAVA_FLOAT, OFFSETS[10], value)
    var maxDepthBounds: Float
        get() = seg.get(JAVA_FLOAT, OFFSETS[11])
        set(value) = seg.set(JAVA_FLOAT, OFFSETS[11], value)
}