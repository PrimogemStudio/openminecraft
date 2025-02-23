package com.primogemstudio.engine.graphics.backend.vk.dev

import com.primogemstudio.engine.bindings.vulkan.khr.surface.VkSurfaceKHRFuncs.vkGetPhysicalDeviceSurfaceCapabilitiesKHR
import com.primogemstudio.engine.bindings.vulkan.khr.surface.VkSurfaceKHRFuncs.vkGetPhysicalDeviceSurfaceFormatsKHR
import com.primogemstudio.engine.bindings.vulkan.khr.surface.VkSurfaceKHRFuncs.vkGetPhysicalDeviceSurfacePresentModesKHR
import com.primogemstudio.engine.bindings.vulkan.khr.surface.VkSurfaceKHRFuncs.vkGetPhysicalDeviceSurfaceSupportKHR
import com.primogemstudio.engine.bindings.vulkan.utils.toFullErr
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_QUEUE_GRAPHICS_BIT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkEnumeratePhysicalDevices
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkGetPhysicalDeviceProperties
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkGetPhysicalDeviceQueueFamilyProperties
import com.primogemstudio.engine.bindings.vulkan.vk10.VkPhysicalDevice
import com.primogemstudio.engine.bindings.vulkan.vk10.VkPhysicalDeviceProperties
import com.primogemstudio.engine.graphics.backend.vk.BackendRendererVk
import com.primogemstudio.engine.i18n.Internationalization.tr
import com.primogemstudio.engine.logging.LoggerFactory

class PhysicalDeviceVk(
    private val renderer: BackendRendererVk
) {
    private val logger = LoggerFactory.getAsyncLogger()
    val physicalDevice: VkPhysicalDevice = vkEnumeratePhysicalDevices(renderer.instance).match(
        { renderer.deviceSelector(it) },
        { throw IllegalStateException(toFullErr("exception.renderer.backend_vk.phy_dev", it)) }
    )
    val physicalDeviceProps: VkPhysicalDeviceProperties = vkGetPhysicalDeviceProperties(physicalDevice)
    val graphicFamily: Int
    val presentFamily: Int

    operator fun invoke(): VkPhysicalDevice = physicalDevice

    init {
        val props = vkGetPhysicalDeviceQueueFamilyProperties(physicalDevice)
        var gf = -1
        var pf = -1
        for (i in props.indices) {
            if (props[i].queueFlags and VK_QUEUE_GRAPHICS_BIT != 0) {
                gf = i
            }

            vkGetPhysicalDeviceSurfaceSupportKHR(physicalDevice, i, renderer.window.surface).match({
                if (it) pf = i
            }, { throw IllegalStateException(toFullErr("exception.renderer.backend_vk.phy_dev_query", it)) })
        }

        graphicFamily = gf
        presentFamily = pf

        if (graphicFamily == -1 || presentFamily == -1) {
            throw IllegalStateException(tr("exception.renderer.backend_vk.family"))
        }

        logger.info(
            tr(
                "engine.renderer.backend_vk.stage.phy_device.family",
                graphicFamily,
                presentFamily,
                physicalDeviceProps.deviceName
            )
        )
    }

    fun fetchSwapChainSupport(): PhysicalDeviceSwapChainSupVk {
        val cap = vkGetPhysicalDeviceSurfaceCapabilitiesKHR(physicalDevice, renderer.window.surface).match(
            { it },
            { throw IllegalStateException(toFullErr("exception.renderer.backend_vk.swp_cap", it)) }
        )
        val formats = vkGetPhysicalDeviceSurfaceFormatsKHR(physicalDevice, renderer.window.surface).match(
            { it },
            { throw IllegalStateException(toFullErr("exception.renderer.backend_vk.swp_format", it)) }
        )
        val presentModes = vkGetPhysicalDeviceSurfacePresentModesKHR(physicalDevice, renderer.window.surface).match(
            { it },
            { throw IllegalStateException(toFullErr("exception.renderer.backend_vk.swp_present", it)) }
        )

        return PhysicalDeviceSwapChainSupVk(cap, formats, presentModes)
    }

    fun allFamilies(): IntArray = intArrayOf(graphicFamily, presentFamily).distinct().toIntArray()
}