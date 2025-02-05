package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT

data class VkImageSubresource(
    private val aspectMask: Int,
    private val mipmap: Int,
    private val arrayLayer: Int
) : IStruct() {
    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_INT,
        JAVA_INT,
        JAVA_INT
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, aspectMask)
        seg.set(JAVA_INT, 4, mipmap)
        seg.set(JAVA_INT, 8, arrayLayer)
    }
}