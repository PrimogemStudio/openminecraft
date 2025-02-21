package com.primogemstudio.engine.neoloader

import com.primogemstudio.engine.i18n.Internationalization.tr
import com.primogemstudio.engine.logging.LoggerFactory

private val logger = LoggerFactory.getLogger()

class MultiNativeLib(private val name: String, private val libs: List<INativeLib>) : INativeLib {
    override fun load(): Boolean {
        logger.info(tr("engine.nativeloader.load", name))
        for (i in libs) {
            try {
                if (!i.load()) continue
                return true
            } catch (e: Exception) {
                logger.error(tr("engine.nativeloader.load.fail", i.toString()), e)
            }
        }
        return false
    }
}