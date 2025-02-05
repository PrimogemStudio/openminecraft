package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.struct.IStruct
import org.joml.Vector3i
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

data class VkSparseImageMemoryBind(
    private val subresource: VkImageSubresource,
    private val offset: Vector3i,
    private val extent: Vector3i,
    private val memory: VkDeviceMemory,
    private val memoryOffset: Long,
    private val flags: Int
) : IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        subresource.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        MemoryLayout.paddingLayout(12),
        MemoryLayout.paddingLayout(12),
        MemoryLayout.paddingLayout(12 + 4),
        ADDRESS,
        JAVA_LONG,
        JAVA_LONG
    )

    override fun construct(seg: MemorySegment) {
        subresource.construct(seg.asSlice(0, 12))
        seg.set(JAVA_INT, 12, offset.x)
        seg.set(JAVA_INT, 16, offset.y)
        seg.set(JAVA_INT, 20, offset.z)
        seg.set(JAVA_INT, 24, extent.x)
        seg.set(JAVA_INT, 28, extent.y)
        seg.set(JAVA_INT, 32, extent.z)
        seg.set(ADDRESS, 40, memory.ref())
        seg.set(JAVA_LONG, 48, memoryOffset)
        seg.set(JAVA_INT, 56, flags)
    }
}