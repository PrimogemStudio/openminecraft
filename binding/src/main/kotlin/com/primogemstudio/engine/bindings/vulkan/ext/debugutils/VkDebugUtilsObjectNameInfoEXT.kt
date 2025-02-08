package com.primogemstudio.engine.bindings.vulkan.ext.debugutils

import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.fetchString
import com.primogemstudio.engine.interfaces.heap.IHeapVar
import com.primogemstudio.engine.interfaces.toCString
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkDebugUtilsObjectNameInfoEXT(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    var sType: Int
        get() = seg.get(JAVA_INT, OFFSETS[0])
        set(value) = seg.set(JAVA_INT, OFFSETS[0], value)
    var next: MemorySegment
        get() = seg.get(ADDRESS, OFFSETS[1])
        set(value) = seg.set(ADDRESS, OFFSETS[1], value)
    var objectType: Int
        get() = seg.get(JAVA_INT, OFFSETS[2])
        set(value) = seg.set(JAVA_INT, OFFSETS[2], value)
    var objectHandle: MemorySegment
        get() = seg.get(ADDRESS, OFFSETS[3])
        set(value) = seg.set(ADDRESS, OFFSETS[3], value)
    var objectName: String
        get() = seg.get(ADDRESS, OFFSETS[4]).fetchString()
        set(value) = seg.set(ADDRESS, OFFSETS[4], value.toCString())
}