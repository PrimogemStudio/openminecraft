package com.primogemstudio.engine.bindings.vulkan.vk10

import com.primogemstudio.engine.foreign.cacheOffsets
import com.primogemstudio.engine.foreign.heap.IHeapObject
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT

class VkImageSubresource(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT,
            JAVA_INT,
            JAVA_INT
        )
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    var aspectMask: Int
        get() = seg.get(JAVA_INT, OFFSETS[0])
        set(value) = seg.set(JAVA_INT, OFFSETS[0], value)
    var mipmap: Int
        get() = seg.get(JAVA_INT, OFFSETS[1])
        set(value) = seg.set(JAVA_INT, OFFSETS[1], value)
    var arrayLayer: Int
        get() = seg.get(JAVA_INT, OFFSETS[2])
        set(value) = seg.set(JAVA_INT, OFFSETS[2], value)
}