package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_DESCRIPTOR_SET_ALLOCATE_INFO
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.interfaces.struct.PointerArrayStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkDescriptorSetAllocateInfo(
    private val next: IStruct? = null,
    private val descriptorPool: VkDescriptorPool,
    private val setLayouts: PointerArrayStruct<VkDescriptorSetLayout>
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED
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

    fun count(): Int = setLayouts.arr.size

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, OFFSETS[0], VK_STRUCTURE_TYPE_DESCRIPTOR_SET_ALLOCATE_INFO)
        seg.set(ADDRESS, OFFSETS[1], next?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, OFFSETS[2], descriptorPool.ref())
        seg.set(JAVA_INT, OFFSETS[3], setLayouts.arr.size)
        seg.set(ADDRESS, OFFSETS[4], setLayouts.pointer())
    }
}