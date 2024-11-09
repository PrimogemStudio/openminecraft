package com.primogemstudio

import com.primogemstudio.utils.LoggerFactory
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.vulkan.*
import org.lwjgl.vulkan.EXTDebugUtils.*
import org.lwjgl.vulkan.VK10.*
import java.io.Closeable

class VkInstanceEngine(
    val appName: String,
    appVer: String,
    enableValidationLayer: Boolean = true,
    vkDebugCallback: VkDebugCallback? = null
): Closeable {
    private val logger = LoggerFactory.getLogger("VkInstanceEngine $appName")

    private var vkInstance: VkInstance? = null
    private lateinit var vkValidationLayer: VkValidationLayer

    init {
        val vkVer = appVer.split("-")[0].split(".").flatMap {
            try { listOf(it.toInt()) }
            catch (e: Exception) { listOf() }
        }

        if (vkVer.size < 3) throw IllegalArgumentException("Corrupt version!")

        vkValidationLayer = VkValidationLayer(this, enableValidationLayer, vkDebugCallback)

        stackPush().use {
            glfwInit()
            initVkInstance(it, appName, vkVer)
            VkPhysicalDeviceWrap.fetchList(vkInstance!!)
        }
    }

    private fun initVkInstance(it: MemoryStack, appName: String, vkVer: List<Int>) {
        val vkAppInfo = VkApplicationInfo.calloc(it).apply {
            sType(VK_STRUCTURE_TYPE_APPLICATION_INFO)
            pApplicationName(it.UTF8Safe(appName))
            applicationVersion(VK_MAKE_VERSION(vkVer[0], vkVer[1], vkVer[2]))
            pEngineName(it.UTF8Safe(appName))
            engineVersion(VK_MAKE_VERSION(vkVer[0], vkVer[1], vkVer[2]))
            apiVersion(VK_API_VERSION_1_0)
        }

        val vkCreateInfo = VkInstanceCreateInfo.calloc(it).apply {
            sType(VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO)
            pApplicationInfo(vkAppInfo)
            ppEnabledExtensionNames(vkValidationLayer.getRequiredExtensions(it))
            vkValidationLayer.vkInitArgs(it) { pb, l ->
                ppEnabledLayerNames(pb)
                pNext(l)
            }
        }

        val vkInstancePtr = it.mallocPointer(1)

        if (vkCreateInstance(vkCreateInfo, null, vkInstancePtr) != VK_SUCCESS) {
            throw RuntimeException("Failed to create vulkan instance")
        }

        vkInstance = VkInstance(vkInstancePtr.get(0), vkCreateInfo)
    }

    override fun close() {

    }
}