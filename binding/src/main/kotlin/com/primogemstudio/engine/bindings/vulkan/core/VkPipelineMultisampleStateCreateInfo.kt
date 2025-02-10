package com.primogemstudio.engine.bindings.vulkan.core

import com.primogemstudio.engine.bindings.vulkan.core.Vk10Funcs.VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.HeapIntArray
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkPipelineMultisampleStateCreateInfo(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_LONG_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_FLOAT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT)) {
        sType = VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO
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
    var rasterizationSamples: Int
        get() = seg.get(JAVA_INT, OFFSETS[3])
        set(value) = seg.set(JAVA_INT, OFFSETS[3], value)
    var sampleShadingEnable: Boolean
        get() = seg.get(JAVA_INT, OFFSETS[4]) != 0
        set(value) = seg.set(JAVA_INT, OFFSETS[4], if (value) 1 else 0)
    var minSampleShading: Float
        get() = seg.get(JAVA_FLOAT, OFFSETS[5])
        set(value) = seg.set(JAVA_FLOAT, OFFSETS[5], value)
    var sampleMask: HeapIntArray
        get() = HeapIntArray(
            Int.MAX_VALUE,
            seg.get(ADDRESS, OFFSETS[6])
        )
        set(value) = seg.set(ADDRESS, OFFSETS[6], value.ref())
    var alphaToCoverageEnable: Boolean
        get() = seg.get(JAVA_INT, OFFSETS[7]) != 0
        set(value) = seg.set(JAVA_INT, OFFSETS[7], if (value) 1 else 0)
    var alphaToOneEnable: Boolean
        get() = seg.get(JAVA_INT, OFFSETS[8]) != 0
        set(value) = seg.set(JAVA_INT, OFFSETS[8], if (value) 1 else 0)
}