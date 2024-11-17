package com.primogemstudio.engine.vk.renderer

import com.primogemstudio.engine.resource.ResourceManager
import com.primogemstudio.engine.vk.VkLogicalDeviceWrap
import com.primogemstudio.engine.vk.shader.ShaderCompiler
import com.primogemstudio.engine.vk.shader.ShaderLanguage
import com.primogemstudio.engine.vk.shader.ShaderType
import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.vulkan.VkShaderModuleCreateInfo
import java.nio.ByteBuffer

class VkRendererTest(private val stack: MemoryStack, private val vkDeviceWrap: VkLogicalDeviceWrap) {
    private val vkShaderCompiler = ShaderCompiler()
    private val vkBaseShaderFrag = vkShaderCompiler.compile(
        ResourceManager.getResource("jar:shaders/basic_shader.frag")?.readAllBytes()?.toString(Charsets.UTF_8) ?: "",
        "basic_shader.frag",
        "main",
        ShaderLanguage.Glsl,
        ShaderType.Fragment
    )
    private val vkBaseShaderVert = vkShaderCompiler.compile(
        ResourceManager.getResource("jar:shaders/basic_shader.vert")?.readAllBytes()?.toString(Charsets.UTF_8) ?: "",
        "basic_shader.vert",
        "main",
        ShaderLanguage.Glsl,
        ShaderType.Vertex
    )

    init {
        println(createShaderModule(vkBaseShaderFrag))
        println(createShaderModule(vkBaseShaderVert))
    }

    private fun createShaderModule(ba: ByteArray): Long {
        val createInfo = VkShaderModuleCreateInfo.calloc(stack)

        createInfo.sType(VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO)
        createInfo.pCode(ByteBuffer.allocateDirect(ba.size).put(ba))

        val pShaderModule = stack.mallocLong(1)

        if (vkCreateShaderModule(vkDeviceWrap.vkDevice, createInfo, null, pShaderModule) != VK_SUCCESS) {
            throw IllegalStateException("Failed to create shader module")
        }

        return pShaderModule[0]
    }
}