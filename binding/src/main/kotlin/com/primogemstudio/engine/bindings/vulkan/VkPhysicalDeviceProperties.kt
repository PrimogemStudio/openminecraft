package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.fetchString
import com.primogemstudio.engine.interfaces.heap.IHeapVar
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_BYTE
import java.lang.foreign.ValueLayout.JAVA_INT

class VkPhysicalDeviceProperties : IHeapVar<MemorySegment> {
    private val seg = Arena.ofAuto().allocate(
        MemoryLayout.structLayout(
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
    )

    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val apiVersion: Int get() = seg.get(JAVA_INT, 0)
    val driverVersion: Int get() = seg.get(JAVA_INT, 4)
    val vendorId: Int get() = seg.get(JAVA_INT, 8)
    val deviceID: Int get() = seg.get(JAVA_INT, 12)
    val deviceType: Int get() = seg.get(JAVA_INT, 16)
    val deviceName: String get() = seg.asSlice(20, 256).fetchString()
    val pipelineCacheUUID: ByteArray get() = seg.asSlice(276, 16).toArray(JAVA_BYTE)
    val limits: VkPhysicalDeviceLimits get() = VkPhysicalDeviceLimits(seg.asSlice(296, 504))
    val sparseProperties: VkPhysicalDeviceSparseProperties
        get() = VkPhysicalDeviceSparseProperties(
            seg.asSlice(
                800,
                20
            )
        )
}