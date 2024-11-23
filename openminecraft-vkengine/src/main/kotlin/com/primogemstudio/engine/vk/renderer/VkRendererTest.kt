package com.primogemstudio.engine.vk.renderer

import com.primogemstudio.engine.resource.ResourceManager
import com.primogemstudio.engine.vk.VkLogicalDeviceWrap
import com.primogemstudio.engine.vk.VkSwapChain
import com.primogemstudio.engine.vk.shader.ShaderCompiler
import com.primogemstudio.engine.vk.shader.ShaderLanguage
import com.primogemstudio.engine.vk.shader.ShaderType
import com.primogemstudio.engine.vk.shader.VkShaderModule
import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.vulkan.VkPipelineShaderStageCreateInfo
import java.io.Closeable

class VkRendererTest(
    stack: MemoryStack,
    vkDeviceWrap: VkLogicalDeviceWrap,
    vkSwapChain: VkSwapChain
) : Closeable {
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

    private var pipelineLayout: VkTestPipelineLayout

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

        pipelineLayout = VkTestPipelineLayout(stack, vkDeviceWrap, vkSwapChain, shaderStages)
    }

    override fun close() {
        vkBaseShaderMFrag.close()
        vkBaseShaderMVert.close()
    }
}