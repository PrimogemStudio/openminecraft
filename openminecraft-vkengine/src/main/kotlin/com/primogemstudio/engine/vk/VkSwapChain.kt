package com.primogemstudio.engine.vk

import org.lwjgl.glfw.GLFW.glfwGetFramebufferSize
import org.lwjgl.glfw.GLFW.glfwWaitEvents
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.vulkan.*
import org.lwjgl.vulkan.KHRSurface.*
import org.lwjgl.vulkan.KHRSwapchain.*
import org.lwjgl.vulkan.VK10.*
import java.io.Closeable
import java.nio.IntBuffer
import kotlin.math.max
import kotlin.math.min

class VkSwapChain(
    private val logicalDevice: VkLogicalDeviceWrap,
    private val physicalDevice: VkPhysicalDeviceWrap,
    private val vkWindow: VkWindow,
    private val onRecreate: () -> Unit
) : Closeable {
    var swapChain: Long = 0
    private var swapChainImages: List<Long> = listOf()
    var swapChainImageViews: List<Long> = listOf()
    var swapChainImageFormat: Int = 0
    var swapChainExtent: VkExtent2D? = null

    init {
        initBase()
    }

    private fun initBase() {
        stackPush().use {
            physicalDevice.updateChainSupport()
            val support = physicalDevice.swapChainSupport

            val surfaceFormat = chooseSwapSurfaceFormat(support.formats!!)
            val presentMode = chooseSwapPresentMode(support.presentModes!!)
            val extent = chooseSwapExtent(it, support.capabilities!!)

            val imageCount = it.ints(support.capabilities!!.minImageCount() + 1)

            if (support.capabilities!!.maxImageCount() > 0 && imageCount[0] > support.capabilities!!.maxImageCount()) {
                imageCount.put(0, support.capabilities!!.maxImageCount())
            }

            val createInfo = VkSwapchainCreateInfoKHR.calloc(it).apply {
                sType(VK_STRUCTURE_TYPE_SWAPCHAIN_CREATE_INFO_KHR)
                surface(vkWindow.vkSurface)

                minImageCount(imageCount[0])
                imageFormat(surfaceFormat.format())
                imageColorSpace(surfaceFormat.colorSpace())
                imageExtent(extent)
                imageArrayLayers(1)
                imageUsage(VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT)

                if (physicalDevice.graphicsFamily != physicalDevice.presentFamily) {
                    imageSharingMode(VK_SHARING_MODE_CONCURRENT)
                    pQueueFamilyIndices(it.ints(physicalDevice.graphicsFamily!!, physicalDevice.presentFamily!!))
                } else imageSharingMode(VK_SHARING_MODE_EXCLUSIVE)

                preTransform(support.capabilities!!.currentTransform())
                compositeAlpha(VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR)
                presentMode(presentMode)
                clipped(true)

                oldSwapchain(VK_NULL_HANDLE)
            }

            val pSwapChain = it.longs(0)
            if (vkCreateSwapchainKHR(logicalDevice.vkDevice, createInfo, null, pSwapChain) != VK_SUCCESS) {
                throw RuntimeException("Failed to create swap chain")
            }

            swapChain = pSwapChain[0]
            vkGetSwapchainImagesKHR(logicalDevice.vkDevice, swapChain, imageCount, null)

            val pSwapchainImages = it.mallocLong(imageCount[0])
            vkGetSwapchainImagesKHR(logicalDevice.vkDevice, swapChain, imageCount, pSwapchainImages)

            val swapChainImagesT = mutableListOf<Long>()
            for (i in 0..<pSwapchainImages.capacity()) {
                swapChainImagesT.add(pSwapchainImages[i])
            }
            swapChainImages = swapChainImagesT

            swapChainImageFormat = surfaceFormat.format()
            swapChainExtent = VkExtent2D.create().set(extent)

            val swapChainImageViewsT = mutableListOf<Long>()
            val pImageView = it.mallocLong(1)

            for (swapChainImage in swapChainImages) {
                val createInfo = VkImageViewCreateInfo.calloc(it).apply {
                    sType(VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO)
                    image(swapChainImage)
                    viewType(VK_IMAGE_VIEW_TYPE_2D)
                    format(swapChainImageFormat)

                    components().apply {
                        r(VK_COMPONENT_SWIZZLE_IDENTITY)
                        g(VK_COMPONENT_SWIZZLE_IDENTITY)
                        b(VK_COMPONENT_SWIZZLE_IDENTITY)
                        a(VK_COMPONENT_SWIZZLE_IDENTITY)
                    }

                    subresourceRange().apply {
                        aspectMask(VK_IMAGE_ASPECT_COLOR_BIT)
                        baseMipLevel(0)
                        levelCount(1)
                        baseArrayLayer(0)
                        layerCount(1)
                    }
                }

                if (vkCreateImageView(logicalDevice.vkDevice, createInfo, null, pImageView) != VK_SUCCESS) {
                    throw RuntimeException("Failed to create image views")
                }

                swapChainImageViewsT.add(pImageView[0])
            }

            swapChainImageViews = swapChainImageViewsT
        }
    }

    private fun chooseSwapSurfaceFormat(availableFormats: VkSurfaceFormatKHR.Buffer): VkSurfaceFormatKHR {
        return availableFormats
            .filter { it.format() == VK_FORMAT_B8G8R8_UNORM }
            .firstOrNull { it.colorSpace() == VK_COLOR_SPACE_SRGB_NONLINEAR_KHR } ?: availableFormats[0]
    }

    private fun chooseSwapPresentMode(availablePresentModes: IntBuffer): Int {
        for (i in 0..<availablePresentModes.capacity()) {
            if (availablePresentModes[i] == VK_PRESENT_MODE_MAILBOX_KHR) return availablePresentModes[i]
        }

        return VK_PRESENT_MODE_FIFO_KHR
    }

    private fun chooseSwapExtent(stack: MemoryStack, capabilities: VkSurfaceCapabilitiesKHR): VkExtent2D {
        if (capabilities.currentExtent().width() != -1) return capabilities.currentExtent()

        val width = stack.ints(0)
        val height = stack.ints(0)
        glfwGetFramebufferSize(vkWindow.window, width, height)

        val clamp = { v: Int, min: Int, max: Int -> min(max, max(v, min)) }
        val actualExtent = VkExtent2D.malloc(stack).set(
            clamp(width[0], capabilities.minImageExtent().width(), capabilities.maxImageExtent().width()),
            clamp(height[0], capabilities.minImageExtent().height(), capabilities.maxImageExtent().height())
        )

        return actualExtent
    }

    fun recreate() {
        stackPush().use {
            val width = it.ints(0)
            val height = it.ints(0)

            while (width[0] == 0 && height[0] == 0) {
                glfwGetFramebufferSize(vkWindow.window, width, height)
                glfwWaitEvents()
            }

            vkDeviceWaitIdle(logicalDevice.vkDevice)

            close()
        }
        initBase()
        onRecreate()
    }

    override fun close() {
        swapChainImageViews.forEach { vkDestroyImageView(logicalDevice.vkDevice, it, null) }
        vkDestroySwapchainKHR(logicalDevice.vkDevice, swapChain, null)
    }
}