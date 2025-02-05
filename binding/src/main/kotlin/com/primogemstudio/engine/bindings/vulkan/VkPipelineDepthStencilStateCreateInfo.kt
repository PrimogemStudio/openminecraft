package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.loader.Platform.sizetLength
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
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        front.close()
        back.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        MemoryLayout.paddingLayout(28 + 4),
        MemoryLayout.paddingLayout(28 + 4),
        JAVA_FLOAT,
        JAVA_FLOAT
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(JAVA_INT, sizetLength() + 12L, if (depthTestEnable) 1 else 0)
        seg.set(JAVA_INT, sizetLength() + 16L, if (depthWriteEnable) 1 else 0)
        seg.set(JAVA_INT, sizetLength() + 20L, depthCompareOp)
        seg.set(JAVA_INT, sizetLength() + 24L, if (depthBoundsTestEnable) 1 else 0)
        seg.set(JAVA_INT, sizetLength() + 28L, if (stencilTestEnable) 1 else 0)
        front.construct(seg.asSlice(sizetLength() + 32L, 28))
        back.construct(seg.asSlice(sizetLength() + 60L, 28))
        seg.set(JAVA_FLOAT, sizetLength() + 88L, minDepthBounds)
        seg.set(JAVA_FLOAT, sizetLength() + 92L, maxDepthBounds)
    }
}