package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.heap.IHeapVar
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT

class VkPhysicalDeviceMemoryProperties : IHeapVar<MemorySegment> {
    private val seg: MemorySegment = Arena.ofAuto().allocate(
        MemoryLayout.structLayout(
            JAVA_INT,
            MemoryLayout.paddingLayout(256),
            JAVA_INT,
            MemoryLayout.paddingLayout(256)
        )
    )

    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val memoryTypeCount: UInt =
        seg.get(JAVA_INT, 0).toUInt()

    fun memoryType(idx: Int): VkMemoryType = VkMemoryType(seg.asSlice(4L + idx * 8L, 8))

    val memoryHeapCount: UInt =
        seg.get(JAVA_INT, 260).toUInt()

    fun memoryHeap(idx: Int): VkMemoryHeap = VkMemoryHeap(seg.asSlice(264L + idx * 16L, 16))
}