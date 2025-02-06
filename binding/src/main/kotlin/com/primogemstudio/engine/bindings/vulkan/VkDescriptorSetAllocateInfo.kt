package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_DESCRIPTOR_SET_ALLOCATE_INFO
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.interfaces.struct.PointerArrayStruct
import com.primogemstudio.engine.loader.Platform.sizetLength
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkDescriptorSetAllocateInfo(
    private val next: IStruct? = null,
    private val descriptorPool: VkDescriptorPool,
    private val setLayouts: PointerArrayStruct<VkDescriptorSetLayout>
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
        ADDRESS,
        JAVA_LONG,
        ADDRESS
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_DESCRIPTOR_SET_ALLOCATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, sizetLength() + 8L, descriptorPool.ref())
        seg.set(JAVA_INT, sizetLength() * 2 + 8L, setLayouts.arr.size)
        seg.set(ADDRESS, sizetLength() * 2 + 16L, setLayouts.pointer())
    }
}