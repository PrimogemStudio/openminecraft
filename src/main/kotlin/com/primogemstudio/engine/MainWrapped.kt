package com.primogemstudio.engine

import com.primogemstudio.engine.loader.Platform
import java.lang.foreign.Arena.ofConfined
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.Linker
import java.lang.foreign.MemorySegment
import java.lang.foreign.SymbolLookup
import java.lang.foreign.ValueLayout.*


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
    linker.downcallHandle(SymbolLookup.loaderLookup().find("glfwInit").get(), FunctionDescriptor.ofVoid()).invoke()
    val window = linker.downcallHandle(
        SymbolLookup.loaderLookup().find("glfwCreateWindow").get(),
        FunctionDescriptor.of(ADDRESS, JAVA_INT, JAVA_INT, ADDRESS, JAVA_LONG, JAVA_LONG)
    ).invoke(640, 480, offHeap.allocateUtf8String("test!"), 0L, 0L) as MemorySegment
    linker.downcallHandle(
        SymbolLookup.loaderLookup().find("glfwShowWindow").get(),
        FunctionDescriptor.ofVoid(ADDRESS)
    ).invoke(window)

    while (!(linker.downcallHandle(
            SymbolLookup.loaderLookup().find("glfwWindowShouldClose").get(),
            FunctionDescriptor.of(JAVA_BOOLEAN, ADDRESS)
        ).invoke(window) as Boolean)
    ) {
        linker.downcallHandle(
            SymbolLookup.loaderLookup().find("glfwSwapBuffers").get(),
            FunctionDescriptor.ofVoid(ADDRESS)
        ).invoke(window)

        linker.downcallHandle(
            SymbolLookup.loaderLookup().find("glfwPollEvents").get(),
            FunctionDescriptor.ofVoid()
        ).invoke()
    }

    linker.downcallHandle(
        SymbolLookup.loaderLookup().find("glfwDestroyWindow").get(),
        FunctionDescriptor.ofVoid(ADDRESS)
    ).invoke(window)
}