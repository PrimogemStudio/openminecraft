package com.primogemstudio.engine

import com.primogemstudio.engine.vk.VkInstanceEngine

fun main() {
    /*System.setProperty("org.lwjgl.harfbuzz.libname", "freetype")*/
    val instance = VkInstanceEngine("OpenMinecraft", "0.0.1-alpha1")
    // instance.close()
    instance.vkWindow!!.mainLoop()
}