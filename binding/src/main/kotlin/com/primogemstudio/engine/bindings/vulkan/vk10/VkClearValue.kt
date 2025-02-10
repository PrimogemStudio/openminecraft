package com.primogemstudio.engine.bindings.vulkan.vk10

import com.primogemstudio.engine.interfaces.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment

class VkClearValue(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.unionLayout(
            VkClearColorValue.LAYOUT,
            VkClearDepthStencilValue.LAYOUT
        )
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var color: VkClearColorValue
        get() = VkClearColorValue(seg)
        set(value) {
            seg.copyFrom(value.ref())
        }
    var depthStencil: VkClearDepthStencilValue
        get() = VkClearDepthStencilValue(seg)
        set(value) {
            seg.copyFrom(value.ref())
        }
}