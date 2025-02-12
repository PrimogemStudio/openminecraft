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
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.GLFW_TRANSPARENT_FRAMEBUFFER
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwCreateWindow
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwDestroyWindow
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwPollEvents
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwSetFramebufferSizeCallback
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwSwapBuffers
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwWindowHint
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwWindowShouldClose
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.GL_COLOR_BUFFER_BIT
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.GL_TRIANGLES
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.GL_TRUE
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.glBegin
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.glClear
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.glClearColor
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.glColor4f
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.glEnd
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.glLineWidth
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.glRotatef
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.glVertex3f
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.glViewport
import com.primogemstudio.engine.bindings.vulkan.vk10.*
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_MAKE_API_VERSION
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_MAKE_VERSION
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkCreateDevice
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkCreateFence
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkCreateInstance
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkEnumerateInstanceLayerProperties
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkEnumeratePhysicalDevices
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkResetFences
import com.primogemstudio.engine.interfaces.heap.HeapByteArray
import com.primogemstudio.engine.interfaces.heap.HeapFloatArray
import com.primogemstudio.engine.interfaces.heap.HeapPointerArray
import com.primogemstudio.engine.interfaces.toCStructArray
import com.primogemstudio.engine.logging.LoggerFactory
import java.lang.foreign.MemorySegment

fun main() {
    /*System.setProperty("org.lwjgl.harfbuzz.libname", "freetype")
    val instance = VkInstanceEngine("OpenMinecraft", "0.0.1-alpha1")
    instance.vkWindow!!.mainLoop()*/

    val logger = LoggerFactory.getLogger()

    glfwInit()
    glfwSetErrorCallback { err, desc ->
        println("$err $desc")
    }

    // glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_COMPAT_PROFILE)
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 1)
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1)
    glfwWindowHint(GLFW_TRANSPARENT_FRAMEBUFFER, GL_TRUE)
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
                    val arr = HeapPointerArray(arrayOf(fence))
                    logger.info("${vkResetFences(dev, arr)}")
                }, { logger.error("vulkan error: $it") })
            }, { logger.error("vulkan error: $it") })
        }, { logger.error("vulkan error: $it") })
    }, { logger.error("vulkan error: $it") })
    vkEnumerateInstanceLayerProperties().match({ r -> println(r.map { it.layerName }) }, {})

    glfwSetCursor(
        window,
        glfwCreateCursor(
            GLFWImage().apply {
                width = 32
                height = 32
                data = HeapByteArray((0..<32 * 32 * 4).map { 0xcc.toByte() }.toByteArray())
            },
            0,
            0
        )
    )

    glfwSetFramebufferSizeCallback(window) { _, width, height ->
        glViewport(0, 0, width, height)
    }
    glfwSwapInterval(0)

    var rotation = 0f
    while (!glfwWindowShouldClose(window)) {
        glClearColor(0f, 0f, 0f, 0f)
        glClear(GL_COLOR_BUFFER_BIT)

        glLineWidth(2f)

        glRotatef(rotation, 0f, 0f, 1f)
        glBegin(GL_TRIANGLES)
        glColor4f(1f, 1f, 0f, 1f)
        glVertex3f(-0.5f, -0.5f, -1f)
        glColor4f(0f, 1f, 1f, 1f)
        glVertex3f(0.5f, -0.5f, -1f)
        glColor4f(1f, 0f, 1f, 1f)
        glVertex3f(0f, 0.5f, -1f)
        glEnd()

        rotation += 0.00001f

        glfwSwapBuffers(window)
        glfwPollEvents()
    }

    glfwDestroyWindow(window)
    glfwTerminate()
}
