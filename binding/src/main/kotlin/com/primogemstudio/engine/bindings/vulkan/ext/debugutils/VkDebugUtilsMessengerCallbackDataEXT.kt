package com.primogemstudio.engine.bindings.vulkan.ext.debugutils

import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.fetchString
import com.primogemstudio.engine.interfaces.heap.IHeapVar
import com.primogemstudio.engine.interfaces.toCPointerArray
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkDebugUtilsMessengerCallbackDataEXT(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val sType: Int get() = seg.get(JAVA_INT, OFFSETS[0])
    val next: MemorySegment get() = seg.get(ADDRESS, OFFSETS[1])
    val flags: Int get() = seg.get(JAVA_INT, OFFSETS[2])
    val messageIdName: String get() = seg.get(ADDRESS, OFFSETS[3]).fetchString()
    val messageIdNumber: Int get() = seg.get(JAVA_INT, OFFSETS[4])
    val message: String get() = seg.get(ADDRESS, OFFSETS[5]).fetchString()
    val queueLabelCount: Int get() = seg.get(JAVA_INT, OFFSETS[6])
    val queueLabels: Array<VkDebugUtilsLabelEXT>
        get() = seg.get(ADDRESS, OFFSETS[7]).toCPointerArray(queueLabelCount).map { VkDebugUtilsLabelEXT(it) }
            .toTypedArray()
    val cmdBufLabelCount: Int get() = seg.get(JAVA_INT, OFFSETS[8])
    val cmdBufLabels: Array<VkDebugUtilsLabelEXT>
        get() = seg.get(ADDRESS, OFFSETS[9]).toCPointerArray(cmdBufLabelCount).map { VkDebugUtilsLabelEXT(it) }
            .toTypedArray()
    val objectCount: Int get() = seg.get(JAVA_INT, OFFSETS[10])
    val objects: Array<VkDebugUtilsObjectNameInfoEXT>
        get() = seg.get(ADDRESS, OFFSETS[11]).toCPointerArray(objectCount).map { VkDebugUtilsObjectNameInfoEXT(it) }
            .toTypedArray()
}