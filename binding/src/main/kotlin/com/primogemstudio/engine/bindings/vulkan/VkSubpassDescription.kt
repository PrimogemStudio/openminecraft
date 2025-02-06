package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.struct.ArrayStruct
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.interfaces.struct.IntArrayStruct
import com.primogemstudio.engine.loader.Platform.sizetLength
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
    init {
        construct(seg)
    }

    override fun close() {
        inputAttachments?.close()
        colorAttachments?.close()
        resolveAttachments?.close()
        depthStencilAttachment?.close()
        preserveAttachments?.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_INT,
        JAVA_INT,
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG,
        ADDRESS,
        ADDRESS,
        ADDRESS,
        JAVA_LONG,
        ADDRESS
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, flags)
        seg.set(JAVA_INT, 4, pipelineBindPoint)
        seg.set(JAVA_INT, 8, inputAttachments?.arr?.size ?: 0)
        seg.set(ADDRESS, 16, inputAttachments?.pointer() ?: MemorySegment.NULL)
        seg.set(
            JAVA_INT,
            sizetLength() + 16L,
            min(colorAttachments?.arr?.size ?: 0, resolveAttachments?.arr?.size ?: 0)
        )
        seg.set(ADDRESS, sizetLength() + 24L, colorAttachments?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, sizetLength() * 2 + 24L, resolveAttachments?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, sizetLength() * 3 + 24L, depthStencilAttachment?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() * 4 + 24L, preserveAttachments?.arr?.size ?: 0)
        seg.set(ADDRESS, sizetLength() * 4 + 32L, preserveAttachments?.pointer() ?: MemorySegment.NULL)
    }
}