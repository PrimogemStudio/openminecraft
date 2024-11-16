package com.primogemstudio.engine

import com.primogemstudio.engine.jmake.JMakeProject
import java.io.File

fun main() {
    System.setProperty("org.lwjgl.harfbuzz.libname", "freetype")
    /*println(VkInstanceEngine("OpenMinecraft", "0.0.1-alpha1"))
    while (true) {
        Thread.sleep(1000)
    }*/

    val proj = JMakeProject(File("/home/coder2/extsources/bullet3"))
    proj.prepareBuild()
    proj.build()
    proj.findFiles()
}