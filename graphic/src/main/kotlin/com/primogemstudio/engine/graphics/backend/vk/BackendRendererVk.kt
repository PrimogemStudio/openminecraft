package com.primogemstudio.engine.graphics.backend.vk

import com.primogemstudio.engine.bindings.vulkan.utils.fromVkApiVersion
import com.primogemstudio.engine.bindings.vulkan.utils.fromVkVersion
import com.primogemstudio.engine.bindings.vulkan.utils.toFullErr
import com.primogemstudio.engine.bindings.vulkan.utils.toVkVersion
import com.primogemstudio.engine.bindings.vulkan.vk10.*
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_API_VERSION_1_0
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkCreateInstance
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkEnumeratePhysicalDevices
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkGetPhysicalDeviceProperties
import com.primogemstudio.engine.graphics.IRenderer
import com.primogemstudio.engine.graphics.data.ApplicationInfo
import com.primogemstudio.engine.types.Version

class BackendRendererVk(
    override val gameInfo: ApplicationInfo,
    val deviceSelector: (Array<VkPhysicalDevice>) -> VkPhysicalDevice
) : IRenderer {
    private lateinit var instance: VkInstance
    private lateinit var physicalDevice: VkPhysicalDevice
    private var physicalDeviceProps: VkPhysicalDeviceProperties

    init {
        vkCreateInstance(
            VkInstanceCreateInfo().apply {
                appInfo = VkApplicationInfo().apply {
                    appName = gameInfo.appName
                    appVersion = gameInfo.appVersion.toVkVersion()
                    engineName = gameInfo.engineName
                    engineVersion = gameInfo.engineVersion.toVkVersion()
                    apiVersion = VK_API_VERSION_1_0
                }
            },
            null
        ).match(
            { instance = it },
            { throw IllegalStateException(toFullErr("exception.renderer.backend_vk.instance", it)) }
        )

        vkEnumeratePhysicalDevices(instance).match(
            { physicalDevice = deviceSelector(it) },
            { throw IllegalStateException(toFullErr("exception.renderer.backend_vk.phy_dev", it)) }
        )

        physicalDeviceProps = vkGetPhysicalDeviceProperties(physicalDevice)
    }

    override fun version(): Version = physicalDeviceProps.driverVersion.fromVkApiVersion()
    override fun driver(): String =
        "${physicalDeviceProps.deviceName} ${physicalDeviceProps.driverVersion.fromVkVersion()}"
}