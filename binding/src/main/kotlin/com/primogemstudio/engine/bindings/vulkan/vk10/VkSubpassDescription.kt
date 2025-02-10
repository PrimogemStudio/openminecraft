package com.primogemstudio.engine.bindings.vulkan.vk10

import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.HeapIntArray
import com.primogemstudio.engine.interfaces.heap.HeapStructArray
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkSubpassDescription(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var flags: Int
        get() = seg.get(JAVA_INT, OFFSETS[0])
        set(value) = seg.set(JAVA_INT, OFFSETS[0], value)
    var pipelineBindPoint: Int
        get() = seg.get(JAVA_INT, OFFSETS[1])
        set(value) = seg.set(JAVA_INT, OFFSETS[1], value)
    var inputAttachments: HeapStructArray<VkAttachmentReference>
        get() = HeapStructArray(
            seg.get(JAVA_INT, OFFSETS[2]),
            seg.get(ADDRESS, OFFSETS[3]).reinterpret(Long.MAX_VALUE),
            VkAttachmentReference.LAYOUT
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[3], value.ref())
            seg.set(JAVA_INT, OFFSETS[2], value.length)
        }
    var colorAttachments: HeapStructArray<VkAttachmentReference>
        get() = HeapStructArray(
            seg.get(JAVA_INT, OFFSETS[4]),
            seg.get(ADDRESS, OFFSETS[5]).reinterpret(Long.MAX_VALUE),
            VkAttachmentReference.LAYOUT
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[5], value.ref())
            seg.set(JAVA_INT, OFFSETS[4], value.length)
        }
    var resolveAttachments: HeapStructArray<VkAttachmentReference>
        get() = HeapStructArray(
            seg.get(JAVA_INT, OFFSETS[4]),
            seg.get(ADDRESS, OFFSETS[6]).reinterpret(Long.MAX_VALUE),
            VkAttachmentReference.LAYOUT
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[6], value.ref())
            seg.set(JAVA_INT, OFFSETS[4], value.length)
        }
    var depthStencilAttachment: VkAttachmentReference
        get() = VkAttachmentReference(seg.get(ADDRESS, OFFSETS[7]).reinterpret(Long.MAX_VALUE))
        set(value) = seg.set(ADDRESS, OFFSETS[7], value.ref())
    var preserveAttachments: HeapIntArray
        get() = HeapIntArray(
            seg.get(JAVA_INT, OFFSETS[8]),
            seg.get(ADDRESS, OFFSETS[9]).reinterpret(Long.MAX_VALUE)
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[9], value.ref())
            seg.set(JAVA_INT, OFFSETS[8], value.length)
        }
}