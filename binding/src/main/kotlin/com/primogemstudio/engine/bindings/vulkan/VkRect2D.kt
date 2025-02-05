package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.struct.IStruct
import org.joml.Vector2i
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT

class VkRect2D(
    private val offset: Vector2i,
    private val extent: Vector2i
) : IStruct() {
    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, offset.x)
        seg.set(JAVA_INT, 4, offset.y)
        seg.set(JAVA_INT, 8, extent.x)
        seg.set(JAVA_INT, 12, extent.y)
    }
}