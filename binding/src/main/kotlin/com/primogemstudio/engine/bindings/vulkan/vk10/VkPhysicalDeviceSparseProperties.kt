package com.primogemstudio.engine.bindings.vulkan.vk10

import com.primogemstudio.engine.foreign.cacheOffsets
import com.primogemstudio.engine.foreign.heap.IHeapObject
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT

class VkPhysicalDeviceSparseProperties(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT
        )
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    var residencyStandard2DBlockShape: Boolean
        get() = seg.get(JAVA_INT, OFFSETS[0]) != 0
        set(value) = seg.set(JAVA_INT, OFFSETS[0], if (value) 1 else 0)
    var residencyStandard2DMultisampleBlockShape: Boolean
        get() = seg.get(JAVA_INT, OFFSETS[1]) != 0
        set(value) = seg.set(JAVA_INT, OFFSETS[1], if (value) 1 else 0)
    var residencyStandard3DBlockShape: Boolean
        get() = seg.get(JAVA_INT, OFFSETS[2]) != 0
        set(value) = seg.set(JAVA_INT, OFFSETS[2], if (value) 1 else 0)
    var residencyAlignedMipSize: Boolean
        get() = seg.get(JAVA_INT, OFFSETS[3]) != 0
        set(value) = seg.set(JAVA_INT, OFFSETS[3], if (value) 1 else 0)
    var residencyNonResidentStrict: Boolean
        get() = seg.get(JAVA_INT, OFFSETS[4]) != 0
        set(value) = seg.set(JAVA_INT, OFFSETS[4], if (value) 1 else 0)
}