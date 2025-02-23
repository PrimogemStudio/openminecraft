package com.primogemstudio.engine.loader.sys

import com.primogemstudio.engine.loader.MultiNativeLib
import com.primogemstudio.engine.loader.SystemNativeLib

object OpenGLESLoader {
    fun source(): MultiNativeLib = MultiNativeLib(
        "opengles",
        listOf(
            SystemNativeLib("opengles", "GLESv2")
        )
    )
}