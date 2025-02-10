package com.primogemstudio.engine.bindings.vulkan.vk10

import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import org.joml.Vector3i
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkImageFormatProperties(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_FLOAT, JAVA_FLOAT, JAVA_FLOAT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_LONG
        )
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var maxExtent: Vector3i
        get() = Vector3i(
            seg.get(JAVA_INT, OFFSETS[0]),
            seg.get(JAVA_INT, OFFSETS[1]),
            seg.get(JAVA_INT, OFFSETS[2])
        )
        set(value) {
            seg.set(JAVA_INT, OFFSETS[0], value[0])
            seg.set(JAVA_INT, OFFSETS[1], value[1])
            seg.set(JAVA_INT, OFFSETS[2], value[2])
        }
    var maxMipLevels: Int
        get() = seg.get(JAVA_INT, OFFSETS[3])
        set(value) = seg.set(JAVA_INT, OFFSETS[3], value)
    var maxArrayLayers: Int
        get() = seg.get(JAVA_INT, OFFSETS[4])
        set(value) = seg.set(JAVA_INT, OFFSETS[4], value)
    var sampleCounts: Int
        get() = seg.get(JAVA_INT, OFFSETS[5])
        set(value) = seg.set(JAVA_INT, OFFSETS[5], value)
    var maxResourceSize: Long
        get() = seg.get(JAVA_LONG, OFFSETS[6])
        set(value) = seg.set(JAVA_LONG, OFFSETS[6], value)
}