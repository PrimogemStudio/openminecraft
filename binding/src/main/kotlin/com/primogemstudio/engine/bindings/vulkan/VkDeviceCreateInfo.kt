package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO
import com.primogemstudio.engine.interfaces.struct.ArrayStruct
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.interfaces.toCStrArray
import com.primogemstudio.engine.loader.Platform.sizetLength
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
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        queueCreateInfos.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_INT,
        JAVA_INT,
        ADDRESS,
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG,
        ADDRESS,
        ADDRESS
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(JAVA_INT, sizetLength() + 12L, queueCreateInfos.arr.size)
        seg.set(ADDRESS, sizetLength() + 16L, queueCreateInfos.pointer())
        seg.set(JAVA_INT, sizetLength() * 2 + 16L, enabledLayers.size)
        seg.set(ADDRESS, sizetLength() * 2 + 24L, enabledLayers.toTypedArray().toCStrArray())
        seg.set(JAVA_INT, sizetLength() * 3 + 24L, enabledExtensions.size)
        seg.set(ADDRESS, sizetLength() * 3 + 32L, enabledExtensions.toTypedArray().toCStrArray())
        seg.set(ADDRESS, sizetLength() * 4 + 32L, features.ref())
    }
}