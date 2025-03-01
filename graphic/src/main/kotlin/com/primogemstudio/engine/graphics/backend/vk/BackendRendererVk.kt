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
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkEnumerateInstanceLayerProperties
import com.primogemstudio.engine.bindings.vulkan.vk10.VkApplicationInfo
import com.primogemstudio.engine.bindings.vulkan.vk10.VkInstance
import com.primogemstudio.engine.bindings.vulkan.vk10.VkInstanceCreateInfo
import com.primogemstudio.engine.bindings.vulkan.vk10.VkPhysicalDevice
import com.primogemstudio.engine.graphics.IRenderer
import com.primogemstudio.engine.graphics.ShaderType
import com.primogemstudio.engine.graphics.backend.vk.dev.LogicalDeviceQueuesVk
import com.primogemstudio.engine.graphics.backend.vk.dev.LogicalDeviceVk
import com.primogemstudio.engine.graphics.backend.vk.dev.PhysicalDeviceVk
import com.primogemstudio.engine.graphics.backend.vk.shader.ShaderCompilerVk
import com.primogemstudio.engine.graphics.backend.vk.swapchain.SwapchainVk
import com.primogemstudio.engine.graphics.backend.vk.validation.ValidationLayerVk
import com.primogemstudio.engine.graphics.data.ApplicationInfo
import com.primogemstudio.engine.graphics.data.ApplicationWindowInfo
import com.primogemstudio.engine.i18n.Internationalization.tr
import com.primogemstudio.engine.logging.LoggerFactory
import com.primogemstudio.engine.resource.Identifier
import com.primogemstudio.engine.types.Version

class BackendRendererVk(
    override val gameInfo: ApplicationInfo,
    override val windowInfo: ApplicationWindowInfo,
    val deviceSelector: (Array<VkPhysicalDevice>) -> VkPhysicalDevice,
    val layerEnabler: (Array<String>) -> Array<String>
) : IRenderer {
    private val logger = LoggerFactory.getAsyncLogger()
    private val compiler = ShaderCompilerVk(this)

    val validationLayer: ValidationLayerVk
    val instance: VkInstance
    override val window: VulkanWindow
    val physicalDevice: PhysicalDeviceVk
    val logicalDevice: LogicalDeviceVk
    val logicalDeviceQueues: LogicalDeviceQueuesVk
    val swapchain: SwapchainVk

    init {
        glfwInit()

        val exts = vkEnumerateInstanceLayerProperties().match({ it }, {
            logger.warn(toFullErr("exception.renderer.backend_vk.layer", it))
            arrayOf()
        })

        validationLayer = ValidationLayerVk(exts)

        instance = vkCreateInstance(
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
                layers = layerEnabler(exts.map { it.layerName }.toTypedArray())
            },
            null
        ).match(
            { it },
            { throw IllegalStateException(toFullErr("exception.renderer.backend_vk.instance", it)) }
        )
        logger.info(tr("engine.renderer.backend_vk.stage.instance", gameInfo.appName, gameInfo.appVersion))
        validationLayer.instanceAttach(this)
        logger.info(tr("engine.renderer.backend_vk.stage.validation"))
        window = VulkanWindow(this, windowInfo) { code, str ->
            logger.error(
                tr(
                    "engine.renderer.backend_vk.log",
                    "GLFW",
                    "$code, $str"
                )
            )
        }
        logger.info(tr("engine.renderer.backend_vk.stage.window"))
        physicalDevice = PhysicalDeviceVk(this)
        logger.info(tr("engine.renderer.backend_vk.stage.phy_device"))
        logicalDevice = LogicalDeviceVk(this)
        logger.info(tr("engine.renderer.backend_vk.stage.logic_device"))
        logicalDeviceQueues = LogicalDeviceQueuesVk(this)
        logger.info(tr("engine.renderer.backend_vk.stage.logic_device_queue"))
        swapchain = SwapchainVk(this)
        logger.info(tr("engine.renderer.backend_vk.stage.swapchain"))

    }

    override fun close() {
        swapchain.close()
        logicalDevice.close()
        validationLayer.close()
        vkDestroyInstance(instance, null)
    }

    override fun version(): Version = physicalDevice.physicalDeviceProps.driverVersion.fromVkApiVersion()
    override fun driver(): String =
        "${physicalDevice.physicalDeviceProps.deviceName} ${physicalDevice.physicalDeviceProps.driverVersion.fromVkVersion()}"

    override fun registerShader(shaderId: Identifier, src: Identifier, type: ShaderType) {
        TODO("Not yet implemented")
    }

    override fun linkShader(progId: Identifier, progs: Array<Identifier>) {
        TODO("Not yet implemented")
    }
}