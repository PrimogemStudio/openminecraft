package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.core.VkBuffer
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.ArrayStruct
import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

data class VkSparseImageOpaqueMemoryBindInfo(
    private val buffer: VkBuffer,
    private val binds: ArrayStruct<VkSparseImageMemoryBind>
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_LONG_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            ADDRESS_UNALIGNED
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        seg.set(ADDRESS, OFFSETS[0], buffer.ref())
        seg.set(JAVA_INT, OFFSETS[1], binds.arr.size)
        seg.set(ADDRESS, OFFSETS[2], binds.pointer())
    }
}