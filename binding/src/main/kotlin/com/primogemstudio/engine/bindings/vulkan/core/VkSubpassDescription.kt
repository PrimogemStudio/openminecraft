package com.primogemstudio.engine.bindings.vulkan.core

import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
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
            seg.get(ADDRESS, OFFSETS[3]),
            VkAttachmentReference.LAYOUT
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[3], value.ref())
            seg.set(JAVA_INT, OFFSETS[2], value.length)
        }

}

/*
        seg.set(
            JAVA_INT,
            OFFSETS[4],
            min(colorAttachments?.length ?: 0, resolveAttachments?.length ?: 0)
        )
        seg.set(ADDRESS, OFFSETS[5], colorAttachments?.ref() ?: MemorySegment.NULL)
        seg.set(ADDRESS, OFFSETS[6], resolveAttachments?.ref() ?: MemorySegment.NULL)
        seg.set(ADDRESS, OFFSETS[7], depthStencilAttachment?.ref() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, OFFSETS[8], preserveAttachments?.length ?: 0)
        seg.set(ADDRESS, OFFSETS[9], preserveAttachments?.ref() ?: MemorySegment.NULL)
    }
}*/