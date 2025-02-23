package com.primogemstudio.engine.bindings.vulkan.vk10

import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_STRUCTURE_TYPE_RENDER_PASS_CREATE_INFO
import com.primogemstudio.engine.foreign.align
import com.primogemstudio.engine.foreign.cacheOffsets
import com.primogemstudio.engine.foreign.heap.HeapStructArray
import com.primogemstudio.engine.foreign.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkRenderPassCreateInfo(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT)) {
        sType = VK_STRUCTURE_TYPE_RENDER_PASS_CREATE_INFO
    }

    var sType: Int
        get() = seg.get(JAVA_INT, OFFSETS[0])
        set(value) = seg.set(JAVA_INT, OFFSETS[0], value)
    var next: MemorySegment
        get() = seg.get(ADDRESS, OFFSETS[1]).reinterpret(Long.MAX_VALUE)
        set(value) = seg.set(ADDRESS, OFFSETS[1], value)
    var flag: Int
        get() = seg.get(JAVA_INT, OFFSETS[2])
        set(value) = seg.set(JAVA_INT, OFFSETS[2], value)
    var attachments: HeapStructArray<VkAttachmentDescription>
        get() = HeapStructArray(
            seg.get(JAVA_INT, OFFSETS[3]),
            seg.get(ADDRESS, OFFSETS[4]).reinterpret(Long.MAX_VALUE),
            VkAttachmentDescription.LAYOUT
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[4], value.ref())
            seg.set(JAVA_INT, OFFSETS[3], value.length)
        }
    var subpasses: HeapStructArray<VkSubpassDescription>
        get() = HeapStructArray(
            seg.get(JAVA_INT, OFFSETS[5]),
            seg.get(ADDRESS, OFFSETS[6]).reinterpret(Long.MAX_VALUE),
            VkSubpassDescription.LAYOUT
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[6], value.ref())
            seg.set(JAVA_INT, OFFSETS[5], value.length)
        }
    var dependencies: HeapStructArray<VkSubpassDependency>
        get() = HeapStructArray(
            seg.get(JAVA_INT, OFFSETS[7]),
            seg.get(ADDRESS, OFFSETS[8]).reinterpret(Long.MAX_VALUE),
            VkSubpassDependency.LAYOUT
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[8], value.ref())
            seg.set(JAVA_INT, OFFSETS[7], value.length)
        }
}