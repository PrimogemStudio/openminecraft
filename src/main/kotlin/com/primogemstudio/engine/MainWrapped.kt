package com.primogemstudio.engine

import com.primogemstudio.engine.bindings.glfw.GLFWBase.glfwInit
import com.primogemstudio.engine.bindings.glfw.GLFWBase.glfwSetErrorCallback
import com.primogemstudio.engine.bindings.glfw.GLFWBase.glfwTerminate
import com.primogemstudio.engine.interfaces.IStub
import com.primogemstudio.engine.interfaces.NativeMethodCache.callFunc
import com.primogemstudio.engine.interfaces.NativeMethodCache.constructStub
import com.primogemstudio.engine.interfaces.fetchCString
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
        println("$err ${desc.fetchCString()}")
    }

    val window =
        callFunc("glfwCreateWindow", MemorySegment::class, 640, 480, offHeap.allocateUtf8String("test!"), 0L, 0L)

    callFunc("glfwShowWindow", MemorySegment::class, window)
    callFunc(
        "glfwSetFramebufferSizeCallback",
        Unit::class,
        window,
        constructStub(CallbackTest::class, CallbackTest { w: MemorySegment, a: Int, b: Int ->
            println("$w $a $b")
        })
    )

    while (!callFunc("glfwWindowShouldClose", Boolean::class, window)) {
        callFunc("glfwSwapBuffers", Unit::class, window)
        callFunc("glfwWaitEvents", Unit::class)
    }

    callFunc("glfwDestroyWindow", Unit::class, window)
    glfwTerminate()
}