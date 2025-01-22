package com.primogemstudio.engine.loader.sys

import com.primogemstudio.engine.i18n.Internationalization.tr
import com.primogemstudio.engine.loader.*

object OSMesaLibLoader {
    fun source(): INativeLibSource = DefaultNativeLibSource(tr("engine.nativeloader.libname", "osmesa")).apply {
        when (Platform.system) {
            PlatformSystem.IOS, PlatformSystem.MacOS -> {
                push(
                    NativeLibInfo(
                        tr("engine.nativeloader.libname.system", "osmesa"),
                        extName = "OSMesa.8",
                        isEmbedded = false,
                        isSystem = true
                    )
                )
            }

            PlatformSystem.Windows -> {
                push(
                    NativeLibInfo(
                        tr("engine.nativeloader.libname.system", "osmesa"),
                        extName = "OSMesa",
                        isEmbedded = false,
                        isSystem = true
                    )
                )
            }

            else -> {
                push(
                    NativeLibInfo(
                        tr("engine.nativeloader.libname.system", "osmesa"),
                        extName = "OSMesa",
                        isEmbedded = false,
                        isSystem = true
                    )
                )
            }
        }
    }
}