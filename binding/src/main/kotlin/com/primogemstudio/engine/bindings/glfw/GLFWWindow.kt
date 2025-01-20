package com.primogemstudio.engine.bindings.glfw

import com.primogemstudio.engine.interfaces.NativeMethodCache.callFunc
import com.primogemstudio.engine.interfaces.genCString
import com.primogemstudio.engine.interfaces.heap.IHeapVar
import java.lang.foreign.MemorySegment

class GLFWWindow(private val data: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = data
    override fun value(): MemorySegment = data
}

object GLFWWindowFuncs {
    const val GLFW_FOCUSED = 0x00020001
    const val GLFW_ICONIFIED = 0x00020002
    const val GLFW_RESIZEABLE = 0x00020003
    const val GLFW_VISIBLE = 0x00020004
    const val GLFW_DECORATED = 0x00020005
    const val GLFW_AUTO_ICONIFY = 0x00020006
    const val GLFW_FLOATING = 0x00020007
    const val GLFW_MAXIMIZED = 0x00020008
    const val GLFW_CENTER_CURSOR = 0x00020009
    const val GLFW_TRANSPARENT_FRAMEBUFFER = 0x0002000a
    const val GLFW_HOVERED = 0x0002000b
    const val GLFW_FOCUS_ON_SHOW = 0x0002000c
    const val GLFW_MOUSE_PASSTHROUGH = 0x0002000d
    const val GLFW_POSITION_X = 0x0002000e
    const val GLFW_POSITION_Y = 0x0002000f
    const val GLFW_RED_BITS = 0x00021001
    const val GLFW_GREEN_BITS = 0x00021002
    const val GLFW_BLUE_BITS = 0x00021003
    const val GLFW_ALPHA_BITS = 0x00021004
    const val GLFW_DEPTH_BITS = 0x00021005
    const val GLFW_STENCIL_BITS = 0x00021006
    const val GLFW_ACCUM_RED_BITS = 0x00021007
    const val GLFW_ACCUM_GREEN_BITS = 0x00021008
    const val GLFW_ACCUM_BLUE_BITS = 0x00021009
    const val GLFW_ACCUM_ALPHA_BITS = 0x0002100a
    const val GLFW_AUX_BUFFERS = 0x0002100b
    const val GLFW_STEREO = 0x0002100c
    const val GLFW_SAMPLES = 0x0002100d
    const val GLFW_SRGB_CAPABLE = 0x0002100e
    const val GLFW_REFRESH_RATE = 0x0002100f
    const val GLFW_DOUBLEBUFFER = 0x00021010
    const val GLFW_CLIENT_API = 0x00022001
    const val GLFW_CONTEXT_VERSION_MAJOR = 0x00022002
    const val GLFW_CONTEXT_VERSION_MINOR = 0x00022003
    const val GLFW_CONTEXT_REVISION = 0x00022004
    const val GLFW_CONTEXT_ROBUSTNESS = 0x00022005
    const val GLFW_OPENGL_FORWARD_COMPAT = 0x00022006
    const val GLFW_CONTEXT_DEBUG = 0x00022007
    const val GLFW_OPENGL_DEBUG_CONTEXT = GLFW_CONTEXT_DEBUG
    const val GLFW_OPENGL_PROFILE = 0x00022008
    const val GLFW_CONTEXT_RELEASE_BEHAVIOR = 0x00022009
    const val GLFW_CONTEXT_NO_ERROR = 0x0002200a
    const val GLFW_CONTEXT_CREATION_API = 0x0002200b
    const val GLFW_SCALE_TO_MONITOR = 0x0002200c
    const val GLFW_SCALE_FRAMEBUFFER = 0x0002200d
    const val GLFW_COCOA_RETINA_FRAMEBUFFER = 0x00023001
    const val GLFW_COCOA_FRAME_NAME = 0x00023002
    const val GLFW_COCOA_GRAPHICS_SWITCHING = 0x00023003
    const val GLFW_X11_CLASS_NAME = 0x00024001
    const val GLFW_X11_INSTANCE_NAME = 0x00024002
    const val GLFW_WIN32_KEYBOARD_MENU = 0x00025001
    const val GLFW_WIN32_SHOWDEFAULT = 0x00025002
    const val GLFW_WAYLAND_APP_ID = 0x00026001

    fun glfwDefaultWindowHints() = callFunc("glfwDefaultWindowHints", Unit::class)
    fun glfwWindowHint(hint: Int, value: Int) = callFunc("glfwWindowHint", Unit::class, hint, value)
    fun glfwWindowHintString(hint: Int, value: String) =
        callFunc("glfwWindowHintString", Unit::class, hint, genCString(value))

    fun glfwCreateWindow(
        width: Int,
        height: Int,
        title: String,
        monitor: MemorySegment,
        share: MemorySegment
    ): GLFWWindow =
        GLFWWindow(callFunc("glfwCreateWindow", MemorySegment::class, width, height, genCString(title), monitor, share))
}