package com.primogemstudio.engine.loader

import com.primogemstudio.engine.i18n.Internationalization.tr
import com.primogemstudio.engine.logging.LoggerFactory
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.StandardCopyOption

private val logger = LoggerFactory.getLogger()

class BundledNativeLib(private val name: String, private val source: InputStream?) : INativeLib {
    override fun load(): Boolean {
        if (source == null) {
            logger.warn(tr("engine.nativeloader.load.nofile", name))
            return false
        }

        val pathr = Files.createTempFile("openminecraftlib", System.mapLibraryName(name))
        pathr.toFile().deleteOnExit()
        Files.copy(source, pathr, StandardCopyOption.REPLACE_EXISTING)
        logger.info(tr("engine.nativeloader.load.copy", name, pathr))
        System.load(pathr.toString())
        return true
    }

    override fun toString(): String = tr("engine.nativeloader.libname.embedded", name)
}