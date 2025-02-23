package com.primogemstudio.engine.bindings.glfw

import com.primogemstudio.engine.bindings.vulkan.khr.surface.VkSurfaceKHR
import com.primogemstudio.engine.bindings.vulkan.memory.VkAllocationCallbacks
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_SUCCESS
import com.primogemstudio.engine.bindings.vulkan.vk10.VkInstance
import com.primogemstudio.engine.bindings.vulkan.vk10.VkPhysicalDevice
import com.primogemstudio.engine.foreign.NativeMethodCache.callFunc
import com.primogemstudio.engine.foreign.NativeMethodCache.callPointerFunc
import com.primogemstudio.engine.foreign.fetchString
import com.primogemstudio.engine.foreign.heap.HeapInt
import com.primogemstudio.engine.foreign.toPointerArray
import com.primogemstudio.engine.types.Result
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.ADDRESS

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

    fun glfwCreateWindowSurface(
        instance: VkInstance,
        window: GLFWWindow,
        allocator: VkAllocationCallbacks?
    ): Result<VkSurfaceKHR, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc(
            "glfwCreateWindowSurface",
            Int::class,
            instance,
            window,
            allocator?.ref() ?: MemorySegment.NULL,
            seg
        )
        return if (retCode == VK_SUCCESS) Result.success(VkSurfaceKHR(seg.get(ADDRESS, 0))) else Result.fail(retCode)
    }
}
