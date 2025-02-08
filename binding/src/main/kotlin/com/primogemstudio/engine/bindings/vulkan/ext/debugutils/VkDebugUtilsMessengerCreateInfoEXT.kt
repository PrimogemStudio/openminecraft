package com.primogemstudio.engine.bindings.vulkan.ext.debugutils

import com.primogemstudio.engine.bindings.vulkan.ext.debugutils.VkEXTDebugUtils.VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT
import com.primogemstudio.engine.interfaces.NativeMethodCache.constructStub
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkDebugUtilsMessengerCreateInfoEXT(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val messageSeverity: Int,
    private val messageType: Int,
    private val callback: VkDebugUtilsMessengerCallbackEXT,
    private val userData: MemorySegment? = null
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = LAYOUT
    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, OFFSETS[0], VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT)
        seg.set(ADDRESS, OFFSETS[1], next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, OFFSETS[2], flags)
        seg.set(JAVA_INT, OFFSETS[3], messageSeverity)
        seg.set(JAVA_INT, OFFSETS[4], messageType)
        seg.set(ADDRESS, OFFSETS[5], constructStub(VkDebugUtilsMessengerCallbackEXT::class, callback))
        seg.set(ADDRESS, OFFSETS[6], userData ?: MemorySegment.NULL)
    }
}