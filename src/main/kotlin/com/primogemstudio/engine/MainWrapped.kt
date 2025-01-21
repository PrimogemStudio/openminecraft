package com.primogemstudio.engine

import com.primogemstudio.engine.bindings.glfw.GLFWBaseFuncs.glfwInit
import com.primogemstudio.engine.bindings.glfw.GLFWBaseFuncs.glfwSetErrorCallback
import com.primogemstudio.engine.bindings.glfw.GLFWBaseFuncs.glfwTerminate
import com.primogemstudio.engine.bindings.glfw.GLFWMonitor
import com.primogemstudio.engine.bindings.glfw.GLFWWindow
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwCreateWindow
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwDestroyWindow
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwShowWindow
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwWindowShouldClose
import com.primogemstudio.engine.interfaces.NativeMethodCache.callFunc
import com.primogemstudio.engine.interfaces.NativeMethodCache.constructStub
import com.primogemstudio.engine.interfaces.stub.IStub
import com.primogemstudio.engine.loader.Platform
import java.lang.foreign.Arena.ofConfined
import java.lang.foreign.MemorySegment
import java.lang.invoke.MethodType

fun interface CallbackTest : IStub {
    fun call(window: MemorySegment, width: Int, height: Int)
    override fun register(): Pair<String, MethodType> = Pair(
        "call",
        MethodType.methodType(
            Void.TYPE,
            MemorySegment::class.java,
            Int::class.java,
            Int::class.java
        )
    )
}

fun main() {
    /*System.setProperty("org.lwjgl.harfbuzz.libname", "freetype")
    val instance = VkInstanceEngine("OpenMinecraft", "0.0.1-alpha1")
    instance.vkWindow!!.mainLoop()*/

    val offHeap = ofConfined()
    Platform.init()

    glfwInit()
    glfwSetErrorCallback { err, desc ->
        println("$err $desc")
    }

    val window = glfwCreateWindow(640, 480, "test!", GLFWMonitor(MemorySegment.NULL), GLFWWindow(MemorySegment.NULL))

    glfwShowWindow(window)
    callFunc(
        "glfwSetFramebufferSizeCallback",
        Unit::class,
        window,
        constructStub(CallbackTest::class, CallbackTest { w: MemorySegment, a: Int, b: Int ->
            println("$w $a $b")
        })
    )

    while (glfwWindowShouldClose(window) != 1) {
        callFunc("glfwSwapBuffers", Unit::class, window)
        callFunc("glfwPollEvents", Unit::class)
    }

    glfwDestroyWindow(window)
    glfwTerminate()
}