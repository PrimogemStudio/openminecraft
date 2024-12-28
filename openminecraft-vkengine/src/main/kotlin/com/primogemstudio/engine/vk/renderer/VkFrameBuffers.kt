package com.primogemstudio.engine.vk.renderer

import com.primogemstudio.engine.vk.VkLogicalDeviceWrap
import com.primogemstudio.engine.vk.VkSwapChain
import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.vulkan.VkFramebufferCreateInfo
import java.io.Closeable

class VkFrameBuffers(
    stack: MemoryStack,
    private val vkDeviceWrap: VkLogicalDeviceWrap,
    vkSwapChain: VkSwapChain,
    vkRenderPass: VkRenderPass
) : Closeable {
    val framebufs = ArrayList<Long>(vkSwapChain.swapChainImageViews.size)

    init {

        val attachments = stack.mallocLong(1)
        val pFramebuffer = stack.mallocLong(1)

        val framebufferInfo = VkFramebufferCreateInfo.calloc(stack).apply {
            sType(VK_STRUCTURE_TYPE_FRAMEBUFFER_CREATE_INFO)
            renderPass(vkRenderPass.renderPass)
            width(vkSwapChain.swapChainExtent!!.width())
            height(vkSwapChain.swapChainExtent!!.height())
            layers(1)
        }

        for (imageView in vkSwapChain.swapChainImageViews) {
            attachments.put(0, imageView)
            framebufferInfo.pAttachments(attachments)

            if (vkCreateFramebuffer(vkDeviceWrap.vkDevice, framebufferInfo, null, pFramebuffer) != VK_SUCCESS) {
                throw RuntimeException("Failed to create frame buffer")
            }

            framebufs.add(pFramebuffer[0])
        }
    }

    override fun close() {
        framebufs.forEach { vkDestroyFramebuffer(vkDeviceWrap.vkDevice, it, null) }
    }
}