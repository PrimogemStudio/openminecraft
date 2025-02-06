package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkDescriptorImageInfo(
    private val sampler: VkSampler,
    private val imageView: VkImageView,
    private val imageLayout: Int
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        seg.set(ADDRESS, OFFSETS[0], sampler.ref())
        seg.set(ADDRESS, OFFSETS[1], imageView.ref())
        seg.set(JAVA_INT, OFFSETS[2], imageLayout)
    }
}