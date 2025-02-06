package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_COPY_DESCRIPTOR_SET
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.loader.Platform.sizetLength
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
        JAVA_INT,
        JAVA_INT,
        ADDRESS,
        JAVA_INT,
        JAVA_INT,
        JAVA_LONG
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_COPY_DESCRIPTOR_SET)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, sizetLength() + 8L, srcSet.ref())
        seg.set(JAVA_INT, sizetLength() * 2 + 8L, srcBinding)
        seg.set(JAVA_INT, sizetLength() * 2 + 12L, srcArrayElement)
        seg.set(ADDRESS, sizetLength() * 2 + 16L, dstSet.ref())
        seg.set(JAVA_INT, sizetLength() * 3 + 16L, dstBinding)
        seg.set(JAVA_INT, sizetLength() * 3 + 20L, dstArrayElement)
        seg.set(JAVA_INT, sizetLength() * 3 + 24L, descriptorCount)
    }
}