package com.primogemstudio.engine.bindings.glfw

import com.primogemstudio.engine.interfaces.NativeMethodCache.callFunc
import com.primogemstudio.engine.interfaces.NativeMethodCache.callVoidFunc
import com.primogemstudio.engine.interfaces.fetchCString
import com.primogemstudio.engine.interfaces.heap.HeapDouble
import com.primogemstudio.engine.interfaces.heap.HeapMutRefArray
import com.primogemstudio.engine.interfaces.heap.IHeapVar
import com.primogemstudio.engine.interfaces.stub.IStub
import java.lang.foreign.MemorySegment
import java.lang.invoke.MethodType

fun interface GLFWMouseButtonFun : IStub {
    fun call(window: GLFWWindow, button: Int, action: Int, mods: Int)
    fun call(window: MemorySegment, button: Int, action: Int, mod: Int) = call(GLFWWindow(window), button, action, mod)
    override fun register(): Pair<String, MethodType> =
        Pair(
            "call",
            MethodType.methodType(
                Void.TYPE,
                MemorySegment::class.java,
                Int::class.java,
                Int::class.java,
                Int::class.java
            )
        )
}

fun interface GLFWCursorPosFun : IStub {
    fun call(window: GLFWWindow, xpos: Double, ypos: Double)
    fun call(window: MemorySegment, xpos: Double, ypos: Double) = call(GLFWWindow(window), xpos, ypos)
    override fun register(): Pair<String, MethodType> =
        Pair(
            "call",
            MethodType.methodType(
                Void.TYPE,
                MemorySegment::class.java,
                Double::class.java,
                Double::class.java
            )
        )
}

fun interface GLFWCursorEnterFun : IStub {
    fun call(window: GLFWWindow, entered: Int)
    fun call(window: MemorySegment, entered: Int) = call(GLFWWindow(window), entered)
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

fun interface GLFWScrollFun : IStub {
    fun call(window: GLFWWindow, xoffset: Double, yoffset: Double)
    fun call(window: MemorySegment, xoffset: Double, yoffset: Double) = call(GLFWWindow(window), xoffset, yoffset)
    override fun register(): Pair<String, MethodType> =
        Pair(
            "call",
            MethodType.methodType(
                Void.TYPE,
                MemorySegment::class.java,
                Double::class.java,
                Double::class.java
            )
        )
}

fun interface GLFWKeyFun : IStub {
    fun call(window: GLFWWindow, key: Int, scancode: Int, action: Int, mods: Int)
    fun call(window: MemorySegment, key: Int, scancode: Int, action: Int, mods: Int) =
        call(GLFWWindow(window), key, scancode, action, mods)

    override fun register(): Pair<String, MethodType> =
        Pair(
            "call",
            MethodType.methodType(
                Void.TYPE,
                MemorySegment::class.java,
                Int::class.java,
                Int::class.java,
                Int::class.java,
                Int::class.java
            )
        )
}

fun interface GLFWCharFun : IStub {
    fun call(window: GLFWWindow, codepoint: Int)
    fun call(window: MemorySegment, codepoint: Int) = call(GLFWWindow(window), codepoint)
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

fun interface GLFWCharModsFun : IStub {
    fun call(window: GLFWWindow, codepoint: Int, mods: Int)
    fun call(window: MemorySegment, codepoint: Int, mods: Int) = call(GLFWWindow(window), codepoint, mods)
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

fun interface GLFWDropFun : IStub {
    fun call(window: GLFWWindow, path_count: Int, paths: Array<String>)
    fun call(window: MemorySegment, path_count: Int, paths: MemorySegment) =
        call(
            GLFWWindow(window),
            path_count,
            HeapMutRefArray(paths, path_count).value().map { it.fetchCString() }.toTypedArray()
        )

    override fun register(): Pair<String, MethodType> =
        Pair(
            "call",
            MethodType.methodType(
                Void.TYPE,
                MemorySegment::class.java,
                Int::class.java,
                MemorySegment::class.java
            )
        )
}

fun interface GLFWJoystickFun : IStub {
    fun call(jid: Int, event: Int)
    override fun register(): Pair<String, MethodType> =
        Pair(
            "call",
            MethodType.methodType(
                Void.TYPE,
                Int::class.java,
                Int::class.java
            )
        )
}

class GLFWCursor(private val data: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = data
    override fun value(): MemorySegment = data
}

object GLFWInputFuncs {
    fun glfwGetInputMode(window: GLFWWindow, mode: Int): Int =
        callFunc("glfwGetInputMode", Int::class, window, mode)

    fun glfwSetInputMode(window: GLFWWindow, mode: Int, value: Int) =
        callVoidFunc("glfwSetInputMode", window, mode, value)

    fun glfwRawMouseMotionSupported(): Int =
        callFunc("glfwRawMouseMotionSupported", Int::class)

    fun glfwGetKeyName(key: Int, scancode: Int): String =
        callFunc("glfwGetKeyName", MemorySegment::class, key, scancode).fetchCString()

    fun glfwGetKeyScancode(key: Int): Int =
        callFunc("glfwGetKeyScancode", Int::class, key)

    fun glfwGetKey(window: GLFWWindow, key: Int): Int =
        callFunc("glfwGetKey", Int::class, window, key)

    fun glfwGetMouseButton(window: GLFWWindow, button: Int): Int =
        callFunc("glfwGetMouseButton", Int::class, window, button)

    fun glfwGetCursorPos(window: GLFWWindow, xpos: HeapDouble, ypos: HeapDouble) =
        callVoidFunc("glfwGetCursorPos", window, xpos, ypos)

    fun glfwSetCursorPos(window: GLFWWindow, xpos: Double, ypos: Double) =
        callVoidFunc("glfwSetCursorPos", window, xpos, ypos)

    fun glfwCreateCursor(image: GLFWImage, xhot: Int, yhot: Int): GLFWCursor =
        GLFWCursor(callFunc("glfwCreateCursor", MemorySegment::class, image, xhot, yhot))

    fun glfwCreateStandardCursor(shape: Int): GLFWCursor =
        GLFWCursor(callFunc("glfwCreateStandardCursor", MemorySegment::class, shape))

    fun glfwDestroyCursor(cursor: GLFWCursor) =
        callVoidFunc("glfwDestroyCursor", cursor)

    fun glfwSetCursor(window: GLFWWindow, cursor: GLFWCursor) =
        callVoidFunc("glfwSetCursor", window, cursor)
}