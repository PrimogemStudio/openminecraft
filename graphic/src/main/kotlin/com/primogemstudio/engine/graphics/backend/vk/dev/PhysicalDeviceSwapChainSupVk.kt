package com.primogemstudio.engine.graphics.backend.vk.dev

import com.primogemstudio.engine.bindings.vulkan.khr.surface.VkSurfaceCapabilitiesKHR
import com.primogemstudio.engine.bindings.vulkan.khr.surface.VkSurfaceFormatKHR

class PhysicalDeviceSwapChainSupVk(
    val capabilities: VkSurfaceCapabilitiesKHR,
    val formats: Array<VkSurfaceFormatKHR>,
    val presentModes: IntArray
)