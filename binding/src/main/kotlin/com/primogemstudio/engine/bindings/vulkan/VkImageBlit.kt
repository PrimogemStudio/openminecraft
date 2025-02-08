package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import org.joml.Vector3i
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT
import java.lang.foreign.ValueLayout.JAVA_INT_UNALIGNED

class VkImageBlit(
    private val srcSubresource: VkImageSubresourceLayers,
    private val srcOffset1: Vector3i,
    private val srcOffset2: Vector3i,
    private val dstSubresource: VkImageSubresourceLayers,
    private val dstOffset1: Vector3i,
    private val dstOffset2: Vector3i
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            VkImageSubresourceLayers.LAYOUT,
            JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED,
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
        seg.set(JAVA_INT, OFFSETS[1], srcOffset1.x)
        seg.set(JAVA_INT, OFFSETS[2], srcOffset1.y)
        seg.set(JAVA_INT, OFFSETS[3], srcOffset1.z)
        seg.set(JAVA_INT, OFFSETS[4], srcOffset2.x)
        seg.set(JAVA_INT, OFFSETS[5], srcOffset2.y)
        seg.set(JAVA_INT, OFFSETS[6], srcOffset2.z)
        dstSubresource.construct(seg.asSlice(OFFSETS[7], VkImageSubresourceLayers.LAYOUT.byteSize()))
        seg.set(JAVA_INT, OFFSETS[8], dstOffset1.x)
        seg.set(JAVA_INT, OFFSETS[9], dstOffset1.y)
        seg.set(JAVA_INT, OFFSETS[10], dstOffset1.z)
        seg.set(JAVA_INT, OFFSETS[11], dstOffset2.x)
        seg.set(JAVA_INT, OFFSETS[12], dstOffset2.y)
        seg.set(JAVA_INT, OFFSETS[13], dstOffset2.z)
    }
}