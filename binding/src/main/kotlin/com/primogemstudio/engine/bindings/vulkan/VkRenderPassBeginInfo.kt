package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.core.Vk10Funcs.VK_STRUCTURE_TYPE_RENDER_PASS_BEGIN_INFO
import com.primogemstudio.engine.bindings.vulkan.core.VkFramebuffer
import com.primogemstudio.engine.bindings.vulkan.core.VkRenderPass
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.ArrayStruct
import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkRenderPassBeginInfo(
    private val next: IStruct? = null,
    private val renderPass: VkRenderPass,
    private val framebuffer: VkFramebuffer,
    private val renderArea: VkRect2D,
    private val clearValues: ArrayStruct<VkClearValue>
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED,
            VkRect2D.LAYOUT,
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
        renderArea.close()
        clearValues.close()
        super.close()
    }

    override fun layout(): MemoryLayout = LAYOUT
    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, OFFSETS[0], VK_STRUCTURE_TYPE_RENDER_PASS_BEGIN_INFO)
        seg.set(ADDRESS, OFFSETS[1], next?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, OFFSETS[2], renderPass.ref())
        seg.set(ADDRESS, OFFSETS[3], framebuffer.ref())
        renderArea.construct(seg.asSlice(OFFSETS[4], VkRect2D.LAYOUT.byteSize()))
        seg.set(JAVA_INT, OFFSETS[5], clearValues.arr.size)
        seg.set(ADDRESS, OFFSETS[6], clearValues.pointer())
    }
}