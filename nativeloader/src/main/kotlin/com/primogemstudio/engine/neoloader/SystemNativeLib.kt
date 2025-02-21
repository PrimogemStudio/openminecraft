package com.primogemstudio.engine.neoloader

import com.primogemstudio.engine.i18n.Internationalization.tr

class SystemNativeLib(private val name: String, private val libname: String) : INativeLib {
    override fun load(): Boolean {
        System.loadLibrary(libname)
        return true
    }

    override fun toString(): String = tr("engine.nativeloader.libname.system", name)
}