package com.primogemstudio.engine

import com.primogemstudio.engine.i18n.Internationalization
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
    println(
        linker.downcallHandle(
            linker.defaultLookup().find("printf").get(),
            FunctionDescriptor.ofVoid(OfChar.ADDRESS)
        ).invoke(offHeap.allocateUtf8String("%llx\n".repeat(16)))
    )

    println(Internationalization.tr("exception.i18n.replacement.trace"))
}