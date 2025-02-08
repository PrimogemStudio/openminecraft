package com.primogemstudio.engine.bindings.vulkan.ext.debugutils

import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.fetchString
import com.primogemstudio.engine.interfaces.heap.IHeapVar
import org.joml.Vector4f
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkDebugUtilsLabelEXT(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
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

    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val sType: Int get() = seg.get(JAVA_INT, OFFSETS[0])
    val next: MemorySegment get() = seg.get(ADDRESS, OFFSETS[1])
    val labelName: String get() = seg.get(ADDRESS, OFFSETS[2]).fetchString()
    val color: Vector4f
        get() = Vector4f(
            seg.get(JAVA_FLOAT, OFFSETS[3]),
            seg.get(JAVA_FLOAT, OFFSETS[4]),
            seg.get(JAVA_FLOAT, OFFSETS[5]),
            seg.get(JAVA_FLOAT, OFFSETS[6])
        )
}