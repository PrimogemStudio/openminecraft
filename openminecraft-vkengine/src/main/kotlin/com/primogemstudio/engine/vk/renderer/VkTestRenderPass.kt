package com.primogemstudio.engine.vk.renderer

import com.primogemstudio.engine.vk.VkLogicalDeviceWrap
import com.primogemstudio.engine.vk.VkSwapChain
import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.*
import org.lwjgl.vulkan.KHRSwapchain.VK_IMAGE_LAYOUT_PRESENT_SRC_KHR
import org.lwjgl.vulkan.VK10.*
import java.io.Closeable

class VkTestRenderPass(
    stack: MemoryStack,
    private val vkDeviceWrap: VkLogicalDeviceWrap,
    private val vkSwapChain: VkSwapChain
) : Closeable {
    var renderPass: Long = 0

    init {
        val colorAttachment = VkAttachmentDescription.calloc(1, stack).let {
            it.format(vkSwapChain.swapChainImageFormat)
            it.samples(VK_SAMPLE_COUNT_1_BIT)
            it.loadOp(VK_ATTACHMENT_LOAD_OP_CLEAR)
            it.storeOp(VK_ATTACHMENT_STORE_OP_STORE)
            it.stencilLoadOp(VK_ATTACHMENT_LOAD_OP_DONT_CARE)
            it.stencilStoreOp(VK_ATTACHMENT_STORE_OP_DONT_CARE)
            it.initialLayout(VK_IMAGE_LAYOUT_UNDEFINED)
            it.finalLayout(VK_IMAGE_LAYOUT_PRESENT_SRC_KHR)
        }

        val colorAttachmentRef = VkAttachmentReference.calloc(1, stack).let {
            it.attachment(0)
            it.layout(VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL)
        }

        val subpass = VkSubpassDescription.calloc(1, stack).let {
            it.pipelineBindPoint(VK_PIPELINE_BIND_POINT_GRAPHICS)
            it.colorAttachmentCount(1)
            it.pColorAttachments(colorAttachmentRef)
        }

        val dependency = VkSubpassDependency.calloc(1, stack).let {
            it.srcSubpass(VK_SUBPASS_EXTERNAL)
            it.dstSubpass(0)
            it.srcStageMask(VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT)
            it.srcAccessMask(0)
            it.dstStageMask(VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT)
            it.dstAccessMask(VK_ACCESS_COLOR_ATTACHMENT_READ_BIT or VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT)
        }

        val renderPassInfo = VkRenderPassCreateInfo.calloc(stack).apply {
            sType(VK_STRUCTURE_TYPE_RENDER_PASS_CREATE_INFO)
            pAttachments(colorAttachment)
            pSubpasses(subpass)
            pDependencies(dependency)
        }

        val pRenderPass = stack.mallocLong(1)
        if (vkCreateRenderPass(vkDeviceWrap.vkDevice, renderPassInfo, null, pRenderPass) != VK_SUCCESS) {
            throw RuntimeException("Failed to create render pass")
        }

        renderPass = pRenderPass[0]
    }

    override fun close() {
        vkDestroyRenderPass(vkDeviceWrap.vkDevice, renderPass, null)
    }
}