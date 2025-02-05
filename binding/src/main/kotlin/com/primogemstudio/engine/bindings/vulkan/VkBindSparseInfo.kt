package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_BIND_SPARSE_INFO
import com.primogemstudio.engine.interfaces.struct.ArrayStruct
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.interfaces.struct.PointerArrayStruct
import com.primogemstudio.engine.loader.Platform.sizetLength
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

data class VkBindSparseInfo(
    private val next: IStruct? = null,
    private val waitSemaphores: PointerArrayStruct<VkSemaphore>? = null,
    private val bufferBinds: ArrayStruct<VkSparseBufferMemoryBindInfo>? = null,
    private val imageOpaqueBinds: ArrayStruct<VkSparseImageOpaqueMemoryBindInfo>? = null,
    private val imageBinds: ArrayStruct<VkSparseImageMemoryBindInfo>? = null,
    private val signalSemaphores: PointerArrayStruct<VkSemaphore>? = null
) : IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        waitSemaphores?.close()
        bufferBinds?.close()
        imageOpaqueBinds?.close()
        imageBinds?.close()
        signalSemaphores?.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG,
        ADDRESS
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_BIND_SPARSE_INFO)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, waitSemaphores?.arr?.size ?: 0)
        seg.set(ADDRESS, sizetLength() + 16L, waitSemaphores?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() * 2 + 16L, bufferBinds?.arr?.size ?: 0)
        seg.set(ADDRESS, sizetLength() * 2 + 24L, bufferBinds?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() * 3 + 24L, imageOpaqueBinds?.arr?.size ?: 0)
        seg.set(ADDRESS, sizetLength() * 3 + 30L, imageOpaqueBinds?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() * 4 + 30L, imageBinds?.arr?.size ?: 0)
        seg.set(ADDRESS, sizetLength() * 4 + 38L, imageBinds?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() * 5 + 38L, signalSemaphores?.arr?.size ?: 0)
        seg.set(ADDRESS, sizetLength() * 5 + 46L, signalSemaphores?.pointer() ?: MemorySegment.NULL)
    }
}