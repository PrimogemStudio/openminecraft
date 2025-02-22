package com.primogemstudio.engine.graphics.backend.vk.dev

import com.primogemstudio.engine.bindings.vulkan.utils.toFullErr
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkEnumeratePhysicalDevices
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkGetPhysicalDeviceProperties
import com.primogemstudio.engine.bindings.vulkan.vk10.VkInstance
import com.primogemstudio.engine.bindings.vulkan.vk10.VkPhysicalDevice
import com.primogemstudio.engine.bindings.vulkan.vk10.VkPhysicalDeviceProperties
import com.primogemstudio.engine.graphics.backend.vk.BackendRendererVk

class PhysicalDeviceVk(
    private val renderer: BackendRendererVk,
    instance: VkInstance
) {
    private lateinit var physicalDevice: VkPhysicalDevice
    val physicalDeviceProps: VkPhysicalDeviceProperties

    init {
        vkEnumeratePhysicalDevices(instance).match(
            { physicalDevice = renderer.deviceSelector(it) },
            { throw IllegalStateException(toFullErr("exception.renderer.backend_vk.phy_dev", it)) }
        )

        physicalDeviceProps = vkGetPhysicalDeviceProperties(physicalDevice)
    }
}