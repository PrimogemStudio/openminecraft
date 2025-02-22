package com.primogemstudio.engine.graphics.backend.opengl

import com.primogemstudio.engine.bindings.glfw.GLFWBaseFuncs.glfwInit
import com.primogemstudio.engine.bindings.glfw.GLFWBaseFuncs.glfwSetErrorCallback
import com.primogemstudio.engine.bindings.glfw.GLFWBaseFuncs.glfwTerminate
import com.primogemstudio.engine.bindings.glfw.GLFWContextFuncs.glfwMakeContextCurrent
import com.primogemstudio.engine.bindings.glfw.GLFWFrameBufferSizeFun
import com.primogemstudio.engine.bindings.glfw.GLFWMonitor
import com.primogemstudio.engine.bindings.glfw.GLFWWindow
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.GLFW_CLIENT_API
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.GLFW_CONTEXT_CREATION_API
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.GLFW_CONTEXT_VERSION_MAJOR
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.GLFW_CONTEXT_VERSION_MINOR
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.GLFW_NATIVE_CONTEXT_API
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.GLFW_OPENGL_API
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.GLFW_TRANSPARENT_FRAMEBUFFER
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwCreateWindow
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwDestroyWindow
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwSetFramebufferSizeCallback
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwWindowHint
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.GL_FALSE
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.GL_TRUE
import com.primogemstudio.engine.graphics.IWindow
import com.primogemstudio.engine.graphics.data.ApplicationInfo
import com.primogemstudio.engine.graphics.data.ApplicationWindowInfo
import java.lang.foreign.MemorySegment

class OpenGLWindow(
    appInfo: ApplicationInfo,
    windowInfo: ApplicationWindowInfo,
    val errorCallback: (Int, String) -> Unit
) : IWindow {
    override var window: GLFWWindow
    override val frameResizeCallback: MutableList<GLFWFrameBufferSizeFun> = mutableListOf()

    init {
        // glfwInitHint(GLFW_PLATFORM, GLFW_PLATFORM_X11)
        glfwInit()
        glfwSetErrorCallback { code, msg -> errorCallback(code, msg) }
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, appInfo.reqApiVersion.major.toInt())
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, appInfo.reqApiVersion.minor.toInt())
        glfwWindowHint(GLFW_TRANSPARENT_FRAMEBUFFER, if (windowInfo.transparent) GL_TRUE else GL_FALSE)
        glfwWindowHint(GLFW_CONTEXT_CREATION_API, GLFW_NATIVE_CONTEXT_API)
        glfwWindowHint(GLFW_CLIENT_API, GLFW_OPENGL_API)

        window = glfwCreateWindow(
            windowInfo.width,
            windowInfo.height,
            windowInfo.windowTitle,
            GLFWMonitor(MemorySegment.NULL),
            GLFWWindow(MemorySegment.NULL)
        )
        glfwMakeContextCurrent(window)

        glfwSetFramebufferSizeCallback(window) { wi, w, h -> frameResizeCallback.forEach { it.call(wi, w, h) } }
    }

    override fun close() {
        glfwDestroyWindow(window)
        glfwTerminate()
    }
}