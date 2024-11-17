package com.primogemstudio.engine

import com.primogemstudio.engine.vk.shader.ShaderCompiler

fun main() {
    System.setProperty("org.lwjgl.harfbuzz.libname", "freetype")
    /*val instance = VkInstanceEngine("OpenMinecraft", "0.0.1-alpha1")
    instance.close()*/
    // instance.vkWindow!!.mainLoop()

    val comp = ShaderCompiler()
    println(comp)

    /*val logger = LoggerFactory.getLogger()
    val projb =
        JMakeProject(File("/home/coder2/extsources/bullet3"), null, listOf(), Toolchain.CLANG) { d, b ->
            if (b != -1.0) logger.info("${(b * 100).toInt()}% $d") else logger.warn(
                d
            )
        }
    projb.build()

    val proj =
        JMakeProject(
            File("/home/coder2/extsources/sabatest"),
            File("/home/coder2/temp.so"),
            projb.getTargets(),
            Toolchain.CLANG
        ) { d, b ->
            if (b != -1.0) logger.info("${(b * 100).toInt()}% $d") else logger.warn(
                d
            )
        }
    proj.build()
    proj.getTargets().forEach { println(it) }*/
}