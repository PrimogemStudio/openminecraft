package com.primogemstudio.engine.loader.sys

import com.primogemstudio.engine.loader.INativeLib
import com.primogemstudio.engine.loader.LocalNativeLib
import com.primogemstudio.engine.loader.MultiNativeLib
import com.primogemstudio.engine.loader.SystemNativeLib
import com.primogemstudio.engine.loader.plat.Platform
import com.primogemstudio.engine.loader.plat.PlatformSystem
import java.io.File

object OpenGLLoader {
    fun source(): MultiNativeLib = MultiNativeLib(
        "opengl",
        mutableListOf<INativeLib>().apply {
            when (Platform.system) {
                PlatformSystem.IOS, PlatformSystem.MacOS -> add(
                    LocalNativeLib(
                        "opengl",
                        File("/System/Library/Frameworks/OpenGL.framework/Versions/Current/OpenGL")
                    )
                )

                PlatformSystem.Windows -> add(SystemNativeLib("opengl", "opengl32"))
                else -> add(SystemNativeLib("opengl", "GL"))
            }
        }
    )
}