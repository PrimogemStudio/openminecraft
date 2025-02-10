package com.primogemstudio.engine.bindings.vulkan.vk10

import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_STRUCTURE_TYPE_SAMPLER_CREATE_INFO
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkSamplerCreateInfo(private val seg: MemorySegment) : IHeapObject(seg) {
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
            JAVA_INT_UNALIGNED,
            JAVA_FLOAT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_FLOAT_UNALIGNED,
            JAVA_FLOAT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT)) {
        sType = VK_STRUCTURE_TYPE_SAMPLER_CREATE_INFO
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
    var magFilter: Int
        get() = seg.get(JAVA_INT, OFFSETS[3])
        set(value) = seg.set(JAVA_INT, OFFSETS[3], value)
    var minFilter: Int
        get() = seg.get(JAVA_INT, OFFSETS[4])
        set(value) = seg.set(JAVA_INT, OFFSETS[4], value)
    var mipmapMode: Int
        get() = seg.get(JAVA_INT, OFFSETS[5])
        set(value) = seg.set(JAVA_INT, OFFSETS[5], value)
    var addressModeU: Int
        get() = seg.get(JAVA_INT, OFFSETS[6])
        set(value) = seg.set(JAVA_INT, OFFSETS[6], value)
    var addressModeV: Int
        get() = seg.get(JAVA_INT, OFFSETS[7])
        set(value) = seg.set(JAVA_INT, OFFSETS[7], value)
    var addressModeW: Int
        get() = seg.get(JAVA_INT, OFFSETS[8])
        set(value) = seg.set(JAVA_INT, OFFSETS[8], value)
    var mipLodBias: Float
        get() = seg.get(JAVA_FLOAT, OFFSETS[9])
        set(value) = seg.set(JAVA_FLOAT, OFFSETS[9], value)
    var anisotropyEnable: Boolean
        get() = seg.get(JAVA_INT, OFFSETS[10]) != 0
        set(value) = seg.set(JAVA_INT, OFFSETS[10], if (value) 1 else 0)
    var maxAnisotropy: Float
        get() = seg.get(JAVA_FLOAT, OFFSETS[11])
        set(value) = seg.set(JAVA_FLOAT, OFFSETS[11], value)
    var compareEnable: Boolean
        get() = seg.get(JAVA_INT, OFFSETS[12]) != 0
        set(value) = seg.set(JAVA_INT, OFFSETS[12], if (value) 1 else 0)
    var compareOp: Int
        get() = seg.get(JAVA_INT, OFFSETS[13])
        set(value) = seg.set(JAVA_INT, OFFSETS[13], value)
    var minLod: Float
        get() = seg.get(JAVA_FLOAT, OFFSETS[14])
        set(value) = seg.set(JAVA_FLOAT, OFFSETS[14], value)
    var maxLod: Float
        get() = seg.get(JAVA_FLOAT, OFFSETS[15])
        set(value) = seg.set(JAVA_FLOAT, OFFSETS[15], value)
    var borderColor: Int
        get() = seg.get(JAVA_INT, OFFSETS[16])
        set(value) = seg.set(JAVA_INT, OFFSETS[16], value)
    var unnormalizedCoordinates: Boolean
        get() = seg.get(JAVA_INT, OFFSETS[17]) != 0
        set(value) = seg.set(JAVA_INT, OFFSETS[17], if (value) 1 else 0)
}