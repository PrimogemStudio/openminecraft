package com.primogemstudio.engine.bindings.vulkan.vk10

import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import org.joml.Vector4f
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_FLOAT
import java.lang.foreign.ValueLayout.JAVA_FLOAT_UNALIGNED

class VkClearColorValue(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_FLOAT_UNALIGNED,
            JAVA_FLOAT_UNALIGNED,
            JAVA_FLOAT_UNALIGNED,
            JAVA_FLOAT_UNALIGNED
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var data: Vector4f
        get() = Vector4f(
            seg.get(JAVA_FLOAT, OFFSETS[0]),
            seg.get(JAVA_FLOAT, OFFSETS[1]),
            seg.get(JAVA_FLOAT, OFFSETS[2]),
            seg.get(JAVA_FLOAT, OFFSETS[3])
        )
        set(value) {
            seg.set(JAVA_FLOAT, OFFSETS[0], value[0])
            seg.set(JAVA_FLOAT, OFFSETS[1], value[1])
            seg.set(JAVA_FLOAT, OFFSETS[2], value[2])
            seg.set(JAVA_FLOAT, OFFSETS[3], value[3])
        }
}