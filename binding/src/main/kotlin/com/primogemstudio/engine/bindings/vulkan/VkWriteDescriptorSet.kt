package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_WRITE_DESCRIPTOR_SET
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.ArrayStruct
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.interfaces.struct.PointerArrayStruct
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
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

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

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, OFFSETS[0], VK_STRUCTURE_TYPE_WRITE_DESCRIPTOR_SET)
        seg.set(ADDRESS, OFFSETS[1], next?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, OFFSETS[2], dstSet.ref())
        seg.set(JAVA_INT, OFFSETS[3], dstBinding)
        seg.set(JAVA_INT, OFFSETS[4], dstArrayElement)
        seg.set(
            JAVA_INT,
            OFFSETS[5],
            min(imageInfo.arr.size, min(bufferInfo.arr.size, texelBufferView.arr.size))
        )
        seg.set(JAVA_INT, OFFSETS[6], descriptorType)
        seg.set(ADDRESS, OFFSETS[7], imageInfo.pointer())
        seg.set(ADDRESS, OFFSETS[8], bufferInfo.pointer())
        seg.set(ADDRESS, OFFSETS[9], texelBufferView.pointer())
    }
}