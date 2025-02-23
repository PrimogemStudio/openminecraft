package com.primogemstudio.engine.bindings.vulkan.khr.swapchain

import com.primogemstudio.engine.bindings.vulkan.khr.surface.VkSurfaceKHR
import com.primogemstudio.engine.bindings.vulkan.memory.VkAllocationCallbacks
import com.primogemstudio.engine.bindings.vulkan.vk10.*
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_SUCCESS
import com.primogemstudio.engine.foreign.NativeMethodCache.callFunc
import com.primogemstudio.engine.foreign.NativeMethodCache.callVoidFunc
import com.primogemstudio.engine.foreign.heap.HeapInt
import com.primogemstudio.engine.foreign.heap.HeapPointerArray
import com.primogemstudio.engine.foreign.heap.HeapStructArray
import com.primogemstudio.engine.foreign.heap.IHeapObject
import com.primogemstudio.engine.types.Result
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.ADDRESS

class VkSwapchainKHR(private val seg: MemorySegment) : IHeapObject(seg)

object VkSwapchainKHRFuncs {
    const val VK_KHR_SWAPCHAIN_SPEC_VERSION: Int = 70
    const val VK_KHR_SWAPCHAIN_EXTENSION_NAME: String = "VK_KHR_swapchain"
    const val VK_STRUCTURE_TYPE_SWAPCHAIN_CREATE_INFO_KHR: Int = 1000001000
    const val VK_STRUCTURE_TYPE_PRESENT_INFO_KHR: Int = 1000001001
    const val VK_IMAGE_LAYOUT_PRESENT_SRC_KHR: Int = 1000001002
    const val VK_SUBOPTIMAL_KHR: Int = 1000001003
    const val VK_ERROR_OUT_OF_DATE_KHR: Int = -1000001004
    const val VK_OBJECT_TYPE_SWAPCHAIN_KHR: Int = 1000001000
    const val VK_STRUCTURE_TYPE_DEVICE_GROUP_PRESENT_CAPABILITIES_KHR: Int = 1000060007
    const val VK_STRUCTURE_TYPE_IMAGE_SWAPCHAIN_CREATE_INFO_KHR: Int = 1000060008
    const val VK_STRUCTURE_TYPE_BIND_IMAGE_MEMORY_SWAPCHAIN_INFO_KHR: Int = 1000060009
    const val VK_STRUCTURE_TYPE_ACQUIRE_NEXT_IMAGE_INFO_KHR: Int = 1000060010
    const val VK_STRUCTURE_TYPE_DEVICE_GROUP_PRESENT_INFO_KHR: Int = 1000060011
    const val VK_STRUCTURE_TYPE_DEVICE_GROUP_SWAPCHAIN_CREATE_INFO_KHR: Int = 1000060012
    const val VK_SWAPCHAIN_CREATE_SPLIT_INSTANCE_BIND_REGIONS_BIT_KHR: Int = 1
    const val VK_SWAPCHAIN_CREATE_PROTECTED_BIT_KHR: Int = 2
    const val VK_DEVICE_GROUP_PRESENT_MODE_LOCAL_BIT_KHR: Int = 1
    const val VK_DEVICE_GROUP_PRESENT_MODE_REMOTE_BIT_KHR: Int = 2
    const val VK_DEVICE_GROUP_PRESENT_MODE_SUM_BIT_KHR: Int = 4
    const val VK_DEVICE_GROUP_PRESENT_MODE_LOCAL_MULTI_DEVICE_BIT_KHR: Int = 8

