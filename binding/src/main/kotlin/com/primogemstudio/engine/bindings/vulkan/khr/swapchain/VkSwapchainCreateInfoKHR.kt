package com.primogemstudio.engine.bindings.vulkan.khr.swapchain

import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.ADDRESS_UNALIGNED
import java.lang.foreign.ValueLayout.JAVA_INT_UNALIGNED

// TODO Complete structure
class VkSwapchainCreateInfoKHR(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,

            ).align()
    }
}