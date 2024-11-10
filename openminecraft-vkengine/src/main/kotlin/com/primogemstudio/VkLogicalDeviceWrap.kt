package com.primogemstudio

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.KHRSwapchain.VK_KHR_SWAPCHAIN_EXTENSION_NAME
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.vulkan.VkDevice
import org.lwjgl.vulkan.VkDeviceCreateInfo
import org.lwjgl.vulkan.VkDeviceQueueCreateInfo
import org.lwjgl.vulkan.VkPhysicalDeviceFeatures

class VkLogicalDeviceWrap(val vkDevice: VkDevice, val graphicsFamily: Int, val currentFamily: Int) {
    companion object {
        fun create(
            stack: MemoryStack,
            physicalDevice: VkPhysicalDeviceWrap,
            validationLayer: VkValidationLayer
        ): VkLogicalDeviceWrap {
            val fm = physicalDevice.unique()
            val queueCreateInfos = VkDeviceQueueCreateInfo.calloc(fm.size, stack)

            for (i in fm.indices) {
                queueCreateInfos[i].apply {
                    sType(VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO)
                    queueFamilyIndex(physicalDevice.graphicsFamily!!)
                    pQueuePriorities(stack.floats(1.0f))
                }
            }

            val deviceFeatures = VkPhysicalDeviceFeatures.calloc(stack)
            val createInfo = VkDeviceCreateInfo.calloc(stack).apply {
                sType(VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO)
                pQueueCreateInfos(queueCreateInfos)
                pEnabledFeatures(deviceFeatures)
                ppEnabledExtensionNames(
                    stack.mallocPointer(1).apply { put(stack.UTF8(VK_KHR_SWAPCHAIN_EXTENSION_NAME)) }.rewind()
                )
                validationLayer.vkDeviceCreateArgs(stack) {
                    ppEnabledLayerNames(it)
                }
            }

            val pDevice = stack.pointers(VK_NULL_HANDLE)
            if (vkCreateDevice(physicalDevice.vkDevice, createInfo, null, pDevice) != VK_SUCCESS) {
                throw RuntimeException("Failed to create logical device")
            }

            return VkLogicalDeviceWrap(
                VkDevice(pDevice[0], physicalDevice.vkDevice, createInfo),
                physicalDevice.graphicsFamily!!,
                physicalDevice.currentFamily!!
            )
        }
    }

    fun destroy() {
        vkDestroyDevice(vkDevice, null)
    }
}