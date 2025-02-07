package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.fetchString
import com.primogemstudio.engine.interfaces.heap.IHeapVar
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT

class VkLayerProperties(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val layerName: String get() = seg.asSlice(0, 256).fetchString()
    val specVersion: Int get() = seg.get(JAVA_INT, 256)
    val implementationVersion: Int get() = seg.get(JAVA_INT, 260)
    val description: String get() = seg.asSlice(264, 256).fetchString()
}