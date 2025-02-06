package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_COPY_DESCRIPTOR_SET
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkCopyDescriptorSet(
    private val next: IStruct? = null,
    private val srcSet: VkDescriptorSet,
    private val srcBinding: Int,
    private val srcArrayElement: Int,
    private val dstSet: VkDescriptorSet,
    private val dstBinding: Int,
    private val dstArrayElement: Int,
    private val descriptorCount: Int
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        super.close()
    }

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, OFFSETS[0], VK_STRUCTURE_TYPE_COPY_DESCRIPTOR_SET)
        seg.set(ADDRESS, OFFSETS[1], next?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, OFFSETS[2], srcSet.ref())
        seg.set(JAVA_INT, OFFSETS[3], srcBinding)
        seg.set(JAVA_INT, OFFSETS[4], srcArrayElement)
        seg.set(ADDRESS, OFFSETS[5], dstSet.ref())
        seg.set(JAVA_INT, OFFSETS[6], dstBinding)
        seg.set(JAVA_INT, OFFSETS[7], dstArrayElement)
        seg.set(JAVA_INT, OFFSETS[8], descriptorCount)
    }
}