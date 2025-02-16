package com.primogemstudio.engine.graphics

import com.primogemstudio.engine.bindings.glfw.GLFWFrameBufferSizeFun
import com.primogemstudio.engine.bindings.glfw.GLFWWindow
import java.io.Closeable

interface IWindow : Closeable {
    val window: GLFWWindow
    val frameResizeCallback: MutableList<GLFWFrameBufferSizeFun>
}