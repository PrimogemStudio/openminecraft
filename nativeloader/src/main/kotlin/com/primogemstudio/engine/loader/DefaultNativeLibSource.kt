package com.primogemstudio.engine.loader

import com.primogemstudio.engine.i18n.Internationalization.tr
import com.primogemstudio.engine.logging.LoggerFactory
import java.io.InputStream

private val logger = LoggerFactory.getLogger()
class DefaultNativeLibSource(private val name: String) : INativeLibSource {
    private val libStack = mutableListOf<NativeLibInfo>()
    private var embedded = true
    private var system = true

    override fun push(lib: NativeLibInfo) {
        libStack.add(lib)
    }

    override fun list(): List<NativeLibInfo> = libStack.toList()
    override fun needEmbeddedLib(e: Boolean) {
        embedded = e
    }

    override fun needSystemLib(e: Boolean) {
        system = e
    }

    override fun name(): String = name
    override fun fetch(): InputStream? =
        libStack.filter {
            logger.debug(tr("engine.nativeloader.libdetail", it.name, it.source != null))
            return@filter (embedded || !it.isEmbedded) || (system || !it.isSystem)
        }.firstOrNull()?.source
}