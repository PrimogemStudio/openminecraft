package com.primogemstudio.engine.bindings.vulkan.core

import com.primogemstudio.engine.interfaces.heap.IHeapObject
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.StructLayout
import java.lang.foreign.ValueLayout.JAVA_INT

class VkPhysicalDeviceFeatures(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT: StructLayout = MemoryLayout.structLayout(
            *Array<MemoryLayout>(55) { _ -> JAVA_INT }
        )
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    fun featureAt(i: Int): Boolean = seg.get(JAVA_INT, 4L * i) == 1
    fun setFeature(i: Int, enabled: Boolean) = seg.set(JAVA_INT, 4L * i, if (enabled) 1 else 0)
}