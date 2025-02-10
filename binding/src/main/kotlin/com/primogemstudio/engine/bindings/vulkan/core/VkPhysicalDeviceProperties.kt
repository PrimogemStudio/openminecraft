package com.primogemstudio.engine.bindings.vulkan.core

import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.fetchString
import com.primogemstudio.engine.interfaces.heap.HeapByteArray
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import com.primogemstudio.engine.interfaces.toCString
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT

class VkPhysicalDeviceProperties(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            MemoryLayout.paddingLayout(256),
            MemoryLayout.paddingLayout(16 + 4),
            MemoryLayout.paddingLayout(VkPhysicalDeviceLimits.size),
            MemoryLayout.paddingLayout(VkPhysicalDeviceSparseProperties.LAYOUT.byteSize()),
        )
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    var apiVersion: Int
        get() = seg.get(JAVA_INT, OFFSETS[0])
        set(value) = seg.set(JAVA_INT, OFFSETS[0], value)
    var driverVersion: Int
        get() = seg.get(JAVA_INT, OFFSETS[1])
        set(value) = seg.set(JAVA_INT, OFFSETS[1], value)
    var vendorId: Int
        get() = seg.get(JAVA_INT, OFFSETS[2])
        set(value) = seg.set(JAVA_INT, OFFSETS[2], value)
    var deviceID: Int
        get() = seg.get(JAVA_INT, OFFSETS[3])
        set(value) = seg.set(JAVA_INT, OFFSETS[3], value)
    var deviceType: Int
        get() = seg.get(JAVA_INT, OFFSETS[4])
        set(value) = seg.set(JAVA_INT, OFFSETS[4], value)
    var deviceName: String
        get() = seg.asSlice(OFFSETS[5], 256).fetchString()
        set(value) {
            seg.asSlice(OFFSETS[5], 256).copyFrom(value.toCString())
        }
    var pipelineCacheUUID: HeapByteArray
        get() = HeapByteArray(16, seg.asSlice(OFFSETS[6], 16))
        set(value) {
            seg.asSlice(OFFSETS[6], 16).copyFrom(value.ref())
        }
    val limits: VkPhysicalDeviceLimits
        get() = VkPhysicalDeviceLimits(seg.asSlice(OFFSETS[7], VkPhysicalDeviceLimits.size))
    var sparseProperties: VkPhysicalDeviceSparseProperties
        get() = VkPhysicalDeviceSparseProperties(
            seg.asSlice(
                OFFSETS[8],
                VkPhysicalDeviceSparseProperties.LAYOUT.byteSize()
            )
        )
        set(value) {
            seg.asSlice(OFFSETS[8], VkPhysicalDeviceSparseProperties.LAYOUT.byteSize()).copyFrom(value.ref())
        }
}