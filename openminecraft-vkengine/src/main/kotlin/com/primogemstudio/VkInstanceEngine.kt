package com.primogemstudio

import com.primogemstudio.utils.LoggerFactory
import org.lwjgl.PointerBuffer
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWVulkan.glfwGetRequiredInstanceExtensions
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.vulkan.*
import org.lwjgl.vulkan.EXTDebugUtils.*
import org.lwjgl.vulkan.VK10.*
import java.io.Closeable
import java.nio.IntBuffer


class VkInstanceEngine(
    appName: String,
    appVer: String,
    private var enableValidationLayer: Boolean = true
): Closeable {
    private val logger = LoggerFactory.getLogger("VkInstanceEngine $appName-$appVer")

    private var vkInstance: VkInstance? = null

    init {
        val vkVer = appVer.split("-")[0].split(".").flatMap {
            try { listOf(it.toInt()) }
            catch (e: Exception) { listOf() }
        }

        if (vkVer.size < 3) throw IllegalArgumentException("Corrupt version!")

        glfwInit()

        val vkLayers = fetchVkLayers()
        logger.info("Vulkan layers: $vkLayers")
        if (enableValidationLayer && !vkLayers.contains("VK_LAYER_KHRONOS_validation")) {
            enableValidationLayer = false
            logger.warn("Vulkan validation layer not supported")
        }

        stackPush().use {
            initVkInstance(it, appName, vkVer)
            initVkPhysicalDevice(it)
        }
    }

    private fun initVkPhysicalDevice(it: MemoryStack) {
        val deviceCount: IntBuffer = it.ints(0)

        vkEnumeratePhysicalDevices(vkInstance!!, deviceCount, null)

        if (deviceCount[0] == 0) {
            throw java.lang.RuntimeException("Failed to find GPUs with Vulkan support")
        }

        val ppPhysicalDevices: PointerBuffer = it.mallocPointer(deviceCount[0])

        vkEnumeratePhysicalDevices(vkInstance!!, deviceCount, ppPhysicalDevices)

        for (i in 0 until ppPhysicalDevices.capacity()) {
            val device = VkPhysicalDevice(ppPhysicalDevices[i], vkInstance!!)
            logger.info(device)
        }
    }

    private fun initVkInstance(it: MemoryStack, appName: String, vkVer: List<Int>) {
        val vkAppInfo = VkApplicationInfo.calloc(it).apply {
            sType(VK_STRUCTURE_TYPE_APPLICATION_INFO)
            pApplicationName(it.UTF8Safe(appName))
            applicationVersion(VK_MAKE_VERSION(vkVer[0], vkVer[1], vkVer[2]))
            pEngineName(it.UTF8Safe(appName))
            engineVersion(VK_MAKE_VERSION(vkVer[0], vkVer[1], vkVer[2]))
            apiVersion(VK_API_VERSION_1_0)
        }

        val vkCreateInfo = VkInstanceCreateInfo.calloc(it).apply {
            sType(VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO)
            pApplicationInfo(vkAppInfo)
            ppEnabledExtensionNames(getRequiredExtensions(it))

            if (enableValidationLayer) {
                ppEnabledLayerNames(validationLayersAsPointerBuffer(it))

                val debugCreateInfo = VkDebugUtilsMessengerCreateInfoEXT.calloc(it)
                debugCreateInfo.sType(VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT)
                debugCreateInfo.messageSeverity(VK_DEBUG_UTILS_MESSAGE_SEVERITY_VERBOSE_BIT_EXT or VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT or VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT)
                debugCreateInfo.messageType(VK_DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT or VK_DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT or VK_DEBUG_UTILS_MESSAGE_TYPE_PERFORMANCE_BIT_EXT)
                debugCreateInfo.pfnUserCallback { _, _, pCallbackData, _ ->
                    val callbackData = VkDebugUtilsMessengerCallbackDataEXT.create(pCallbackData)
                    logger.info("Vulkan message: ${callbackData.pMessageString()}")
                    VK_FALSE
                }
                pNext(debugCreateInfo.address())
            }
        }

        val vkInstancePtr = it.mallocPointer(1)

        if (vkCreateInstance(vkCreateInfo, null, vkInstancePtr) != VK_SUCCESS) {
            throw RuntimeException("Failed to create vulkan instance")
        }

        vkInstance = VkInstance(vkInstancePtr.get(0), vkCreateInfo)
    }

    private fun fetchVkLayers(): List<String> = stackPush().use { stack ->
        val layerCount = stack.ints(0)

        vkEnumerateInstanceLayerProperties(layerCount, null)
        val availableLayers = VkLayerProperties.malloc(layerCount[0], stack)

        vkEnumerateInstanceLayerProperties(layerCount, availableLayers)
        return availableLayers.map(VkLayerProperties::layerNameString)
    }

    private fun validationLayersAsPointerBuffer(stack: MemoryStack): PointerBuffer {
        val buffer = stack.mallocPointer(1)
        buffer.put(stack.UTF8("VK_LAYER_KHRONOS_validation"))
        return buffer.rewind()
    }

    private fun getRequiredExtensions(stack: MemoryStack): PointerBuffer? {
        val glfwExtensions = glfwGetRequiredInstanceExtensions()

        if (!enableValidationLayer) return glfwExtensions

        val extensions = stack.mallocPointer(glfwExtensions!!.capacity() + 1)
        extensions.put(glfwExtensions)
        extensions.put(stack.UTF8(VK_EXT_DEBUG_UTILS_EXTENSION_NAME))
        return extensions.rewind()
    }

    override fun close() {

    }
}