package com.primogemstudio.engine.graphics

import com.primogemstudio.engine.graphics.data.ApplicationInfo
import com.primogemstudio.engine.types.Version

interface IRenderer {
    fun version(): Version
    fun driver(): String
    val gameInfo: ApplicationInfo
}