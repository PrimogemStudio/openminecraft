package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.interfaces.toCStrArray
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
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED,
            JAVA_INT_UNALIGNED,
            ADDRESS_UNALIGNED
        ).align()
        val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        appInfo.close()
        super.close()
    }

    override fun layout(): MemoryLayout = LAYOUT
    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, OFFSETS[0], VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO)
        seg.set(ADDRESS, OFFSETS[1], next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, OFFSETS[2], flag)
        seg.set(ADDRESS, OFFSETS[3], appInfo.pointer())
        seg.set(JAVA_INT, OFFSETS[4], layers.size)
        seg.set(ADDRESS, OFFSETS[5], layers.toTypedArray().toCStrArray())
        seg.set(JAVA_INT, OFFSETS[6], extensions.size)
        seg.set(ADDRESS, OFFSETS[7], extensions.toTypedArray().toCStrArray())
    }
}