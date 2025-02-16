package com.primogemstudio.engine.graphics.backend.vk

import com.primogemstudio.engine.bindings.glfw.GLFWBaseFuncs.glfwInit
import com.primogemstudio.engine.bindings.glfw.GLFWVulkanFuncs.glfwGetRequiredInstanceExtensions
import com.primogemstudio.engine.bindings.vulkan.utils.fromVkApiVersion
import com.primogemstudio.engine.bindings.vulkan.utils.fromVkVersion
import com.primogemstudio.engine.bindings.vulkan.utils.toFullErr
import com.primogemstudio.engine.bindings.vulkan.utils.toVkVersion
import com.primogemstudio.engine.bindings.vulkan.vk10.*
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_MAKE_API_VERSION
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkCreateInstance
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkDestroyInstance
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkEnumeratePhysicalDevices
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkGetPhysicalDeviceProperties
import com.primogemstudio.engine.graphics.IRenderer
import com.primogemstudio.engine.graphics.data.ApplicationInfo
import com.primogemstudio.engine.graphics.data.ApplicationWindowInfo
import com.primogemstudio.engine.types.Version

class BackendRendererVk(
    override val gameInfo: ApplicationInfo,
    override val windowInfo: ApplicationWindowInfo,
    val deviceSelector: (Array<VkPhysicalDevice>) -> VkPhysicalDevice
) : IRenderer {
    lateinit var instance: VkInstance
    private lateinit var physicalDevice: VkPhysicalDevice
    private var physicalDeviceProps: VkPhysicalDeviceProperties
    override var window: VulkanWindow

    init {
        glfwInit()
        vkCreateInstance(
            VkInstanceCreateInfo().apply {
                appInfo = VkApplicationInfo().apply {
                    appName = gameInfo.appName
                    appVersion = gameInfo.appVersion.toVkVersion()
                    engineName = gameInfo.engineName
                    engineVersion = gameInfo.engineVersion.toVkVersion()
                    apiVersion = VK_MAKE_API_VERSION(
                        gameInfo.reqApiVersion.major.toInt(),
                        gameInfo.reqApiVersion.minor.toInt(),
                        gameInfo.reqApiVersion.patch.toInt(),
                        gameInfo.reqApiVersion.ext.toInt()
                    )
                }
                extensions = glfwGetRequiredInstanceExtensions()
            },
            null
        ).match(
            { instance = it },
            { throw IllegalStateException(toFullErr("exception.renderer.backend_vk.instance", it)) }
        )

        window = VulkanWindow(this, windowInfo) { code, str -> }

        vkEnumeratePhysicalDevices(instance).match(
            { physicalDevice = deviceSelector(it) },
            { throw IllegalStateException(toFullErr("exception.renderer.backend_vk.phy_dev", it)) }
        )

        physicalDeviceProps = vkGetPhysicalDeviceProperties(physicalDevice)
    }

    override fun close() {
        vkDestroyInstance(instance, null)
    }

    override fun version(): Version = physicalDeviceProps.driverVersion.fromVkApiVersion()
    override fun driver(): String =
        "${physicalDeviceProps.deviceName} ${physicalDeviceProps.driverVersion.fromVkVersion()}"
}