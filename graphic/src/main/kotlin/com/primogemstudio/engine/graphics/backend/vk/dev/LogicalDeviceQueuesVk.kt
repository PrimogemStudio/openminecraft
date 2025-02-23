package com.primogemstudio.engine.graphics.backend.vk.dev

import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkGetDeviceQueue
import com.primogemstudio.engine.graphics.backend.vk.BackendRendererVk

class LogicalDeviceQueuesVk(
    private val renderer: BackendRendererVk
) {
    val graphicsQueue = vkGetDeviceQueue(renderer.logicalDevice(), renderer.physicalDevice.graphicFamily, 0)
    val presentQueue = vkGetDeviceQueue(renderer.logicalDevice(), renderer.physicalDevice.presentFamily, 0)
}