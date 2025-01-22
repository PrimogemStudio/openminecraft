package com.primogemstudio.engine.loader.sys

import com.primogemstudio.engine.i18n.Internationalization.tr
import com.primogemstudio.engine.loader.*
import java.nio.file.Files
import java.nio.file.Path

object OpenGLLibLoader {
    fun source(): INativeLibSource = DefaultNativeLibSource(tr("engine.nativeloader.libname", "opengl")).apply {
        when (Platform.system) {
            PlatformSystem.IOS, PlatformSystem.MacOS -> {
                push(
                    NativeLibInfo(
                        tr("engine.nativeloader.libname.system", "opengl"),
                        try {
                            Files.newInputStream(Path.of("/System/Library/Frameworks/OpenGL.framework/Versions/Current/OpenGL"))
                        } catch (_: Exception) {
                            null
                        },
                        isEmbedded = false,
                        isSystem = true
                    )
                )
            }

            PlatformSystem.Windows -> {
                push(
                    NativeLibInfo(
                        tr("engine.nativeloader.libname.system", "opengl"),
                        extName = "opengl32",
                        isEmbedded = false,
                        isSystem = true
                    )
                )
            }

            else -> {
                push(
                    NativeLibInfo(
                        tr("engine.nativeloader.libname.system", "opengl"),
                        extName = "GL",
                        isEmbedded = false,
                        isSystem = true
                    )
                )
            }
        }
    }
}