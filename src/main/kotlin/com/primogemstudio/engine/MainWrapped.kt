package com.primogemstudio.engine

import com.primogemstudio.engine.i18n.Internationalization
import com.primogemstudio.engine.logging.LoggerFactory
import java.lang.foreign.Arena.ofConfined
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.Linker
import java.lang.foreign.ValueLayout.ADDRESS
import java.lang.foreign.ValueLayout.JAVA_INT


fun main() {
    /*System.setProperty("org.lwjgl.harfbuzz.libname", "freetype")
    val instance = VkInstanceEngine("OpenMinecraft", "0.0.1-alpha1")
    instance.vkWindow!!.mainLoop()*/

    val offHeap = ofConfined()

    val linker = Linker.nativeLinker()
    linker.downcallHandle(
        linker.defaultLookup().find("puts").get(),
        FunctionDescriptor.ofVoid(ADDRESS)
    ).invoke(offHeap.allocateUtf8String("test!"))

    val la = LoggerFactory.getLogger("Test")
    la.debug(Internationalization.tr("exception.i18n.replacement.trace"))
    la.info(Internationalization.tr("exception.i18n.replacement.trace"))
    la.warn(Internationalization.tr("exception.i18n.replacement.trace"))
    la.error(Internationalization.tr("exception.i18n.replacement.trace"))
    la.fatal(Internationalization.tr("exception.i18n.replacement.trace"))

    val test = TestNativeCall()
    linker.downcallHandle(
        linker.upcallStub(
            test.fetchHandle(),
            FunctionDescriptor.ofVoid(JAVA_INT),
            offHeap
        ),
        FunctionDescriptor.ofVoid(JAVA_INT)
    ).invoke(55)
}
