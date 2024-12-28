package com.primogemstudio.engine.vk.renderer

import com.primogemstudio.engine.vk.VkLogicalDeviceWrap
import com.primogemstudio.engine.vk.VkSwapChain
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.vulkan.*
import org.lwjgl.vulkan.VK10.*
import java.io.Closeable

class VkTestCommandBuffer(
    stack: MemoryStack,
    private val vkDeviceWrap: VkLogicalDeviceWrap,
    private val vkSwapChain: VkSwapChain,
    vkFrameBuffers: VkFrameBuffers,
    vkPipeline: VkTestPipeline,
    private val vkRenderPass: VkRenderPass
) : Closeable {
    private var commandPool: Long
    var commandBuffers: List<VkCommandBuffer>

    init {
        val poolInfo = VkCommandPoolCreateInfo.calloc(stack).apply {
            sType(VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO)
            queueFamilyIndex(vkDeviceWrap.graphicsFamily)
        }

        val pCommandPool = stack.mallocLong(1)
        if (vkCreateCommandPool(vkDeviceWrap.vkDevice, poolInfo, null, pCommandPool) != VK_SUCCESS) {
            throw RuntimeException("Failed to create command pool")
        }

        commandPool = pCommandPool[0]

        val commandBuffersCount = vkFrameBuffers.framebufs.size
        val commandBuffersT = mutableListOf<VkCommandBuffer>()

        val allocInfo = VkCommandBufferAllocateInfo.calloc(stack).apply {
            sType(VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO)
            commandPool(commandPool)
            level(VK_COMMAND_BUFFER_LEVEL_PRIMARY)
            commandBufferCount(commandBuffersCount)
        }

        val pCommandBuffers = stack.mallocPointer(commandBuffersCount)

        if (vkAllocateCommandBuffers(vkDeviceWrap.vkDevice, allocInfo, pCommandBuffers) != VK_SUCCESS) {
            throw RuntimeException("Failed to allocate command buffers")
        }

        for (i in 0..<commandBuffersCount) {
            commandBuffersT.add(VkCommandBuffer(pCommandBuffers[i], vkDeviceWrap.vkDevice))
        }

        val beginInfo = VkCommandBufferBeginInfo.calloc(stack).apply {
            sType(VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO)
        }

        val renderArea = VkRect2D.calloc(stack).apply {
            offset(VkOffset2D.calloc(stack).set(0, 0))
            extent(vkSwapChain.swapChainExtent!!)
        }

        val clearValues = VkClearValue.calloc(1, stack)
        clearValues.color().float32(stack.floats(0f, 0f, 0f, 0f))

        val renderPassInfo = VkRenderPassBeginInfo.calloc(stack).apply {
            sType(VK_STRUCTURE_TYPE_RENDER_PASS_BEGIN_INFO)
            renderPass(vkRenderPass.renderPass)
            renderArea(renderArea)
            pClearValues(clearValues)
        }

        for (i in 0..<commandBuffersCount) {
            val commandBuffer = commandBuffersT[i]
            if (vkBeginCommandBuffer(commandBuffer, beginInfo) != VK_SUCCESS) {
                throw RuntimeException("Failed to begin recording command buffer")
            }
            renderPassInfo.framebuffer(vkFrameBuffers.framebufs[i])

            vkCmdBeginRenderPass(commandBuffer, renderPassInfo, VK_SUBPASS_CONTENTS_INLINE)
            vkCmdBindPipeline(commandBuffer, VK_PIPELINE_BIND_POINT_GRAPHICS, vkPipeline.graphicsPipeline)
            vkCmdDraw(commandBuffer, 3, 1, 0, 0)
            vkCmdEndRenderPass(commandBuffer)

            if (vkEndCommandBuffer(commandBuffer) != VK_SUCCESS) {
                throw RuntimeException("Failed to record command buffer")
            }
        }

        commandBuffers = commandBuffersT
    }

    override fun close() {
        stackPush().use {
            val p = it.mallocPointer(commandBuffers.size)
            commandBuffers.forEach(p::put)
            vkFreeCommandBuffers(vkDeviceWrap.vkDevice, commandPool, p.rewind())
        }
    }

    fun cleanup() {
        vkDestroyCommandPool(vkDeviceWrap.vkDevice, commandPool, null)
    }
}