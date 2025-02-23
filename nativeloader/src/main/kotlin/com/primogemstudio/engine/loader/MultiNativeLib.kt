package com.primogemstudio.engine.loader

import com.primogemstudio.engine.i18n.Internationalization.tr
import com.primogemstudio.engine.logging.LoggerFactory

private val logger = LoggerFactory.getLogger()

class MultiNativeLib(private val name: String, private val libs: List<INativeLib>) : INativeLib {
    override fun load(): Boolean {
        logger.info(tr("engine.nativeloader.load", name))
        var idx = 0
        for (i in libs) {
            idx++
            try {
                if (System.getProperty(
                        "openminecraft.skipSystemLib",
                        "false"
                    ) == "true" && i is SystemNativeLib && idx != libs.size
                ) continue
                if (!i.load()) continue
                logger.info(tr("engine.nativeloader.load.success", name))
                return true
            } catch (e: Throwable) {
                logger.error(tr("engine.nativeloader.load.fail", i.toString()), e)
            }
        }
        return false
    }
}
