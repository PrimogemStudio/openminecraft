package com.primogemstudio.engine

import com.primogemstudio.engine.interfaces.NativeMethodCache.callFunc
import com.primogemstudio.engine.loader.Platform
import java.lang.foreign.Arena.ofConfined
import java.lang.foreign.Arena.ofShared
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.Linker
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.ADDRESS
import java.lang.foreign.ValueLayout.JAVA_INT
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType

object WindowCallback {
    @JvmStatic
    fun framebufCallback(window: MemorySegment, width: Int, height: Int) {
        println("$window $width $height")
    }
}

fun interface CallbackTest {
    fun call(window: MemorySegment, width: Int, height: Int)
}

fun main() {
    /*System.setProperty("org.lwjgl.harfbuzz.libname", "freetype")
    val instance = VkInstanceEngine("OpenMinecraft", "0.0.1-alpha1")
    instance.vkWindow!!.mainLoop()*/

    val offHeap = ofConfined()

    val linker = Linker.nativeLinker()

    Platform.init()

    val rawMethod = MethodHandles.lookup().findVirtual(
        CallbackTest::class.java,
        "call",
        MethodType.methodType(
            Void.TYPE,
            MemorySegment::class.java,
            Int::class.java,
            Int::class.java
        )
    )

    val processed = MethodHandles.foldArguments(
        rawMethod,
        MethodHandles.constant(
            CallbackTest::class.java,
            CallbackTest { w: MemorySegment, a: Int, b: Int ->
                println("$w $a $b")
            }
        )
    )

    val callbackStub = linker.upcallStub(
        processed,
        FunctionDescriptor.ofVoid(ADDRESS, JAVA_INT, JAVA_INT),
        ofShared()
    )

    callFunc("glfwInit", MemorySegment::class).address()
    val window =
        callFunc("glfwCreateWindow", MemorySegment::class, 640, 480, offHeap.allocateUtf8String("test!"), 0L, 0L)

    callFunc("glfwShowWindow", MemorySegment::class, window)
    callFunc<Any>("glfwSetFramebufferSizeCallback", null, window, callbackStub)

    while (!callFunc("glfwWindowShouldClose", Boolean::class, window)) {
        callFunc<Any>("glfwSwapBuffers", null, window)
        callFunc<Any>("glfwWaitEvents", null)
    }

    callFunc<Any>("glfwDestroyWindow", null, window)
    callFunc("glfwTerminate", MemorySegment::class)
}