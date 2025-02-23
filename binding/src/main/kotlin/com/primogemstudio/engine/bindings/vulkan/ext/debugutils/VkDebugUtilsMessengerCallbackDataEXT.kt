package com.primogemstudio.engine.bindings.vulkan.ext.debugutils

import com.primogemstudio.engine.bindings.vulkan.ext.debugutils.VkEXTDebugUtils.VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CALLBACK_DATA_EXT
import com.primogemstudio.engine.foreign.align
import com.primogemstudio.engine.foreign.cacheOffsets
import com.primogemstudio.engine.foreign.fetchString
import com.primogemstudio.engine.foreign.heap.HeapStructArray
import com.primogemstudio.engine.foreign.heap.IHeapObject
import com.primogemstudio.engine.foreign.toCString
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkDebugUtilsMessengerCallbackDataEXT(private val seg: MemorySegment) : IHeapObject(seg) {
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

    constructor() : this(Arena.ofAuto().allocate(LAYOUT)) {
        sType = VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CALLBACK_DATA_EXT
    }

    var sType: Int
        get() = seg.get(JAVA_INT, OFFSETS[0])
        set(value) = seg.set(JAVA_INT, OFFSETS[0], value)
    var next: MemorySegment
        get() = seg.get(ADDRESS, OFFSETS[1])
        set(value) = seg.set(ADDRESS, OFFSETS[1], value)
    var flags: Int
        get() = seg.get(JAVA_INT, OFFSETS[2])
        set(value) = seg.set(JAVA_INT, OFFSETS[2], value)
    var messageIdName: String
        get() = seg.get(ADDRESS, OFFSETS[3]).fetchString()
        set(value) = seg.set(ADDRESS, OFFSETS[3], value.toCString())
    var messageIdNumber: Int
        get() = seg.get(JAVA_INT, OFFSETS[4])
        set(value) = seg.set(JAVA_INT, OFFSETS[4], value)
    var message: String
        get() = seg.get(ADDRESS, OFFSETS[5]).fetchString()
        set(value) = seg.set(ADDRESS, OFFSETS[5], value.toCString())

    var queueLabels: HeapStructArray<VkDebugUtilsLabelEXT>
        get() = HeapStructArray(
            seg.get(JAVA_INT, OFFSETS[6]),
            seg.get(ADDRESS, OFFSETS[7]),
            VkDebugUtilsLabelEXT.LAYOUT
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[7], value.ref())
            seg.set(JAVA_INT, OFFSETS[6], value.length)
        }

    var cmdBufLabels: HeapStructArray<VkDebugUtilsLabelEXT>
        get() = HeapStructArray(
            seg.get(JAVA_INT, OFFSETS[8]),
            seg.get(ADDRESS, OFFSETS[9]),
            VkDebugUtilsLabelEXT.LAYOUT
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[9], value.ref())
            seg.set(JAVA_INT, OFFSETS[8], value.length)
        }

    var objects: HeapStructArray<VkDebugUtilsObjectNameInfoEXT>
        get() = HeapStructArray(
            seg.get(JAVA_INT, OFFSETS[10]),
            seg.get(ADDRESS, OFFSETS[11]),
            VkDebugUtilsLabelEXT.LAYOUT
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[11], value.ref())
            seg.set(JAVA_INT, OFFSETS[10], value.length)
        }
}