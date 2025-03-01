package com.primogemstudio.engine.graphics.backend.vk.shader

import com.primogemstudio.engine.bindings.vulkan.utils.toFullErr
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkCreateShaderModule
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkDestroyShaderModule
import com.primogemstudio.engine.bindings.vulkan.vk10.VkShaderModule
import com.primogemstudio.engine.bindings.vulkan.vk10.VkShaderModuleCreateInfo
import com.primogemstudio.engine.foreign.heap.HeapByteArray
import com.primogemstudio.engine.graphics.backend.vk.BackendRendererVk
import java.io.Closeable
import java.lang.foreign.MemorySegment

class ShaderModuleVk(
    private val renderer: BackendRendererVk,
    private val data: MemorySegment
) : Closeable {
    private val shaderModule: VkShaderModule = vkCreateShaderModule(
        renderer.logicalDevice.device,
        VkShaderModuleCreateInfo().apply {
            code = HeapByteArray(data.byteSize().toInt(), data)
        },
        null
    ).match(
        { it },
        { throw IllegalStateException(toFullErr("exception.renderer.backend_vk.shader", it)) }
    )

    operator fun invoke(): VkShaderModule = shaderModule
    override fun close() {
        vkDestroyShaderModule(renderer.logicalDevice.device, shaderModule, null)
    }
}