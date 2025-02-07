package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT

class VkClearAttachment(
    private val aspectMask: Int,
    private val colorAttachment: Int,
    private val clearValue: VkClearValue
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT,
            JAVA_INT,
            VkClearValue.LAYOUT
        )

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = LAYOUT
    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, OFFSETS[0], aspectMask)
        seg.set(JAVA_INT, OFFSETS[1], colorAttachment)
        clearValue.construct(seg.asSlice(OFFSETS[2], VkClearValue.LAYOUT.byteSize()))
    }
}