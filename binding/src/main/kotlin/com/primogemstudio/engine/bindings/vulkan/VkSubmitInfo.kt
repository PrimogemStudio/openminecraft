package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_SUBMIT_INFO
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.interfaces.struct.IntArrayStruct
import com.primogemstudio.engine.interfaces.struct.PointerArrayStruct
import com.primogemstudio.engine.loader.Platform.sizetLength
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

data class VkSubmitInfo(
    private val next: IStruct? = null,
    private val waitSemaphores: PointerArrayStruct<VkSemaphore>,
    private val waitDstStageMask: IntArrayStruct,
    private val commandBuffers: PointerArrayStruct<VkCommandBuffer>,
    private val signalSemaphores: PointerArrayStruct<VkSemaphore>
) : IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        waitSemaphores.close()
        commandBuffers.close()
        signalSemaphores.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG,
        ADDRESS,
        ADDRESS,
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG,
        ADDRESS
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_SUBMIT_INFO)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, waitSemaphores.arr.size)
        seg.set(ADDRESS, sizetLength() + 16L, waitSemaphores.pointer())
        seg.set(ADDRESS, sizetLength() * 2 + 16L, waitDstStageMask.pointer())
        seg.set(JAVA_INT, sizetLength() * 3 + 16L, commandBuffers.arr.size)
        seg.set(ADDRESS, sizetLength() * 3 + 24L, commandBuffers.pointer())
        seg.set(JAVA_INT, sizetLength() * 4 + 24L, signalSemaphores.arr.size)
        seg.set(ADDRESS, sizetLength() * 4 + 32L, signalSemaphores.pointer())
    }
}