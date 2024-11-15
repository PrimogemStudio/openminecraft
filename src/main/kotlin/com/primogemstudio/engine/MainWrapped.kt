package com.primogemstudio.engine

import com.primogemstudio.engine.vk.VkInstanceEngine

fun main() {
    System.setProperty("org.lwjgl.harfbuzz.libname", "freetype")
    println(VkInstanceEngine("OpenMinecraft", "0.0.1-alpha1"))
    while (true) {
        Thread.sleep(1000)
    }
}