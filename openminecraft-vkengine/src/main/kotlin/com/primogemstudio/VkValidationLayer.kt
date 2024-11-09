package com.primogemstudio

import com.primogemstudio.utils.LoggerFactory
import org.lwjgl.PointerBuffer
import org.lwjgl.glfw.GLFWVulkan.glfwGetRequiredInstanceExtensions
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.vulkan.EXTDebugUtils.*
import org.lwjgl.vulkan.VK10.VK_FALSE
import org.lwjgl.vulkan.VK10.vkEnumerateInstanceLayerProperties
import org.lwjgl.vulkan.VkDebugUtilsMessengerCallbackDataEXT
import org.lwjgl.vulkan.VkDebugUtilsMessengerCreateInfoEXT
import org.lwjgl.vulkan.VkLayerProperties

@Suppress("ALL")
class VkValidationLayer(
    instanceEngine: VkInstanceEngine,
    var enableValidationLayer: Boolean = true,
    val vkDebugCallback: VkDebugCallback? = null
) {
    companion object {
        private fun fetchVkLayers(): List<String> = stackPush().use { stack ->
            val layerCount = stack.ints(0)

            vkEnumerateInstanceLayerProperties(layerCount, null)
            val availableLayers = VkLayerProperties.malloc(layerCount[0], stack)

            vkEnumerateInstanceLayerProperties(layerCount, availableLayers)
            return availableLayers.map(VkLayerProperties::layerNameString)
        }
    }
    private val logger = LoggerFactory.getLogger("VkValidationLayer ${instanceEngine.appName}")

    init {
        val vkLayers = fetchVkLayers()
        logger.info("Vulkan layers: $vkLayers")
        if (enableValidationLayer && !vkLayers.contains("VK_LAYER_KHRONOS_validation")) {
            enableValidationLayer = false
            logger.warn("Vulkan validation layer not supported")
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