package com.primogemstudio.engine.bindings.vulkan.vk10

import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_STRUCTURE_TYPE_WRITE_DESCRIPTOR_SET
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.HeapPointerArray
import com.primogemstudio.engine.interfaces.heap.HeapStructArray
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkWriteDescriptorSet(private val seg: MemorySegment) : IHeapObject(seg) {
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

    constructor() : this(Arena.ofAuto().allocate(LAYOUT)) {
        sType = VK_STRUCTURE_TYPE_WRITE_DESCRIPTOR_SET
    }

    var sType: Int
        get() = seg.get(JAVA_INT, OFFSETS[0])
        set(value) = seg.set(JAVA_INT, OFFSETS[0], value)
    var next: MemorySegment
        get() = seg.get(ADDRESS, OFFSETS[1]).reinterpret(Long.MAX_VALUE)
        set(value) = seg.set(ADDRESS, OFFSETS[1], value)
    var dstSet: VkDescriptorSet
        get() = VkDescriptorSet(seg.get(ADDRESS, OFFSETS[2]))
        set(value) = seg.set(ADDRESS, OFFSETS[2], value.ref())
    var dstBinding: Int
        get() = seg.get(JAVA_INT, OFFSETS[3])
        set(value) = seg.set(JAVA_INT, OFFSETS[3], value)
    var dstArrayElement: Int
        get() = seg.get(JAVA_INT, OFFSETS[4])
        set(value) = seg.set(JAVA_INT, OFFSETS[4], value)
    var descriptorType: Int
        get() = seg.get(JAVA_INT, OFFSETS[6])
        set(value) = seg.set(JAVA_INT, OFFSETS[6], value)
    var imageInfo: HeapStructArray<VkDescriptorImageInfo>
        get() = HeapStructArray(
            seg.get(JAVA_INT, OFFSETS[5]),
            seg.get(ADDRESS, OFFSETS[7]),
            VkDescriptorImageInfo.LAYOUT
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[7], value.ref())
            seg.set(JAVA_INT, OFFSETS[5], value.length)
        }
    var bufferInfo: HeapStructArray<VkDescriptorBufferInfo>
        get() = HeapStructArray(
            seg.get(JAVA_INT, OFFSETS[5]),
            seg.get(ADDRESS, OFFSETS[8]),
            VkDescriptorBufferInfo.LAYOUT
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[8], value.ref())
            seg.set(JAVA_INT, OFFSETS[5], value.length)
        }
    var texelBufferView: HeapPointerArray<VkBufferView>
        get() = HeapPointerArray(
            seg.get(JAVA_INT, OFFSETS[5]),
            seg.get(ADDRESS, OFFSETS[9])
        ) { VkBufferView(it) }
        set(value) {
            seg.set(ADDRESS, OFFSETS[9], value.ref())
            seg.set(JAVA_INT, OFFSETS[5], value.length)
        }
}