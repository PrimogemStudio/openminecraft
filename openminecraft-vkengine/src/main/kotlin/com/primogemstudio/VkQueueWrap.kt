package com.primogemstudio

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.VK10.VK_NULL_HANDLE
import org.lwjgl.vulkan.VK10.vkGetDeviceQueue
import org.lwjgl.vulkan.VkQueue

class VkQueueWrap(val vkGraphicsQueue: VkQueue, val vkCurrentQueue: VkQueue) {
    companion object {
        fun createFromLogicalDevice(stack: MemoryStack, lDevice: VkLogicalDeviceWrap): VkQueueWrap {
            val pQueue = stack.pointers(VK_NULL_HANDLE)
            vkGetDeviceQueue(lDevice.vkDevice, lDevice.graphicsFamily, 0, pQueue)
            val graphicsQueue = VkQueue(pQueue[0], lDevice.vkDevice)
            vkGetDeviceQueue(lDevice.vkDevice, lDevice.currentFamily, 0, pQueue)
            val currentQueue = VkQueue(pQueue[0], lDevice.vkDevice)

            return VkQueueWrap(graphicsQueue, currentQueue)
        }
    }
}