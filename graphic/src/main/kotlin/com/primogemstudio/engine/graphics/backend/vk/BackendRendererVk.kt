package com.primogemstudio.engine.graphics.backend.vk

import com.primogemstudio.engine.bindings.glfw.GLFWBaseFuncs.glfwInit
import com.primogemstudio.engine.bindings.glfw.GLFWVulkanFuncs.glfwGetRequiredInstanceExtensions
import com.primogemstudio.engine.bindings.vulkan.khr.swapchain.VkSwapchainKHRFuncs.VK_IMAGE_LAYOUT_PRESENT_SRC_KHR
import com.primogemstudio.engine.bindings.vulkan.utils.fromVkApiVersion
import com.primogemstudio.engine.bindings.vulkan.utils.fromVkVersion
import com.primogemstudio.engine.bindings.vulkan.utils.toFullErr
import com.primogemstudio.engine.bindings.vulkan.utils.toVkVersion
import com.primogemstudio.engine.bindings.vulkan.vk10.*
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_ACCESS_COLOR_ATTACHMENT_READ_BIT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_ATTACHMENT_LOAD_OP_CLEAR
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_ATTACHMENT_LOAD_OP_DONT_CARE
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_ATTACHMENT_STORE_OP_DONT_CARE
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_ATTACHMENT_STORE_OP_STORE
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_IMAGE_LAYOUT_UNDEFINED
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_MAKE_API_VERSION
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_PIPELINE_BIND_POINT_GRAPHICS
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_SAMPLE_COUNT_1_BIT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_SHADER_STAGE_ALL_GRAPHICS
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_SHADER_STAGE_COMPUTE_BIT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_SHADER_STAGE_FRAGMENT_BIT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_SHADER_STAGE_GEOMETRY_BIT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_SHADER_STAGE_TESSELLATION_CONTROL_BIT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_SHADER_STAGE_TESSELLATION_EVALUATION_BIT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_SHADER_STAGE_VERTEX_BIT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_SUBPASS_EXTERNAL
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkCreateInstance
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkCreateRenderPass
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkCreateShaderModule
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkDestroyInstance
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkDestroyRenderPass
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkDestroyShaderModule
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkEnumerateInstanceLayerProperties
import com.primogemstudio.engine.foreign.heap.HeapStructArray
import com.primogemstudio.engine.foreign.toCStructArray
import com.primogemstudio.engine.graphics.IRenderer
import com.primogemstudio.engine.graphics.ShaderType
import com.primogemstudio.engine.graphics.backend.vk.dev.LogicalDeviceQueuesVk
import com.primogemstudio.engine.graphics.backend.vk.dev.LogicalDeviceVk
import com.primogemstudio.engine.graphics.backend.vk.dev.PhysicalDeviceVk
import com.primogemstudio.engine.graphics.backend.vk.shader.ShaderCompilerVk
import com.primogemstudio.engine.graphics.backend.vk.shader.ShaderLanguage
import com.primogemstudio.engine.graphics.backend.vk.swapchain.SwapchainVk
import com.primogemstudio.engine.graphics.backend.vk.validation.ValidationLayerVk
import com.primogemstudio.engine.graphics.data.ApplicationInfo
import com.primogemstudio.engine.graphics.data.ApplicationWindowInfo
import com.primogemstudio.engine.i18n.Internationalization.tr
import com.primogemstudio.engine.logging.LoggerFactory
import com.primogemstudio.engine.resource.Identifier
import com.primogemstudio.engine.types.Version
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class BackendRendererVk(
    override val gameInfo: ApplicationInfo,
    override val windowInfo: ApplicationWindowInfo,
    val deviceSelector: (Array<VkPhysicalDevice>) -> VkPhysicalDevice,
    val layerEnabler: (Array<String>) -> Array<String>
) : IRenderer {
    private val logger = LoggerFactory.getAsyncLogger()
    private val compiler = ShaderCompilerVk(this)
    private val shaders = mutableMapOf<Identifier, VkShaderModule>()
    private val shaderTypes = mutableMapOf<Identifier, ShaderType>()
    private val shaderProgs = mutableMapOf<Identifier, HeapStructArray<VkPipelineShaderStageCreateInfo>>()
    private val renderPasses = mutableMapOf<Identifier, VkRenderPass>()

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
                layers = validationLayer.layerArg() + layerEnabler(exts.map { it.layerName }.toTypedArray())
                next = validationLayer.preInstanceAttach().ref()
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
        renderPasses.values.forEach { vkDestroyRenderPass(logicalDevice(), it, null) }
        shaders.values.forEach { vkDestroyShaderModule(logicalDevice(), it, null) }

        swapchain.close()
        logicalDevice.close()
        validationLayer.close()
        vkDestroyInstance(instance, null)
    }

    override fun version(): Version = physicalDevice.physicalDeviceProps.driverVersion.fromVkApiVersion()
    override fun driver(): String =
        "${physicalDevice.physicalDeviceProps.deviceName} ${physicalDevice.physicalDeviceProps.driverVersion.fromVkVersion()}"

    @OptIn(DelicateCoroutinesApi::class)
    override fun registerShader(shaderId: Identifier, src: Identifier, type: ShaderType): Deferred<Int> =
        GlobalScope.async {
            shaderTypes[shaderId] = type
            try {
                val compilerType = com.primogemstudio.engine.graphics.backend.vk.shader.ShaderType.entries[type.ordinal]
                shaders[shaderId] = vkCreateShaderModule(
                    logicalDevice(),
                    VkShaderModuleCreateInfo().apply {
                        code = compiler.compile(
                            src,
                            compilerType,
                            ShaderLanguage.Glsl
                        )
                    },
                    null
                ).match(
                    { it },
                    { throw IllegalStateException(toFullErr("exception.renderer.backend_vk.shader", it)) }
                )
                logger.info(
                    tr(
                        "engine.renderer.backend_vk.stage.shader_compile",
                        tr("engine.shader.types.${compilerType.type}"),
                        shaderId.toString()
                    )
                )
                0
            } catch (_: Exception) {
                1
            }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun linkShader(progId: Identifier, progs: Array<Identifier>): Deferred<Int> = GlobalScope.async {
        val shaders = progs.filter { shaders.containsKey(it) }
        shaderProgs[progId] = HeapStructArray<VkPipelineShaderStageCreateInfo>(
            VkPipelineShaderStageCreateInfo.LAYOUT,
            shaders.size
        ).apply {
            for (i in shaders.indices) {
                VkPipelineShaderStageCreateInfo(this[i]).apply {
                    module = this@BackendRendererVk.shaders[shaders[i]]!!
                    stage = when (this@BackendRendererVk.shaderTypes[shaders[i]]!!) {
                        ShaderType.Vertex -> VK_SHADER_STAGE_VERTEX_BIT
                        ShaderType.Fragment -> VK_SHADER_STAGE_FRAGMENT_BIT
                        ShaderType.TessControl -> VK_SHADER_STAGE_TESSELLATION_CONTROL_BIT
                        ShaderType.TessEvaluation -> VK_SHADER_STAGE_TESSELLATION_EVALUATION_BIT
                        ShaderType.Geometry -> VK_SHADER_STAGE_GEOMETRY_BIT
                        ShaderType.Compute -> VK_SHADER_STAGE_COMPUTE_BIT
                        else -> VK_SHADER_STAGE_ALL_GRAPHICS
                    }
                    name = "main"
                }
            }
        }
        0
    }

    override fun createRenderPass(passId: Identifier) {
        renderPasses[passId] = vkCreateRenderPass(logicalDevice(), VkRenderPassCreateInfo().apply {
            attachments = arrayOf(VkAttachmentDescription().apply {
                format = swapchain.swapchainImageFormat
                samples = VK_SAMPLE_COUNT_1_BIT
                loadOp = VK_ATTACHMENT_LOAD_OP_CLEAR
                storeOp = VK_ATTACHMENT_STORE_OP_STORE
                stencilLoadOp = VK_ATTACHMENT_LOAD_OP_DONT_CARE
                stencilStoreOp = VK_ATTACHMENT_STORE_OP_DONT_CARE
                initialLayout = VK_IMAGE_LAYOUT_UNDEFINED
                finalLayout = VK_IMAGE_LAYOUT_PRESENT_SRC_KHR
            }).toCStructArray(VkAttachmentDescription.LAYOUT)
            subpasses = arrayOf(VkSubpassDescription().apply {
                pipelineBindPoint = VK_PIPELINE_BIND_POINT_GRAPHICS
                colorAttachments = arrayOf(VkAttachmentReference().apply {
                    attachment = 0
                    layout = VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL
                }).toCStructArray(VkAttachmentReference.LAYOUT)
            }).toCStructArray(VkSubpassDescription.LAYOUT)
            dependencies = arrayOf(VkSubpassDependency().apply {
                srcSubpass = VK_SUBPASS_EXTERNAL
                dstSubpass = 0
                srcStageMask = VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT
                srcAccessMask = 0
                dstStageMask = VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT
                dstAccessMask = VK_ACCESS_COLOR_ATTACHMENT_READ_BIT or VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT
            }).toCStructArray(VkSubpassDependency.LAYOUT)
        }, null).match(
            { it },
            { throw IllegalStateException(toFullErr("exception.renderer.backend_vk.renderpass", it)) })
    }
}