package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.core.Vk10Funcs.VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO
import com.primogemstudio.engine.bindings.vulkan.core.VkImage
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import org.joml.Vector4i
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkImageViewCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val image: VkImage,
    private val viewType: Int,
    private val format: Int,
    private val components: Vector4i,
    private val range: VkImageSubresourceRange
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED,
            VkImageSubresourceRange.LAYOUT
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        range.close()
        super.close()
    }

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, OFFSETS[0], VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO)
        seg.set(ADDRESS, OFFSETS[1], next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, OFFSETS[2], flags)
        seg.set(ADDRESS, OFFSETS[3], image.ref())
        seg.set(JAVA_INT, OFFSETS[4], viewType)
        seg.set(JAVA_INT, OFFSETS[5], format)
        seg.set(JAVA_INT, OFFSETS[6], components.x)
        seg.set(JAVA_INT, OFFSETS[7], components.y)
        seg.set(JAVA_INT, OFFSETS[8], components.z)
        seg.set(JAVA_INT, OFFSETS[9], components.w)
        range.construct(seg.asSlice(OFFSETS[10], VkImageSubresourceRange.LAYOUT.byteSize()))
    }
}