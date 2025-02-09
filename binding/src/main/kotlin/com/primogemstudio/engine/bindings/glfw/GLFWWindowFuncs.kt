package com.primogemstudio.engine.bindings.glfw

import com.primogemstudio.engine.interfaces.NativeMethodCache.callFunc
import com.primogemstudio.engine.interfaces.NativeMethodCache.callPointerFunc
import com.primogemstudio.engine.interfaces.NativeMethodCache.callVoidFunc
import com.primogemstudio.engine.interfaces.NativeMethodCache.constructStub
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.fetchString
import com.primogemstudio.engine.interfaces.heap.HeapByteArray
import com.primogemstudio.engine.interfaces.heap.HeapFloat
import com.primogemstudio.engine.interfaces.heap.HeapInt
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.interfaces.stub.IStub
import com.primogemstudio.engine.interfaces.toCString
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.ADDRESS
import java.lang.foreign.ValueLayout.JAVA_INT
import java.lang.invoke.MethodType

class GLFWWindow(data: MemorySegment) : IHeapObject(data)

data class GLFWImage(
    private val width: Int,
    private val height: Int,
    private val pixels: HeapByteArray
) : IStruct() {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(JAVA_INT, JAVA_INT, ADDRESS)
        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = LAYOUT
    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, OFFSETS[0], width)
        seg.set(JAVA_INT, OFFSETS[1], height)
        seg.set(ADDRESS, OFFSETS[2], pixels.ref())
    }
}

fun interface GLFWWindowPosFun : IStub {
    fun call(window: GLFWWindow, xpos: Int, ypos: Int)
    fun call(window: MemorySegment, xpos: Int, ypos: Int) = call(GLFWWindow(window), xpos, ypos)
    override fun register(): Pair<String, MethodType> =
        Pair(
            "call",
            MethodType.methodType(
                Void.TYPE,
                MemorySegment::class.java,
                Int::class.java,
                Int::class.java
            )
        )
}

fun interface GLFWWindowSizeFun : IStub {
    fun call(window: GLFWWindow, width: Int, height: Int)
    fun call(window: MemorySegment, width: Int, height: Int) = call(GLFWWindow(window), width, height)
    override fun register(): Pair<String, MethodType> =
        Pair(
            "call",
            MethodType.methodType(
                Void.TYPE,
                MemorySegment::class.java,
                Int::class.java,
                Int::class.java
            )
        )
}

fun interface GLFWWindowCloseFun : IStub {
    fun call(window: GLFWWindow)
    fun call(window: MemorySegment) = call(GLFWWindow(window))
    override fun register(): Pair<String, MethodType> =
        Pair(
            "call",
            MethodType.methodType(
                Void.TYPE,
                MemorySegment::class.java
            )
        )
}

fun interface GLFWWindowRefreshFun : IStub {
    fun call(window: GLFWWindow)
    fun call(window: MemorySegment) = call(GLFWWindow(window))
    override fun register(): Pair<String, MethodType> =
        Pair(
            "call",
            MethodType.methodType(
                Void.TYPE,
                MemorySegment::class.java
            )
        )
}

fun interface GLFWWindowFocusFun : IStub {
    fun call(window: GLFWWindow, focused: Int)
    fun call(window: MemorySegment, focused: Int) = call(GLFWWindow(window), focused)
    override fun register(): Pair<String, MethodType> =
        Pair(
            "call",
            MethodType.methodType(
                Void.TYPE,
                MemorySegment::class.java,
                Int::class.java
            )
        )
}

fun interface GLFWWindowIconifyFun : IStub {
    fun call(window: GLFWWindow, iconified: Int)
    fun call(window: MemorySegment, iconified: Int) = call(GLFWWindow(window), iconified)
    override fun register(): Pair<String, MethodType> =
        Pair(
            "call",
            MethodType.methodType(
                Void.TYPE,
                MemorySegment::class.java,
                Int::class.java
            )
        )
}

fun interface GLFWWindowMaximizeFun : IStub {
    fun call(window: GLFWWindow, maximized: Int)
    fun call(window: MemorySegment, maximized: Int) = call(GLFWWindow(window), maximized)
    override fun register(): Pair<String, MethodType> =
        Pair(
            "call",
            MethodType.methodType(
                Void.TYPE,
                MemorySegment::class.java,
                Int::class.java
            )
        )
}

fun interface GLFWFrameBufferSizeFun : IStub {
    fun call(window: GLFWWindow, width: Int, height: Int)
    fun call(window: MemorySegment, width: Int, height: Int) = call(GLFWWindow(window), width, height)
    override fun register(): Pair<String, MethodType> =
        Pair(
            "call",
            MethodType.methodType(
                Void.TYPE,
                MemorySegment::class.java,
                Int::class.java,
                Int::class.java
            )
        )
}

