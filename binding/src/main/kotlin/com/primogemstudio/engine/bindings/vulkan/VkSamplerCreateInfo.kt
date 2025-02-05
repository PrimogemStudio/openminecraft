package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_SAMPLER_CREATE_INFO
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.loader.Platform.sizetLength
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
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_FLOAT,
        JAVA_INT,
        JAVA_FLOAT,
        JAVA_INT,
        JAVA_INT,
        JAVA_FLOAT,
        JAVA_FLOAT,
        JAVA_INT,
        JAVA_INT
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_SAMPLER_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(JAVA_INT, sizetLength() + 12L, magFilter)
        seg.set(JAVA_INT, sizetLength() + 16L, minFilter)
        seg.set(JAVA_INT, sizetLength() + 20L, mipmapMode)
        seg.set(JAVA_INT, sizetLength() + 24L, addressModeU)
        seg.set(JAVA_INT, sizetLength() + 28L, addressModeV)
        seg.set(JAVA_INT, sizetLength() + 32L, addressModeW)
        seg.set(JAVA_FLOAT, sizetLength() + 36L, mipLodBias)
        seg.set(JAVA_INT, sizetLength() + 40L, if (anisotropyEnable) 1 else 0)
        seg.set(JAVA_FLOAT, sizetLength() + 44L, maxAnisotropy)
        seg.set(JAVA_INT, sizetLength() + 48L, if (compareEnable) 1 else 0)
        seg.set(JAVA_INT, sizetLength() + 52L, compareOp)
        seg.set(JAVA_FLOAT, sizetLength() + 56L, minLod)
        seg.set(JAVA_FLOAT, sizetLength() + 60L, maxLod)
        seg.set(JAVA_INT, sizetLength() + 64L, borderColor)
        seg.set(JAVA_INT, sizetLength() + 68L, if (unnormalizedCoordinates) 1 else 0)
    }
}