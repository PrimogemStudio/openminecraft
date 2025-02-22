package com.primogemstudio.engine.graphics.backend.vk.dev

import com.primogemstudio.engine.bindings.vulkan.khr.surface.VkSurfaceKHRFuncs.vkGetPhysicalDeviceSurfaceSupportKHR
import com.primogemstudio.engine.bindings.vulkan.utils.toFullErr
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_QUEUE_GRAPHICS_BIT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkEnumeratePhysicalDevices
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkGetPhysicalDeviceProperties
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkGetPhysicalDeviceQueueFamilyProperties
import com.primogemstudio.engine.bindings.vulkan.vk10.VkInstance
import com.primogemstudio.engine.bindings.vulkan.vk10.VkPhysicalDevice
import com.primogemstudio.engine.bindings.vulkan.vk10.VkPhysicalDeviceProperties
import com.primogemstudio.engine.graphics.backend.vk.BackendRendererVk
import com.primogemstudio.engine.i18n.Internationalization.tr

class PhysicalDeviceVk(
    private val renderer: BackendRendererVk,
    instance: VkInstance
) {
    private lateinit var physicalDevice: VkPhysicalDevice
    val physicalDeviceProps: VkPhysicalDeviceProperties
    val graphicFamily: Int
    val presentFamily: Int

    init {
        vkEnumeratePhysicalDevices(instance).match(
            { physicalDevice = renderer.deviceSelector(it) },
            { throw IllegalStateException(toFullErr("exception.renderer.backend_vk.phy_dev", it)) }
        )

        physicalDeviceProps = vkGetPhysicalDeviceProperties(physicalDevice)

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
    }
}