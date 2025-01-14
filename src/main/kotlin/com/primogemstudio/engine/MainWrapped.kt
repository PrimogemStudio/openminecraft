package com.primogemstudio.engine

import com.primogemstudio.engine.i18n.Internationalization
import com.primogemstudio.engine.logging.LoggerFactory
import java.lang.foreign.Arena
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.Linker
import java.lang.foreign.ValueLayout.OfChar


fun main() {
    /*System.setProperty("org.lwjgl.harfbuzz.libname", "freetype")
    val instance = VkInstanceEngine("OpenMinecraft", "0.0.1-alpha1")
    instance.vkWindow!!.mainLoop()*/

    val offHeap = Arena.ofConfined()

    val linker = Linker.nativeLinker()
    linker.downcallHandle(
        linker.defaultLookup().find("puts").get(),
        FunctionDescriptor.ofVoid(OfChar.ADDRESS)
    ).invoke(offHeap.allocateUtf8String("test!"))

    val la = LoggerFactory.getLogger("Test")
    la.debug(Internationalization.tr("exception.i18n.replacement.trace"))
    la.info(Internationalization.tr("exception.i18n.replacement.trace"))
    la.warn(Internationalization.tr("exception.i18n.replacement.trace"))
    la.error(Internationalization.tr("exception.i18n.replacement.trace"))
    la.fatal(Internationalization.tr("exception.i18n.replacement.trace"))
}