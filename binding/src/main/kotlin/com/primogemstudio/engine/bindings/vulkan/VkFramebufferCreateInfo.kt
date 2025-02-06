package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_FRAMEBUFFER_CREATE_INFO
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.interfaces.struct.PointerArrayStruct
import com.primogemstudio.engine.loader.Platform.sizetLength
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkFramebufferCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val renderPass: VkRenderPass,
    private val attachments: PointerArrayStruct<VkImageView>? = null,
    private val width: Int,
    private val height: Int,
    private val layers: Int
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
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG,
        ADDRESS,
        JAVA_INT,
        JAVA_INT,
        JAVA_LONG
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_FRAMEBUFFER_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(ADDRESS, sizetLength() + 16L, renderPass.ref())
        seg.set(JAVA_INT, sizetLength() * 2 + 16L, attachments?.arr?.size ?: 0)
        seg.set(ADDRESS, sizetLength() * 2 + 24L, attachments?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() * 3 + 24L, width)
        seg.set(JAVA_INT, sizetLength() * 3 + 28L, height)
        seg.set(JAVA_INT, sizetLength() * 3 + 32L, layers)
    }
}