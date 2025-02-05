package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.interfaces.struct.PointerArrayStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.ADDRESS
import java.lang.foreign.ValueLayout.JAVA_INT

class VkDescriptorSetLayoutBinding(
    private val binding: Int,
    private val descriptorType: Int,
    private val descriptorCount: Int,
    private val stageFlags: Int,
    private val immutableSamplers: PointerArrayStruct<VkSampler>? = null
) : IStruct() {
    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        ADDRESS
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, binding)
        seg.set(JAVA_INT, 4, descriptorType)
        seg.set(JAVA_INT, 8, descriptorCount)
        seg.set(JAVA_INT, 12, stageFlags)
        seg.set(ADDRESS, 16, immutableSamplers?.pointer() ?: MemorySegment.NULL)
    }
}