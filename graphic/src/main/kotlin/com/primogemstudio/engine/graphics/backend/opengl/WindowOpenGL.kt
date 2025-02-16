package com.primogemstudio.engine.graphics.backend.opengl

import com.primogemstudio.engine.bindings.glfw.GLFWBaseFuncs.glfwInit
import com.primogemstudio.engine.bindings.glfw.GLFWBaseFuncs.glfwSetErrorCallback
import com.primogemstudio.engine.bindings.glfw.GLFWWindow
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.GLFW_CONTEXT_VERSION_MAJOR
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.GLFW_CONTEXT_VERSION_MINOR
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwWindowHint
import com.primogemstudio.engine.graphics.IWindow
import com.primogemstudio.engine.graphics.data.ApplicationInfo

class WindowOpenGL(
    appInfo: ApplicationInfo,
    val errorCallback: (Int, String) -> Unit
) : IWindow {
    override lateinit var window: GLFWWindow

    init {
        glfwInit()
        glfwSetErrorCallback { code, msg -> errorCallback(code, msg) }
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, appInfo.reqApiVersion.major.toInt())
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, appInfo.reqApiVersion.minor.toInt())
    }
}