package com.primogemstudio

import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.vulkan.KHRSwapchain.vkDestroySwapchainKHR
import org.lwjgl.vulkan.VkExtent2D
import java.io.Closeable

class VkSwapChain(val logicalDevice: VkLogicalDeviceWrap) : Closeable {
    private var swapChain: Long = 0
    private var swapChainImages: List<Long> = listOf()
    private var swapChainImageFormat: Int = 0
    private var swapChainExtent: VkExtent2D? = null

    init {
        stackPush()
    }

    override fun close() {
        vkDestroySwapchainKHR(logicalDevice.vkDevice, swapChain, null)
    }
}