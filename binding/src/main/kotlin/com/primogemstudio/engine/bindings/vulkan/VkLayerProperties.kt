package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.fetchString
import com.primogemstudio.engine.interfaces.heap.IHeapVar
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.ADDRESS
import java.lang.foreign.ValueLayout.JAVA_INT

class VkLayerProperties(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val layerName: String get() = seg.get(ADDRESS, 0).fetchString()
    val specVersion: Int get() = seg.get(JAVA_INT, 256)
    val implementationVersion: Int get() = seg.get(JAVA_INT, 260)
    val description: String get() = seg.get(ADDRESS, 264).fetchString()
}