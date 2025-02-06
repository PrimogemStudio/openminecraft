package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.loader.Platform.sizetLength
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkCommandBufferAllocateInfo(
    private val next: IStruct? = null,
    private val commandPool: VkCommandPool,
    private val level: Int,
    private val commandBufferCount: Int
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
        JAVA_INT
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, sizetLength() + 8L, commandPool.ref())
        seg.set(JAVA_INT, sizetLength() * 2 + 8L, level)
        seg.set(JAVA_INT, sizetLength() * 2 + 12L, commandBufferCount)
    }
}