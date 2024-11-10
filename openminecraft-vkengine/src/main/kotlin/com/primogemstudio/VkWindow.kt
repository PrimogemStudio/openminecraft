package com.primogemstudio

import org.lwjgl.glfw.GLFWVulkan.glfwCreateWindowSurface
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.vulkan.VK10.VK_NULL_HANDLE
import org.lwjgl.vulkan.VK10.VK_SUCCESS
import org.lwjgl.vulkan.VkInstance

class VkWindow(
    private val instanceAccessor: () -> VkInstance,
    window: Long
) {
    var surface: Long = 0

    init {
        stackPush().use {
            val pSurface = it.longs(VK_NULL_HANDLE)
            if (glfwCreateWindowSurface(instanceAccessor(), window, null, pSurface) != VK_SUCCESS) {
                throw RuntimeException("Failed to create window surface")
            }

            surface = pSurface[0]
        }
    }
}