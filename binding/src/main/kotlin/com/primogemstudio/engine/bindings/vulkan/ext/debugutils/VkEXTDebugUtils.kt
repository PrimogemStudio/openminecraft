package com.primogemstudio.engine.bindings.vulkan.ext.debugutils

import com.primogemstudio.engine.bindings.vulkan.memory.VkAllocationCallbacks
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_SUCCESS
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkGetDeviceProcAddr
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkGetInstanceProcAddr
import com.primogemstudio.engine.bindings.vulkan.vk10.VkCommandBuffer
import com.primogemstudio.engine.bindings.vulkan.vk10.VkDevice
import com.primogemstudio.engine.bindings.vulkan.vk10.VkInstance
import com.primogemstudio.engine.bindings.vulkan.vk10.VkQueue
import com.primogemstudio.engine.interfaces.NativeMethodCache.callFunc
import com.primogemstudio.engine.interfaces.NativeMethodCache.callVoidFunc
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import com.primogemstudio.engine.types.Result
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.ADDRESS

class VkDebugUtilsMessengerEXT(seg: MemorySegment) : IHeapObject(seg)

object VkEXTDebugUtils {
    const val VK_EXT_DEBUG_UTILS_SPEC_VERSION: Int = 2
    const val VK_EXT_DEBUG_UTILS_EXTENSION_NAME: String = "VK_EXT_debug_utils"
    const val VK_STRUCTURE_TYPE_DEBUG_UTILS_OBJECT_NAME_INFO_EXT: Int = 1000128000
    const val VK_STRUCTURE_TYPE_DEBUG_UTILS_OBJECT_TAG_INFO_EXT: Int = 1000128001
    const val VK_STRUCTURE_TYPE_DEBUG_UTILS_LABEL_EXT: Int = 1000128002
    const val VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CALLBACK_DATA_EXT: Int = 1000128003
    const val VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT: Int = 1000128004
    const val VK_OBJECT_TYPE_DEBUG_UTILS_MESSENGER_EXT: Int = 1000128000
    const val VK_DEBUG_UTILS_MESSAGE_SEVERITY_VERBOSE_BIT_EXT: Int = 1
    const val VK_DEBUG_UTILS_MESSAGE_SEVERITY_INFO_BIT_EXT: Int = 16
    const val VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT: Int = 256
    const val VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT: Int = 4096
    const val VK_DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT: Int = 1
    const val VK_DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT: Int = 2
    const val VK_DEBUG_UTILS_MESSAGE_TYPE_PERFORMANCE_BIT_EXT: Int = 4

    fun vkSetDebugUtilsObjectNameEXT(device: VkDevice, nameInfo: VkDebugUtilsObjectNameInfoEXT): Int =
        callFunc("vkSetDebugUtilsObjectNameEXT", { vkGetDeviceProcAddr(device, it) }, Int::class, device, nameInfo)

    fun vkSetDebugUtilsObjectTagEXT(device: VkDevice, tagInfo: VkDebugUtilsObjectTagInfoEXT): Int =
        callFunc("vkSetDebugUtilsObjectTagEXT", { vkGetDeviceProcAddr(device, it) }, Int::class, device, tagInfo)

    fun vkQueueBeginDebugUtilsLabelEXT(instance: VkInstance, queue: VkQueue, labelInfo: VkDebugUtilsLabelEXT) =
        callVoidFunc("vkQueueBeginDebugUtilsLabelEXT", { vkGetInstanceProcAddr(instance, it) }, queue, labelInfo)

    fun vkQueueEndDebugUtilsLabelEXT(instance: VkInstance, queue: VkQueue) =
        callVoidFunc("vkQueueEndDebugUtilsLabelEXT", { vkGetInstanceProcAddr(instance, it) }, queue)

    fun vkQueueInsertDebugUtilsLabelEXT(instance: VkInstance, queue: VkQueue, labelInfo: VkDebugUtilsLabelEXT) =
        callVoidFunc("vkQueueInsertDebugUtilsLabelEXT", { vkGetInstanceProcAddr(instance, it) }, queue, labelInfo)

    fun vkCmdBeginDebugUtilsLabelEXT(
        instance: VkInstance,
        commandBuffer: VkCommandBuffer,
        labelInfo: VkDebugUtilsLabelEXT
    ) =
        callVoidFunc("vkCmdBeginDebugUtilsLabelEXT", { vkGetInstanceProcAddr(instance, it) }, commandBuffer, labelInfo)

    fun vkCmdEndDebugUtilsLabelEXT(instance: VkInstance, commandBuffer: VkCommandBuffer) =
        callVoidFunc("vkCmdEndDebugUtilsLabelEXT", { vkGetInstanceProcAddr(instance, it) }, commandBuffer)

    fun vkCmdInsertDebugUtilsLabelEXT(
        instance: VkInstance,
        commandBuffer: VkCommandBuffer,
        labelInfo: VkDebugUtilsLabelEXT
    ) =
        callVoidFunc("vkCmdInsertDebugUtilsLabelEXT", { vkGetInstanceProcAddr(instance, it) }, commandBuffer, labelInfo)

    fun vkCreateDebugUtilsMessengerEXT(
        instance: VkInstance,
        createInfo: VkDebugUtilsMessengerCreateInfoEXT,
        allocator: VkAllocationCallbacks?
    ): Result<VkDebugUtilsMessengerEXT, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc(
            "vkCreateDebugUtilsMessengerEXT",
            { vkGetInstanceProcAddr(instance, it) },
            Int::class,
            instance,
            createInfo,
            allocator?.ref() ?: MemorySegment.NULL,
            seg
        )
        return if (retCode == VK_SUCCESS) Result.success(
            VkDebugUtilsMessengerEXT(
                seg.get(
                    ADDRESS,
                    0
                )
            )
        ) else Result.fail(retCode)
    }

    fun vkDestroyDebugUtilsMessengerEXT(
        instance: VkInstance,
        messenger: VkDebugUtilsMessengerEXT,
        allocator: VkAllocationCallbacks?
    ) =
        callVoidFunc(
            "vkDestroyDebugUtilsMessengerEXT",
            { vkGetInstanceProcAddr(instance, it) },
            instance,
            messenger,
            allocator?.ref() ?: MemorySegment.NULL
        )

    fun vkSubmitDebugUtilsMessageEXT(
        instance: VkInstance,
        severity: Int,
        types: Int,
        callbackData: VkDebugUtilsMessengerCallbackDataEXT
    ) =
        callVoidFunc("vkSubmitDebugUtilsMessageEXT", instance, severity, types, callbackData)
}