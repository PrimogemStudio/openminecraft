package com.primogemstudio.engine

import com.primogemstudio.engine.bindings.glfw.GLFWBaseFuncs.glfwInit
import com.primogemstudio.engine.bindings.glfw.GLFWBaseFuncs.glfwSetErrorCallback
import com.primogemstudio.engine.bindings.glfw.GLFWBaseFuncs.glfwTerminate
import com.primogemstudio.engine.bindings.glfw.GLFWContextFuncs.glfwMakeContextCurrent
import com.primogemstudio.engine.bindings.glfw.GLFWContextFuncs.glfwSwapInterval
import com.primogemstudio.engine.bindings.glfw.GLFWImage
import com.primogemstudio.engine.bindings.glfw.GLFWInputFuncs.glfwCreateCursor
import com.primogemstudio.engine.bindings.glfw.GLFWInputFuncs.glfwSetCursor
import com.primogemstudio.engine.bindings.glfw.GLFWMonitor
import com.primogemstudio.engine.bindings.glfw.GLFWWindow
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.GLFW_CONTEXT_VERSION_MAJOR
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.GLFW_CONTEXT_VERSION_MINOR
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.GLFW_OPENGL_CORE_PROFILE
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.GLFW_OPENGL_PROFILE
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwCreateWindow
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwDestroyWindow
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwPollEvents
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwSetFramebufferSizeCallback
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwSwapBuffers
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwWindowHint
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwWindowShouldClose
import com.primogemstudio.engine.bindings.vulkan.VkFenceCreateInfo
import com.primogemstudio.engine.bindings.vulkan.core.*
import com.primogemstudio.engine.bindings.vulkan.core.Vk10Funcs.VK_MAKE_API_VERSION
import com.primogemstudio.engine.bindings.vulkan.core.Vk10Funcs.VK_MAKE_VERSION
import com.primogemstudio.engine.bindings.vulkan.core.Vk10Funcs.vkCreateDevice
import com.primogemstudio.engine.bindings.vulkan.core.Vk10Funcs.vkCreateFence
import com.primogemstudio.engine.bindings.vulkan.core.Vk10Funcs.vkCreateInstance
import com.primogemstudio.engine.bindings.vulkan.core.Vk10Funcs.vkEnumerateInstanceLayerProperties
import com.primogemstudio.engine.bindings.vulkan.core.Vk10Funcs.vkEnumeratePhysicalDevices
import com.primogemstudio.engine.bindings.vulkan.core.Vk10Funcs.vkResetFences
import com.primogemstudio.engine.interfaces.heap.HeapFloatArray
import com.primogemstudio.engine.interfaces.struct.ByteArrayStruct
import com.primogemstudio.engine.interfaces.struct.PointerArrayStruct
import com.primogemstudio.engine.interfaces.toCStructArray
import com.primogemstudio.engine.loader.Platform
import com.primogemstudio.engine.logging.LoggerFactory
import java.lang.foreign.MemorySegment

fun main() {
    /*System.setProperty("org.lwjgl.harfbuzz.libname", "freetype")
    val instance = VkInstanceEngine("OpenMinecraft", "0.0.1-alpha1")
    instance.vkWindow!!.mainLoop()*/

    val logger = LoggerFactory.getLogger()

    Platform.init()

    glfwInit()
    glfwSetErrorCallback { err, desc ->
        println("$err $desc")
    }

    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3)
    val window = glfwCreateWindow(
        640,
        480,
        "test!",
        GLFWMonitor(MemorySegment.NULL),
        GLFWWindow(MemorySegment.NULL)
    )
    glfwMakeContextCurrent(window)

    vkCreateInstance(
        VkInstanceCreateInfo().apply {
            appInfo = VkApplicationInfo().apply {
                appName = "test"
                appVersion = VK_MAKE_VERSION(0, 0, 1)
                engineName = "test"
                engineVersion = VK_MAKE_VERSION(0, 0, 1)
                apiVersion = VK_MAKE_API_VERSION(1, 0, 0, 0)
            }
            layers = arrayOf("VK_LAYER_KHRONOS_validation")
            extensions = arrayOf("VK_EXT_debug_utils")
        },
        allocator = null
    ).match({ instance ->
        vkEnumeratePhysicalDevices(instance).match({ phyDevice -> 
            vkCreateDevice(
                phyDevice[0],
                VkDeviceCreateInfo().apply {
                    queueCreateInfos = arrayOf(VkDeviceQueueCreateInfo().apply {
                        queueFamilyIndex = 0
                        queuePriorities = HeapFloatArray(floatArrayOf(1f))
                    }).toCStructArray(VkDeviceQueueCreateInfo.LAYOUT)
                    layers = arrayOf()
                    extensions = arrayOf()
                    features = VkPhysicalDeviceFeatures()
                },
                null
            ).match({ dev -> 
                vkCreateFence(dev, VkFenceCreateInfo(), null).match({ fence -> 
                    val arr = PointerArrayStruct(arrayOf(fence))
                    logger.info("${vkResetFences(dev, arr)}")
                }, { logger.error("vulkan error: $it") })
            }, { logger.error("vulkan error: $it") })
        }, { logger.error("vulkan error: $it") })
    }, { logger.error("vulkan error: $it") })
    vkEnumerateInstanceLayerProperties().match({ r -> println(r.map { it.layerName }) }, {})

    glfwSetCursor(
        window,
        glfwCreateCursor(
            GLFWImage(32, 32, ByteArrayStruct((0..<32 * 32 * 4).map { 0xcc.toByte() }.toByteArray())),
            0,
            0
        )
    )

    glfwSetFramebufferSizeCallback(window) { _, width, height ->
        println("$width $height")
    }
    glfwSwapInterval(0)

    while (glfwWindowShouldClose(window) != 1) {
        glfwSwapBuffers(window)
        glfwPollEvents()
    }

    glfwDestroyWindow(window)
    glfwTerminate()
}
