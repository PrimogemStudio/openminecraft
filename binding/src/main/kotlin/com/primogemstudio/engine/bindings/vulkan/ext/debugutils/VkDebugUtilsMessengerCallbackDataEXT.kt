package com.primogemstudio.engine.bindings.vulkan.ext.debugutils

import com.primogemstudio.engine.bindings.vulkan.ext.debugutils.VkEXTDebugUtils.VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CALLBACK_DATA_EXT
import com.primogemstudio.engine.interfaces.*
import com.primogemstudio.engine.interfaces.heap.IHeapObject
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

    var queueLabels: Array<VkDebugUtilsLabelEXT>
        get() = seg.get(ADDRESS, OFFSETS[7]).toPointerArray(seg.get(JAVA_INT, OFFSETS[6]))
            .map { VkDebugUtilsLabelEXT(it) }
            .toTypedArray()
        set(value) {
            seg.set(ADDRESS, OFFSETS[7], value.toCStructArray(VkDebugUtilsLabelEXT.LAYOUT).ref())
            seg.set(JAVA_INT, OFFSETS[6], value.size)
        }

    var cmdBufLabels: Array<VkDebugUtilsLabelEXT>
        get() = seg.get(ADDRESS, OFFSETS[9]).toPointerArray(seg.get(JAVA_INT, OFFSETS[8]))
            .map { VkDebugUtilsLabelEXT(it) }
            .toTypedArray()
        set(value) {
            seg.set(ADDRESS, OFFSETS[9], value.toCStructArray(VkDebugUtilsLabelEXT.LAYOUT).ref())
            seg.set(JAVA_INT, OFFSETS[8], value.size)
        }

    var objects: Array<VkDebugUtilsObjectNameInfoEXT>
        get() = seg.get(ADDRESS, OFFSETS[11]).toPointerArray(seg.get(JAVA_INT, OFFSETS[10]))
            .map { VkDebugUtilsObjectNameInfoEXT(it) }
            .toTypedArray()
        set(value) {
            seg.set(ADDRESS, OFFSETS[11], value.toCStructArray(VkDebugUtilsLabelEXT.LAYOUT).ref())
            seg.set(JAVA_INT, OFFSETS[10], value.size)
        }
}