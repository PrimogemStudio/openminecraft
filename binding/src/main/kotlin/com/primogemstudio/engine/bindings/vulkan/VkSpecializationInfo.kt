package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.struct.ArrayStruct
import com.primogemstudio.engine.interfaces.struct.ByteArrayStruct
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.loader.Platform.sizetLength
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkSpecializationInfo(
    private val mapEntries: ArrayStruct<VkSpecializationMapEntry>,
    private val data: ByteArrayStruct
) : IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        mapEntries.close()
        data.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        ADDRESS,
        ADDRESS
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, mapEntries.arr.size)
        seg.set(ADDRESS, 8, mapEntries.pointer())
        seg.set(JAVA_INT, sizetLength() + 8L, data.arr.size)
        seg.set(ADDRESS, sizetLength() * 2 + 8L, data.pointer())
    }
}