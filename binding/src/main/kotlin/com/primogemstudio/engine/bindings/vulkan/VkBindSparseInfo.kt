package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.core.Vk10Funcs.VK_STRUCTURE_TYPE_BIND_SPARSE_INFO
import com.primogemstudio.engine.bindings.vulkan.core.VkSemaphore
import com.primogemstudio.engine.bindings.vulkan.core.VkSparseBufferMemoryBindInfo
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.HeapPointerArray
import com.primogemstudio.engine.interfaces.heap.HeapStructArray
import com.primogemstudio.engine.interfaces.struct.ArrayStruct
import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

data class VkBindSparseInfo(
    private val next: IStruct? = null,
    private val waitSemaphores: HeapPointerArray<VkSemaphore>? = null,
    private val bufferBinds: HeapStructArray<VkSparseBufferMemoryBindInfo>? = null,
    private val imageOpaqueBinds: ArrayStruct<VkSparseImageOpaqueMemoryBindInfo>? = null,
    private val imageBinds: ArrayStruct<VkSparseImageMemoryBindInfo>? = null,
    private val signalSemaphores: HeapPointerArray<VkSemaphore>? = null
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, OFFSETS[0], VK_STRUCTURE_TYPE_BIND_SPARSE_INFO)
        seg.set(ADDRESS, OFFSETS[1], next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, OFFSETS[2], waitSemaphores?.length ?: 0)
        seg.set(ADDRESS, OFFSETS[3], waitSemaphores?.ref() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, OFFSETS[4], bufferBinds?.length ?: 0)
        seg.set(ADDRESS, OFFSETS[5], bufferBinds?.ref() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, OFFSETS[6], imageOpaqueBinds?.arr?.size ?: 0)
        seg.set(ADDRESS, OFFSETS[7], imageOpaqueBinds?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, OFFSETS[8], imageBinds?.arr?.size ?: 0)
        seg.set(ADDRESS, OFFSETS[9], imageBinds?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, OFFSETS[10], signalSemaphores?.length ?: 0)
        seg.set(ADDRESS, OFFSETS[11], signalSemaphores?.ref() ?: MemorySegment.NULL)
    }
}