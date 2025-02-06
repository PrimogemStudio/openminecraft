package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_IMAGE_CREATE_INFO
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.interfaces.struct.IntArrayStruct
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
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        queueFamilyIndices.close()
        super.close()
    }

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, OFFSETS[0], VK_STRUCTURE_TYPE_IMAGE_CREATE_INFO)
        seg.set(ADDRESS, OFFSETS[1], next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, OFFSETS[2], flags)
        seg.set(JAVA_INT, OFFSETS[3], imageType)
        seg.set(JAVA_INT, OFFSETS[4], format)
        seg.set(JAVA_INT, OFFSETS[5], extent.x)
        seg.set(JAVA_INT, OFFSETS[6], extent.y)
        seg.set(JAVA_INT, OFFSETS[7], extent.z)
        seg.set(JAVA_INT, OFFSETS[8], mipLevels)
        seg.set(JAVA_INT, OFFSETS[9], arrayLayers)
        seg.set(JAVA_INT, OFFSETS[10], samples)
        seg.set(JAVA_INT, OFFSETS[11], tiling)
        seg.set(JAVA_INT, OFFSETS[12], usage)
        seg.set(JAVA_INT, OFFSETS[13], sharingMode)
        seg.set(JAVA_INT, OFFSETS[14], queueFamilyIndices.arr.size)
        seg.set(ADDRESS, OFFSETS[15], queueFamilyIndices.pointer())
        seg.set(JAVA_INT, OFFSETS[16], initialLayout)
    }
}