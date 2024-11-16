package com.primogemstudio.engine

import com.primogemstudio.engine.jmake.JMakeProject
import com.primogemstudio.engine.utils.LoggerFactory
import java.io.File

fun main() {
    System.setProperty("org.lwjgl.harfbuzz.libname", "freetype")
    /*println(VkInstanceEngine("OpenMinecraft", "0.0.1-alpha1"))
    while (true) {
        Thread.sleep(1000)
    }*/

    val logger = LoggerFactory.getLogger()
    val proj =
        JMakeProject(File("/home/coder2/extsources/sabatest")) { d, b ->
            if (b != -1.0) logger.info(d) else logger.warn(
                d
            )
        }
    proj.prepareBuild()
    proj.build()
    proj.getTargets().forEach { println(it) }
}