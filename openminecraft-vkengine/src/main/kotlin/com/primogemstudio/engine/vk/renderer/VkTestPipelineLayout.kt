package com.primogemstudio.engine.vk.renderer

import com.primogemstudio.engine.vk.VkLogicalDeviceWrap
import com.primogemstudio.engine.vk.VkSwapChain
import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.vulkan.VkPipelineInputAssemblyStateCreateInfo
import org.lwjgl.vulkan.VkPipelineVertexInputStateCreateInfo
import org.lwjgl.vulkan.VkViewport
import java.io.Closeable

class VkTestPipelineLayout(
    val stack: MemoryStack,
    private val vkDeviceWrap: VkLogicalDeviceWrap,
    private val vkSwapChain: VkSwapChain
) : Closeable {
    private var pipelineLayout: Long

    init {
        val vertexInputInfo = VkPipelineVertexInputStateCreateInfo.calloc(stack).apply {
            sType(VK_STRUCTURE_TYPE_PIPELINE_VERTEX_INPUT_STATE_CREATE_INFO)
        }

        val inputAssembly = VkPipelineInputAssemblyStateCreateInfo.calloc(stack).apply {
            sType(VK_STRUCTURE_TYPE_PIPELINE_INPUT_ASSEMBLY_STATE_CREATE_INFO)
            topology(VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST)
            primitiveRestartEnable(false)
        }

        val viewport = VkViewport.calloc(1, stack)

        pipelineLayout = 0
    }

    override fun close() {
        vkDestroyPipelineLayout(vkDeviceWrap.vkDevice, pipelineLayout, null)
    }
}