package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.ArrayStruct
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.interfaces.struct.IntArrayStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*
import kotlin.math.min

class VkSubpassDescription(
    private val flags: Int = 0,
    private val pipelineBindPoint: Int,
    private val inputAttachments: ArrayStruct<VkAttachmentReference>? = null,
    private val colorAttachments: ArrayStruct<VkAttachmentReference>? = null,
    private val resolveAttachments: ArrayStruct<VkAttachmentReference>? = null,
    private val depthStencilAttachment: VkAttachmentReference? = null,
    private val preserveAttachments: IntArrayStruct? = null
) : IStruct() {
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

    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, OFFSETS[0], flags)
        seg.set(JAVA_INT, OFFSETS[1], pipelineBindPoint)
        seg.set(JAVA_INT, OFFSETS[2], inputAttachments?.arr?.size ?: 0)
        seg.set(ADDRESS, OFFSETS[3], inputAttachments?.pointer() ?: MemorySegment.NULL)
        seg.set(
            JAVA_INT,
            OFFSETS[4],
            min(colorAttachments?.arr?.size ?: 0, resolveAttachments?.arr?.size ?: 0)
        )
        seg.set(ADDRESS, OFFSETS[5], colorAttachments?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, OFFSETS[6], resolveAttachments?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, OFFSETS[7], depthStencilAttachment?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, OFFSETS[8], preserveAttachments?.arr?.size ?: 0)
        seg.set(ADDRESS, OFFSETS[9], preserveAttachments?.pointer() ?: MemorySegment.NULL)
    }
}