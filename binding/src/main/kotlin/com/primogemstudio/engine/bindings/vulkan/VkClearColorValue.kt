package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import org.joml.Vector4f
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_FLOAT
import java.lang.foreign.ValueLayout.JAVA_FLOAT_UNALIGNED

class VkClearColorValue(
    private val data: Vector4f
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_FLOAT_UNALIGNED,
            JAVA_FLOAT_UNALIGNED,
            JAVA_FLOAT_UNALIGNED,
            JAVA_FLOAT_UNALIGNED
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_FLOAT, OFFSETS[0], data[0])
        seg.set(JAVA_FLOAT, OFFSETS[1], data[1])
        seg.set(JAVA_FLOAT, OFFSETS[2], data[2])
        seg.set(JAVA_FLOAT, OFFSETS[3], data[3])
    }
}