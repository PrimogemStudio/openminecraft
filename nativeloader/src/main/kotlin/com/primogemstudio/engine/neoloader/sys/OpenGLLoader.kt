package com.primogemstudio.engine.neoloader.sys

import com.primogemstudio.engine.neoloader.INativeLib
import com.primogemstudio.engine.neoloader.LocalNativeLib
import com.primogemstudio.engine.neoloader.MultiNativeLib
import com.primogemstudio.engine.neoloader.SystemNativeLib
import com.primogemstudio.engine.neoloader.plat.Platform
import com.primogemstudio.engine.neoloader.plat.PlatformSystem
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