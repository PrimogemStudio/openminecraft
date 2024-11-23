package com.primogemstudio.engine.vk.shader

import com.primogemstudio.engine.vk.VkLogicalDeviceWrap
import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.vulkan.VkShaderModuleCreateInfo
import java.io.Closeable

class VkShaderModule(stack: MemoryStack, private val vkDeviceWrap: VkLogicalDeviceWrap, shaderData: VkShaderData) :
    Closeable {
    private var shaderModule: Long

    init {
        val createInfo = VkShaderModuleCreateInfo.calloc(stack).apply {
            sType(VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO)
            pCode(shaderData.buffer!!)
        }

        val pShaderModule = stack.mallocLong(1)

        if (vkCreateShaderModule(vkDeviceWrap.vkDevice, createInfo, null, pShaderModule) != VK_SUCCESS) {
            throw IllegalStateException("Failed to create shader module")
        }

        shaderModule = pShaderModule[0]

        shaderData.close()
    }

    override fun close() {
        vkDestroyShaderModule(vkDeviceWrap.vkDevice, shaderModule, null)
    }
}