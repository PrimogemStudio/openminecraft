package com.primogemstudio.engine.vk

import com.primogemstudio.engine.logging.LoggerFactory
import org.lwjgl.PointerBuffer
import org.lwjgl.glfw.GLFWVulkan.glfwGetRequiredInstanceExtensions
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.system.MemoryUtil.NULL
import org.lwjgl.vulkan.*
import org.lwjgl.vulkan.EXTDebugUtils.*
import org.lwjgl.vulkan.VK10.*
import java.io.Closeable
import java.nio.LongBuffer

@Suppress("ALL")
class VkValidationLayer(
    instanceEngine: VkInstanceEngine,
    private val instanceAccessor: () -> VkInstance,
    var enableValidationLayer: Boolean = true,
    private val vkDebugCallback: VkDebugCallback? = null
) : Closeable {
    private val logger = LoggerFactory.getLogger("VkValidationLayer ${instanceEngine.appName}")
    private var debugMessenger: Long = 0

    init {
        stackPush().use { stack ->
            val layerCount = stack.ints(0)

            vkEnumerateInstanceLayerProperties(layerCount, null)
            val availableLayers = VkLayerProperties.malloc(layerCount[0], stack)

            vkEnumerateInstanceLayerProperties(layerCount, availableLayers)
            val vkLayers = availableLayers.map(VkLayerProperties::layerNameString)
            logger.info("Vulkan layers: $vkLayers")
            if (enableValidationLayer && !vkLayers.contains("VK_LAYER_KHRONOS_validation")) {
                enableValidationLayer = false
                logger.warn("Vulkan validation layer not supported")
            }
        }
    }

    fun getRequiredExtensions(stack: MemoryStack): PointerBuffer? {
        val glfwExtensions = glfwGetRequiredInstanceExtensions()

        if (!enableValidationLayer) return glfwExtensions

        if (glfwExtensions != null) {
            val extensions = stack.mallocPointer(glfwExtensions.capacity() + 1)
            extensions.put(glfwExtensions)
            extensions.put(stack.UTF8(VK_EXT_DEBUG_UTILS_EXTENSION_NAME))
            return extensions.rewind()
        }
        else return stack.mallocPointer(1).apply { put(stack.UTF8(VK_EXT_DEBUG_UTILS_EXTENSION_NAME)) }.rewind()
    }

    fun getDebugCreateInfo(stack: MemoryStack): VkDebugUtilsMessengerCreateInfoEXT {
        val debugCreateInfo = VkDebugUtilsMessengerCreateInfoEXT.calloc(stack)
        debugCreateInfo.sType(VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT)
        debugCreateInfo.messageSeverity(
            VK_DEBUG_UTILS_MESSAGE_SEVERITY_VERBOSE_BIT_EXT or
                    VK_DEBUG_UTILS_MESSAGE_SEVERITY_INFO_BIT_EXT or
                    VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT or
                    VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT
        )
        debugCreateInfo.messageType(
            VK_DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT or
                    VK_DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT or
                    VK_DEBUG_UTILS_MESSAGE_TYPE_PERFORMANCE_BIT_EXT
        )
        debugCreateInfo.pfnUserCallback { severity, msgType, pCallbackData, _ ->
            val callbackData = VkDebugUtilsMessengerCallbackDataEXT.create(pCallbackData)

            val severityT = when (severity) {
                VK_DEBUG_UTILS_MESSAGE_SEVERITY_VERBOSE_BIT_EXT -> VkMessageSeverity.Verbose
                VK_DEBUG_UTILS_MESSAGE_SEVERITY_INFO_BIT_EXT -> VkMessageSeverity.Info
                VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT -> VkMessageSeverity.Warning
                VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT -> VkMessageSeverity.Error
                else -> VkMessageSeverity.Verbose
            }

            val logCall = when (severityT) {
                VkMessageSeverity.Verbose -> { msg: String -> logger.debug(msg) }
                VkMessageSeverity.Info -> { msg: String -> logger.info(msg) }
                VkMessageSeverity.Warning -> { msg: String -> logger.warn(msg) }
                VkMessageSeverity.Error -> { msg: String -> logger.error(msg) }
            }

            val type = when (msgType) {
                VK_DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT -> VkMessageType.General
                VK_DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT -> VkMessageType.Validation
                VK_DEBUG_UTILS_MESSAGE_TYPE_PERFORMANCE_BIT_EXT -> VkMessageType.Performance
                else -> VkMessageType.General
            }

            logCall("$type ${callbackData.pMessageString()}")

            vkDebugCallback?.invoke(severityT, type, callbackData)
            VK_FALSE
        }
        return debugCreateInfo
    }

    private fun createDebugUtilsMessengerEXT(
        instance: VkInstance,
        createInfo: VkDebugUtilsMessengerCreateInfoEXT,
        allocationCallbacks: VkAllocationCallbacks?,
        pDebugMessenger: LongBuffer
    ): Int {
        if (vkGetInstanceProcAddr(instance, "vkCreateDebugUtilsMessengerEXT") != NULL) {
            return vkCreateDebugUtilsMessengerEXT(instance, createInfo, allocationCallbacks, pDebugMessenger)
        }

        return VK_ERROR_EXTENSION_NOT_PRESENT
    }

    private fun destroyDebugUtilsMessengerEXT(
        instance: VkInstance,
        debugMessenger: Long,
        allocationCallbacks: VkAllocationCallbacks?
    ) {
        if (vkGetInstanceProcAddr(instance, "vkDestroyDebugUtilsMessengerEXT") != NULL) {
            vkDestroyDebugUtilsMessengerEXT(instance, debugMessenger, allocationCallbacks)
        }
    }

    fun preInstance(stack: MemoryStack) {
        if (!enableValidationLayer) return

        val pDebugMessenger = stack.longs(VK_NULL_HANDLE)
        if (createDebugUtilsMessengerEXT(
                instanceAccessor(),
                getDebugCreateInfo(stack),
                null,
                pDebugMessenger
            ) != VK_SUCCESS
        ) {
            throw RuntimeException("Failed to set up debug messenger")
        }

        debugMessenger = pDebugMessenger[0]
    }

    override fun close() {
        if (enableValidationLayer) destroyDebugUtilsMessengerEXT(instanceAccessor(), debugMessenger, null)
    }

    inline fun vkInitArgs(stack: MemoryStack, initFunc: (PointerBuffer, Long) -> Unit) {
        if (enableValidationLayer) initFunc(
            stack.mallocPointer(1).put(stack.UTF8("VK_LAYER_KHRONOS_validation")).rewind(),
            getDebugCreateInfo(stack).address()
        )
    }

    inline fun vkDeviceCreateArgs(stack: MemoryStack, initFunc: (PointerBuffer) -> Unit) {
        if (enableValidationLayer) initFunc(
            stack.mallocPointer(1).put(stack.UTF8("VK_LAYER_KHRONOS_validation")).rewind()
        )
    }
}