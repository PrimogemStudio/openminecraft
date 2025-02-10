package com.primogemstudio.engine.bindings.vulkan.core

import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import org.joml.Vector3i
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT
import java.lang.foreign.ValueLayout.JAVA_INT_UNALIGNED

class VkSparseImageFormatProperties(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var aspectMask: Int
        get() = seg.get(JAVA_INT, OFFSETS[0])
        set(value) = seg.set(JAVA_INT, OFFSETS[0], value)
    var imageGranularity: Vector3i
        get() = Vector3i(
            seg.get(JAVA_INT, OFFSETS[1]),
            seg.get(JAVA_INT, OFFSETS[2]),
            seg.get(JAVA_INT, OFFSETS[3])
        )
        set(value) {
            seg.set(JAVA_INT, OFFSETS[1], value[0])
            seg.set(JAVA_INT, OFFSETS[2], value[1])
            seg.set(JAVA_INT, OFFSETS[3], value[2])
        }
    var flags: Int
        get() = seg.get(JAVA_INT, OFFSETS[4])
        set(value) = seg.set(JAVA_INT, OFFSETS[4], value)
}