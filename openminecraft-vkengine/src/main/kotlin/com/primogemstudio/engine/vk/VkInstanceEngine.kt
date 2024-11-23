package com.primogemstudio.engine.vk

import com.primogemstudio.engine.i18n.Internationalization.tr
import com.primogemstudio.engine.logging.LoggerFactory
import com.primogemstudio.engine.vk.renderer.VkRendererTest
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.vulkan.KHRSwapchain.VK_KHR_SWAPCHAIN_EXTENSION_NAME
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
    private var vkQueue: VkQueueWrap? = null
    private var vkSwapChain: VkSwapChain? = null
    var vkWindow: VkWindow? = null

    private var vkTestRenderer: VkRendererTest? = null

    init {
        val vkVer = appVer.split("-")[0].split(".").flatMap {
            try { listOf(it.toInt()) }
            catch (e: Exception) { listOf() }
        }

        if (vkVer.size < 3) throw IllegalArgumentException(tr("exception.engine.app.version_corrupt", appVer))

        vkValidationLayer = VkValidationLayer(this, { vkInstance!! }, enableValidationLayer, vkDebugCallback)

        stackPush().use { stk ->
            run {
                logger.info(tr("engine.stage.glfw_init"))
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
                vkWindow = VkWindow({ vkInstance!! }, 400, 400, "Test!")
            }

            run {
                logger.info("Selecting Physical Device...")
                vkPhysicalDevice =
                    VkPhysicalDeviceWrap.fetchList(stk, vkInstance!!, vkWindow!!).firstOrNull { it.suitable() }.let {
                        if (it == null) throw IllegalStateException("No suitable GPU was found")
                        it
                    }
            }

            run {
                logger.info("Creating Logical Device...")
                vkLogicalDevice = VkLogicalDeviceWrap.create(stk, vkPhysicalDevice!!, vkValidationLayer)
            }

            run {
                logger.info("Fetching Queue...")
                vkQueue = VkQueueWrap.createFromLogicalDevice(stk, vkLogicalDevice!!)
            }

            run {
                logger.info("Creating Swap Chain...")
                vkSwapChain = VkSwapChain(vkLogicalDevice!!, vkPhysicalDevice!!, vkWindow!!)
            }

            run {
                logger.info("Initialize renderer...")
                vkTestRenderer = VkRendererTest(stk, vkLogicalDevice!!)
            }
        }
    }

    private fun initVkInstance(stk: MemoryStack, appName: String, vkVer: List<Int>) {
        val vkAppInfo = VkApplicationInfo.calloc(stk).apply {
            sType(VK_STRUCTURE_TYPE_APPLICATION_INFO)
            pApplicationName(stk.UTF8Safe(appName))
            applicationVersion(VK_MAKE_VERSION(vkVer[0], vkVer[1], vkVer[2]))
            pEngineName(stk.UTF8Safe(appName))
            engineVersion(VK_MAKE_VERSION(vkVer[0], vkVer[1], vkVer[2]))
            apiVersion(VK_API_VERSION_1_0)
        }

        val vkCreateInfo = VkInstanceCreateInfo.calloc(stk).apply {
            sType(VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO)
            pApplicationInfo(vkAppInfo)
            ppEnabledExtensionNames(vkValidationLayer.getRequiredExtensions(stk).let {
                val nBuff = stk.mallocPointer((it?.capacity() ?: 0) + 1)
                if (it != null) nBuff.put(it)
                nBuff.put(stk.UTF8Safe(VK_KHR_SWAPCHAIN_EXTENSION_NAME)!!)
                it
            })
            vkValidationLayer.vkInitArgs(stk) { pb, l ->
                ppEnabledLayerNames(pb)
                pNext(l)
            }
        }

        val vkInstancePtr = stk.mallocPointer(1)

        if (vkCreateInstance(vkCreateInfo, null, vkInstancePtr) != VK_SUCCESS) {
            throw RuntimeException("Failed to create vulkan instance")
        }

        vkInstance = VkInstance(vkInstancePtr.get(0), vkCreateInfo)
    }

    override fun close() {
        vkTestRenderer!!.close()
        vkSwapChain!!.close()
        vkWindow!!.close()
        vkLogicalDevice!!.close()
        vkValidationLayer.close()
        vkDestroyInstance(vkInstance!!, null)
        glfwTerminate()
    }
}