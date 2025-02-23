package com.primogemstudio.engine.loader

import com.primogemstudio.engine.i18n.Internationalization.tr
import java.io.File

class LocalNativeLib(private val name: String, private val lib: File) : INativeLib {
    override fun load(): Boolean {
        if (!lib.exists()) return false
        System.load(lib.absolutePath)
        return true
    }

    override fun toString(): String = tr("engine.nativeloader.libname.external", name)
}