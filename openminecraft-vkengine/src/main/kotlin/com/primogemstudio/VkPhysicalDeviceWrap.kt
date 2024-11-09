package com.primogemstudio

import org.lwjgl.PointerBuffer
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.vulkan.VkInstance
import org.lwjgl.vulkan.VkPhysicalDevice
import org.lwjgl.vulkan.VkQueueFamilyProperties
import java.util.stream.IntStream

class VkPhysicalDeviceWrap(val vkDevice: VkPhysicalDevice, val graphicsFamily: Int?) {
    companion object {
        fun fetchList(vkInstance: VkInstance): List<VkPhysicalDeviceWrap> {
            stackPush().use {
                val devices = mutableListOf<VkPhysicalDeviceWrap>()

                val deviceCount = it.ints(0)
                vkEnumeratePhysicalDevices(vkInstance, deviceCount, null)
                if (deviceCount.get(0) == 0) throw RuntimeException("No suitable GPU was founded")

                val ppPhysicalDevices: PointerBuffer = it.mallocPointer(deviceCount[0])
                vkEnumeratePhysicalDevices(vkInstance, deviceCount, ppPhysicalDevices)

                for (i in 0 ..< ppPhysicalDevices.capacity()) {
                    val vkDevice = VkPhysicalDevice(ppPhysicalDevices[i], vkInstance)
                    devices.add(VkPhysicalDeviceWrap(
                        vkDevice,
                        findQueueFamilies(vkDevice)
                    ))
                }

                return devices
            }
        }

        private fun findQueueFamilies(device: VkPhysicalDevice): Int? {
            stackPush().use { stack ->
                var result: Int? = null
                val queueFamilyCount = stack.ints(0)
                vkGetPhysicalDeviceQueueFamilyProperties(device, queueFamilyCount, null)

                val queueFamilies =
                    VkQueueFamilyProperties.malloc(queueFamilyCount[0], stack)

                vkGetPhysicalDeviceQueueFamilyProperties(device, queueFamilyCount, queueFamilies)

                IntStream.range(0, queueFamilies.capacity())
                    .filter { index: Int ->
                        (queueFamilies[index]
                            .queueFlags() and VK_QUEUE_GRAPHICS_BIT) != 0
                    }
                    .findFirst()
                    .ifPresent { index: Int -> result = index }
                return result
            }
        }
    }

    fun suitable(): Boolean = graphicsFamily != null
}