package com.primogemstudio.engine.bindings.vulkan.khr.surface

import com.primogemstudio.engine.bindings.vulkan.memory.VkAllocationCallbacks
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_SUCCESS
import com.primogemstudio.engine.bindings.vulkan.vk10.VkInstance
import com.primogemstudio.engine.bindings.vulkan.vk10.VkPhysicalDevice
import com.primogemstudio.engine.interfaces.NativeMethodCache.callFunc
import com.primogemstudio.engine.interfaces.NativeMethodCache.callVoidFunc
import com.primogemstudio.engine.interfaces.heap.HeapInt
import com.primogemstudio.engine.interfaces.heap.HeapIntArray
import com.primogemstudio.engine.interfaces.heap.HeapStructArray
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import com.primogemstudio.engine.types.Result
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT

class VkSurfaceKHR(seg: MemorySegment) : IHeapObject(seg)

object VkSurfaceKHRFuncs {
    const val VK_KHR_SURFACE_SPEC_VERSION: Int = 25
    const val VK_KHR_SURFACE_EXTENSION_NAME: String = "VK_KHR_surface"
    const val VK_ERROR_SURFACE_LOST_KHR: Int = -1000000000
    const val VK_ERROR_NATIVE_WINDOW_IN_USE_KHR: Int = -1000000001
    const val VK_OBJECT_TYPE_SURFACE_KHR: Int = 1000000000
    const val VK_SURFACE_TRANSFORM_IDENTITY_BIT_KHR: Int = 1
    const val VK_SURFACE_TRANSFORM_ROTATE_90_BIT_KHR: Int = 2
    const val VK_SURFACE_TRANSFORM_ROTATE_180_BIT_KHR: Int = 4
    const val VK_SURFACE_TRANSFORM_ROTATE_270_BIT_KHR: Int = 8
    const val VK_SURFACE_TRANSFORM_HORIZONTAL_MIRROR_BIT_KHR: Int = 16
    const val VK_SURFACE_TRANSFORM_HORIZONTAL_MIRROR_ROTATE_90_BIT_KHR: Int = 32
    const val VK_SURFACE_TRANSFORM_HORIZONTAL_MIRROR_ROTATE_180_BIT_KHR: Int = 64
    const val VK_SURFACE_TRANSFORM_HORIZONTAL_MIRROR_ROTATE_270_BIT_KHR: Int = 128
    const val VK_SURFACE_TRANSFORM_INHERIT_BIT_KHR: Int = 256
    const val VK_PRESENT_MODE_IMMEDIATE_KHR: Int = 0
    const val VK_PRESENT_MODE_MAILBOX_KHR: Int = 1
    const val VK_PRESENT_MODE_FIFO_KHR: Int = 2
    const val VK_PRESENT_MODE_FIFO_RELAXED_KHR: Int = 3
    const val VK_COLOR_SPACE_SRGB_NONLINEAR_KHR: Int = 0
    const val VK_COLORSPACE_SRGB_NONLINEAR_KHR: Int = 0
    const val VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR: Int = 1
    const val VK_COMPOSITE_ALPHA_PRE_MULTIPLIED_BIT_KHR: Int = 2
    const val VK_COMPOSITE_ALPHA_POST_MULTIPLIED_BIT_KHR: Int = 4
    const val VK_COMPOSITE_ALPHA_INHERIT_BIT_KHR: Int = 8

    fun vkDestroySurfaceKHR(instance: VkInstance, surface: VkSurfaceKHR, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroySurfaceKHR", instance, surface, allocator?.ref() ?: MemorySegment.NULL)

    fun vkGetPhysicalDeviceSurfaceSupportKHR(
        instance: VkInstance,
        queueFamilyIndex: Int,
        surface: VkSurfaceKHR
    ): Result<Boolean, Int> {
        val seg = Arena.ofAuto().allocate(JAVA_INT)
        val retCode =
            callFunc("vkGetPhysicalDeviceSurfaceSupportKHR", Int::class, instance, queueFamilyIndex, surface, seg)
        return if (retCode == VK_SUCCESS) Result.success(seg.get(JAVA_INT, 0) != 0) else Result.fail(retCode)
    }

    fun vkGetPhysicalDeviceSurfaceCapabilitiesKHR(
        physicalDevice: VkPhysicalDevice,
        surface: VkSurfaceKHR
    ): Result<VkSurfaceCapabilitiesKHR, Int> {
        val seg = Arena.ofAuto().allocate(VkSurfaceCapabilitiesKHR.LAYOUT)
        val retCode = callFunc("vkGetPhysicalDeviceSurfaceCapabilitiesKHR", Int::class, physicalDevice, surface, seg)
        return if (retCode == VK_SUCCESS) Result.success(VkSurfaceCapabilitiesKHR(seg)) else Result.fail(retCode)
    }

    fun vkGetPhysicalDeviceSurfaceFormatsKHR(
        physicalDevice: VkPhysicalDevice,
        surface: VkSurfaceKHR
    ): Result<Array<VkSurfaceCapabilitiesKHR>, Int> {
        val count = HeapInt()
        callFunc(
            "vkGetPhysicalDeviceSurfaceFormatsKHR",
            Int::class,
            physicalDevice,
            surface,
            count,
            MemorySegment.NULL
        ).apply {
            if (this != VK_SUCCESS) return Result.fail(this)
        }
        val sarr = HeapStructArray<VkSurfaceCapabilitiesKHR>(VkSurfaceCapabilitiesKHR.LAYOUT, count.value())
        callFunc("vkGetPhysicalDeviceSurfaceFormatsKHR", Int::class, physicalDevice, surface, count, sarr).apply {
            if (this != VK_SUCCESS) return Result.fail(this)
        }
        return Result.success((0..<count.value()).map { VkSurfaceCapabilitiesKHR(sarr[it]) }.toTypedArray())
    }

    fun vkGetPhysicalDeviceSurfacePresentModesKHR(
        physicalDevice: VkPhysicalDevice,
        surface: VkSurfaceKHR
    ): Result<IntArray, Int> {
        val count = HeapInt()
        callFunc(
            "vkGetPhysicalDeviceSurfacePresentModesKHR",
            Int::class,
            physicalDevice,
            surface,
            count,
            MemorySegment.NULL
        ).apply {
            if (this != VK_SUCCESS) return Result.fail(this)
        }
        val iarr = HeapIntArray(count.value())
        callFunc("vkGetPhysicalDeviceSurfacePresentModesKHR", Int::class, physicalDevice, surface, count, iarr).apply {
            if (this != VK_SUCCESS) return Result.fail(this)
        }
        return Result.success(iarr.value())
    }
}