package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO
import com.primogemstudio.engine.interfaces.struct.ByteArrayStruct
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.loader.Platform.sizetLength
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*


class VkShaderModuleCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val code: ByteArrayStruct
) : IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        code.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG,
        ADDRESS,
        ADDRESS
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(JAVA_INT, sizetLength() + 16L, code.arr.size)
        seg.set(ADDRESS, sizetLength() * 2 + 16L, code.pointer())
    }
}