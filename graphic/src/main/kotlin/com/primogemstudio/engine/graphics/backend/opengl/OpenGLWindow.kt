package com.primogemstudio.engine.graphics.backend.opengl

import com.primogemstudio.engine.bindings.glfw.GLFWBaseFuncs.glfwInit
import com.primogemstudio.engine.bindings.glfw.GLFWBaseFuncs.glfwSetErrorCallback
import com.primogemstudio.engine.bindings.glfw.GLFWBaseFuncs.glfwTerminate
import com.primogemstudio.engine.bindings.glfw.GLFWContextFuncs.glfwMakeContextCurrent
import com.primogemstudio.engine.bindings.glfw.GLFWFrameBufferSizeFun
import com.primogemstudio.engine.bindings.glfw.GLFWMonitor
import com.primogemstudio.engine.bindings.glfw.GLFWWindow
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.GLFW_CONTEXT_VERSION_MAJOR
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.GLFW_CONTEXT_VERSION_MINOR
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwCreateWindow
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwDestroyWindow
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwSetFramebufferSizeCallback
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwWindowHint
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
        glfwInit()
        glfwSetErrorCallback { code, msg -> errorCallback(code, msg) }
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, appInfo.reqApiVersion.major.toInt())
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, appInfo.reqApiVersion.minor.toInt())

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