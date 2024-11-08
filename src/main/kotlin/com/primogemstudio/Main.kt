package com.primogemstudio

import com.primogemstudio.utils.TestLog
import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.*
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWVulkan.*

fun main() {
    glfwInit()

    var vkInstance: VkInstance
    MemoryStack.stackPush().use {
        val vkAppInfo = VkApplicationInfo.calloc(it).apply {
            sType(VK_STRUCTURE_TYPE_APPLICATION_INFO)
            pApplicationName(it.UTF8Safe("Open Minecraft Test"))
            applicationVersion(VK_MAKE_VERSION(1, 0, 0))
            pEngineName(it.UTF8Safe("Open Minecraft"))
            engineVersion(VK_MAKE_VERSION(1, 0, 0))
            apiVersion(VK_API_VERSION_1_0)
        }

        val vkCreateInfo = VkInstanceCreateInfo.calloc(it).apply {
            sType(VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO)
            pApplicationInfo(vkAppInfo)
            ppEnabledExtensionNames(glfwGetRequiredInstanceExtensions())
            ppEnabledLayerNames(null)
        }

        val vkInstancePtr = it.mallocPointer(1)

        if (vkCreateInstance(vkCreateInfo, null, vkInstancePtr) != VK_SUCCESS) {
            throw RuntimeException("Failed to create vulkan instance")
        }

        vkInstance = VkInstance(vkInstancePtr.get(0), vkCreateInfo)
    }



    println(vkInstance)
    TestLog.log()
}