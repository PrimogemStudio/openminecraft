package com.primogemstudio.engine.bindings.glfw

import com.primogemstudio.engine.bindings.vulkan.core.VkInstance
import com.primogemstudio.engine.bindings.vulkan.core.VkPhysicalDevice
import com.primogemstudio.engine.bindings.vulkan.memory.VkAllocationCallbacks
import com.primogemstudio.engine.interfaces.NativeMethodCache.callFunc
import com.primogemstudio.engine.interfaces.NativeMethodCache.callPointerFunc
import com.primogemstudio.engine.interfaces.fetchString
import com.primogemstudio.engine.interfaces.heap.HeapInt
import com.primogemstudio.engine.interfaces.toPointerArray
import java.lang.foreign.MemorySegment

object GLFWVulkanFuncs {
    fun glfwVulkanSupported(): Int =
        callFunc("glfwVulkanSupported", Int::class)

    fun glfwGetRequiredInstanceExtensions(): Array<String> {
        val count = HeapInt()
        return callPointerFunc("glfwGetRequiredInstanceExtensions", count).toPointerArray(count.value())
            .map { it.fetchString() }
            .toTypedArray()
    }

    fun glfwGetInstanceProcAddress(instance: VkInstance, procname: String): MemorySegment =
        callPointerFunc("glfwGetInstanceProcAddress", instance, procname)

    fun glfwGetPhysicalDevicePresentationSupport(
        instance: VkInstance,
        device: VkPhysicalDevice,
        queueFamily: Int
    ): Int =
        callFunc("glfwGetPhysicalDevicePresentationSupport", Int::class, instance, device, queueFamily)

    // surface -> VkSurfaceKHR
    fun glfwCreateWindowSurface(
        instance: VkInstance,
        window: GLFWWindow,
        allocator: VkAllocationCallbacks,
        surface: MemorySegment
    ): Int =
        callFunc("glfwCreateWindowSurface", Int::class, instance, window, allocator.pointer(), surface)
}
