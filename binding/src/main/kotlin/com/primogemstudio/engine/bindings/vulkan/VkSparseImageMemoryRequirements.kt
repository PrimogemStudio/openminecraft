package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.heap.IHeapVar
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT
import java.lang.foreign.ValueLayout.JAVA_LONG

class VkSparseImageMemoryRequirements(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val formatProperties: VkSparseImageFormatProperties get() = VkSparseImageFormatProperties(seg.asSlice(0, 20))
    val imageMipTailFirstLod: UInt get() = seg.get(JAVA_INT, 20).toUInt()
    val imageMipTailSize: Long get() = seg.get(JAVA_LONG, 24)
    val imageMipTailOffset: Long get() = seg.get(JAVA_LONG, 32)
    val imageMipTailStride: Long get() = seg.get(JAVA_LONG, 40)
}