    fun vkCreateSwapchainKHR(
        device: VkDevice,
        createInfo: VkSwapchainCreateInfoKHR,
        allocator: VkAllocationCallbacks?
    ): Result<VkSwapchainKHR, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc(
            "vkCreateSwapchainKHR",
            Int::class,
            device,
            createInfo,
            allocator?.ref() ?: MemorySegment.NULL,
            seg
        )
        return if (retCode == VK_SUCCESS) Result.success(VkSwapchainKHR(seg.get(ADDRESS, 0))) else Result.fail(retCode)
    }

    fun vkDestroySwapchainKHR(device: VkDevice, swapchain: VkSwapchainKHR, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroySwapchainKHR", device, swapchain, allocator?.ref() ?: MemorySegment.NULL)

    fun vkGetSwapchainImagesKHR(device: VkDevice, swapchain: VkSwapchainKHR): Result<Array<VkImage>, Int> {
        val count = HeapInt()
        callFunc("vkGetSwapchainImagesKHR", Int::class, device, swapchain, count, MemorySegment.NULL).apply {
            if (this != VK_SUCCESS) return Result.fail(this)
        }
        val parr = HeapPointerArray<VkImage>(count.value())
        callFunc("vkGetSwapchainImagesKHR", Int::class, device, swapchain, count, parr).apply {
            if (this != VK_SUCCESS) return Result.fail(this)
        }

        return Result.success(parr.value().map { VkImage(it) }.toTypedArray())
    }

    fun vkAcquireNextImageKHR(
        device: VkDevice,
        swapchain: VkSwapchainKHR,
        timeout: Long,
        semaphore: VkSemaphore,
        fence: VkFence
    ): Result<Int, Int> {
        val idx = HeapInt()
        val retCode = callFunc("vkAcquireNextImageKHR", Int::class, device, swapchain, timeout, semaphore, fence, idx)
        return if (retCode == VK_SUCCESS) Result.success(idx.value()) else Result.fail(retCode)
    }

    fun vkQueuePresentKHR(queue: VkQueue, presentInfo: VkPresentInfoKHR): Int =
        callFunc("vkQueuePresentKHR", Int::class, queue, presentInfo)

    fun vkGetDeviceGroupPresentCapabilitiesKHR(
        device: VkDevice,
        deviceGroupPresentCapabilities: VkDeviceGroupPresentCapabilitiesKHR
    ): Int =
        callFunc("vkGetDeviceGroupPresentCapabilitiesKHR", Int::class, device, deviceGroupPresentCapabilities)

    fun vkGetDeviceGroupSurfacePresentModesKHR(device: VkDevice, surface: VkSurfaceKHR): Result<Int, Int> {
        val data = HeapInt()
        val retCode = callFunc("vkGetDeviceGroupSurfacePresentModesKHR", Int::class, device, surface)
        return if (retCode == VK_SUCCESS) Result.success(data.value()) else Result.fail(retCode)
    }

    fun vkGetPhysicalDevicePresentRectanglesKHR(
        physicalDevice: VkPhysicalDevice,
        surface: VkSurfaceKHR
    ): Result<Array<VkRect2D>, Int> {
        val count = HeapInt()
        callFunc(
            "vkGetPhysicalDevicePresentRectanglesKHR",
            Int::class,
            physicalDevice,
            surface,
            count,
            MemorySegment.NULL
        ).apply {
            if (this != VK_SUCCESS) return Result.fail(this)
        }
        val sarr = HeapStructArray<VkRect2D>(VkRect2D.LAYOUT, count.value())
        callFunc("vkGetPhysicalDevicePresentRectanglesKHR", Int::class, physicalDevice, surface, count, sarr).apply {
            if (this != VK_SUCCESS) return Result.fail(this)
        }
        return Result.success((0..<count.value()).map { VkRect2D(sarr[it]) }.toTypedArray())
    }

    fun vkAcquireNextImage2KHR(device: VkDevice, acquireInfo: VkAcquireNextImageInfoKHR): Result<Int, Int> {
        val data = HeapInt()
        val retCode = callFunc("vkAcquireNextImage2KHR", Int::class, device, acquireInfo, data)
        return if (retCode == VK_SUCCESS) Result.success(data.value()) else Result.fail(retCode)
    }
}