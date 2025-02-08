package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.core.Vk10Funcs.VK_STRUCTURE_TYPE_PIPELINE_DEPTH_STENCIL_STATE_CREATE_INFO
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkPipelineDepthStencilStateCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val depthTestEnable: Boolean,
    private val depthWriteEnable: Boolean,
    private val depthCompareOp: Int,
    private val depthBoundsTestEnable: Boolean,
    private val stencilTestEnable: Boolean,
    private val front: VkStencilOpState,
    private val back: VkStencilOpState,
    private val minDepthBounds: Float,
    private val maxDepthBounds: Float
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            VkStencilOpState.LAYOUT,
            VkStencilOpState.LAYOUT,
            JAVA_FLOAT_UNALIGNED,
            JAVA_FLOAT_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, OFFSETS[0], VK_STRUCTURE_TYPE_PIPELINE_DEPTH_STENCIL_STATE_CREATE_INFO)
        seg.set(ADDRESS, OFFSETS[1], next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, OFFSETS[2], flags)
        seg.set(JAVA_INT, OFFSETS[3], if (depthTestEnable) 1 else 0)
        seg.set(JAVA_INT, OFFSETS[4], if (depthWriteEnable) 1 else 0)
        seg.set(JAVA_INT, OFFSETS[5], depthCompareOp)
        seg.set(JAVA_INT, OFFSETS[6], if (depthBoundsTestEnable) 1 else 0)
        seg.set(JAVA_INT, OFFSETS[7], if (stencilTestEnable) 1 else 0)
        front.construct(seg.asSlice(OFFSETS[8], VkStencilOpState.LAYOUT.byteSize()))
        back.construct(seg.asSlice(OFFSETS[9], VkStencilOpState.LAYOUT.byteSize()))
        seg.set(JAVA_FLOAT, OFFSETS[10], minDepthBounds)
        seg.set(JAVA_FLOAT, OFFSETS[11], maxDepthBounds)
    }
}