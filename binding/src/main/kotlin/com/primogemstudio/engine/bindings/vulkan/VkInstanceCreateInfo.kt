package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.interfaces.toCStrArray
import com.primogemstudio.engine.loader.Platform.sizetLength
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

data class VkInstanceCreateInfo(
    private val next: IStruct? = null,
    private val flag: Int = 0,
    private val appInfo: VkApplicationInfo,
    private val layers: List<String> = listOf(),
    private val extensions: List<String> = listOf(),
) : IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        appInfo.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG,
        ADDRESS
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO)
        seg.set(ADDRESS, 8 * 1, next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, 8 * 1 + sizetLength() * 1L, flag)
        seg.set(ADDRESS, 8 * 2 + sizetLength() * 1L, appInfo.pointer())
        seg.set(JAVA_INT, 8 * 2 + sizetLength() * 2L, layers.size)
        seg.set(ADDRESS, 8 * 3 + sizetLength() * 2L, layers.toTypedArray().toCStrArray())
        seg.set(JAVA_INT, 8 * 3 + sizetLength() * 3L, extensions.size)
        seg.set(ADDRESS, 8 * 4 + sizetLength() * 3L, extensions.toTypedArray().toCStrArray())
    }
}