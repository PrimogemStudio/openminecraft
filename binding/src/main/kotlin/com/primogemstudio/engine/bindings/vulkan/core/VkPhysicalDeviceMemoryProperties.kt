package com.primogemstudio.engine.bindings.vulkan.core

import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT

class VkPhysicalDeviceMemoryProperties(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT,
            MemoryLayout.sequenceLayout(32, VkMemoryType.LAYOUT),
            JAVA_INT,
            MemoryLayout.sequenceLayout(16, VkMemoryHeap.LAYOUT)
        )
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var memoryTypeCount: Int
        get() = seg.get(JAVA_INT, OFFSETS[0])
        set(value) = seg.set(JAVA_INT, OFFSETS[0], value)

    fun memoryType(idx: Int): VkMemoryType =
        VkMemoryType(seg.asSlice(OFFSETS[1] + idx * VkMemoryType.LAYOUT.byteSize(), VkMemoryType.LAYOUT.byteSize()))

    var memoryHeapCount: Int
        get() = seg.get(JAVA_INT, OFFSETS[2])
        set(value) = seg.set(JAVA_INT, OFFSETS[2], value)

    fun memoryHeap(idx: Int): VkMemoryHeap =
        VkMemoryHeap(seg.asSlice(OFFSETS[3] + idx * VkMemoryHeap.LAYOUT.byteSize(), VkMemoryHeap.LAYOUT.byteSize()))
}