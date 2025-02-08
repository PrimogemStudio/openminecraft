package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import org.joml.Vector3i
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT
import java.lang.foreign.ValueLayout.JAVA_INT_UNALIGNED

class VkImageCopy(
    private val srcSubresource: VkImageSubresourceLayers,
    private val srcOffset: Vector3i,
    private val dstSubresource: VkImageSubresourceLayers,
    private val dstOffset: Vector3i,
    private val extent: Vector3i
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            VkImageSubresourceLayers.LAYOUT,
            JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED,
            VkImageSubresourceLayers.LAYOUT,
            JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = LAYOUT
    override fun construct(seg: MemorySegment) {
        srcSubresource.construct(seg.asSlice(OFFSETS[0], VkImageSubresourceLayers.LAYOUT.byteSize()))
        seg.set(JAVA_INT, OFFSETS[1], srcOffset.x)
        seg.set(JAVA_INT, OFFSETS[2], srcOffset.y)
        seg.set(JAVA_INT, OFFSETS[3], srcOffset.z)
        dstSubresource.construct(seg.asSlice(OFFSETS[4], VkImageSubresourceLayers.LAYOUT.byteSize()))
        seg.set(JAVA_INT, OFFSETS[5], dstOffset.x)
        seg.set(JAVA_INT, OFFSETS[6], dstOffset.y)
        seg.set(JAVA_INT, OFFSETS[7], dstOffset.z)
        seg.set(JAVA_INT, OFFSETS[8], extent.x)
        seg.set(JAVA_INT, OFFSETS[9], extent.y)
        seg.set(JAVA_INT, OFFSETS[10], extent.z)
    }
}