package com.primogemstudio.engine.loader.sys

import com.primogemstudio.engine.i18n.Internationalization.tr
import com.primogemstudio.engine.loader.DefaultNativeLibSource
import com.primogemstudio.engine.loader.INativeLibSource
import com.primogemstudio.engine.loader.NativeLibInfo

object OpenGLESLibLoader {
    fun source(): INativeLibSource = DefaultNativeLibSource(tr("engine.nativeloader.libname", "opengles")).apply {
        push(
            NativeLibInfo(
                tr("engine.nativeloader.libname.system", "opengles"),
                extName = "GLESv2",
                isEmbedded = false,
                isSystem = true
            )
        )
    }
}