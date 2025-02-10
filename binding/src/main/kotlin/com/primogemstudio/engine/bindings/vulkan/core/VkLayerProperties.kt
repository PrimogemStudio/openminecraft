package com.primogemstudio.engine.bindings.vulkan.core

import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.fetchString
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import com.primogemstudio.engine.interfaces.toCString
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT
import java.lang.foreign.ValueLayout.JAVA_INT_UNALIGNED

class VkLayerProperties(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            MemoryLayout.paddingLayout(256),
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            MemoryLayout.paddingLayout(256)
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var layerName: String
        get() = seg.asSlice(OFFSETS[0], 256).fetchString()
        set(value) {
            seg.asSlice(OFFSETS[0], 256).copyFrom(value.toCString())
        }
    var specVersion: Int
        get() = seg.get(JAVA_INT, OFFSETS[1])
        set(value) = seg.set(JAVA_INT, OFFSETS[1], value)
    var implementationVersion: Int
        get() = seg.get(JAVA_INT, OFFSETS[2])
        set(value) = seg.set(JAVA_INT, OFFSETS[2], value)
    var description: String
        get() = seg.asSlice(OFFSETS[3], 256).fetchString()
        set(value) {
            seg.asSlice(OFFSETS[3], 256).copyFrom(value.toCString())
        }
}