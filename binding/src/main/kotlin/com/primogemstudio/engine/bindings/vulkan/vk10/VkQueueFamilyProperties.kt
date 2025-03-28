package com.primogemstudio.engine.bindings.vulkan.vk10

import com.primogemstudio.engine.foreign.align
import com.primogemstudio.engine.foreign.cacheOffsets
import com.primogemstudio.engine.foreign.heap.IHeapObject
import org.joml.Vector3i
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT
import java.lang.foreign.ValueLayout.JAVA_INT_UNALIGNED

class VkQueueFamilyProperties(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var queueFlags: Int
        get() = seg.get(JAVA_INT, OFFSETS[0])
        set(value) = seg.set(JAVA_INT, OFFSETS[0], value)
    var queueCount: Int
        get() = seg.get(JAVA_INT, OFFSETS[1])
        set(value) = seg.set(JAVA_INT, OFFSETS[1], value)
    var timestampValidBits: Int
        get() = seg.get(JAVA_INT, OFFSETS[2])
        set(value) = seg.set(JAVA_INT, OFFSETS[2], value)
    var minImageTransferGranularity: Vector3i
        get() = Vector3i(
            seg.get(JAVA_INT, OFFSETS[3]),
            seg.get(JAVA_INT, OFFSETS[4]),
            seg.get(JAVA_INT, OFFSETS[5])
        )
        set(value) {
            seg.set(JAVA_INT, OFFSETS[3], value[0])
            seg.set(JAVA_INT, OFFSETS[4], value[1])
            seg.set(JAVA_INT, OFFSETS[5], value[2])
        }
}