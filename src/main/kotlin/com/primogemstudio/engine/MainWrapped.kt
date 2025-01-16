package com.primogemstudio.engine

import com.primogemstudio.engine.loader.Platform
import java.lang.foreign.Arena.ofConfined
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.Linker
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

    Platform.load(Platform.libProvider("glfw"))
}