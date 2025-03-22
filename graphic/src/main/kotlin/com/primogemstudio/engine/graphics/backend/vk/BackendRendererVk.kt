package com.primogemstudio.engine.graphics.backend.vk

import com.primogemstudio.engine.bindings.glfw.GLFWBaseFuncs.glfwInit
import com.primogemstudio.engine.bindings.glfw.GLFWVulkanFuncs.glfwGetRequiredInstanceExtensions
import com.primogemstudio.engine.bindings.vulkan.khr.swapchain.VkPresentInfoKHR
import com.primogemstudio.engine.bindings.vulkan.khr.swapchain.VkSwapchainKHRFuncs.VK_ERROR_OUT_OF_DATE_KHR
import com.primogemstudio.engine.bindings.vulkan.khr.swapchain.VkSwapchainKHRFuncs.VK_IMAGE_LAYOUT_PRESENT_SRC_KHR
import com.primogemstudio.engine.bindings.vulkan.khr.swapchain.VkSwapchainKHRFuncs.VK_SUBOPTIMAL_KHR
import com.primogemstudio.engine.bindings.vulkan.khr.swapchain.VkSwapchainKHRFuncs.vkAcquireNextImageKHR
import com.primogemstudio.engine.bindings.vulkan.khr.swapchain.VkSwapchainKHRFuncs.vkQueuePresentKHR
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
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_BUFFER_USAGE_VERTEX_BUFFER_BIT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_COLOR_COMPONENT_A_BIT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_COLOR_COMPONENT_B_BIT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_COLOR_COMPONENT_G_BIT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_COLOR_COMPONENT_R_BIT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_COMMAND_BUFFER_LEVEL_PRIMARY
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_CULL_MODE_BACK_BIT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_FENCE_CREATE_SIGNALED_BIT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_FORMAT_R32G32B32_SFLOAT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_FORMAT_R32G32_SFLOAT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_FRONT_FACE_CLOCKWISE
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_IMAGE_LAYOUT_UNDEFINED
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_LOGIC_OP_COPY
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_MAKE_API_VERSION
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_MEMORY_PROPERTY_HOST_COHERENT_BIT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_PIPELINE_BIND_POINT_GRAPHICS
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_POLYGON_MODE_FILL
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_SAMPLE_COUNT_1_BIT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_SHADER_STAGE_ALL_GRAPHICS
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_SHADER_STAGE_COMPUTE_BIT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_SHADER_STAGE_FRAGMENT_BIT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_SHADER_STAGE_GEOMETRY_BIT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_SHADER_STAGE_TESSELLATION_CONTROL_BIT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_SHADER_STAGE_TESSELLATION_EVALUATION_BIT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_SHADER_STAGE_VERTEX_BIT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_SHARING_MODE_EXCLUSIVE
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_SUBPASS_CONTENTS_INLINE
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_SUBPASS_EXTERNAL
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_SUCCESS
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_VERTEX_INPUT_RATE_VERTEX
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkAllocateCommandBuffers
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkBeginCommandBuffer
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkCmdBeginRenderPass
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkCmdBindPipeline
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkCmdBindVertexBuffers
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkCmdDraw
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkCmdEndRenderPass
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkCreateCommandPool
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkCreateFence
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkCreateFramebuffer
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkCreateGraphicsPipelines
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkCreateInstance
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkCreatePipelineLayout
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkCreateRenderPass
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkCreateSemaphore
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkCreateShaderModule
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkDestroyCommandPool
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkDestroyFence
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkDestroyFramebuffer
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkDestroyInstance
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkDestroyPipeline
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkDestroyPipelineLayout
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkDestroyRenderPass
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkDestroySemaphore
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkDestroyShaderModule
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkDeviceWaitIdle
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkEndCommandBuffer
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkEnumerateInstanceLayerProperties
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkFreeCommandBuffers
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkGetPhysicalDeviceFeatures
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkQueueSubmit
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkResetFences
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkWaitForFences
import com.primogemstudio.engine.foreign.heap.HeapByteArray
import com.primogemstudio.engine.foreign.heap.HeapIntArray
import com.primogemstudio.engine.foreign.heap.HeapLongArray
import com.primogemstudio.engine.foreign.heap.HeapPointerArray
import com.primogemstudio.engine.foreign.toCStructArray
import com.primogemstudio.engine.graphics.IRenderer
import com.primogemstudio.engine.graphics.ShaderType
import com.primogemstudio.engine.graphics.backend.vk.dev.LogicalDeviceQueuesVk
import com.primogemstudio.engine.graphics.backend.vk.dev.LogicalDeviceVk
import com.primogemstudio.engine.graphics.backend.vk.dev.PhysicalDeviceVk
import com.primogemstudio.engine.graphics.backend.vk.pipeline.FrameDataVk
import com.primogemstudio.engine.graphics.backend.vk.pipeline.MemoryBufferVk
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
import kotlinx.coroutines.*
import org.joml.Vector2i
import org.joml.Vector4f
import java.lang.foreign.MemorySegment

