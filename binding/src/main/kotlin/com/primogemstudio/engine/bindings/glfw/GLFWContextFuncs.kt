package com.primogemstudio.engine.bindings.glfw

import com.primogemstudio.engine.foreign.NativeMethodCache.callFunc
import com.primogemstudio.engine.foreign.NativeMethodCache.callPointerFunc
import com.primogemstudio.engine.foreign.NativeMethodCache.callVoidFunc
import com.primogemstudio.engine.foreign.toCString
import java.lang.foreign.MemorySegment

object GLFWContextFuncs {
    fun glfwMakeContextCurrent(window: GLFWWindow) =
        callVoidFunc("glfwMakeContextCurrent", window)

    fun glfwGetCurrentContext(): GLFWWindow =
        GLFWWindow(callPointerFunc("glfwGetCurrentContext"))

    fun glfwSwapInterval(interval: Int) =
        callVoidFunc("glfwSwapInterval", interval)

    fun glfwExtensionSupported(str: String): Int =
        callFunc("glfwExtensionSupported", Int::class, str.toCString())

    fun glfwGetProcAddress(str: String): MemorySegment =
        callPointerFunc("glfwGetProcAddress", str.toCString())
}