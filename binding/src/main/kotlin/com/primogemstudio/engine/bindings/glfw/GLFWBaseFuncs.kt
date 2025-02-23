package com.primogemstudio.engine.bindings.glfw

import com.primogemstudio.engine.bindings.vulkan.vk10.VkInstance
import com.primogemstudio.engine.foreign.NativeMethodCache.callFunc
import com.primogemstudio.engine.foreign.NativeMethodCache.callPointerFunc
import com.primogemstudio.engine.foreign.NativeMethodCache.callVoidFunc
import com.primogemstudio.engine.foreign.NativeMethodCache.constructStub
import com.primogemstudio.engine.foreign.cacheOffsets
import com.primogemstudio.engine.foreign.fetchString
import com.primogemstudio.engine.foreign.heap.HeapInt
import com.primogemstudio.engine.foreign.heap.IHeapObject
import com.primogemstudio.engine.foreign.stub.IStub
import com.primogemstudio.engine.foreign.toCStrArray
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.ADDRESS
import java.lang.invoke.MethodType

interface GLFWAllocateFun : IStub {
    fun call(size: Long, user: MemorySegment): MemorySegment
    fun call(size: MemorySegment, user: MemorySegment): MemorySegment = call(size.address(), user)
    override fun register(): Pair<String, MethodType> =
        Pair(
            "call",
            MethodType.methodType(
                MemorySegment::class.java,
                MemorySegment::class.java,
                MemorySegment::class.java
            )
        )
}

interface GLFWReallocateFun : IStub {
    fun call(block: MemorySegment, size: Long, user: MemorySegment): MemorySegment
    fun call(block: MemorySegment, size: MemorySegment, user: MemorySegment): MemorySegment =
        call(block, size.address(), user)
    override fun register(): Pair<String, MethodType> =
        Pair(
            "call",
            MethodType.methodType(
                MemorySegment::class.java,
                MemorySegment::class.java,
                MemorySegment::class.java,
                MemorySegment::class.java
            )
        )
}

fun interface GLFWDeallocateFun : IStub {
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

fun interface GLFWErrorFun : IStub {
    fun call(errorCode: Int, desc: String)
    fun call(errorCode: Int, desc: MemorySegment) = call(errorCode, desc.fetchString())
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

fun interface PFN_vkGetInstanceProcAddr : IStub {
    fun call(vkInstance: VkInstance, funcname: String)
    fun call(vkInstance: VkInstance, funcname: MemorySegment) = call(vkInstance, funcname.fetchString())
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

class GLFWAllocator(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(ADDRESS, ADDRESS, ADDRESS)
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    constructor() : this(Arena.ofAuto().allocate(LAYOUT))

    var allocator: GLFWAllocateFun
        get() {
            TODO("Unsupported Operation!")
        }
        set(value) = seg.set(ADDRESS, OFFSETS[0], constructStub(GLFWAllocateFun::class, value))
    var reallocator: GLFWReallocateFun
        get() {
            TODO("Unsupported Operation!")
        }
        set(value) = seg.set(ADDRESS, OFFSETS[1], constructStub(GLFWReallocateFun::class, value))
    var deallocator: GLFWDeallocateFun
        get() {
            TODO("Unsupported Operation!")
        }
        set(value) = seg.set(ADDRESS, OFFSETS[2], constructStub(GLFWDeallocateFun::class, value))
}

object GLFWBaseFuncs {
    const val GLFW_NO_ERROR = 0
    const val GLFW_NOT_INITIALIZED = 0x00010001
    const val GLFW_NO_CURRENT_CONTEXT = 0x00010002
    const val GLFW_INVALID_ENUM = 0x00010003
    const val GLFW_INVALID_VALUE = 0x00010004
    const val GLFW_OUT_OF_MEMORY = 0x00010005
    const val GLFW_API_UNAVAILABLE = 0x00010006
    const val GLFW_VERSION_UNAVAILABLE = 0x00010007
    const val GLFW_PLATFORM_ERROR = 0x00010008
    const val GLFW_FORMAT_UNAVAILABLE = 0x00010009
    const val GLFW_NO_WINDOW_CONTEXT = 0x0001000a
    const val GLFW_CURSOR_UNAVAILABLE = 0x0001000b
    const val GLFW_FEATURE_UNAVAILABLE = 0x0001000c
    const val GLFW_FEATURE_UNIMPLEMENTED = 0x0001000d
    const val GLFW_PLATFORM_UNAVAILABLE = 0x0001000e

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

    fun glfwInit(): Boolean =
        callFunc("glfwInit", Boolean::class)
    fun glfwTerminate() =
        callVoidFunc("glfwTerminate")
    fun glfwInitHint(hint: Int, value: Int) =
        callVoidFunc("glfwInitHint", hint, value)
    fun glfwInitAllocator(allocator: GLFWAllocator) =
        callVoidFunc("glfwInitAllocator", allocator)
    fun glfwInitVulkanLoader(proc: PFN_vkGetInstanceProcAddr) =
        callVoidFunc("glfwInitVulkanLoader", proc)
    fun glfwGetVersion(major: HeapInt, minor: HeapInt, rev: HeapInt) =
        callVoidFunc("glfwGetVersion", major, minor, rev)
    fun glfwGetVersionString(): String =
        callPointerFunc("glfwGetVersionString").fetchString()
    fun glfwGetError(desc: Array<String>): Int =
        callFunc("glfwGetError", Int::class, desc.toCStrArray())
    fun glfwSetErrorCallback(callback: GLFWErrorFun): MemorySegment =
        callPointerFunc("glfwSetErrorCallback", constructStub(GLFWErrorFun::class, callback))
    fun glfwGetPlatform(): Int =
        callFunc("glfwGetPlatform", Int::class)
    fun glfwPlatformSupported(platform: Int): Int =
        callFunc("glfwPlatformSupported", Int::class, platform)
}
