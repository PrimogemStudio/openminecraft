package com.primogemstudio.engine.bindings.vulkan.ext.debugutils

import com.primogemstudio.engine.foreign.align
import com.primogemstudio.engine.foreign.cacheOffsets
import com.primogemstudio.engine.foreign.fetchString
import com.primogemstudio.engine.foreign.heap.IHeapObject
import com.primogemstudio.engine.foreign.toCString
import org.joml.Vector4f
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkDebugUtilsLabelEXT(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_FLOAT_UNALIGNED,
            JAVA_FLOAT_UNALIGNED,
            JAVA_FLOAT_UNALIGNED,
            JAVA_FLOAT_UNALIGNED
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var sType: Int
        get() = seg.get(JAVA_INT, OFFSETS[0])
        set(value) = seg.set(JAVA_INT, OFFSETS[0], value)
    var next: MemorySegment
        get() = seg.get(ADDRESS, OFFSETS[1])
        set(value) = seg.set(ADDRESS, OFFSETS[1], value)
    var labelName: String
        get() = seg.get(ADDRESS, OFFSETS[2]).fetchString()
        set(value) = seg.set(ADDRESS, OFFSETS[2], value.toCString())
    var color: Vector4f
        get() = Vector4f(
            seg.get(JAVA_FLOAT, OFFSETS[3]),
            seg.get(JAVA_FLOAT, OFFSETS[4]),
            seg.get(JAVA_FLOAT, OFFSETS[5]),
            seg.get(JAVA_FLOAT, OFFSETS[6])
        )
        set(value) {
            seg.set(JAVA_FLOAT, OFFSETS[3], value[0])
            seg.set(JAVA_FLOAT, OFFSETS[4], value[1])
            seg.set(JAVA_FLOAT, OFFSETS[5], value[2])
            seg.set(JAVA_FLOAT, OFFSETS[6], value[3])
        }
}