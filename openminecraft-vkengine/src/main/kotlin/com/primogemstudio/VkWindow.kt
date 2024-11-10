package com.primogemstudio

import org.lwjgl.glfw.GLFW.glfwCreateWindow
import org.lwjgl.glfw.GLFWVulkan.glfwCreateWindowSurface
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.vulkan.VK10.VK_NULL_HANDLE
import org.lwjgl.vulkan.VK10.VK_SUCCESS
import org.lwjgl.vulkan.VkInstance

class VkWindow(
    private val instanceAccessor: () -> VkInstance,
    var width: Int,
    var height: Int,
    private var title: String
) {
    var surface: Long = 0
    private var window: Long = 0

    init {
        stackPush().use {
            window = glfwCreateWindow(width, height, title, 0, 0)
            val pSurface = it.longs(VK_NULL_HANDLE)
            if (glfwCreateWindowSurface(instanceAccessor(), window, null, pSurface) != VK_SUCCESS) {
                throw RuntimeException("Failed to create window surface")
            }

            surface = pSurface[0]
        }
    }
}