package com.primogemstudio.engine.vk

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWVulkan.glfwCreateWindowSurface
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.vulkan.KHRSurface.vkDestroySurfaceKHR
import org.lwjgl.vulkan.VK10.VK_NULL_HANDLE
import org.lwjgl.vulkan.VK10.VK_SUCCESS
import org.lwjgl.vulkan.VkInstance
import java.io.Closeable

class VkWindow(
    private val instanceAccessor: () -> VkInstance,
    var width: Int,
    var height: Int,
    private var title: String,
    var renderCall: () -> Unit
) : Closeable {
    var vkSurface: Long = 0
    var window: Long = 0
    var sizeChanging = false

    init {
        stackPush().use {
            glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)
            window = glfwCreateWindow(width, height, title, 0, 0)
            val pSurface = it.longs(VK_NULL_HANDLE)
            if (glfwCreateWindowSurface(instanceAccessor(), window, null, pSurface) != VK_SUCCESS) {
                throw RuntimeException("Failed to create window surface")
            }
            glfwSetFramebufferSizeCallback(window) { _, w, h ->
                sizeChanging = true
            }

            vkSurface = pSurface[0]
        }
    }

    override fun close() {
        vkDestroySurfaceKHR(instanceAccessor(), vkSurface, null)
    }

    fun mainLoop() {
        while (!glfwWindowShouldClose(window)) {
            renderCall()
            glfwPollEvents()
        }
    }
}