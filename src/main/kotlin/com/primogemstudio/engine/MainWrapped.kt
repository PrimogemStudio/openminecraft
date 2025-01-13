package com.primogemstudio.engine

import java.lang.foreign.Arena
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.Linker
import java.lang.foreign.ValueLayout.OfChar


fun main() {
    /*System.setProperty("org.lwjgl.harfbuzz.libname", "freetype")*/
    /*val instance = VkInstanceEngine("OpenMinecraft", "0.0.1-alpha1")
    instance.vkWindow!!.mainLoop()*/

    val offHeap = Arena.ofConfined()

    val linker = Linker.nativeLinker()
    linker.downcallHandle(
        linker.defaultLookup().find("printf").get(),
        FunctionDescriptor.ofVoid(OfChar.ADDRESS)
    ).invoke(offHeap.allocateUtf8String("test %llx %llx"))
}