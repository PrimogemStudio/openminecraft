package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_PIPELINE_CACHE_CREATE_INFO
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.ByteArrayStruct
import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkPipelineCacheCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val initialData: ByteArrayStruct
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_LONG_UNALIGNED,
            ADDRESS_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        initialData.close()
        super.close()
    }

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, OFFSETS[0], VK_STRUCTURE_TYPE_PIPELINE_CACHE_CREATE_INFO)
        seg.set(ADDRESS, OFFSETS[1], next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, OFFSETS[2], flags)
        seg.set(JAVA_INT, OFFSETS[3], initialData.arr.size)
        seg.set(ADDRESS, OFFSETS[4], initialData.pointer())
    }
}