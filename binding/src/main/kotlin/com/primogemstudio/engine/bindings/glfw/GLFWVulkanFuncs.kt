package com.primogemstudio.engine.bindings.glfw

import com.primogemstudio.engine.interfaces.NativeMethodCache.callFunc
import com.primogemstudio.engine.interfaces.NativeMethodCache.callPointerFunc
import com.primogemstudio.engine.interfaces.fetchString
import com.primogemstudio.engine.interfaces.heap.HeapInt
import com.primogemstudio.engine.interfaces.heap.HeapMutRefArray
import java.lang.foreign.MemorySegment

object GLFWVulkanFuncs {
    fun glfwVulkanSupported(): Int =
        callFunc("glfwVulkanSupported", Int::class)

    fun glfwGetRequiredInstanceExtensions(count: HeapInt): Array<String> =
        HeapMutRefArray(callPointerFunc("glfwGetRequiredInstanceExtensions", count), count.value())
            .value()
            .map { it.fetchString() }
            .toTypedArray()

    // instance -> VkInstance
    fun glfwGetInstanceProcAddress(instance: MemorySegment, procname: String): MemorySegment =
        callPointerFunc("glfwGetInstanceProcAddress", instance, procname)

    // instance -> VkInstance
    // device -> VkPhysicalDevice
    fun glfwGetPhysicalDevicePresentationSupport(
        instance: MemorySegment,
        device: MemorySegment,
        queueFamily: Int
    ): Int =
        callFunc("glfwGetPhysicalDevicePresentationSupport", Int::class, instance, device, queueFamily)

    // instance -> VkInstance
    // allocator -> VkAllocationCallbacks
    // surface -> VkSurfaceKHR
    // @return -> VkResult
    fun glfwCreateWindowSurface(
        instance: MemorySegment,
        window: GLFWWindow,
        allocator: MemorySegment,
        surface: MemorySegment
    ): Int =
        callFunc("glfwCreateWindowSurface", Int::class, instance, window, allocator, surface)
}