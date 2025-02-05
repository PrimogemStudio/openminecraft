package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_APPLICATION_INFO
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.interfaces.toCString
import com.primogemstudio.engine.loader.Platform.sizetLength
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

data class VkApplicationInfo(
    private val next: IStruct? = null,
    private val appName: String,
    private val appVersion: Int,
    private val engineName: String,
    private val engineVersion: Int,
    private val apiVersion: Int
) : IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS_UNALIGNED,
        ADDRESS_UNALIGNED,
        JAVA_LONG,
        ADDRESS_UNALIGNED,
        JAVA_LONG,
        JAVA_LONG
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_APPLICATION_INFO)
        seg.set(ADDRESS, 8 * 1, next?.pointer() ?: MemorySegment.NULL)
        seg.set(ADDRESS, 8 * 1 + sizetLength() * 1L, appName.toCString())
        seg.set(JAVA_INT, 8 * 1 + sizetLength() * 2L, appVersion)
        seg.set(ADDRESS, 8 * 2 + sizetLength() * 2L, engineName.toCString())
        seg.set(JAVA_INT, 8 * 2 + sizetLength() * 3L, engineVersion)
        seg.set(JAVA_INT, 8 * 3 + sizetLength() * 3L, apiVersion)
    }
}