class BackendRendererVk(
    override val gameInfo: ApplicationInfo,
    override val windowInfo: ApplicationWindowInfo,
    val deviceSelector: (Array<VkPhysicalDevice>) -> VkPhysicalDevice,
    val layerEnabler: (Array<String>) -> Array<String>
) : IRenderer, IReinitable {
    private val logger = LoggerFactory.getAsyncLogger()
    private val compiler = ShaderCompilerVk(this)
    private val shaders = mutableMapOf<Identifier, HeapByteArray>()
    private val shaderTypes = mutableMapOf<Identifier, ShaderType>()
    private val shaderProgs = mutableMapOf<Identifier, Array<Identifier>>()
    private val renderPasses = mutableMapOf<Identifier, VkRenderPass>()
    private val pipelineCreationData = mutableMapOf<Identifier, Pair<Identifier, Identifier>>()
    private val pipelines = mutableMapOf<Identifier, VkPipeline>()
    private val pipelineLayouts = mutableMapOf<Identifier, VkPipelineLayout>()
    private var swapchainFramebufferBindedPass: Identifier? = null
    private val swapchainFramebuffers = mutableListOf<VkFramebuffer>()
    private val vertexBuffers = mutableMapOf<Identifier, MemoryBufferVk>()

    private val swapchainCommandBuffers = mutableListOf<VkCommandBuffer>()

    private val imageSyncObjects = mutableListOf<FrameDataVk>()

    val validationLayer: ValidationLayerVk
    val instance: VkInstance
    override val window: VulkanWindow
    val physicalDevice: PhysicalDeviceVk
    val logicalDevice: LogicalDeviceVk
    val logicalDeviceQueues: LogicalDeviceQueuesVk
    val swapchain: SwapchainVk
    val commandPool: VkCommandPool

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
        logicalDevice = LogicalDeviceVk(this, vkGetPhysicalDeviceFeatures(physicalDevice()))
        logger.info(tr("engine.renderer.backend_vk.stage.logic_device"))
        logicalDeviceQueues = LogicalDeviceQueuesVk(this)
        logger.info(tr("engine.renderer.backend_vk.stage.logic_device_queue"))
        swapchain = SwapchainVk(this)
        logger.info(tr("engine.renderer.backend_vk.stage.swapchain"))
        commandPool = vkCreateCommandPool(
            logicalDevice(),
            VkCommandPoolCreateInfo().apply { queueFamilyIndex = physicalDevice.graphicFamily },
            null
        ).match({ it }, { throw IllegalStateException() })
        logger.info(tr("engine.renderer.backend_vk.stage.commandbuffer"))

        val sc = VkSemaphoreCreateInfo()
        val fc = VkFenceCreateInfo().apply { flag = VK_FENCE_CREATE_SIGNALED_BIT }

        swapchain.swapchainImages.indices.forEach {
            imageSyncObjects.add(
                FrameDataVk(
                    vkCreateSemaphore(logicalDevice(), sc, null).match({ it }, { throw IllegalStateException() }),
                    vkCreateSemaphore(logicalDevice(), sc, null).match({ it }, { throw IllegalStateException() }),
                    vkCreateFence(logicalDevice(), fc, null).match({ it }, { throw IllegalStateException() }),
                    vkCreateFence(logicalDevice(), fc, null).match({ it }, { throw IllegalStateException() })
                )
            )
        }
    }

    override fun close() {
        vkDeviceWaitIdle(logicalDevice())

        vkFreeCommandBuffers(logicalDevice(), commandPool, HeapPointerArray(swapchainCommandBuffers.toTypedArray()))
        swapchainCommandBuffers.clear()
        vertexBuffers.forEach { it.value.close() }
        vertexBuffers.clear()
        vkDestroyCommandPool(logicalDevice(), commandPool, null)
        swapchainFramebuffers.forEach { vkDestroyFramebuffer(logicalDevice(), it, null) }
        swapchainFramebuffers.clear()
        pipelineCreationData.clear()
        pipelineLayouts.values.forEach { vkDestroyPipelineLayout(logicalDevice(), it, null) }
        pipelineLayouts.clear()
        pipelines.values.forEach { vkDestroyPipeline(logicalDevice(), it, null) }
        pipelines.clear()
        renderPasses.values.forEach { vkDestroyRenderPass(logicalDevice(), it, null) }
        renderPasses.clear()
        shaderProgs.clear()
        shaderTypes.clear()
        shaderProgs.clear()
        shaders.clear()

        imageSyncObjects.forEach {
            vkDestroyFence(logicalDevice(), it.renderFinishedFence, null)
            vkDestroySemaphore(logicalDevice(), it.imageAvailableSemaphore, null)
            vkDestroySemaphore(logicalDevice(), it.renderFinishedSemaphore, null)
        }
        imageSyncObjects.clear()

        swapchain.close()
        logicalDevice.close()
        validationLayer.close()
        window.close()
        vkDestroyInstance(instance, null)
    }

    override fun reinit() {
        window.resetState()
        vkDeviceWaitIdle(logicalDevice())

        swapchain.reinit()

        shaderProgs.forEach { runBlocking { linkShader(it.key, it.value).join() } }

        renderPasses.values.forEach { vkDestroyRenderPass(logicalDevice(), it, null) }
        val target = renderPasses.map { it.key }.toTypedArray()
        renderPasses.clear()
        target.forEach { createRenderPass(it) }

        pipelineLayouts.values.forEach { vkDestroyPipelineLayout(logicalDevice(), it, null) }
        pipelines.values.forEach { vkDestroyPipeline(logicalDevice(), it, null) }

        pipelineLayouts.clear()
        pipelines.clear()
        pipelineCreationData.forEach { createPipeline(it.key, it.value.first, it.value.second) }

        swapchainFramebuffers.forEach { vkDestroyFramebuffer(logicalDevice(), it, null) }
        swapchainFramebuffers.clear()
        swapchainFramebufferBindedPass?.let { bindOutputFramebuffer(it) }

        vkFreeCommandBuffers(logicalDevice(), commandPool, HeapPointerArray(swapchainCommandBuffers.toTypedArray()))
        swapchainCommandBuffers.clear()
        createTestCommandBuffer()
    }

    override fun version(): Version = physicalDevice.physicalDeviceProps.driverVersion.fromVkApiVersion()
    override fun driver(): String =
        "${physicalDevice.physicalDeviceProps.deviceName} ${physicalDevice.physicalDeviceProps.driverVersion.fromVkVersion()}"

    @OptIn(DelicateCoroutinesApi::class)
    override fun registerShader(shaderId: Identifier, src: Identifier, type: ShaderType): Deferred<Int> =
        CoroutineScope(Dispatchers.Default).async {
            shaderTypes[shaderId] = type
            try {
                val compilerType = com.primogemstudio.engine.graphics.backend.vk.shader.ShaderType.entries[type.ordinal]
                shaders[shaderId] = compiler.compile(
                    src,
                    compilerType,
                    ShaderLanguage.Glsl
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
    override fun linkShader(progId: Identifier, progs: Array<Identifier>): Deferred<Int> =
        CoroutineScope(Dispatchers.Default).async {
            shaderProgs[progId] = progs.filter { shaders.containsKey(it) }.toTypedArray()

        logger.info(tr("engine.renderer.backend_vk.stage.shader_link", progs.toList()))
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

    override fun createPipeline(pipeId: Identifier, progId: Identifier, passId: Identifier) {
        pipelineCreationData[pipeId] = Pair(progId, passId)
        val shaderModules = shaderProgs[progId]!!
            .map { Pair(shaders[it], it) }
            .map {
                Pair(
                    vkCreateShaderModule(
                        logicalDevice(),
                        VkShaderModuleCreateInfo().apply { code = it.first!! },
                        null
                    ).match(
                        { it },
                        { throw IllegalStateException(toFullErr("exception.renderer.backend_vk.shader", it)) }),
                    it.second
                )
            }
        pipelines[pipeId] = vkCreateGraphicsPipelines(
            logicalDevice(),
            VkPipelineCache(MemorySegment.NULL),
            arrayOf(VkGraphicsPipelineCreateInfo().apply {
                shaders = shaderModules.map {
                    VkPipelineShaderStageCreateInfo().apply {
                        module = it.first
                        stage = when (this@BackendRendererVk.shaderTypes[it.second]!!) {
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
                }.toTypedArray().toCStructArray(VkPipelineShaderStageCreateInfo.LAYOUT)
                vertex = VkPipelineVertexInputStateCreateInfo().apply {
                    bindings = arrayOf(VkVertexInputBindingDescription().apply {
                        binding = 0
                        stride = (2 + 3) * Float.SIZE_BYTES
                        inputRate = VK_VERTEX_INPUT_RATE_VERTEX
                    }).toCStructArray(VkVertexInputBindingDescription.LAYOUT)
                    attributes = arrayOf(
                        VkVertexInputAttributeDescription().apply {
                            binding = 0
                            location = 0
                            format = VK_FORMAT_R32G32_SFLOAT
                            position = 0
                        },
                        VkVertexInputAttributeDescription().apply {
                            binding = 0
                            location = 1
                            format = VK_FORMAT_R32G32B32_SFLOAT
                            position = 2 * Float.SIZE_BYTES
                        },
                    ).toCStructArray(VkVertexInputAttributeDescription.LAYOUT)
                }
                inputAssembly = VkPipelineInputAssemblyStateCreateInfo().apply {
                    topology = VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST
                    primitiveRestartEnable = false
                }
                viewport = VkPipelineViewportStateCreateInfo().apply {
                    viewports = arrayOf(VkViewport().apply {
                        x = 0f
                        y = 0f
                        width = swapchain.swapchainExtent.x.toFloat()
                        height = swapchain.swapchainExtent.y.toFloat()
                        minDepth = 0f
                        maxDepth = 1f
                    }).toCStructArray(VkViewport.LAYOUT)
                    scissors = arrayOf(VkRect2D().apply {
                        offset = Vector2i(0, 0)
                        extent = swapchain.swapchainExtent
                    }).toCStructArray(VkRect2D.LAYOUT)
                }
                rasterization = VkPipelineRasterizationStateCreateInfo().apply {
                    depthClampEnable = false
                    rasterizerDiscardEnable = false
                    polygonMode = VK_POLYGON_MODE_FILL
                    lineWidth = 1f
                    cullMode = VK_CULL_MODE_BACK_BIT
                    frontFace = VK_FRONT_FACE_CLOCKWISE
                    depthBiasEnable = true
                }
                multisample = VkPipelineMultisampleStateCreateInfo().apply {
                    sampleShadingEnable = false
                    rasterizationSamples = VK_SAMPLE_COUNT_1_BIT
                }
                colorBlend = VkPipelineColorBlendStateCreateInfo().apply {
                    logicOpEnable = false
                    logicOp = VK_LOGIC_OP_COPY
                    attachments = arrayOf(VkPipelineColorBlendAttachmentState().apply {
                        colorWriteMask =
                            VK_COLOR_COMPONENT_R_BIT or VK_COLOR_COMPONENT_G_BIT or VK_COLOR_COMPONENT_B_BIT or VK_COLOR_COMPONENT_A_BIT
                        blendEnable = false
                    }).toCStructArray(VkPipelineColorBlendAttachmentState.LAYOUT)
                    blendConstants = Vector4f(0f)
                }

                layout = vkCreatePipelineLayout(logicalDevice(), VkPipelineLayoutCreateInfo().apply {}, null).match(
                    { it },
                    { throw IllegalStateException(toFullErr("exception.renderer.backend_vk.pipeline_layout", it)) }
                )
                pipelineLayouts[pipeId] = layout

                renderPass = renderPasses[passId]!!
                subpass = 0
                basePipeline = VkPipeline(MemorySegment.NULL)
                basePipelineIndex = -1
            }).toCStructArray(VkGraphicsPipelineCreateInfo.LAYOUT),
            null
        ).match(
            { it },
            { throw IllegalStateException(toFullErr("exception.renderer.backend_vk.pipeline", it)) }
        )[0]

        shaderModules.forEach { vkDestroyShaderModule(logicalDevice(), it.first, null) }

        logger.info(tr("engine.renderer.backend_vk.stage.pipeline", pipeId, progId, passId))
    }

    override fun bindOutputFramebuffer(passId: Identifier) {
        swapchainFramebufferBindedPass = passId

        swapchainFramebuffers.forEach { vkDestroyFramebuffer(logicalDevice(), it, null) }
        swapchainFramebuffers.clear()

        val ci = VkFramebufferCreateInfo().apply {
            renderPass = renderPasses[passId]!!
            width = swapchain.swapchainExtent.x
            height = swapchain.swapchainExtent.y
            layers = 1
        }

        for (image in swapchain.swapchainImageViews) {
            ci.attachments = HeapPointerArray(arrayOf(image))
            swapchainFramebuffers.add(
                vkCreateFramebuffer(logicalDevice(), ci, null).match(
                    { it },
                    { throw IllegalStateException(toFullErr("exception.renderer.backend_vk.framebuffer", it)) }
                ))
        }

        logger.info(tr("engine.renderer.backend_vk.stage.framebuffer", passId))
    }

    override fun createVertexBuffer(vtxId: Identifier, size: Long) {
        vertexBuffers[vtxId] = MemoryBufferVk(
            this,
            size,
            VK_BUFFER_USAGE_VERTEX_BUFFER_BIT,
            VK_SHARING_MODE_EXCLUSIVE,
            VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT or VK_MEMORY_PROPERTY_HOST_COHERENT_BIT
        )

        logger.info(tr("engine.renderer.backend_vk.stage.vtxbuffer", size))
    }

    override fun writeVertexBuffer(vtxId: Identifier, arr: ByteArray) {
        vertexBuffers[vtxId]!!.apply {
            mapMemory().apply { put(arr) }
            unmapMemory()
        }
    }

    override fun createTestCommandBuffer() {
        val commandBuffers = vkAllocateCommandBuffers(logicalDevice(), VkCommandBufferAllocateInfo().apply {
            commandPool = this@BackendRendererVk.commandPool
            level = VK_COMMAND_BUFFER_LEVEL_PRIMARY
            commandBufferCount = swapchainFramebuffers.size
        }).match({ it }, { throw IllegalStateException() })

        val passBeginInfo = VkRenderPassBeginInfo().apply {
            renderPass = renderPasses.values.toList()[0]
            renderArea = VkRect2D().apply {
                offset = Vector2i(0, 0)
                extent = swapchain.swapchainExtent
            }
            clearValues = arrayOf(VkClearValue().apply {
                color = VkClearColorValue().apply { data = Vector4f(0f) }
            }).toCStructArray(VkClearValue.LAYOUT)
        }
        val beginInfo = VkCommandBufferBeginInfo()

        for (i in commandBuffers.indices) {
            passBeginInfo.framebuffer = swapchainFramebuffers[i]

            val cb = commandBuffers[i]
            vkBeginCommandBuffer(cb, beginInfo)
            vkCmdBeginRenderPass(cb, passBeginInfo, VK_SUBPASS_CONTENTS_INLINE)
            vkCmdBindPipeline(cb, VK_PIPELINE_BIND_POINT_GRAPHICS, pipelines.values.toList()[0])

            vkCmdBindVertexBuffers(
                cb, 0, HeapPointerArray(arrayOf(vertexBuffers.values.toList()[0]())), HeapLongArray(
                    longArrayOf(0)
                )
            )
            vkCmdDraw(cb, 3, 1, 0, 0)
            vkCmdEndRenderPass(cb)
            vkEndCommandBuffer(cb)
        }

        swapchainCommandBuffers.addAll(commandBuffers)
    }

    private var imageIndex = 0

    fun render() {
        val frame = imageSyncObjects[imageIndex]

        frameImageAcquireWait(frame)
        frameImageAcquire(frame).apply {
            if (this != -1) {
                frameSubmitWait(frame)
                frameSubmit(frame, this)
                framePresent(frame, this)
            }
        }

        imageIndex = (imageIndex + 1) % swapchain.swapchainImages.size
    }

    private fun frameImageAcquire(frame: FrameDataVk): Int {
        vkResetFences(logicalDevice(), HeapPointerArray(arrayOf(frame.imageAvailableFence)))

        val imageIdx = vkAcquireNextImageKHR(
            logicalDevice(),
            swapchain.swapchain,
            Long.MAX_VALUE,
            frame.imageAvailableSemaphore,
            frame.imageAvailableFence
        ).match({ it }, {
            if (it == VK_ERROR_OUT_OF_DATE_KHR || it == VK_SUBOPTIMAL_KHR) {
                this.reinit()
                return@frameImageAcquire -1
            } else throw IllegalStateException()
        })

        return imageIdx
    }

    private fun frameImageAcquireWait(frame: FrameDataVk) {
        vkWaitForFences(logicalDevice(), HeapPointerArray(arrayOf(frame.imageAvailableFence)), true, Long.MAX_VALUE)
    }

    private fun frameSubmit(frame: FrameDataVk, imageIdx: Int) {
        vkResetFences(logicalDevice(), HeapPointerArray(arrayOf(frame.renderFinishedFence)))

        val retCode = vkQueueSubmit(
            logicalDeviceQueues.graphicsQueue,
            arrayOf(VkSubmitInfo().apply {
                waitSemaphores = HeapPointerArray(arrayOf(frame.imageAvailableSemaphore))
                waitDstStageMask = HeapIntArray(intArrayOf(VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT))
                signalSemaphores = HeapPointerArray(arrayOf(frame.renderFinishedSemaphore))
                commandBuffers = HeapPointerArray(arrayOf(swapchainCommandBuffers[imageIdx]))
            }).toCStructArray(VkSubmitInfo.LAYOUT),
            frame.renderFinishedFence
        )
        if (retCode != VK_SUCCESS) {
            throw IllegalStateException()
        }
    }

    private fun frameSubmitWait(frame: FrameDataVk) {
        vkWaitForFences(logicalDevice(), HeapPointerArray(arrayOf(frame.renderFinishedFence)), true, Long.MAX_VALUE)
    }

    private fun framePresent(frame: FrameDataVk, imageIdx: Int) {
        val result = vkQueuePresentKHR(
            logicalDeviceQueues.presentQueue,
            VkPresentInfoKHR().apply {
                waitSemaphores = HeapPointerArray(arrayOf(frame.renderFinishedSemaphore))
                swapchains = HeapPointerArray(arrayOf(swapchain.swapchain))
                imageIndices = HeapIntArray(intArrayOf(imageIdx))
            }
        )
        if (result == VK_ERROR_OUT_OF_DATE_KHR || result == VK_SUBOPTIMAL_KHR || window.resizing()) {
            reinit()
        } else if (result != VK_SUCCESS) {
            throw IllegalStateException()
        }
    }
}