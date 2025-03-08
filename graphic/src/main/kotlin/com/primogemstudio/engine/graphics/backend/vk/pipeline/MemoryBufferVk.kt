package com.primogemstudio.engine.graphics.backend.vk.pipeline

import com.primogemstudio.engine.bindings.vulkan.utils.toFullErr
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkAllocateMemory
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkBindBufferMemory
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkCreateBuffer
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkDestroyBuffer
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkFreeMemory
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkGetBufferMemoryRequirements
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkMapMemory
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkUnmapMemory
import com.primogemstudio.engine.bindings.vulkan.vk10.VkBuffer
import com.primogemstudio.engine.bindings.vulkan.vk10.VkBufferCreateInfo
import com.primogemstudio.engine.bindings.vulkan.vk10.VkDeviceMemory
import com.primogemstudio.engine.bindings.vulkan.vk10.VkMemoryAllocateInfo
import com.primogemstudio.engine.graphics.backend.vk.BackendRendererVk
import com.primogemstudio.engine.i18n.Internationalization.tr
import java.io.Closeable
import java.nio.ByteBuffer

class MemoryBufferVk(
    private val renderer: BackendRendererVk,
    private val size: Long,
    private val usage: Int,
    private val sharingMode: Int,
    private val properties: Int
) : Closeable {
    private val buffer = vkCreateBuffer(renderer.logicalDevice(), VkBufferCreateInfo().apply {
        size = this@MemoryBufferVk.size
        usage = this@MemoryBufferVk.usage
        sharingMode = this@MemoryBufferVk.sharingMode
    }, null).match({ it }, { throw IllegalStateException(toFullErr("exception.renderer.backend_vk.buffer", it)) })
    private val memory: VkDeviceMemory

    init {
        val req = vkGetBufferMemoryRequirements(renderer.logicalDevice(), buffer)

        val type = (0..<renderer.physicalDevice.memoryProps.memoryTypeCount)
            .map { Pair(it, renderer.physicalDevice.memoryProps.memoryType(it)) }
            .firstOrNull() { req.type.and(1.shl(it.first)) != 0 && it.second.propertyFlags.and(properties) == properties }?.first

        if (type == null) throw IllegalStateException(tr("exception.renderer.backend_vk.buffer_memtype", properties))

        memory = vkAllocateMemory(renderer.logicalDevice(), VkMemoryAllocateInfo().apply {
            allocationSize = req.size
            typeIndex = type
        }, null).match(
            { it },
            { throw IllegalStateException(toFullErr("exception.renderer.backend_vk.buffer_mem", it)) })

        vkBindBufferMemory(renderer.logicalDevice(), buffer, memory, 0)
    }

    fun mapMemory(): ByteBuffer {
        return vkMapMemory(renderer.logicalDevice(), memory, 0, size, 0).match({ it }, {
            throw IllegalStateException(
                toFullErr("exception.renderer.backend_vk.buffer_map", it)
            )
        })
    }

    fun unmapMemory() {
        vkUnmapMemory(renderer.logicalDevice(), memory)
    }

    operator fun invoke(): VkBuffer = buffer

    override fun close() {
        vkDestroyBuffer(renderer.logicalDevice(), buffer, null)
        vkFreeMemory(renderer.logicalDevice(), memory, null)
    }
}