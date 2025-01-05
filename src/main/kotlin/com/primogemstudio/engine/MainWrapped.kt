package com.primogemstudio.engine

import java.lang.foreign.Linker

fun main() {
    /*System.setProperty("org.lwjgl.harfbuzz.libname", "freetype")*/
    /*val instance = VkInstanceEngine("OpenMinecraft", "0.0.1-alpha1")
    instance.vkWindow!!.mainLoop()*/

    val linker = Linker.nativeLinker()
    println(linker.defaultLookup().find("puts").get().address())
}