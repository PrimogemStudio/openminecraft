package com.primogemstudio

import com.primogemstudio.utils.MemoryManager
import com.primogemstudio.utils.TestLog
import org.lwjgl.PointerBuffer
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWVulkan.*
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.vulkan.*
import org.lwjgl.vulkan.EXTDebugUtils.*
import org.lwjgl.vulkan.VK10.*
import java.nio.LongBuffer
import java.util.stream.Collectors.toSet


val vkValidationLayers = listOf("VK_LAYER_KHRONOS_validation")
val vkEnableValidationLayers = true

var vkInstance: VkInstance? = null
var debugMessenger: Long? = null

fun debugCallback(messageSeverity: Int, messageType: Int, pCallbackData: Long, pUserData: Long): Int {
    val callbackData = VkDebugUtilsMessengerCallbackDataEXT.create(pCallbackData)
    println("Vulkan validation: ${callbackData.pMessageString()}")
    return VK_FALSE
}

private fun createDebugUtilsMessengerEXT(
    instance: VkInstance, createInfo: VkDebugUtilsMessengerCreateInfoEXT,
    allocationCallbacks: VkAllocationCallbacks?, pDebugMessenger: LongBuffer
): Int {
    if (vkGetInstanceProcAddr(instance, "vkCreateDebugUtilsMessengerEXT") != 0L) {
        return vkCreateDebugUtilsMessengerEXT(instance, createInfo, allocationCallbacks, pDebugMessenger)
    }

    return VK_ERROR_EXTENSION_NOT_PRESENT
}

private fun destroyDebugUtilsMessengerEXT(
    instance: VkInstance,
    debugMessenger: Long,
    allocationCallbacks: VkAllocationCallbacks?
) {
    if (vkGetInstanceProcAddr(instance, "vkDestroyDebugUtilsMessengerEXT") != 0L) {
        vkDestroyDebugUtilsMessengerEXT(instance, debugMessenger, allocationCallbacks)
    }
}

fun checkValidationLayerSupport(): Boolean {
    stackPush().use { stack ->
        val layerCount = stack.ints(0)
        vkEnumerateInstanceLayerProperties(layerCount, null)

        val availableLayers = VkLayerProperties.malloc(layerCount[0], stack)

        vkEnumerateInstanceLayerProperties(layerCount, availableLayers)

        val availableLayerNames = availableLayers.stream()
            .map { obj: VkLayerProperties -> obj.layerNameString() }
            .collect(toSet())
        return availableLayerNames.containsAll(vkValidationLayers)
    }
}

private fun validationLayersAsPointerBuffer(stack: MemoryStack): PointerBuffer {
    val buffer = stack.mallocPointer(vkValidationLayers.size)
    vkValidationLayers.stream().map(stack::UTF8).forEach(buffer::put)
    return buffer.rewind()
}

private fun getRequiredExtensions(stack: MemoryStack): PointerBuffer? {
    val glfwExtensions = glfwGetRequiredInstanceExtensions()

    if (vkEnableValidationLayers) {
        val extensions = stack.mallocPointer(glfwExtensions!!.capacity() + 1)

        extensions.put(glfwExtensions)
        extensions.put(stack.UTF8(VK_EXT_DEBUG_UTILS_EXTENSION_NAME))

        return extensions.rewind()
    }

    return glfwExtensions
}

private fun populateDebugMessengerCreateInfo(debugCreateInfo: VkDebugUtilsMessengerCreateInfoEXT) {
    debugCreateInfo.sType(VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT)
    debugCreateInfo.messageSeverity(VK_DEBUG_UTILS_MESSAGE_SEVERITY_VERBOSE_BIT_EXT or VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT or VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT)
    debugCreateInfo.messageType(VK_DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT or VK_DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT or VK_DEBUG_UTILS_MESSAGE_TYPE_PERFORMANCE_BIT_EXT)
    debugCreateInfo.pfnUserCallback(::debugCallback)
}

private fun setupDebugMessenger() {
    if (!vkEnableValidationLayers) {
        return
    }

    stackPush().use { stack ->
        val createInfo = VkDebugUtilsMessengerCreateInfoEXT.calloc(stack)
        populateDebugMessengerCreateInfo(createInfo)

        val pDebugMessenger = stack.longs(VK_NULL_HANDLE)

        if (createDebugUtilsMessengerEXT(vkInstance!!, createInfo, null, pDebugMessenger) != VK_SUCCESS) {
            throw java.lang.RuntimeException("Failed to set up debug messenger")
        }
        debugMessenger = pDebugMessenger[0]
    }
}

fun main() {
    glfwInit()

    if(vkEnableValidationLayers && !checkValidationLayerSupport()) {
        throw RuntimeException("Validation requested but not supported")
    }
    stackPush().use {
        val vkAppInfo = VkApplicationInfo.calloc(it).apply {
            sType(VK_STRUCTURE_TYPE_APPLICATION_INFO)
            pApplicationName(it.UTF8Safe("Open Minecraft Test"))
            applicationVersion(VK_MAKE_VERSION(1, 0, 0))
            pEngineName(it.UTF8Safe("Open Minecraft"))
            engineVersion(VK_MAKE_VERSION(1, 0, 0))
            apiVersion(VK_API_VERSION_1_0)
        }

        val vkCreateInfo = VkInstanceCreateInfo.calloc(it).apply {
            sType(VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO)
            pApplicationInfo(vkAppInfo)
            ppEnabledExtensionNames(getRequiredExtensions(it))

            if (vkEnableValidationLayers) {
                ppEnabledLayerNames(validationLayersAsPointerBuffer(it))

                val debugCreateInfo: VkDebugUtilsMessengerCreateInfoEXT =
                    VkDebugUtilsMessengerCreateInfoEXT.calloc(it)
                populateDebugMessengerCreateInfo(debugCreateInfo)
                pNext(debugCreateInfo.address())
            }
        }

        val vkInstancePtr = it.mallocPointer(1)

        if (vkCreateInstance(vkCreateInfo, null, vkInstancePtr) != VK_SUCCESS) {
            throw RuntimeException("Failed to create vulkan instance")
        }

        vkInstance = VkInstance(vkInstancePtr.get(0), vkCreateInfo)
    }
    setupDebugMessenger()

    MemoryManager.test()

    println(vkInstance)
    TestLog.log()

    destroyDebugUtilsMessengerEXT(vkInstance!!, debugMessenger!!, null)
}