package com.primogemstudio.engine.neoloader.sys

import com.primogemstudio.engine.neoloader.MultiNativeLib
import com.primogemstudio.engine.neoloader.SystemNativeLib

object OpenGLESLoader {
    fun source(): MultiNativeLib = MultiNativeLib(
        "opengles",
        listOf(
            SystemNativeLib("opengles", "GLESv2")
        )
    )
}