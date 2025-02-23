package com.primogemstudio.engine.graphics.backend.vk.dev

import com.primogemstudio.engine.bindings.vulkan.khr.swapchain.VkSwapchainKHRFuncs.VK_KHR_SWAPCHAIN_EXTENSION_NAME
import com.primogemstudio.engine.bindings.vulkan.utils.toFullErr
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkCreateDevice
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkDestroyDevice
import com.primogemstudio.engine.bindings.vulkan.vk10.VkDevice
import com.primogemstudio.engine.bindings.vulkan.vk10.VkDeviceCreateInfo
import com.primogemstudio.engine.bindings.vulkan.vk10.VkDeviceQueueCreateInfo
import com.primogemstudio.engine.bindings.vulkan.vk10.VkPhysicalDeviceFeatures
import com.primogemstudio.engine.graphics.backend.vk.BackendRendererVk
import com.primogemstudio.engine.interfaces.heap.HeapFloatArray
import com.primogemstudio.engine.interfaces.toCStructArray
import java.io.Closeable

class LogicalDeviceVk(
    private val renderer: BackendRendererVk
) : Closeable {
    val device: VkDevice = vkCreateDevice(
        renderer.physicalDevice(),
        VkDeviceCreateInfo().apply {
            queueCreateInfos = renderer.physicalDevice.allFamilies().map {
                VkDeviceQueueCreateInfo().apply {
                    queueFamilyIndex = it
                    queuePriorities = HeapFloatArray(floatArrayOf(1f))
                }
            }.toTypedArray().toCStructArray(VkDeviceQueueCreateInfo.LAYOUT)
            features = VkPhysicalDeviceFeatures()
            extensions = arrayOf(VK_KHR_SWAPCHAIN_EXTENSION_NAME)
            layers = renderer.validationLayer.layerArg()
        },
        null
    ).match(
        { it },
        { throw IllegalStateException(toFullErr("exception.renderer.backend_vk.logic_dev", it)) }
    )

    operator fun invoke(): VkDevice = device

    override fun close() {
        vkDestroyDevice(device, null)
    }
}