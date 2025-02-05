package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.loader.Platform.sizetLength
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
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        range.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG,
        JAVA_LONG,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT, JAVA_INT, JAVA_INT, JAVA_INT,
        MemoryLayout.paddingLayout(20 + 4)
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(ADDRESS, sizetLength() + 16L, image.ref())
        seg.set(JAVA_INT, sizetLength() + 24L, viewType)
        seg.set(JAVA_INT, sizetLength() + 28L, format)

        seg.set(JAVA_INT, sizetLength() + 32L, components.x)
        seg.set(JAVA_INT, sizetLength() + 36L, components.y)
        seg.set(JAVA_INT, sizetLength() + 40L, components.z)
        seg.set(JAVA_INT, sizetLength() + 44L, components.w)

        range.construct(seg.asSlice(sizetLength() + 48L, 16L))
    }
}