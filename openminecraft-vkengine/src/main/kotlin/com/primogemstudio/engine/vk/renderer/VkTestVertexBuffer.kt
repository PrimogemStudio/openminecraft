package com.primogemstudio.engine.vk.renderer

import com.primogemstudio.engine.vk.VkLogicalDeviceWrap
import com.primogemstudio.engine.vk.VkPhysicalDeviceWrap
import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.vulkan.VkBufferCreateInfo
import org.lwjgl.vulkan.VkMemoryAllocateInfo
import org.lwjgl.vulkan.VkMemoryRequirements
import org.lwjgl.vulkan.VkPhysicalDeviceMemoryProperties
import java.io.Closeable

class VkTestVertexBuffer(
    val stack: MemoryStack,
    private val vkPhysicalDeviceWrap: VkPhysicalDeviceWrap,
    private val vkDeviceWrap: VkLogicalDeviceWrap
) : Closeable {
    var vertexBuffer = 0L
    private var vertexBufferMemory = 0L
    private var tempPointerBuffer = stack.mallocPointer(1)

    init {
        val bufferInfo = VkBufferCreateInfo.calloc(stack).apply {
            sType(VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO)
            size((2 + 3) * Float.SIZE_BYTES * 3L)
            usage(VK_BUFFER_USAGE_VERTEX_BUFFER_BIT)
            sharingMode(VK_SHARING_MODE_EXCLUSIVE)
        }

        val pVertexBuffer = stack.mallocLong(1)

        if (vkCreateBuffer(vkDeviceWrap.vkDevice, bufferInfo, null, pVertexBuffer) != VK_SUCCESS) {
            throw RuntimeException("Failed to create vertex buffer")
        }
        vertexBuffer = pVertexBuffer[0]

        val memRequirements = VkMemoryRequirements.malloc(stack)
        vkGetBufferMemoryRequirements(vkDeviceWrap.vkDevice, vertexBuffer, memRequirements)

        val allocInfo = VkMemoryAllocateInfo.calloc(stack).apply {
            sType(VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO)
            allocationSize(memRequirements.size())
            memoryTypeIndex(
                findMemoryType(
                    stack, memRequirements.memoryTypeBits(),
                    VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT or VK_MEMORY_PROPERTY_HOST_COHERENT_BIT, vkPhysicalDeviceWrap
                )
            )
        }

        val pVertexBufferMemory = stack.mallocLong(1)
        if (vkAllocateMemory(vkDeviceWrap.vkDevice, allocInfo, null, pVertexBufferMemory) != VK_SUCCESS) {
            throw RuntimeException("Failed to allocate vertex buffer memory")
        }

        vertexBufferMemory = pVertexBufferMemory[0]

        vkBindBufferMemory(vkDeviceWrap.vkDevice, vertexBuffer, vertexBufferMemory, 0)
        vkMapMemory(vkDeviceWrap.vkDevice, vertexBufferMemory, 0, bufferInfo.size(), 0, tempPointerBuffer)
        tempPointerBuffer.getByteBuffer(0, bufferInfo.size().toInt()).apply {
            floatArrayOf(
                0.0f, -0.5f, 1.0f, 0.0f, 0.0f,
                0.5f, 0.5f, 0.0f, 1.0f, 0.0f,
                -0.5f, 0.5f, 0.0f, 0.0f, 1.0f
            ).forEach { putFloat(it) }
        }
        vkUnmapMemory(vkDeviceWrap.vkDevice, vertexBufferMemory)
    }

    override fun close() {
        vkDestroyBuffer(vkDeviceWrap.vkDevice, vertexBuffer, null)
        vkFreeMemory(vkDeviceWrap.vkDevice, vertexBufferMemory, null)
    }

    companion object {
        fun findMemoryType(
            stack: MemoryStack,
            typeFilter: Int,
            properties: Int,
            vkPhysicalDeviceWrap: VkPhysicalDeviceWrap
        ): Int {
            val memProperties = VkPhysicalDeviceMemoryProperties.malloc(stack)
            vkGetPhysicalDeviceMemoryProperties(vkPhysicalDeviceWrap.vkDevice, memProperties)

            for (i in 0..<memProperties.memoryTypeCount()) {
                if (
                    (typeFilter.and(1.shl(i))) != 0 &&
                    (memProperties.memoryTypes(i).propertyFlags().and(properties)) == properties
                ) {
                    return i
                }
            }

            throw RuntimeException("Failed to find suitable memory type")
        }
    }
}