fun interface GLFWWindowContentScaleFun : IStub {
    fun call(window: GLFWWindow, xscale: Float, yscale: Float)
    fun call(window: MemorySegment, xscale: Float, yscale: Float) = call(GLFWWindow(window), xscale, yscale)
    override fun register(): Pair<String, MethodType> =
        Pair(
            "call",
            MethodType.methodType(
                Void.TYPE,
                MemorySegment::class.java,
                Float::class.java,
                Float::class.java
            )
        )
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
    const val GLFW_OPENGL_CORE_PROFILE = 0x00032001
    const val GLFW_OPENGL_COMPAT_PROFILE = 0x00032002
    const val GLFW_CONTEXT_RELEASE_BEHAVIOR = 0x00022009
    const val GLFW_CONTEXT_NO_ERROR = 0x0002200a
    const val GLFW_CONTEXT_CREATION_API = 0x0002200b
    const val GLFW_NATIVE_CONTEXT_API = 0x00036001
    const val GLFW_EGL_CONTEXT_API = 0x00036002
    const val GLFW_OSMESA_CONTEXT_API = 0x00036003
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

    fun glfwDefaultWindowHints() =
        callVoidFunc("glfwDefaultWindowHints")

    fun glfwWindowHint(hint: Int, value: Int) =
        callVoidFunc("glfwWindowHint", hint, value)
    fun glfwWindowHintString(hint: Int, value: String) =
        callVoidFunc("glfwWindowHintString", hint, value.toCString())
    fun glfwCreateWindow(
        width: Int,
        height: Int,
        title: String,
        monitor: GLFWMonitor,
        share: GLFWWindow
    ): GLFWWindow =
        GLFWWindow(callPointerFunc("glfwCreateWindow", width, height, title.toCString(), monitor, share))
    fun glfwDestroyWindow(window: GLFWWindow) =
        callVoidFunc("glfwDestroyWindow", window)

    fun glfwWindowShouldClose(window: GLFWWindow): Int =
        callFunc("glfwWindowShouldClose", Int::class, window)
    fun glfwSetWindowShouldClose(window: GLFWWindow, value: Int) =
        callVoidFunc("glfwSetWindowShouldClose", window, value)
    fun glfwGetWindowTitle(window: GLFWWindow): String =
        callPointerFunc("glfwGetWindowTitle", window).fetchString()
    fun glfwSetWindowTitle(window: GLFWWindow, title: String) =
        callVoidFunc("glfwSetWindowTitle", window, title.toCString())
    fun glfwSetWindowIcon(window: GLFWWindow, count: Int, vararg images: GLFWImage) =
        callVoidFunc("glfwSetWindowIcon", window, count, *images)
    fun glfwGetWindowPos(window: GLFWWindow, xpos: HeapInt, ypos: HeapInt) =
        callVoidFunc("glfwGetWindowPos", window, xpos, ypos)
    fun glfwSetWindowPos(window: GLFWWindow, xpos: Int, ypos: Int) =
        callVoidFunc("glfwSetWindowPos", window, xpos, ypos)
    fun glfwGetWindowSize(window: GLFWWindow, width: HeapInt, height: HeapInt) =
        callVoidFunc("glfwGetWindowSize", window, width, height)
    fun glfwSetWindowSizeLimits(
        window: GLFWWindow,
        minwidth: Int,
        minheight: Int,
        maxwidth: Int,
        maxheight: Int
    ) =
        callVoidFunc("glfwSetWindowSizeLimits", window, minwidth, minheight, maxwidth, maxheight)
    fun glfwSetWindowAspectRatio(window: GLFWWindow, numer: Int, denom: Int) =
        callVoidFunc("glfwSetWindowAspectRatio", window, numer, denom)
    fun glfwSetWindowSize(window: GLFWWindow, width: Int, height: Int) =
        callVoidFunc("glfwSetWindowSize", window, width, height)
    fun glfwGetFramebufferSize(window: GLFWWindow, width: HeapInt, height: HeapInt) =
        callVoidFunc("glfwGetFramebufferSize", window, width, height)
    fun glfwGetWindowFrameSize(
        window: GLFWWindow,
        left: HeapInt,
        top: HeapInt,
        right: HeapInt,
        bottom: HeapInt
    ) =
        callVoidFunc("glfwGetWindowFrameSize", window, left, top, right, bottom)
    fun glfwGetWindowContentScale(window: GLFWWindow, xscale: HeapFloat, yscale: HeapFloat) =
        callVoidFunc("glfwGetWindowContentScale", window, xscale, yscale)
    fun glfwGetWindowOpacity(window: GLFWWindow): Float = callFunc("glfwGetWindowOpacity", Float::class, window)
    fun glfwSetWindowOpacity(window: GLFWWindow, opacity: Float) = callVoidFunc("glfwSetWindowOpacity", window, opacity)
    fun glfwIconifyWindow(window: GLFWWindow) =
        callVoidFunc("glfwIconifyWindow", window)

    fun glfwRestoreWindow(window: GLFWWindow) =
        callVoidFunc("glfwRestoreWindow", window)

    fun glfwMaximizeWindow(window: GLFWWindow) =
        callVoidFunc("glfwMaximizeWindow", window)

    fun glfwShowWindow(window: GLFWWindow) =
        callVoidFunc("glfwShowWindow", window)

    fun glfwHideWindow(window: GLFWWindow) =
        callVoidFunc("glfwHideWindow", window)

    fun glfwFocusWindow(window: GLFWWindow) =
        callVoidFunc("glfwFocusWindow", window)

    fun glfwRequestWindowAttention(window: GLFWWindow) =
        callVoidFunc("glfwRequestWindowAttention", window)
    fun glfwGetWindowMonitor(window: GLFWWindow): GLFWMonitor =
        GLFWMonitor(callPointerFunc("glfwGetWindowMonitor", window))
    fun glfwSetWindowMonitor(
        window: GLFWWindow,
        monitor: GLFWMonitor,
        xpos: Int,
        ypos: Int,
        width: Int,
        height: Int,
        refreshRate: Int
    ) =
        callVoidFunc("glfwSetWindowMonitor", window, monitor, xpos, ypos, width, height, refreshRate)

    fun glfwGetWindowAttrib(window: GLFWWindow, attrib: Int): Int =
        callFunc("glfwGetWindowAttrib", Int::class, window, attrib)

    fun glfwSetWindowAttrib(window: GLFWWindow, attrib: Int, value: Int) =
        callVoidFunc("glfwSetWindowAttrib", window, attrib, value)

    fun glfwSetWindowUserPointer(window: GLFWWindow, pointer: MemorySegment) =
        callVoidFunc("glfwSetWindowUserPointer", window, pointer)

    fun glfwGetWindowUserPointer(window: GLFWWindow): MemorySegment =
        callPointerFunc("glfwGetWindowUserPointer", window)

    fun glfwSetWindowPosCallback(window: GLFWWindow, callback: GLFWWindowPosFun): MemorySegment =
        callPointerFunc(
            "glfwSetWindowPosCallback",
            window,
            constructStub(GLFWWindowPosFun::class, callback)
        )

    fun glfwSetWindowSizeCallback(window: GLFWWindow, callback: GLFWWindowSizeFun): MemorySegment =
        callPointerFunc(
            "glfwSetWindowSizeCallback",
            window,
            constructStub(GLFWWindowSizeFun::class, callback)
        )

    fun glfwSetWindowCloseCallback(window: GLFWWindow, callback: GLFWWindowCloseFun): MemorySegment =
        callPointerFunc(
            "glfwSetWindowCloseCallback",
            window,
            constructStub(GLFWWindowCloseFun::class, callback)
        )

    fun glfwSetWindowRefreshCallback(window: GLFWWindow, callback: GLFWWindowRefreshFun): MemorySegment =
        callPointerFunc(
            "glfwSetWindowRefreshCallback",
            window,
            constructStub(GLFWWindowRefreshFun::class, callback)
        )

    fun glfwSetWindowFocusCallback(window: GLFWWindow, callback: GLFWWindowFocusFun): MemorySegment =
        callPointerFunc(
            "glfwSetWindowFocusCallback",
            window,
            constructStub(GLFWWindowFocusFun::class, callback)
        )

    fun glfwSetWindowIconifyCallback(window: GLFWWindow, callback: GLFWWindowIconifyFun): MemorySegment =
        callPointerFunc(
            "glfwSetWindowIconifyCallback",
            window,
            constructStub(GLFWWindowIconifyFun::class, callback)
        )

    fun glfwSetFramebufferSizeCallback(window: GLFWWindow, callback: GLFWFrameBufferSizeFun): MemorySegment =
        callPointerFunc(
            "glfwSetFramebufferSizeCallback",
            window,
            constructStub(GLFWFrameBufferSizeFun::class, callback)
        )

    fun glfwSetWindowContentScaleCallback(window: GLFWWindow, callback: GLFWWindowContentScaleFun): MemorySegment =
        callPointerFunc(
            "glfwSetWindowContentScaleCallback",
            window,
            constructStub(GLFWWindowContentScaleFun::class, callback)
        )

    fun glfwPollEvents() =
        callVoidFunc("glfwPollEvents")

    fun glfwWaitEvents() =
        callVoidFunc("glfwWaitEvents")

    fun glfwWaitEventsTimeout(timeout: Double) =
        callVoidFunc("glfwWaitEventsTimeout", timeout)

    fun glfwPostEmptyEvent() =
        callVoidFunc("glfwPostEmptyEvent")

    fun glfwSwapBuffers(window: GLFWWindow) =
        callVoidFunc("glfwSwapBuffers", window)
}
