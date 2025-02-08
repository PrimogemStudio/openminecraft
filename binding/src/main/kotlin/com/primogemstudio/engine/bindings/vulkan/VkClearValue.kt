package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment

class VkClearValue(
    private val color: VkClearColorValue? = null,
    private val depthStencil: VkClearDepthStencilValue? = null
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.unionLayout(
            VkClearColorValue.LAYOUT,
            VkClearDepthStencilValue.LAYOUT
        )
    }

    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = LAYOUT
    override fun construct(seg: MemorySegment) {
        color?.construct(seg)
        depthStencil?.construct(seg)
    }
}