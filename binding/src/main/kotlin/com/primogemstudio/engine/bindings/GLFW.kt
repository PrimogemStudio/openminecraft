package com.primogemstudio.engine.bindings

import com.primogemstudio.engine.interfaces.IStruct
import com.primogemstudio.engine.interfaces.IStub
import com.primogemstudio.engine.interfaces.NativeMethodCache.callFunc
import com.primogemstudio.engine.interfaces.NativeMethodCache.constructStub
import com.primogemstudio.engine.interfaces.fetchCString
import com.primogemstudio.engine.interfaces.heap.HeapInt
import com.primogemstudio.engine.loader.Platform.sizetLength
import com.primogemstudio.engine.loader.Platform.sizetMap
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.ADDRESS
import java.lang.invoke.MethodType

interface GLFWAllocateFun : IStub {
    fun call(size: Long, user: MemorySegment)
    fun call(size: Int, user: MemorySegment)
    override fun register(): Pair<String, MethodType> =
        Pair(
            "call",
            MethodType.methodType(
                Void.TYPE,
                sizetMap().java,
                MemorySegment::class.java
            )
        )
}

interface GLFWReallocateFun : IStub {
    fun call(block: MemorySegment, size: Long, user: MemorySegment)
    fun call(block: MemorySegment, size: Int, user: MemorySegment)
    override fun register(): Pair<String, MethodType> =
        Pair(
            "call",
            MethodType.methodType(
                Void.TYPE,
                MemorySegment::class.java,
                sizetMap().java,
                MemorySegment::class.java
            )
        )
}

interface GLFWDeallocateFun : IStub {
    fun call(block: MemorySegment, user: MemorySegment)
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

interface GLFWErrorFun : IStub {
    fun call(errorCode: Int, desc: MemorySegment)
    override fun register(): Pair<String, MethodType> =
        Pair(
            "call",
            MethodType.methodType(
                Void.TYPE,
                Int::class.java,
                MemorySegment::class.java
            )
        )
}

class GLFWAllocator(
    private val allocator: GLFWAllocateFun,
    private val reallocator: GLFWReallocateFun,
    private val deallocator: GLFWDeallocateFun
) : IStruct {
    override fun layout(): MemoryLayout = MemoryLayout.structLayout(ADDRESS, ADDRESS, ADDRESS)
    override fun construct(seg: MemorySegment) {
        seg.set(ADDRESS, 0, constructStub(GLFWAllocateFun::class, allocator))
        seg.set(ADDRESS, sizetLength() * 1L, constructStub(GLFWReallocateFun::class, reallocator))
        seg.set(ADDRESS, sizetLength() * 2L, constructStub(GLFWDeallocateFun::class, deallocator))
    }
}

object GLFW {
    const val GLFW_TRUE = 1
    const val GLFW_FALSE = 0
    const val GLFW_JOYSTICK_HAT_BUTTONS = 0x00050001
    const val GLFW_ANGLE_PLATFORM_TYPE = 0x00050002
    const val GLFW_PLATFORM = 0x00050003
    const val GLFW_COCOA_CHDIR_RESOURCES = 0x00051001
    const val GLFW_COCOA_MENUBAR = 0x00051002
    const val GLFW_X11_XCB_VULKAN_SURFACE = 0x00052001
    const val GLFW_WAYLAND_LIBDECOR = 0x00053001
    const val GLFW_ANY_PLATFORM = 0x00060000
    const val GLFW_PLATFORM_WIN32 = 0x00060001
    const val GLFW_PLATFORM_COCOA = 0x00060002
    const val GLFW_PLATFORM_WAYLAND = 0x00060003
    const val GLFW_PLATFORM_X11 = 0x00060004
    const val GLFW_PLATFORM_NULL = 0x00060005

    fun glfwInit(): Boolean = callFunc("glfwInit", Boolean::class)
    fun glfwTerminate() = callFunc("glfwTerminate", Unit::class)
    fun glfwInitHint(hint: Int, value: Int) = callFunc("glfwInitHint", Unit::class, hint, value)

    // glfwInitVulkanLoader(PFN_vkGetInstanceProcAddr)
    // PFN_vkGetInstanceAddr -> VkInstance, char*

    fun glfwGetVersionString(): String = callFunc("glfwGetVersionString", MemorySegment::class).fetchCString()
    fun glfwGetVersion(major: HeapInt, minor: HeapInt, rev: HeapInt) =
        callFunc("glfwGetVersion", Unit::class, major, minor, rev)
}