package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.core.Vk10Funcs.VK_STRUCTURE_TYPE_SUBMIT_INFO
import com.primogemstudio.engine.bindings.vulkan.core.VkCommandBuffer
import com.primogemstudio.engine.bindings.vulkan.core.VkSemaphore
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.interfaces.struct.IntArrayStruct
import com.primogemstudio.engine.interfaces.struct.PointerArrayStruct
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
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
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

    override fun close() {
        next?.close()
        waitSemaphores.close()
        commandBuffers.close()
        signalSemaphores.close()
        super.close()
    }

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, OFFSETS[0], VK_STRUCTURE_TYPE_SUBMIT_INFO)
        seg.set(ADDRESS, OFFSETS[1], next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, OFFSETS[2], waitSemaphores.arr.size)
        seg.set(ADDRESS, OFFSETS[3], waitSemaphores.pointer())
        seg.set(ADDRESS, OFFSETS[4], waitDstStageMask.pointer())
        seg.set(JAVA_INT, OFFSETS[5], commandBuffers.arr.size)
        seg.set(ADDRESS, OFFSETS[6], commandBuffers.pointer())
        seg.set(JAVA_INT, OFFSETS[7], signalSemaphores.arr.size)
        seg.set(ADDRESS, OFFSETS[8], signalSemaphores.pointer())
    }
}