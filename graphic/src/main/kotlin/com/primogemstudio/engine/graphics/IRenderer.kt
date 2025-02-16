package com.primogemstudio.engine.graphics

import com.primogemstudio.engine.graphics.data.ApplicationInfo
import com.primogemstudio.engine.graphics.data.ApplicationWindowInfo
import com.primogemstudio.engine.types.Version
import java.io.Closeable

interface IRenderer : Closeable {
    fun version(): Version
    fun driver(): String
    val gameInfo: ApplicationInfo
    val windowInfo: ApplicationWindowInfo
    val window: IWindow
}