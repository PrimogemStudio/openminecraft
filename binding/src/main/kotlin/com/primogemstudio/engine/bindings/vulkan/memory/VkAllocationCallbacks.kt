package com.primogemstudio.engine.bindings.vulkan.memory

import com.primogemstudio.engine.interfaces.NativeMethodCache.constructStub
import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import com.primogemstudio.engine.interfaces.stub.IStub
import com.primogemstudio.engine.loader.Platform.sizetMap
import java.lang.foreign.Arena
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

class VkAllocationCallbacks(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            ADDRESS,
            ADDRESS,
            ADDRESS,
            ADDRESS,
            ADDRESS,
            ADDRESS
        ).align()
        private val OFFSET = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var userdata: MemorySegment
        get() = seg.get(ADDRESS, OFFSET[0])
        set(value) = seg.set(ADDRESS, OFFSET[0], value)
    var allocateFunc: VkAllocateFunc
        get() {
            TODO("Unsupported Operation!")
        }
        set(value) = seg.set(ADDRESS, OFFSET[1], constructStub(VkAllocateFunc::class, value))
    var reallocateFunc: VkReallocateFunc
        get() {
            TODO("Unsupported Operation!")
        }
        set(value) = seg.set(ADDRESS, OFFSET[2], constructStub(VkReallocateFunc::class, value))
    var freeFunc: VkFreeFunc
        get() {
            TODO("Unsupported Operation!")
        }
        set(value) = seg.set(ADDRESS, OFFSET[3], constructStub(VkFreeFunc::class, value))
    var internalAllocationFunc: VkInternalMemNotificationFunc
        get() {
            TODO("Unsupported Operation!")
        }
        set(value) = seg.set(ADDRESS, OFFSET[4], constructStub(VkInternalMemNotificationFunc::class, value))
    var internalFreeFunc: VkInternalMemNotificationFunc
        get() {
            TODO("Unsupported Operation!")
        }
        set(value) = seg.set(ADDRESS, OFFSET[5], constructStub(VkInternalMemNotificationFunc::class, value))
}