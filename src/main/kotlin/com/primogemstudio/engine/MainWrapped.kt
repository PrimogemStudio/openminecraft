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
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_MAKE_API_VERSION
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_MAKE_VERSION
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.vkCreateInstance
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.vkEnumeratePhysicalDevices
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.vkGetPhysicalDeviceQueueFamilyProperties
import com.primogemstudio.engine.bindings.vulkan.VkApplicationInfo
import com.primogemstudio.engine.bindings.vulkan.VkInstanceCreateInfo
import com.primogemstudio.engine.interfaces.heap.HeapInt
import com.primogemstudio.engine.loader.Platform
import java.lang.foreign.MemorySegment

fun main() {
    /*System.setProperty("org.lwjgl.harfbuzz.libname", "freetype")
    val instance = VkInstanceEngine("OpenMinecraft", "0.0.1-alpha1")
    instance.vkWindow!!.mainLoop()*/

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
    val dev = vkEnumeratePhysicalDevices(vkInstance.first, HeapInt()).first[0]
    println(vkGetPhysicalDeviceQueueFamilyProperties(dev, HeapInt()))
    /*listOf(
        "SIZEOF"
    ).forEach { println(Class.forName("org.lwjgl.vulkan.VkQueueFamilyProperties").getField(it).get(null)) }*/

    glfwSetCursor(
        window,
        glfwCreateCursor(GLFWImage(32, 32, (0..<32 * 32 * 4).map { 0xcc.toByte() }.toByteArray()), 0, 0)
    )

    // glfwShowWindow(window)
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
