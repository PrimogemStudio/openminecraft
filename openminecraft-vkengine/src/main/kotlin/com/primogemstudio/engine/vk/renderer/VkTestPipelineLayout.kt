package com.primogemstudio.engine.vk.renderer

import com.primogemstudio.engine.vk.VkLogicalDeviceWrap
import com.primogemstudio.engine.vk.VkSwapChain
import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.*
import org.lwjgl.vulkan.VK10.*
import java.io.Closeable

class VkTestPipelineLayout(
    val stack: MemoryStack,
    private val vkDeviceWrap: VkLogicalDeviceWrap,
    private val vkSwapChain: VkSwapChain,
    private val vkShaderStage: VkPipelineShaderStageCreateInfo.Buffer,
    private val vkRenderPass: VkTestRenderPass
) : Closeable {
    private var pipelineLayout: Long
    private var graphicsPipeline: Long

    init {
        val vertexInputInfo = VkPipelineVertexInputStateCreateInfo.calloc(stack).apply {
            sType(VK_STRUCTURE_TYPE_PIPELINE_VERTEX_INPUT_STATE_CREATE_INFO)
        }

        val inputAssembly = VkPipelineInputAssemblyStateCreateInfo.calloc(stack).apply {
            sType(VK_STRUCTURE_TYPE_PIPELINE_INPUT_ASSEMBLY_STATE_CREATE_INFO)
            topology(VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST)
            primitiveRestartEnable(false)
        }

        val viewport = VkViewport.calloc(1, stack).let {
            it.x(0f)
            it.y(0f)
            it.width(vkSwapChain.swapChainExtent!!.width().toFloat())
            it.height(vkSwapChain.swapChainExtent!!.height().toFloat())
            it.minDepth(0f)
            it.maxDepth(1f)
        }

        val scissor = VkRect2D.calloc(1, stack).let {
            it.offset(VkOffset2D.calloc(stack).set(0, 0))
            it.extent(vkSwapChain.swapChainExtent!!)
        }

        val viewportState = VkPipelineViewportStateCreateInfo.calloc(stack).apply {
            sType(VK_STRUCTURE_TYPE_PIPELINE_VIEWPORT_STATE_CREATE_INFO)
            pViewports(viewport)
            pScissors(scissor)
        }

        val rasterizer = VkPipelineRasterizationStateCreateInfo.calloc(stack).apply {
            sType(VK_STRUCTURE_TYPE_PIPELINE_RASTERIZATION_STATE_CREATE_INFO)
            depthClampEnable(false)
            rasterizerDiscardEnable(false)
            polygonMode(VK_POLYGON_MODE_FILL)
            lineWidth(1f)
            cullMode(VK_CULL_MODE_BACK_BIT)
            frontFace(VK_FRONT_FACE_CLOCKWISE)
            depthBiasEnable(true)
        }

        val multisampling = VkPipelineMultisampleStateCreateInfo.calloc(stack).apply {
            sType(VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO)
            sampleShadingEnable(false)
            rasterizationSamples(VK_SAMPLE_COUNT_1_BIT)
        }

        val colorBlendAttachment = VkPipelineColorBlendAttachmentState.calloc(1, stack).let {
            it.colorWriteMask(
                VK_COLOR_COMPONENT_R_BIT or
                        VK_COLOR_COMPONENT_G_BIT or
                        VK_COLOR_COMPONENT_B_BIT or
                        VK_COLOR_COMPONENT_A_BIT
            )
            it.blendEnable(false)
        }

        val colorBlending = VkPipelineColorBlendStateCreateInfo.calloc(stack).apply {
            sType(VK_STRUCTURE_TYPE_PIPELINE_COLOR_BLEND_STATE_CREATE_INFO)
            logicOpEnable(false)
            logicOp(VK_LOGIC_OP_COPY)
            pAttachments(colorBlendAttachment)
            blendConstants(stack.floats(0f, 0f, 0f, 0f))
        }

        val pipelineLayoutInfo = VkPipelineLayoutCreateInfo.calloc(stack).apply {
            sType(VK_STRUCTURE_TYPE_PIPELINE_LAYOUT_CREATE_INFO)
        }

        val pPipelineLayout = stack.longs(VK_NULL_HANDLE)

        if (vkCreatePipelineLayout(vkDeviceWrap.vkDevice, pipelineLayoutInfo, null, pPipelineLayout) != VK_SUCCESS) {
            throw RuntimeException("Failed to create pipeline layout")
        }

        pipelineLayout = pPipelineLayout[0]

        val pipelineInfo = VkGraphicsPipelineCreateInfo.calloc(1, stack).let {
            it.sType(VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO)
            it.pStages(vkShaderStage)
            it.pVertexInputState(vertexInputInfo)
            it.pInputAssemblyState(inputAssembly)
            it.pViewportState(viewportState)
            it.pRasterizationState(rasterizer)
            it.pMultisampleState(multisampling)
            it.pColorBlendState(colorBlending)
            it.layout(pipelineLayout)
            it.renderPass(vkRenderPass.renderPass)
            it.subpass(0)
            it.basePipelineHandle(VK_NULL_HANDLE)
            it.basePipelineIndex(-1)
        }

        val pGraphicsPipeline = stack.mallocLong(1)
        if (vkCreateGraphicsPipelines(
                vkDeviceWrap.vkDevice,
                VK_NULL_HANDLE,
                pipelineInfo,
                null,
                pGraphicsPipeline
            ) != VK_SUCCESS
        ) {
            throw RuntimeException("Failed to create graphics pipeline")
        }

        graphicsPipeline = pGraphicsPipeline[0]
    }

    override fun close() {
        vkDestroyPipeline(vkDeviceWrap.vkDevice, graphicsPipeline, null)
        vkDestroyPipelineLayout(vkDeviceWrap.vkDevice, pipelineLayout, null)
    }
}