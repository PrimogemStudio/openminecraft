package com.primogemstudio.engine

import com.primogemstudio.engine.interfaces.NativeMethodCache.callFunc
import com.primogemstudio.engine.loader.Platform
import java.lang.foreign.Arena.ofConfined
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.Linker
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.ADDRESS
import java.lang.foreign.ValueLayout.JAVA_INT


fun main() {
    /*System.setProperty("org.lwjgl.harfbuzz.libname", "freetype")
    val instance = VkInstanceEngine("OpenMinecraft", "0.0.1-alpha1")
    instance.vkWindow!!.mainLoop()*/

    val offHeap = ofConfined()

    val linker = Linker.nativeLinker()
    linker.downcallHandle(
        linker.defaultLookup().find("puts").get(),
        FunctionDescriptor.ofVoid(ADDRESS)
    ).invoke(offHeap.allocateUtf8String("test!"))

    val test = TestNativeCall()
    linker.downcallHandle(
        linker.upcallStub(
            test.fetchHandle(),
            FunctionDescriptor.ofVoid(JAVA_INT),
            offHeap
        ),
        FunctionDescriptor.ofVoid(JAVA_INT)
    ).invoke(55)

    /*listOf("freetype", "glfw", "meshoptimizer", "mmd", "openal", "shaderc", "stb", "xxhash", "yoga").forEach {
        Platform.load(Platform.libProvider(it))
    }*/
    Platform.init()
    callFunc("glfwInit", MemorySegment::class).address()
    val window =
        callFunc("glfwCreateWindow", MemorySegment::class, 640, 480, offHeap.allocateUtf8String("test!"), 0L, 0L)

    callFunc("glfwShowWindow", MemorySegment::class, window)

    while (!callFunc("glfwWindowShouldClose", Boolean::class, window)) {
        callFunc<Any>("glfwSwapBuffers", null, window)
        callFunc<Any>("glfwPollEvents", null)
    }

    callFunc<Any>("glfwDestroyWindow", null, window)
    callFunc("glfwTerminate", MemorySegment::class)
}