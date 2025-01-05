package com.primogemstudio.engine

import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.Linker
import java.lang.foreign.ValueLayout.OfChar

fun main() {
    /*System.setProperty("org.lwjgl.harfbuzz.libname", "freetype")*/
    /*val instance = VkInstanceEngine("OpenMinecraft", "0.0.1-alpha1")
    instance.vkWindow!!.mainLoop()*/

    val linker = Linker.nativeLinker()
    println(
        linker.downcallHandle(
            linker.defaultLookup().find("sinf").get(),
            FunctionDescriptor.of(OfChar.JAVA_FLOAT, OfChar.JAVA_FLOAT)
        ).invoke(0.5f)
    )
}