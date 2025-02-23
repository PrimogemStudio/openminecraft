package com.primogemstudio.engine.bindings.vulkan.khr.surface

import com.primogemstudio.engine.foreign.align
import com.primogemstudio.engine.foreign.cacheOffsets
import com.primogemstudio.engine.foreign.heap.IHeapObject
import org.joml.Vector2i
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT
import java.lang.foreign.ValueLayout.JAVA_INT_UNALIGNED

class VkSurfaceCapabilitiesKHR(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED, JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var minImageCount: Int
        get() = seg.get(JAVA_INT, OFFSETS[0])
        set(value) = seg.set(JAVA_INT, OFFSETS[0], value)
    var maxImageCount: Int
        get() = seg.get(JAVA_INT, OFFSETS[1])
        set(value) = seg.set(JAVA_INT, OFFSETS[1], value)
    var currentExtent: Vector2i
        get() = Vector2i(
            seg.get(JAVA_INT, OFFSETS[2]),
            seg.get(JAVA_INT, OFFSETS[3])
        )
        set(value) {
            seg.set(JAVA_INT, OFFSETS[2], value[0])
            seg.set(JAVA_INT, OFFSETS[3], value[1])
        }
    var minImageExtent: Vector2i
        get() = Vector2i(
            seg.get(JAVA_INT, OFFSETS[4]),
            seg.get(JAVA_INT, OFFSETS[5])
        )
        set(value) {
            seg.set(JAVA_INT, OFFSETS[4], value[0])
            seg.set(JAVA_INT, OFFSETS[5], value[1])
        }
    var maxImageExtent: Vector2i
        get() = Vector2i(
            seg.get(JAVA_INT, OFFSETS[6]),
            seg.get(JAVA_INT, OFFSETS[7])
        )
        set(value) {
            seg.set(JAVA_INT, OFFSETS[6], value[0])
            seg.set(JAVA_INT, OFFSETS[7], value[1])
        }
    var maxImageArrayLayers: Int
        get() = seg.get(JAVA_INT, OFFSETS[8])
        set(value) = seg.set(JAVA_INT, OFFSETS[8], value)
    var supportedTransforms: Int
        get() = seg.get(JAVA_INT, OFFSETS[9])
        set(value) = seg.set(JAVA_INT, OFFSETS[9], value)
    var currentTransform: Int
        get() = seg.get(JAVA_INT, OFFSETS[10])
        set(value) = seg.set(JAVA_INT, OFFSETS[10], value)
    var supportedCompositeAlpha: Int
        get() = seg.get(JAVA_INT, OFFSETS[11])
        set(value) = seg.set(JAVA_INT, OFFSETS[11], value)
    var supportedUsageFlags: Int
        get() = seg.get(JAVA_INT, OFFSETS[12])
        set(value) = seg.set(JAVA_INT, OFFSETS[12], value)
}