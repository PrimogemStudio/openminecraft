package com.primogemstudio.engine.vk.renderer

import com.primogemstudio.engine.resource.ResourceManager
import com.primogemstudio.engine.vk.VkLogicalDeviceWrap
import com.primogemstudio.engine.vk.VkQueueWrap
import com.primogemstudio.engine.vk.VkSwapChain
import com.primogemstudio.engine.vk.shader.ShaderCompiler
import com.primogemstudio.engine.vk.shader.ShaderLanguage
import com.primogemstudio.engine.vk.shader.ShaderType
import com.primogemstudio.engine.vk.shader.VkShaderModule
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.vulkan.*
import org.lwjgl.vulkan.KHRSwapchain.*
import org.lwjgl.vulkan.VK10.*
import java.io.Closeable

class VkRendererTest(
    private val stack: MemoryStack,
    private val vkDeviceWrap: VkLogicalDeviceWrap,
    private val vkSwapChain: VkSwapChain,
    private val vkQueueWrap: VkQueueWrap
) : Closeable {
    companion object {
        private val MAX_FRAMES_IN_FLIGHT: Int = 2
    }
    private val vkShaderCompiler = ShaderCompiler()
    private val vkBaseShaderFrag = vkShaderCompiler.compile(
        ResourceManager.getResource("jar:assets/openmc_vkengine/shaders/basic_shader.frag")?.readAllBytes()
            ?.toString(Charsets.UTF_8) ?: "",
        "basic_shader.frag",
        "main",
        ShaderLanguage.Glsl,
        ShaderType.Fragment
    )
    private val vkBaseShaderVert = vkShaderCompiler.compile(
        ResourceManager.getResource("jar:assets/openmc_vkengine/shaders/basic_shader.vert")?.readAllBytes()
            ?.toString(Charsets.UTF_8) ?: "",
        "basic_shader.vert",
        "main",
        ShaderLanguage.Glsl,
        ShaderType.Vertex
    )
    private var vkBaseShaderMFrag = VkShaderModule(stack, vkDeviceWrap, vkBaseShaderFrag)
    private var vkBaseShaderMVert = VkShaderModule(stack, vkDeviceWrap, vkBaseShaderVert)
    private var vkRenderPass = VkTestRenderPass(stack, vkDeviceWrap, vkSwapChain)
    private var vkPipeline: VkTestPipeline
    private var vkFramebufs: VkTestFrameBuffers
    private var vkCommandBuffer: VkTestCommandBuffer

    private var inFlightFrames: MutableList<Frame>
    private var imagesInFlight: MutableMap<Int, Frame>
    private var currentFrame: Int = 0

    init {
        val shaderStages = VkPipelineShaderStageCreateInfo.calloc(2, stack)
        shaderStages[0].apply {
            sType(VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO)
            stage(VK_SHADER_STAGE_VERTEX_BIT)
            module(vkBaseShaderMVert.shaderModule)
            pName(stack.UTF8("main"))
        }

        shaderStages[1].apply {
            sType(VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO)
            stage(VK_SHADER_STAGE_FRAGMENT_BIT)
            module(vkBaseShaderMFrag.shaderModule)
            pName(stack.UTF8("main"))
        }

        vkPipeline = VkTestPipeline(stack, vkDeviceWrap, vkSwapChain, shaderStages, vkRenderPass)
        vkFramebufs = VkTestFrameBuffers(stack, vkDeviceWrap, vkSwapChain, vkRenderPass)
        vkCommandBuffer = VkTestCommandBuffer(stack, vkDeviceWrap, vkSwapChain, vkFramebufs, vkPipeline, vkRenderPass)

        inFlightFrames = mutableListOf()
        imagesInFlight = mutableMapOf()

        val semaphoreInfo = VkSemaphoreCreateInfo.calloc(stack).apply {
            sType(VK_STRUCTURE_TYPE_SEMAPHORE_CREATE_INFO)
        }
        val fenceInfo = VkFenceCreateInfo.calloc(stack).apply {
            sType(VK_STRUCTURE_TYPE_FENCE_CREATE_INFO)
            flags(VK_FENCE_CREATE_SIGNALED_BIT)
        }
        val pImageAvailableSemaphore = stack.mallocLong(1)
        val pRenderFinishedSemaphore = stack.mallocLong(1)
        val pFence = stack.mallocLong(1)

        for (i in 0..<MAX_FRAMES_IN_FLIGHT) {
            if (
                vkCreateSemaphore(vkDeviceWrap.vkDevice, semaphoreInfo, null, pImageAvailableSemaphore) != VK_SUCCESS ||
                vkCreateSemaphore(vkDeviceWrap.vkDevice, semaphoreInfo, null, pRenderFinishedSemaphore) != VK_SUCCESS ||
                vkCreateFence(vkDeviceWrap.vkDevice, fenceInfo, null, pFence) != VK_SUCCESS
            ) {
                throw RuntimeException("Failed to create sync objects for the frame $i")
            }
            inFlightFrames.add(Frame(pImageAvailableSemaphore[0], pRenderFinishedSemaphore[0], pFence[0]))
        }
    }

    fun render() {
        stackPush().use { stk ->
            val thisFrame = inFlightFrames[currentFrame]
            vkWaitForFences(vkDeviceWrap.vkDevice, thisFrame.fence, true, Long.MAX_VALUE)

            val pImageIndex = stk.mallocInt(1)
            vkAcquireNextImageKHR(
                vkDeviceWrap.vkDevice,
                vkSwapChain.swapChain,
                Long.MAX_VALUE,
                thisFrame.imageAvailableSemaphore,
                VK_NULL_HANDLE,
                pImageIndex
            )

            val imageIndex = pImageIndex[0]

            if (imagesInFlight.containsKey(imageIndex)) {
                vkWaitForFences(vkDeviceWrap.vkDevice, imagesInFlight[imageIndex]!!.fence, true, Long.MAX_VALUE)
            }
            imagesInFlight[imageIndex] = thisFrame

            val submitInfo = VkSubmitInfo.calloc(stk).apply {
                sType(VK_STRUCTURE_TYPE_SUBMIT_INFO)
                waitSemaphoreCount(1)
                pWaitSemaphores(stk.longs(thisFrame.imageAvailableSemaphore))
                pWaitDstStageMask(stk.ints(VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT))
                pSignalSemaphores(stk.longs(thisFrame.renderFinishedSemaphore))
                pCommandBuffers(stk.pointers(vkCommandBuffer.commandBuffers[imageIndex]))
            }
            vkResetFences(vkDeviceWrap.vkDevice, thisFrame.fence)

            if (vkQueueSubmit(vkQueueWrap.vkGraphicsQueue, submitInfo, thisFrame.fence) != VK_SUCCESS) {
                throw RuntimeException("Failed to submit draw command buffer")
            }

            val presentInfo = VkPresentInfoKHR.calloc(stk).apply {
                sType(VK_STRUCTURE_TYPE_PRESENT_INFO_KHR)
                pWaitSemaphores(stk.longs(thisFrame.renderFinishedSemaphore))
                swapchainCount(1)
                pSwapchains(stk.longs(vkSwapChain.swapChain))
                pImageIndices(pImageIndex)
            }

            vkQueuePresentKHR(vkQueueWrap.vkPresentQueue, presentInfo)
            currentFrame = (currentFrame + 1) % MAX_FRAMES_IN_FLIGHT
        }
    }

    override fun close() {
        vkBaseShaderMFrag.close()
        vkBaseShaderMVert.close()
        vkFramebufs.close()
        vkPipeline.close()

        inFlightFrames.forEach {
            vkDestroySemaphore(vkDeviceWrap.vkDevice, it.imageAvailableSemaphore, null)
            vkDestroySemaphore(vkDeviceWrap.vkDevice, it.renderFinishedSemaphore, null)
            vkDestroyFence(vkDeviceWrap.vkDevice, it.fence, null)
        }
        imagesInFlight.clear()
    }
}

data class Frame(
    val imageAvailableSemaphore: Long,
    val renderFinishedSemaphore: Long,
    val fence: Long
)