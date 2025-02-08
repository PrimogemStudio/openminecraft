package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.core.Vk10Funcs.VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.ArrayStruct
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.interfaces.toCStrArray
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

data class VkDeviceCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val queueCreateInfos: ArrayStruct<VkDeviceQueueCreateInfo>,
    private val enabledLayers: List<String> = listOf(),
    private val enabledExtensions: List<String> = listOf(),
    private val features: VkPhysicalDeviceFeatures = VkPhysicalDeviceFeatures()
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            ADDRESS_UNALIGNED
        ).align()
        private val OFFSET = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        queueCreateInfos.close()
        super.close()
    }

    override fun layout(): MemoryLayout = LAYOUT

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, OFFSET[0], VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO)
        seg.set(ADDRESS, OFFSET[1], next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, OFFSET[2], flags)
        seg.set(JAVA_INT, OFFSET[3], queueCreateInfos.arr.size)
        seg.set(ADDRESS, OFFSET[4], queueCreateInfos.pointer())
        seg.set(JAVA_INT, OFFSET[5], enabledLayers.size)
        seg.set(ADDRESS, OFFSET[6], enabledLayers.toTypedArray().toCStrArray())
        seg.set(JAVA_INT, OFFSET[7], enabledExtensions.size)
        seg.set(ADDRESS, OFFSET[8], enabledExtensions.toTypedArray().toCStrArray())
        seg.set(ADDRESS, OFFSET[9], features.ref())
    }
}