package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.struct.IStruct
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.ADDRESS

class VkAllocationCallbacks(
    private val userdata: MemorySegment,

): IStruct {
    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        ADDRESS,
        ADDRESS,
        ADDRESS,
        ADDRESS,
        ADDRESS,
        ADDRESS
    )

    override fun construct(seg: MemorySegment) {
        TODO("Not yet implemented")
    }
}