package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_PIPELINE_VIEWPORT_STATE_CREATE_INFO
import com.primogemstudio.engine.interfaces.struct.ArrayStruct
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.loader.Platform.sizetLength
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkPipelineViewportStateCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val viewports: ArrayStruct<VkViewport>,
    private val scissors: ArrayStruct<VkRect2D>
) : IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        viewports.close()
        scissors.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_INT,
        JAVA_INT,
        ADDRESS,
        JAVA_LONG,
        ADDRESS
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_PIPELINE_VIEWPORT_STATE_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(JAVA_INT, sizetLength() + 12L, viewports.arr.size)
        seg.set(ADDRESS, sizetLength() + 16L, viewports.pointer())
        seg.set(JAVA_INT, sizetLength() * 2 + 16L, scissors.arr.size)
        seg.set(ADDRESS, sizetLength() * 2 + 24L, scissors.pointer())
    }
}