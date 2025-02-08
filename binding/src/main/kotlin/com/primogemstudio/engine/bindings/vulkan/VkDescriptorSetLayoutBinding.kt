package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.core.VkSampler
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.interfaces.struct.PointerArrayStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkDescriptorSetLayoutBinding(
    private val binding: Int,
    private val descriptorType: Int,
    private val descriptorCount: Int,
    private val stageFlags: Int,
    private val immutableSamplers: PointerArrayStruct<VkSampler>? = null
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, OFFSETS[0], binding)
        seg.set(JAVA_INT, OFFSETS[1], descriptorType)
        seg.set(JAVA_INT, OFFSETS[2], descriptorCount)
        seg.set(JAVA_INT, OFFSETS[3], stageFlags)
        seg.set(ADDRESS, OFFSETS[4], immutableSamplers?.pointer() ?: MemorySegment.NULL)
    }
}