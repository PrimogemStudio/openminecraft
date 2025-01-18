package com.primogemstudio.engine

import com.primogemstudio.engine.bindings.GLFW
import com.primogemstudio.engine.interfaces.IStub
import com.primogemstudio.engine.interfaces.NativeMethodCache.callFunc
import com.primogemstudio.engine.interfaces.NativeMethodCache.constructStub
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

@OptIn(ExperimentalStdlibApi::class)
fun main() {
    /*System.setProperty("org.lwjgl.harfbuzz.libname", "freetype")
    val instance = VkInstanceEngine("OpenMinecraft", "0.0.1-alpha1")
    instance.vkWindow!!.mainLoop()*/

    val offHeap = ofConfined()
    Platform.init()

    println(GLFW.glfwInit())
    println(GLFW.glfwGetVersionString())

    val window =
        callFunc("glfwCreateWindow", MemorySegment::class, 640, 480, offHeap.allocateUtf8String("test!"), 0L, 0L)

    callFunc("glfwShowWindow", MemorySegment::class, window)
    callFunc<Any>(
        "glfwSetFramebufferSizeCallback",
        null,
        window,
        constructStub(CallbackTest::class, CallbackTest { w: MemorySegment, a: Int, b: Int ->
            println("$w $a $b")
        })
    )

    while (!callFunc("glfwWindowShouldClose", Boolean::class, window)) {
        callFunc<Any>("glfwSwapBuffers", null, window)
        callFunc<Any>("glfwWaitEvents", null)
    }

    callFunc<Any>("glfwDestroyWindow", null, window)
    callFunc("glfwTerminate", MemorySegment::class)
}