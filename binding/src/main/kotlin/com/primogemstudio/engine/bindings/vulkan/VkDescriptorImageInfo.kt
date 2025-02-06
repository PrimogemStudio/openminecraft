package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.loader.Platform.sizetLength
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkDescriptorImageInfo(
    private val sampler: VkSampler,
    private val imageView: VkImageView,
    private val imageLayout: Int
) : IStruct() {
    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        ADDRESS,
        ADDRESS,
        JAVA_LONG
    )

    override fun construct(seg: MemorySegment) {
        seg.set(ADDRESS, 0, sampler.ref())
        seg.set(ADDRESS, sizetLength() * 1L, imageView.ref())
        seg.set(JAVA_INT, sizetLength() * 2L, imageLayout)
    }
}