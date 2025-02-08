package com.primogemstudio.engine.bindings.vulkan.ext.debugutils

import com.primogemstudio.engine.bindings.vulkan.ext.debugutils.VkEXTDebugUtils.VK_STRUCTURE_TYPE_DEBUG_UTILS_OBJECT_TAG_INFO_EXT
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.interfaces.toCString
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkDebugUtilsObjectTagInfoEXT(
    private val next: IStruct? = null,
    private val type: Int,
    private val handle: MemorySegment,
    private val tagName: String,
    private val tagSize: Long,
    private val tag: MemorySegment
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        super.close()
    }

    override fun layout(): MemoryLayout = LAYOUT
    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, OFFSETS[0], VK_STRUCTURE_TYPE_DEBUG_UTILS_OBJECT_TAG_INFO_EXT)
        seg.set(ADDRESS, OFFSETS[1], next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, OFFSETS[2], type)
        seg.set(ADDRESS, OFFSETS[3], handle)
        seg.set(ADDRESS, OFFSETS[4], tagName.toCString())
        seg.set(JAVA_LONG, OFFSETS[5], tagSize)
        seg.set(ADDRESS, OFFSETS[6], tag)
    }
}