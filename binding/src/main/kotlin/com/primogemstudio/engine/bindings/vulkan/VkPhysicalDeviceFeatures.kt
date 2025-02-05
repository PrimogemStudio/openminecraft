package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.heap.IHeapVar
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT

class VkPhysicalDeviceFeatures : IHeapVar<MemorySegment> {
    private val seg = Arena.ofAuto().allocate(
        MemoryLayout.structLayout(
            *Array<MemoryLayout>(55) { _ -> JAVA_INT }
        ))

    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    fun featureAt(i: Int): Boolean = seg.get(JAVA_INT, 4L * i) == 1
    fun setFeature(i: Int, enabled: Boolean) = seg.set(JAVA_INT, 4L * i, if (enabled) 1 else 0)
}