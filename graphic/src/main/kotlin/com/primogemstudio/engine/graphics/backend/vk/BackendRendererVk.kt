package com.primogemstudio.engine.graphics.backend.vk

import com.primogemstudio.engine.bindings.glfw.GLFWBaseFuncs.glfwInit
import com.primogemstudio.engine.bindings.glfw.GLFWVulkanFuncs.glfwGetRequiredInstanceExtensions
import com.primogemstudio.engine.bindings.vulkan.utils.fromVkApiVersion
import com.primogemstudio.engine.bindings.vulkan.utils.fromVkVersion
import com.primogemstudio.engine.bindings.vulkan.utils.toFullErr
import com.primogemstudio.engine.bindings.vulkan.utils.toVkVersion
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_MAKE_API_VERSION
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkCreateInstance
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkDestroyInstance
import com.primogemstudio.engine.bindings.vulkan.vk10.VkApplicationInfo
import com.primogemstudio.engine.bindings.vulkan.vk10.VkInstance
import com.primogemstudio.engine.bindings.vulkan.vk10.VkInstanceCreateInfo
import com.primogemstudio.engine.bindings.vulkan.vk10.VkPhysicalDevice
import com.primogemstudio.engine.graphics.IRenderer
import com.primogemstudio.engine.graphics.backend.vk.dev.PhysicalDeviceVk
import com.primogemstudio.engine.graphics.backend.vk.validation.ValidationLayerVk
import com.primogemstudio.engine.graphics.data.ApplicationInfo
import com.primogemstudio.engine.graphics.data.ApplicationWindowInfo
import com.primogemstudio.engine.logging.LoggerFactory
import com.primogemstudio.engine.types.Version

class BackendRendererVk(
    override val gameInfo: ApplicationInfo,
    override val windowInfo: ApplicationWindowInfo,
    val deviceSelector: (Array<VkPhysicalDevice>) -> VkPhysicalDevice
) : IRenderer {
    private val logger = LoggerFactory.getAsyncLogger()
    private val validationLayer = ValidationLayerVk()
    lateinit var instance: VkInstance
    override var window: VulkanWindow
    val physicalDevice: PhysicalDeviceVk

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
                extensions = validationLayer.appendExt(glfwGetRequiredInstanceExtensions())
                layers = validationLayer.layerArg()
            },
            null
        ).match(
            { instance = it },
            { throw IllegalStateException(toFullErr("exception.renderer.backend_vk.instance", it)) }
        )
        validationLayer.instanceAttach(instance)

        window = VulkanWindow(this, windowInfo) { code, str -> }

        physicalDevice = PhysicalDeviceVk(this, instance)

    }

    override fun close() {
        validationLayer.close()
        vkDestroyInstance(instance, null)
    }

    override fun version(): Version = physicalDevice.physicalDeviceProps.driverVersion.fromVkApiVersion()
    override fun driver(): String =
        "${physicalDevice.physicalDeviceProps.deviceName} ${physicalDevice.physicalDeviceProps.driverVersion.fromVkVersion()}"
}