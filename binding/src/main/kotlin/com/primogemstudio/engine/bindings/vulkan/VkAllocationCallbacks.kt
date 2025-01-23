package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.interfaces.NativeMethodCache.constructStub
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.interfaces.stub.IStub
import com.primogemstudio.engine.loader.Platform.sizetLength
import com.primogemstudio.engine.loader.Platform.sizetMap
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.ADDRESS
import java.lang.invoke.MethodType

const val VK_SYSTEM_ALLOCATION_SCOPE_COMMAND = 0
const val VK_SYSTEM_ALLOCATION_SCOPE_OBJECT = 1
const val VK_SYSTEM_ALLOCATION_SCOPE_CACHE = 2
const val VK_SYSTEM_ALLOCATION_SCOPE_DEVICE = 3
const val VK_SYSTEM_ALLOCATION_SCOPE_INSTANCE = 4
const val VK_INTERNAL_ALLOCATION_TYPE_EXECUTABLE = 0

interface VkAllocateFunc : IStub {
    fun call(userdata: MemorySegment, size: Long, alignment: Long, allocationScope: Int): MemorySegment
    fun call(userdata: MemorySegment, size: Int, alignment: Int, allocationScope: Int): MemorySegment
    override fun register(): Pair<String, MethodType> =
        Pair(
            "call",
            MethodType.methodType(
                MemorySegment::class.java,
                MemorySegment::class.java,
                sizetMap().java,
                sizetMap().java,
                Int::class.java
            )
        )
}

interface VkReallocateFunc : IStub {
    fun call(
        userdata: MemorySegment,
        seg: MemorySegment,
        size: Long,
        alignment: Long,
        allocationScope: Int
    ): MemorySegment

    fun call(
        userdata: MemorySegment,
        seg: MemorySegment,
        size: Int,
        alignment: Int,
        allocationScope: Int
    ): MemorySegment

    override fun register(): Pair<String, MethodType> =
        Pair(
            "call",
            MethodType.methodType(
                MemorySegment::class.java,
                MemorySegment::class.java,
                MemorySegment::class.java,
                sizetMap().java,
                sizetMap().java,
                Int::class.java
            )
        )
}

fun interface VkFreeFunc : IStub {
    fun call(userdata: MemorySegment, seg: MemorySegment)
    override fun register(): Pair<String, MethodType> =
        Pair(
            "call",
            MethodType.methodType(
                Void.TYPE,
                MemorySegment::class.java,
                MemorySegment::class.java
            )
        )
}

interface VkInternalMemNotificationFunc : IStub {
    fun call(userdata: MemorySegment, size: Long, type: Int, scope: Int)
    fun call(userdata: MemorySegment, size: Int, type: Int, scope: Int)
    override fun register(): Pair<String, MethodType> =
        Pair(
            "call",
            MethodType.methodType(
                Void.TYPE,
                MemorySegment::class.java,
                sizetMap().java,
                Int::class.java,
                Int::class.java
            )
        )
}

data class VkAllocationCallbacks(
    private val userdata: MemorySegment,
    private val allocateFunc: VkAllocateFunc,
    private val reallocateFunc: VkReallocateFunc,
    private val freeFunc: VkFreeFunc,
    private val internalAllocationFunc: VkInternalMemNotificationFunc,
    private val internalFreeFunc: VkInternalMemNotificationFunc
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
        seg.set(ADDRESS, 0, userdata)
        seg.set(ADDRESS, sizetLength() * 1L, constructStub(VkAllocateFunc::class, allocateFunc))
        seg.set(ADDRESS, sizetLength() * 2L, constructStub(VkReallocateFunc::class, reallocateFunc))
        seg.set(ADDRESS, sizetLength() * 3L, constructStub(VkFreeFunc::class, freeFunc))
        seg.set(
            ADDRESS,
            sizetLength() * 4L,
            constructStub(VkInternalMemNotificationFunc::class, internalAllocationFunc)
        )
        seg.set(ADDRESS, sizetLength() * 5L, constructStub(VkInternalMemNotificationFunc::class, internalFreeFunc))
    }
}