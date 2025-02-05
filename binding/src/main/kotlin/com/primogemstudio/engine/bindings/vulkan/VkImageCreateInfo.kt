package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_IMAGE_CREATE_INFO
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.interfaces.struct.IntArrayStruct
import com.primogemstudio.engine.loader.Platform.sizetLength
import org.joml.Vector3i
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkImageCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val imageType: Int,
    private val format: Int,
    private val extent: Vector3i,
    private val mipLevels: Int,
    private val arrayLayers: Int,
    private val samples: Int,
    private val tiling: Int,
    private val usage: Int,
    private val sharingMode: Int,
    private val queueFamilyIndices: IntArrayStruct,
    private val initialLayout: Int
) : IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        queueFamilyIndices.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_INT,
        JAVA_INT,
        JAVA_LONG,
        JAVA_INT, JAVA_INT, JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_IMAGE_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(JAVA_INT, sizetLength() + 12L, imageType)
        seg.set(JAVA_INT, sizetLength() + 16L, format)
        seg.set(JAVA_INT, sizetLength() + 20L, extent.x)
        seg.set(JAVA_INT, sizetLength() + 24L, extent.y)
        seg.set(JAVA_INT, sizetLength() + 28L, extent.z)

        seg.set(JAVA_INT, sizetLength() + 32L, mipLevels)
        seg.set(JAVA_INT, sizetLength() + 36L, arrayLayers)
        seg.set(JAVA_INT, sizetLength() + 40L, samples)
        seg.set(JAVA_INT, sizetLength() + 44L, tiling)
        seg.set(JAVA_INT, sizetLength() + 48L, usage)
        seg.set(JAVA_INT, sizetLength() + 52L, sharingMode)
        seg.set(JAVA_INT, sizetLength() + 56L, queueFamilyIndices.arr.size)
        seg.set(ADDRESS, sizetLength() + 64L, queueFamilyIndices.pointer())
        seg.set(JAVA_INT, sizetLength() * 2 + 64L, initialLayout)
    }
}