package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.fetchString
import com.primogemstudio.engine.interfaces.heap.IHeapVar
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_BYTE
import java.lang.foreign.ValueLayout.JAVA_INT

class VkPhysicalDeviceProperties : IHeapVar<MemorySegment> {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            MemoryLayout.paddingLayout(256),
            MemoryLayout.paddingLayout(16 + 4),
            MemoryLayout.paddingLayout(504),
            MemoryLayout.paddingLayout(24),
        )
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    private val seg = Arena.ofAuto().allocate(LAYOUT)

    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val apiVersion: Int get() = seg.get(JAVA_INT, OFFSETS[0])
    val driverVersion: Int get() = seg.get(JAVA_INT, OFFSETS[1])
    val vendorId: Int get() = seg.get(JAVA_INT, OFFSETS[2])
    val deviceID: Int get() = seg.get(JAVA_INT, OFFSETS[3])
    val deviceType: Int get() = seg.get(JAVA_INT, OFFSETS[4])
    val deviceName: String get() = seg.asSlice(OFFSETS[5], 256).fetchString()
    val pipelineCacheUUID: ByteArray get() = seg.asSlice(OFFSETS[6], 16).toArray(JAVA_BYTE)
    val limits: VkPhysicalDeviceLimits get() = VkPhysicalDeviceLimits(seg.asSlice(OFFSETS[7], 504))
    val sparseProperties: VkPhysicalDeviceSparseProperties
        get() = VkPhysicalDeviceSparseProperties(
            seg.asSlice(
                OFFSETS[8],
                20
            )
        )
}