package com.primogemstudio

import org.lwjgl.PointerBuffer
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.vulkan.KHRSurface.vkGetPhysicalDeviceSurfaceSupportKHR
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.vulkan.VkInstance
import org.lwjgl.vulkan.VkPhysicalDevice
import org.lwjgl.vulkan.VkQueueFamilyProperties
import java.nio.IntBuffer


class VkPhysicalDeviceWrap(val vkDevice: VkPhysicalDevice, val graphicsFamily: Int?, val currentFamily: Int?) {
    companion object {
        fun fetchList(vkInstance: VkInstance, vkWindow: VkWindow): List<VkPhysicalDeviceWrap> {
            stackPush().use {
                val devices = mutableListOf<VkPhysicalDeviceWrap>()

                val deviceCount = it.ints(0)
                vkEnumeratePhysicalDevices(vkInstance, deviceCount, null)
                if (deviceCount.get(0) == 0) throw RuntimeException("No suitable GPU was founded")

                val ppPhysicalDevices: PointerBuffer = it.mallocPointer(deviceCount[0])
                vkEnumeratePhysicalDevices(vkInstance, deviceCount, ppPhysicalDevices)

                for (i in 0 ..< ppPhysicalDevices.capacity()) {
                    val vkDevice = VkPhysicalDevice(ppPhysicalDevices[i], vkInstance)
                    val fm = findQueueFamilies(vkDevice, vkWindow.surface)
                    devices.add(VkPhysicalDeviceWrap(
                        vkDevice,
                        fm[0],
                        fm[1]
                    ))
                }

                return devices
            }
        }

        private fun findQueueFamilies(device: VkPhysicalDevice, surface: Long): IntArray {
            stackPush().use { stack ->
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
    }

    fun suitable(): Boolean = graphicsFamily != null && currentFamily != null
}