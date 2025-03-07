package com.primogemstudio.engine.bindings.vulkan.vk10

import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_STRUCTURE_TYPE_PIPELINE_COLOR_BLEND_STATE_CREATE_INFO
import com.primogemstudio.engine.foreign.align
import com.primogemstudio.engine.foreign.cacheOffsets
import com.primogemstudio.engine.foreign.heap.HeapStructArray
import com.primogemstudio.engine.foreign.heap.IHeapObject
import org.joml.Vector4f
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkPipelineColorBlendStateCreateInfo(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_FLOAT_UNALIGNED,
            JAVA_FLOAT_UNALIGNED,
            JAVA_FLOAT_UNALIGNED,
            JAVA_FLOAT_UNALIGNED
        ).align()
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT)) {
        sType = VK_STRUCTURE_TYPE_PIPELINE_COLOR_BLEND_STATE_CREATE_INFO
    }

    var sType: Int
        get() = seg.get(JAVA_INT, OFFSETS[0])
        set(value) = seg.set(JAVA_INT, OFFSETS[0], value)
    var next: MemorySegment
        get() = seg.get(ADDRESS, OFFSETS[1]).reinterpret(Long.MAX_VALUE)
        set(value) = seg.set(ADDRESS, OFFSETS[1], value)
    var flag: Int
        get() = seg.get(JAVA_INT, OFFSETS[2])
        set(value) = seg.set(JAVA_INT, OFFSETS[2], value)
    var logicOpEnable: Boolean
        get() = seg.get(JAVA_INT, OFFSETS[3]) != 0
        set(value) = seg.set(JAVA_INT, OFFSETS[3], if (value) 1 else 0)
    var logicOp: Int
        get() = seg.get(JAVA_INT, OFFSETS[4])
        set(value) = seg.set(JAVA_INT, OFFSETS[4], value)
    var attachments: HeapStructArray<VkPipelineColorBlendAttachmentState>
        get() = HeapStructArray(
            seg.get(JAVA_INT, OFFSETS[5]),
            seg.get(ADDRESS, OFFSETS[6]),
            VkPipelineColorBlendAttachmentState.LAYOUT
        )
        set(value) {
            seg.set(ADDRESS, OFFSETS[6], value.ref())
            seg.set(JAVA_INT, OFFSETS[5], value.length)
        }
    var blendConstants: Vector4f
        get() = Vector4f(
            seg.get(JAVA_FLOAT, OFFSETS[7]),
            seg.get(JAVA_FLOAT, OFFSETS[8]),
            seg.get(JAVA_FLOAT, OFFSETS[9]),
            seg.get(JAVA_FLOAT, OFFSETS[10])
        )
        set(value) {
            seg.set(JAVA_FLOAT, OFFSETS[7], value.x)
            seg.set(JAVA_FLOAT, OFFSETS[8], value.y)
            seg.set(JAVA_FLOAT, OFFSETS[9], value.z)
            seg.set(JAVA_FLOAT, OFFSETS[10], value.w)
        }
}