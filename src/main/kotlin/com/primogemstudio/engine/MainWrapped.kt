package com.primogemstudio.engine

import com.primogemstudio.engine.bindings.glfw.GLFWBaseFuncs.glfwInit
import com.primogemstudio.engine.bindings.glfw.GLFWBaseFuncs.glfwSetErrorCallback
import com.primogemstudio.engine.bindings.glfw.GLFWBaseFuncs.glfwTerminate
import com.primogemstudio.engine.bindings.glfw.GLFWMonitor
import com.primogemstudio.engine.bindings.glfw.GLFWWindow
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwCreateWindow
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwDestroyWindow
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwSetFramebufferSizeCallback
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwShowWindow
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwSwapBuffers
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwWaitEvents
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwWindowShouldClose
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

    val window = glfwCreateWindow(
        640,
        480,
        "test!",
        GLFWMonitor(MemorySegment.NULL),
        GLFWWindow(MemorySegment.NULL)
    )

    glfwShowWindow(window)
    glfwSetFramebufferSizeCallback(window) { _, width, height ->
        println("$width $height")
    }

    while (glfwWindowShouldClose(window) != 1) {
        glfwSwapBuffers(window)
        glfwWaitEvents()
    }

    glfwDestroyWindow(window)
    glfwTerminate()
}