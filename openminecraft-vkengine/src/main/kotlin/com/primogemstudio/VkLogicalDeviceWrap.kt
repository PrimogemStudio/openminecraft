package com.primogemstudio

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.*
import org.lwjgl.vulkan.VK10.*


class VkLogicalDeviceWrap(val vkDevice: VkDevice, val vkGraphicsQueue: VkQueue, val vkCurrentQueue: VkQueue) {
    companion object {
        fun create(
            stack: MemoryStack,
            physicalDevice: VkPhysicalDeviceWrap,
            validationLayer: VkValidationLayer
        ): VkLogicalDeviceWrap {
            val queueCreateInfos = VkDeviceQueueCreateInfo.calloc(1, stack).let {
                it.sType(VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO)
                it.queueFamilyIndex(physicalDevice.graphicsFamily!!)
                it.pQueuePriorities(stack.floats(1.0f))
                it
            }
            val deviceFeatures = VkPhysicalDeviceFeatures.calloc(stack)
            val createInfo = VkDeviceCreateInfo.calloc(stack).apply {
                sType(VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO)
                pQueueCreateInfos(queueCreateInfos)
                pEnabledFeatures(deviceFeatures)
                validationLayer.vkDeviceCreateArgs(stack) {
                    ppEnabledLayerNames(it)
                }
            }

            val pDevice = stack.pointers(VK_NULL_HANDLE)
            if (vkCreateDevice(physicalDevice.vkDevice, createInfo, null, pDevice) != VK_SUCCESS) {
                throw RuntimeException("Failed to create logical device")
            }

            val device = VkDevice(pDevice[0], physicalDevice.vkDevice, createInfo)

            val pQueue = stack.pointers(VK_NULL_HANDLE)
            vkGetDeviceQueue(device, physicalDevice.graphicsFamily!!, 0, pQueue)
            val graphicsQueue = VkQueue(pQueue[0], device)
            vkGetDeviceQueue(device, physicalDevice.currentFamily!!, 0, pQueue)
            val currentQueue = VkQueue(pQueue[0], device)

            return VkLogicalDeviceWrap(device, graphicsQueue, currentQueue)
        }
    }

    fun destroy() {
        vkDestroyDevice(vkDevice, null)
    }
}