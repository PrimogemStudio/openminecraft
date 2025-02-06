package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_WRITE_DESCRIPTOR_SET
import com.primogemstudio.engine.interfaces.struct.ArrayStruct
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.interfaces.struct.PointerArrayStruct
import com.primogemstudio.engine.loader.Platform.sizetLength
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*
import kotlin.math.min

class VkWriteDescriptorSet(
    private val next: IStruct? = null,
    private val dstSet: VkDescriptorSet,
    private val dstBinding: Int,
    private val dstArrayElement: Int,
    private val descriptorType: Int,
    private val imageInfo: ArrayStruct<VkDescriptorImageInfo>,
    private val bufferInfo: ArrayStruct<VkDescriptorBufferInfo>,
    private val texelBufferView: PointerArrayStruct<VkBufferView>
) : IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        imageInfo.close()
        bufferInfo.close()
        texelBufferView.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        ADDRESS,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        ADDRESS,
        ADDRESS,
        ADDRESS
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_WRITE_DESCRIPTOR_SET)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, sizetLength() + 8L, dstSet.ref())
        seg.set(JAVA_INT, sizetLength() * 2 + 8L, dstBinding)
        seg.set(JAVA_INT, sizetLength() * 2 + 12L, dstArrayElement)
        seg.set(
            JAVA_INT,
            sizetLength() * 2 + 16L,
            min(imageInfo.arr.size, min(bufferInfo.arr.size, texelBufferView.arr.size))
        )
        seg.set(JAVA_INT, sizetLength() * 2 + 20L, descriptorType)
        seg.set(ADDRESS, sizetLength() * 2 + 24L, imageInfo.pointer())
        seg.set(ADDRESS, sizetLength() * 3 + 24L, bufferInfo.pointer())
        seg.set(ADDRESS, sizetLength() * 4 + 24L, texelBufferView.pointer())
    }
}