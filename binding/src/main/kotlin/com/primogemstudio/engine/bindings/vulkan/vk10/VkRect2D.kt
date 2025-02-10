package com.primogemstudio.engine.bindings.vulkan.vk10

import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import org.joml.Vector2i
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT

class VkRect2D(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT
        )

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var offset: Vector2i
        get() = Vector2i(seg.get(JAVA_INT, OFFSETS[0]), seg.get(JAVA_INT, OFFSETS[1]))
        set(value) {
            seg.set(JAVA_INT, OFFSETS[0], value[0])
            seg.set(JAVA_INT, OFFSETS[1], value[1])
        }
    var extent: Vector2i
        get() = Vector2i(seg.get(JAVA_INT, OFFSETS[2]), seg.get(JAVA_INT, OFFSETS[3]))
        set(value) {
            seg.set(JAVA_INT, OFFSETS[2], value[0])
            seg.set(JAVA_INT, OFFSETS[3], value[1])
        }
}