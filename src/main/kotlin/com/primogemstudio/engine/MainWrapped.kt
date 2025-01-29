package com.primogemstudio.engine

import com.primogemstudio.engine.bindings.glfw.GLFWBaseFuncs.glfwInit
import com.primogemstudio.engine.bindings.glfw.GLFWBaseFuncs.glfwSetErrorCallback
import com.primogemstudio.engine.bindings.glfw.GLFWBaseFuncs.glfwTerminate
import com.primogemstudio.engine.bindings.glfw.GLFWContextFuncs.glfwMakeContextCurrent
import com.primogemstudio.engine.bindings.glfw.GLFWContextFuncs.glfwSwapInterval
import com.primogemstudio.engine.bindings.glfw.GLFWImage
import com.primogemstudio.engine.bindings.glfw.GLFWInputFuncs.glfwCreateCursor
import com.primogemstudio.engine.bindings.glfw.GLFWInputFuncs.glfwSetClipboardString
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
import com.primogemstudio.engine.bindings.glfw.GLFWVulkanFuncs.glfwGetRequiredInstanceExtensions
import com.primogemstudio.engine.bindings.vulkan.*
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_MAKE_API_VERSION
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_MAKE_VERSION
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.vkCreateDevice
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.vkCreateFence
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.vkResetFences
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.vkGetFenceStatus
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.vkCreateInstance
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.vkEnumeratePhysicalDevices
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.vkGetPhysicalDeviceQueueFamilyProperties
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.vkAllocateMemory
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.vkFreeMemory
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.vkMapMemory
import com.primogemstudio.engine.interfaces.heap.*
import com.primogemstudio.engine.interfaces.struct.*
import com.primogemstudio.engine.interfaces.NativeMethodCache.callVoidFunc
import com.primogemstudio.engine.loader.Platform
import com.primogemstudio.engine.vk.VkInstanceEngine
import com.primogemstudio.engine.logging.LoggerFactory
import java.lang.foreign.MemorySegment
import java.util.*
import java.lang.foreign.Arena

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

    val vkInstance = vkCreateInstance(
        VkInstanceCreateInfo(
            appInfo = VkApplicationInfo(
                appName = "test",
                appVersion = VK_MAKE_VERSION(0, 0, 1),
                engineName = "test",
                engineVersion = VK_MAKE_VERSION(0, 0, 1),
                apiVersion = VK_MAKE_API_VERSION(1, 0, 0, 0)
            )
        ),
        allocator = null
    )
    val dev = vkEnumeratePhysicalDevices(vkInstance.first).first[0]
    listOf(
        "SIZEOF"
    ).forEach {
        logger.info(
            "$it " + Class.forName("org.lwjgl.vulkan.VkFenceCreateInfo")
                .getField(it.uppercase(Locale.getDefault())).get(null)
        )
    }
    val devi = vkCreateDevice(
            dev,
            VkDeviceCreateInfo(
                null,
                0,
                ArrayStruct(arrayOf(
                    VkDeviceQueueCreateInfo(
                        null,
                        0,
                        0,
                        FloatArrayStruct(floatArrayOf(1f))
                    )
                )),
                listOf(),
                listOf(),
                VkPhysicalDeviceFeatures()
            ),
            null
        ).first
    val fence = vkCreateFence(devi, VkFenceCreateInfo(), null).first
    val arr = PointerArrayStruct(arrayOf(fence))
    println(vkResetFences(devi, arr))

    glfwSetCursor(
        window,
        glfwCreateCursor(GLFWImage(32, 32, (0 ..< 32 * 32 * 4).map { 0xcc.toByte() }.toByteArray()), 0, 0)
    )

    glfwSetFramebufferSizeCallback(window) { _, width, height ->
        println("$width $height")
    }
    glfwSetClipboardString(window, "test!")
    glfwSwapInterval(0)

    while (glfwWindowShouldClose(window) != 1) {
        glfwSwapBuffers(window)
        glfwPollEvents()
    }

    glfwDestroyWindow(window)
    glfwTerminate()
}
