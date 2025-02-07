package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import org.joml.Vector3i
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkBufferImageCopy(
    private val bufferOffset: Long,
    private val bufferRowLength: Int,
    private val bufferImageHeight: Int,
    private val imageSubresourceLayers: VkImageSubresourceLayers,
    private val imageOffset: Vector3i,
    private val imageExtent: Vector3i
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_LONG_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            VkImageSubresourceLayers.LAYOUT,
            JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED,
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = LAYOUT
    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_LONG, OFFSETS[0], bufferOffset)
        seg.set(JAVA_INT, OFFSETS[1], bufferRowLength)
        seg.set(JAVA_INT, OFFSETS[2], bufferImageHeight)
        imageSubresourceLayers.construct(seg.asSlice(OFFSETS[3], VkImageSubresourceLayers.LAYOUT.byteSize()))
        seg.set(JAVA_INT, OFFSETS[4], imageOffset.x)
        seg.set(JAVA_INT, OFFSETS[5], imageOffset.y)
        seg.set(JAVA_INT, OFFSETS[6], imageOffset.z)
        seg.set(JAVA_INT, OFFSETS[7], imageExtent.x)
        seg.set(JAVA_INT, OFFSETS[8], imageExtent.y)
        seg.set(JAVA_INT, OFFSETS[9], imageExtent.z)
    }
}