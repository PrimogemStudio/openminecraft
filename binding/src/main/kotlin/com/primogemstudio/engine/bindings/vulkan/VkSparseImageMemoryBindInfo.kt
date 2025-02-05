package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.struct.ArrayStruct
import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

data class VkSparseImageMemoryBindInfo(
    private val buffer: VkBuffer,
    private val binds: ArrayStruct<VkSparseImageMemoryBind>
) : IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        binds.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        ADDRESS,
        JAVA_LONG,
        ADDRESS
    )

    override fun construct(seg: MemorySegment) {
        seg.set(ADDRESS, 0, buffer.ref())
        seg.set(JAVA_INT, 8, binds.arr.size)
        seg.set(ADDRESS, 16, binds.pointer())
    }
}