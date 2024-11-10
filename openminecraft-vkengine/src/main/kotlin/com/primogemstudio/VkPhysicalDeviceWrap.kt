package com.primogemstudio

import org.lwjgl.PointerBuffer
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.vulkan.*
import org.lwjgl.vulkan.KHRSurface.*
import org.lwjgl.vulkan.KHRSwapchain.VK_KHR_SWAPCHAIN_EXTENSION_NAME
import org.lwjgl.vulkan.VK10.*
import java.nio.IntBuffer

class VkPhysicalDeviceWrap(
    val vkDevice: VkPhysicalDevice,
    val surface: Long,
    val graphicsFamily: Int?,
    val currentFamily: Int?
) {
    companion object {
        fun fetchList(stack: MemoryStack, vkInstance: VkInstance, vkWindow: VkWindow): List<VkPhysicalDeviceWrap> {
            val devices = mutableListOf<VkPhysicalDeviceWrap>()

            val deviceCount = stack.ints(0)
            vkEnumeratePhysicalDevices(vkInstance, deviceCount, null)
            if (deviceCount.get(0) == 0) throw RuntimeException("No suitable GPU was founded")

            val ppPhysicalDevices: PointerBuffer = stack.mallocPointer(deviceCount[0])
            vkEnumeratePhysicalDevices(vkInstance, deviceCount, ppPhysicalDevices)

            for (i in 0..<ppPhysicalDevices.capacity()) {
                val vkDevice = VkPhysicalDevice(ppPhysicalDevices[i], vkInstance)
                val fm = findQueueFamilies(stack, vkDevice, vkWindow.surface)
                devices.add(
                    VkPhysicalDeviceWrap(
                        vkDevice,
                        vkWindow.surface,
                        fm[0],
                        fm[1]
                    )
                )
            }

            return devices
        }

        private fun findQueueFamilies(stack: MemoryStack, device: VkPhysicalDevice, surface: Long): IntArray {
            var result: Int? = null
            var current: Int? = null

            val queueFamilyCount = stack.ints(0)
            vkGetPhysicalDeviceQueueFamilyProperties(device, queueFamilyCount, null)

            val queueFamilies = VkQueueFamilyProperties.malloc(queueFamilyCount[0], stack)

            vkGetPhysicalDeviceQueueFamilyProperties(device, queueFamilyCount, queueFamilies)

            val presentSupport: IntBuffer = stack.ints(VK_FALSE)

            for (i in 0..<queueFamilies.capacity()) {
                if ((queueFamilies[i].queueFlags() and VK_QUEUE_GRAPHICS_BIT) != 0) {
                    result = i
                }
                vkGetPhysicalDeviceSurfaceSupportKHR(device, i, surface, presentSupport)

                if (presentSupport[0] == VK_TRUE) {
                    current = i
                }

                if (result != null && current != null) break
            }

            if (result == null || current == null) throw IllegalStateException("Unable to find a suitable device queue family")

            return intArrayOf(result, current)
        }
    }

    private var extensions: Map<String, Int>
    private var swapChainSupport = VkSwapChainSupportDetails()

    init {
        stackPush().use { stack ->
            val extensionCount = stack.ints(0)
            vkEnumerateDeviceExtensionProperties(vkDevice, null as String?, extensionCount, null)

            val availableExtensions = VkExtensionProperties.malloc(extensionCount[0], stack)
            vkEnumerateDeviceExtensionProperties(vkDevice, null as String?, extensionCount, availableExtensions)

            extensions = availableExtensions.associate { Pair(it.extensionNameString(), it.specVersion()) }

            swapChainSupport.apply {
                capabilities = VkSurfaceCapabilitiesKHR.malloc(stack)
                vkGetPhysicalDeviceSurfaceCapabilitiesKHR(vkDevice, surface, capabilities!!)

                val count = stack.ints(0)
                vkGetPhysicalDeviceSurfaceFormatsKHR(vkDevice, surface, count, null)

                if (count[0] != 0) {
                    swapChainSupport.formats = VkSurfaceFormatKHR.malloc(count[0], stack)
                    vkGetPhysicalDeviceSurfaceFormatsKHR(vkDevice, surface, count, swapChainSupport.formats)
                }

                vkGetPhysicalDeviceSurfacePresentModesKHR(vkDevice, surface, count, null)

                if (count[0] != 0) {
                    swapChainSupport.presentModes = stack.mallocInt(count[0])
                    vkGetPhysicalDeviceSurfacePresentModesKHR(vkDevice, surface, count, swapChainSupport.presentModes)
                }
            }
        }
    }

    fun suitable(): Boolean =
        graphicsFamily != null && currentFamily != null && extensions.containsKey(VK_KHR_SWAPCHAIN_EXTENSION_NAME) && (swapChainSupport.formats?.hasRemaining()
            ?: false) && (swapChainSupport.presentModes?.hasRemaining() ?: false)
    fun unique(): IntArray = intArrayOf(graphicsFamily!!, currentFamily!!).distinct().toIntArray()
}