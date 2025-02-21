package com.primogemstudio.engine.graphics.backend.vk

import com.primogemstudio.engine.bindings.glfw.GLFWBaseFuncs.glfwInit
import com.primogemstudio.engine.bindings.glfw.GLFWBaseFuncs.glfwSetErrorCallback
import com.primogemstudio.engine.bindings.glfw.GLFWBaseFuncs.glfwTerminate
import com.primogemstudio.engine.bindings.glfw.GLFWFrameBufferSizeFun
import com.primogemstudio.engine.bindings.glfw.GLFWMonitor
import com.primogemstudio.engine.bindings.glfw.GLFWVulkanFuncs.glfwCreateWindowSurface
import com.primogemstudio.engine.bindings.glfw.GLFWWindow
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.GLFW_CLIENT_API
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.GLFW_NO_API
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.GLFW_TRANSPARENT_FRAMEBUFFER
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwCreateWindow
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwDestroyWindow
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwSetFramebufferSizeCallback
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwWindowHint
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.GL_FALSE
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.GL_TRUE
import com.primogemstudio.engine.bindings.vulkan.khr.surface.VkSurfaceKHR
import com.primogemstudio.engine.bindings.vulkan.khr.surface.VkSurfaceKHRFuncs.vkDestroySurfaceKHR
import com.primogemstudio.engine.bindings.vulkan.utils.toFullErr
import com.primogemstudio.engine.graphics.IWindow
import com.primogemstudio.engine.graphics.data.ApplicationWindowInfo
import java.lang.foreign.MemorySegment

class VulkanWindow(
    private val rendererVk: BackendRendererVk,
    windowInfo: ApplicationWindowInfo,
    val errorCallback: (Int, String) -> Unit
) : IWindow {
    override var window: GLFWWindow
    private lateinit var surface: VkSurfaceKHR
    private var resizing: Boolean = false
    override val frameResizeCallback: MutableList<GLFWFrameBufferSizeFun> = mutableListOf()

    init {
        glfwInit()
        glfwSetErrorCallback { code, msg -> errorCallback(code, msg) }
        glfwWindowHint(GLFW_CLIENT_API, GLFW_NO_API)
        glfwWindowHint(GLFW_TRANSPARENT_FRAMEBUFFER, if (windowInfo.transparent) GL_TRUE else GL_FALSE)

        window = glfwCreateWindow(
            windowInfo.width,
            windowInfo.height,
            windowInfo.windowTitle,
            GLFWMonitor(MemorySegment.NULL),
            GLFWWindow(MemorySegment.NULL)
        )

        glfwCreateWindowSurface(rendererVk.instance, window, null).match(
            { surface = it },
            { throw IllegalStateException(toFullErr("exception.renderer.backend_vk.surface", it)) }
        )

        glfwSetFramebufferSizeCallback(window) { wi, w, h ->
            resizing = true
            frameResizeCallback.forEach { it.call(wi, w, h) }
        }
    }

    fun resizing(): Boolean = resizing
    fun resetState() {
        resizing = false
    }

    override fun close() {
        vkDestroySurfaceKHR(rendererVk.instance, surface, null)
        glfwDestroyWindow(window)
        glfwTerminate()
    }
}