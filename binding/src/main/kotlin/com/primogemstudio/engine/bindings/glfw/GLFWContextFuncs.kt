package com.primogemstudio.engine.bindings.glfw

import com.primogemstudio.engine.interfaces.NativeMethodCache.callFunc
import com.primogemstudio.engine.interfaces.NativeMethodCache.callPointerFunc
import com.primogemstudio.engine.interfaces.NativeMethodCache.callVoidFunc
import com.primogemstudio.engine.interfaces.toCString
import java.lang.foreign.MemorySegment

class GLFWContextFuncs {
    fun glfwMakeContextCurrent(window: GLFWWindow) =
        callVoidFunc("glfwMakeContextCurren", window)

    fun glfwGetCurrentContext(): GLFWWindow =
        GLFWWindow(callPointerFunc("glfwGetCurrentContext"))

    fun glfwSwapInterval(interval: Int) =
        callVoidFunc("glfwSwapInterval", interval)

    fun glfwExtensionSupported(str: String): Int =
        callFunc("glfwExtensionSupported", Int::class, str.toCString())

    fun glfwGetProcAddress(str: String): MemorySegment =
        callPointerFunc("glfwGetProcAddress", str.toCString())
}