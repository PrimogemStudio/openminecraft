package com.primogemstudio.engine.bindings.vulkan.vk10

import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_STRUCTURE_TYPE_PIPELINE_RASTERIZATION_STATE_CREATE_INFO
import com.primogemstudio.engine.foreign.align
import com.primogemstudio.engine.foreign.cacheOffsets
import com.primogemstudio.engine.foreign.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkPipelineRasterizationStateCreateInfo(private val seg: MemorySegment) : IHeapObject(seg) {
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
            JAVA_INT_UNALIGNED,
            JAVA_FLOAT_UNALIGNED,
            JAVA_FLOAT_UNALIGNED,
            JAVA_FLOAT_UNALIGNED,
            JAVA_FLOAT_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT)) {
        sType = VK_STRUCTURE_TYPE_PIPELINE_RASTERIZATION_STATE_CREATE_INFO
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
    var depthClampEnable: Boolean
        get() = seg.get(JAVA_INT, OFFSETS[3]) != 0
        set(value) = seg.set(JAVA_INT, OFFSETS[3], if (value) 1 else 0)
    var rasterizerDiscardEnable: Boolean
        get() = seg.get(JAVA_INT, OFFSETS[4]) != 0
        set(value) = seg.set(JAVA_INT, OFFSETS[4], if (value) 1 else 0)
    var polygonMode: Int
        get() = seg.get(JAVA_INT, OFFSETS[5])
        set(value) = seg.set(JAVA_INT, OFFSETS[5], value)
    var cullMode: Int
        get() = seg.get(JAVA_INT, OFFSETS[6])
        set(value) = seg.set(JAVA_INT, OFFSETS[6], value)
    var frontFace: Int
        get() = seg.get(JAVA_INT, OFFSETS[7])
        set(value) = seg.set(JAVA_INT, OFFSETS[7], value)
    var depthBiasEnable: Boolean
        get() = seg.get(JAVA_INT, OFFSETS[8]) != 0
        set(value) = seg.set(JAVA_INT, OFFSETS[8], if (value) 1 else 0)
    var depthBiasConstantFactor: Float
        get() = seg.get(JAVA_FLOAT, OFFSETS[9])
        set(value) = seg.set(JAVA_FLOAT, OFFSETS[9], value)
    var depthBiasClamp: Float
        get() = seg.get(JAVA_FLOAT, OFFSETS[10])
        set(value) = seg.set(JAVA_FLOAT, OFFSETS[10], value)
    var depthBiasSlopeFactor: Float
        get() = seg.get(JAVA_FLOAT, OFFSETS[11])
        set(value) = seg.set(JAVA_FLOAT, OFFSETS[11], value)
    var lineWidth: Float
        get() = seg.get(JAVA_FLOAT, OFFSETS[12])
        set(value) = seg.set(JAVA_FLOAT, OFFSETS[12], value)
}