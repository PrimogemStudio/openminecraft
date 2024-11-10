package com.primogemstudio

import com.primogemstudio.utils.LoggerFactory
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.vulkan.VkApplicationInfo
import org.lwjgl.vulkan.VkInstance
import org.lwjgl.vulkan.VkInstanceCreateInfo
import java.io.Closeable

class VkInstanceEngine(
    val appName: String,
    appVer: String,
    enableValidationLayer: Boolean = true,
    vkDebugCallback: VkDebugCallback? = null
): Closeable {
    private val logger = LoggerFactory.getLogger("VkInstanceEngine $appName")

    private var vkInstance: VkInstance? = null
    private var vkValidationLayer: VkValidationLayer
    private var vkPhysicalDevice: VkPhysicalDeviceWrap? = null
    private var vkLogicalDevice: VkLogicalDeviceWrap? = null
    private var vkWindow: VkWindow? = null

    init {
        val vkVer = appVer.split("-")[0].split(".").flatMap {
            try { listOf(it.toInt()) }
            catch (e: Exception) { listOf() }
        }

        if (vkVer.size < 3) throw IllegalArgumentException("Corrupt version!")

        vkValidationLayer = VkValidationLayer(this, { vkInstance!! }, enableValidationLayer, vkDebugCallback)

        stackPush().use { stk ->
            run {
                logger.info("GLFW Init...")
                glfwInit()
            }

            run {
                logger.info("Vulkan Instance Init...")
                initVkInstance(stk, appName, vkVer)
                vkValidationLayer.preInstance(stk)
            }

            run {
                logger.info("Creating Main Window and Surface...")
                glfwWindowHint(GLFW_CLIENT_API, GLFW_NO_API)
                glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE)
                vkWindow = VkWindow(this, { vkInstance!! }, glfwCreateWindow(400, 400, "Test!", 0, 0))
            }

            run {
                logger.info("Selecting Physical Device...")
                vkPhysicalDevice =
                    VkPhysicalDeviceWrap.fetchList(vkInstance!!, vkWindow!!).firstOrNull { it.suitable() }.let {
                        if (it == null) throw IllegalStateException("No suitable GPU was found")
                        it
                    }
            }

            run {
                logger.info("Creating Logical Device...")
                vkLogicalDevice = VkLogicalDeviceWrap.create(stk, vkPhysicalDevice!!, vkValidationLayer)
            }
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
        vkValidationLayer.close()
        vkLogicalDevice!!.destroy()
        vkDestroyInstance(vkInstance!!, null)
        glfwTerminate()
    }
}