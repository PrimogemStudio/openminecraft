package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.core.Vk10Funcs.VK_STRUCTURE_TYPE_SAMPLER_CREATE_INFO
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkSamplerCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val magFilter: Int,
    private val minFilter: Int,
    private val mipmapMode: Int,
    private val addressModeU: Int,
    private val addressModeV: Int,
    private val addressModeW: Int,
    private val mipLodBias: Float,
    private val anisotropyEnable: Boolean,
    private val maxAnisotropy: Float,
    private val compareEnable: Boolean,
    private val compareOp: Int,
    private val minLod: Float,
    private val maxLod: Float,
    private val borderColor: Int,
    private val unnormalizedCoordinates: Boolean
) : IStruct() {
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

    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, OFFSETS[0], VK_STRUCTURE_TYPE_SAMPLER_CREATE_INFO)
        seg.set(ADDRESS, OFFSETS[1], next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, OFFSETS[2], flags)
        seg.set(JAVA_INT, OFFSETS[3], magFilter)
        seg.set(JAVA_INT, OFFSETS[4], minFilter)
        seg.set(JAVA_INT, OFFSETS[5], mipmapMode)
        seg.set(JAVA_INT, OFFSETS[6], addressModeU)
        seg.set(JAVA_INT, OFFSETS[7], addressModeV)
        seg.set(JAVA_INT, OFFSETS[8], addressModeW)
        seg.set(JAVA_FLOAT, OFFSETS[9], mipLodBias)
        seg.set(JAVA_INT, OFFSETS[10], if (anisotropyEnable) 1 else 0)
        seg.set(JAVA_FLOAT, OFFSETS[11], maxAnisotropy)
        seg.set(JAVA_INT, OFFSETS[12], if (compareEnable) 1 else 0)
        seg.set(JAVA_INT, OFFSETS[13], compareOp)
        seg.set(JAVA_FLOAT, OFFSETS[14], minLod)
        seg.set(JAVA_FLOAT, OFFSETS[15], maxLod)
        seg.set(JAVA_INT, OFFSETS[16], borderColor)
        seg.set(JAVA_INT, OFFSETS[17], if (unnormalizedCoordinates) 1 else 0)
    }
}