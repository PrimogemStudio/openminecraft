package com.primogemstudio.engine.graphics.backend.vk.swapchain

import com.primogemstudio.engine.bindings.vulkan.khr.surface.VkSurfaceCapabilitiesKHR
import com.primogemstudio.engine.bindings.vulkan.khr.surface.VkSurfaceFormatKHR
import com.primogemstudio.engine.bindings.vulkan.khr.surface.VkSurfaceKHRFuncs.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR
import com.primogemstudio.engine.bindings.vulkan.khr.surface.VkSurfaceKHRFuncs.VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR
import com.primogemstudio.engine.bindings.vulkan.khr.surface.VkSurfaceKHRFuncs.VK_PRESENT_MODE_IMMEDIATE_KHR
import com.primogemstudio.engine.bindings.vulkan.khr.surface.VkSurfaceKHRFuncs.VK_PRESENT_MODE_MAILBOX_KHR
import com.primogemstudio.engine.bindings.vulkan.khr.swapchain.VkSwapchainCreateInfoKHR
import com.primogemstudio.engine.bindings.vulkan.khr.swapchain.VkSwapchainKHR
import com.primogemstudio.engine.bindings.vulkan.khr.swapchain.VkSwapchainKHRFuncs.vkCreateSwapchainKHR
import com.primogemstudio.engine.bindings.vulkan.khr.swapchain.VkSwapchainKHRFuncs.vkDestroySwapchainKHR
import com.primogemstudio.engine.bindings.vulkan.khr.swapchain.VkSwapchainKHRFuncs.vkGetSwapchainImagesKHR
import com.primogemstudio.engine.bindings.vulkan.utils.toFullErr
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_COMPONENT_SWIZZLE_IDENTITY
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_FORMAT_B8G8R8_UNORM
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_IMAGE_ASPECT_COLOR_BIT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_IMAGE_VIEW_TYPE_2D
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_SHARING_MODE_CONCURRENT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_SHARING_MODE_EXCLUSIVE
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkCreateImageView
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkDestroyImageView
import com.primogemstudio.engine.bindings.vulkan.vk10.VkImage
import com.primogemstudio.engine.bindings.vulkan.vk10.VkImageSubresourceRange
import com.primogemstudio.engine.bindings.vulkan.vk10.VkImageView
import com.primogemstudio.engine.bindings.vulkan.vk10.VkImageViewCreateInfo
import com.primogemstudio.engine.foreign.heap.HeapIntArray
import com.primogemstudio.engine.graphics.backend.vk.BackendRendererVk
import com.primogemstudio.engine.graphics.backend.vk.IReinitable
import org.joml.Vector2i
import org.joml.Vector4i
import kotlin.math.max
import kotlin.math.min
import kotlin.properties.Delegates

class SwapchainVk(
    private val renderer: BackendRendererVk
) : IReinitable {
    lateinit var swapchain: VkSwapchainKHR
    lateinit var swapchainImages: Array<VkImage>
    lateinit var swapchainExtent: Vector2i
    var swapchainImageFormat by Delegates.notNull<Int>()
    lateinit var swapchainImageViews: Array<VkImageView>

    init {
        swapchainBaseInit()
    }

    private fun swapchainBaseInit() {
        val supp = renderer.physicalDevice.fetchSwapChainSupport()

        var imageCount = supp.capabilities.minImageCount + 1
        if (supp.capabilities.maxImageCount in 1..<imageCount) {
            imageCount = supp.capabilities.maxImageCount
        }

        swapchain = vkCreateSwapchainKHR(
            renderer.logicalDevice(),
            VkSwapchainCreateInfoKHR().apply {
                surface = renderer.window.surface
                minImageCount = imageCount
                imageFormat = chooseFormats(supp.formats).format
                imageColorSpace = chooseFormats(supp.formats).colorSpace
                imageExtent = chooseExtent(supp.capabilities)
                imageArrayLayers = 1
                imageUsage = VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT

                if (renderer.physicalDevice.presentFamily != renderer.physicalDevice.graphicFamily) {
                    imageSharingMode = VK_SHARING_MODE_CONCURRENT
                    queueFamilyIndices = HeapIntArray(
                        intArrayOf(
                            renderer.physicalDevice.presentFamily,
                            renderer.physicalDevice.graphicFamily
                        )
                    )
                } else {
                    imageSharingMode = VK_SHARING_MODE_EXCLUSIVE
                }

                preTransform = supp.capabilities.currentTransform
                compositeAlpha = VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR
                presentMode = choosePresentModes(supp.presentModes)
                clipped = true
                if (::swapchain.isInitialized) oldSwapchain = swapchain
            },
            null
        ).match(
            { it },
            { throw IllegalStateException(toFullErr("exception.renderer.backend_vk.swapchain", it)) }
        )

        swapchainImages = vkGetSwapchainImagesKHR(renderer.logicalDevice(), swapchain).match(
            { it },
            { throw IllegalStateException(toFullErr("exception.renderer.backend_vk.swapchain_img", it)) }
        )

        swapchainExtent = chooseExtent(supp.capabilities)
        swapchainImageFormat = chooseFormats(supp.formats).format

        swapchainImageViews = swapchainImages.map {
            vkCreateImageView(renderer.logicalDevice(), VkImageViewCreateInfo().apply {
                image = it
                viewType = VK_IMAGE_VIEW_TYPE_2D
                format = swapchainImageFormat
                components = Vector4i(
                    VK_COMPONENT_SWIZZLE_IDENTITY,
                    VK_COMPONENT_SWIZZLE_IDENTITY,
                    VK_COMPONENT_SWIZZLE_IDENTITY,
                    VK_COMPONENT_SWIZZLE_IDENTITY
                )
                range = VkImageSubresourceRange().apply {
                    aspectMask = VK_IMAGE_ASPECT_COLOR_BIT
                    baseMipLevel = 0
                    levelCount = 1
                    baseArrayLayer = 0
                    layerCount = 1
                }
            }, null).match(
                { it },
                { throw IllegalStateException(toFullErr("exception.renderer.backend_vk.swapchain_img_view", it)) }
            )
        }.toTypedArray()
    }

    private fun chooseFormats(formats: Array<VkSurfaceFormatKHR>): VkSurfaceFormatKHR =
        formats.firstOrNull { it.format == VK_FORMAT_B8G8R8_UNORM && it.colorSpace == VK_COLOR_SPACE_SRGB_NONLINEAR_KHR }
            ?: formats[0]

    private fun choosePresentModes(modes: IntArray): Int =
        modes.firstOrNull { it == VK_PRESENT_MODE_MAILBOX_KHR } ?: VK_PRESENT_MODE_IMMEDIATE_KHR

    private fun chooseExtent(cap: VkSurfaceCapabilitiesKHR): Vector2i {
        if (cap.currentExtent.x != -1) return cap.currentExtent

        val ws = renderer.window.size()
        val mi = cap.minImageExtent
        val ma = cap.maxImageExtent

        return Vector2i(
            min(ma.x, max(ws.x, mi.x)),
            min(ma.x, max(ws.x, mi.y))
        )
    }

    override fun reinit() {
        close()
        swapchainBaseInit()
    }

    override fun close() {
        swapchainImageViews.forEach { vkDestroyImageView(renderer.logicalDevice(), it, null) }
        vkDestroySwapchainKHR(renderer.logicalDevice(), swapchain, null)
    }
}