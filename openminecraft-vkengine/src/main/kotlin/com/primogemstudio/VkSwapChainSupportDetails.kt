package com.primogemstudio

import org.lwjgl.vulkan.VkSurfaceCapabilitiesKHR
import org.lwjgl.vulkan.VkSurfaceFormatKHR
import java.nio.IntBuffer

data class VkSwapChainSupportDetails(
    var capabilities: VkSurfaceCapabilitiesKHR? = null,
    var formats: VkSurfaceFormatKHR.Buffer? = null,
    var presentModes: IntBuffer? = null
)
