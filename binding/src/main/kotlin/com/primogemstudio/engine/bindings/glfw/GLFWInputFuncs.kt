package com.primogemstudio.engine.bindings.glfw

import com.primogemstudio.engine.interfaces.NativeMethodCache.callFunc
import com.primogemstudio.engine.interfaces.NativeMethodCache.callPointerFunc
import com.primogemstudio.engine.interfaces.NativeMethodCache.callVoidFunc
import com.primogemstudio.engine.interfaces.fetchString
import com.primogemstudio.engine.interfaces.heap.*
import com.primogemstudio.engine.interfaces.stub.IStub
import com.primogemstudio.engine.interfaces.toCString
import com.primogemstudio.engine.interfaces.toPointerArray
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
            paths.toPointerArray(path_count).map { it.fetchString() }.toTypedArray()
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

class GLFWCursor(data: MemorySegment) : IHeapObject(data)
class GLFWGamepadState(data: MemorySegment) : IHeapObject(data)

object GLFWInputFuncs {
    const val GLFW_RELEASE = 0
    const val GLFW_PRESS = 1
    const val GLFW_REPEAT = 2
    const val GLFW_KEY_UNKNOWN = -1

    const val GLFW_GAMEPAD_AXIS_LEFT_Y = 1
    const val GLFW_GAMEPAD_AXIS_RIGHT_X = 2
    const val GLFW_GAMEPAD_AXIS_RIGHT_Y = 3
    const val GLFW_GAMEPAD_AXIS_LEFT_TRIGGER = 4
    const val GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER = 5
    const val GLFW_GAMEPAD_AXIS_LAST = GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER
    const val GLFW_GAMEPAD_BUTTON_A = 0
    const val GLFW_GAMEPAD_BUTTON_B = 1
    const val GLFW_GAMEPAD_BUTTON_X = 2
    const val GLFW_GAMEPAD_BUTTON_Y = 3
    const val GLFW_GAMEPAD_BUTTON_LEFT_BUMPER = 4
    const val GLFW_GAMEPAD_BUTTON_RIGHT_BUMPER = 5
    const val GLFW_GAMEPAD_BUTTON_BACK = 6
    const val GLFW_GAMEPAD_BUTTON_START = 7
    const val GLFW_GAMEPAD_BUTTON_GUIDE = 8
    const val GLFW_GAMEPAD_BUTTON_LEFT_THUMB = 9
    const val GLFW_GAMEPAD_BUTTON_RIGHT_THUMB = 10
    const val GLFW_GAMEPAD_BUTTON_DPAD_UP = 11
    const val GLFW_GAMEPAD_BUTTON_DPAD_RIGHT = 12
    const val GLFW_GAMEPAD_BUTTON_DPAD_DOWN = 13
    const val GLFW_GAMEPAD_BUTTON_DPAD_LEFT = 14
    const val GLFW_GAMEPAD_BUTTON_LAST = GLFW_GAMEPAD_BUTTON_DPAD_LEFT
    const val GLFW_GAMEPAD_BUTTON_CROSS = GLFW_GAMEPAD_BUTTON_A
    const val GLFW_GAMEPAD_BUTTON_CIRCLE = GLFW_GAMEPAD_BUTTON_B
    const val GLFW_GAMEPAD_BUTTON_SQUARE = GLFW_GAMEPAD_BUTTON_X
    const val GLFW_GAMEPAD_BUTTON_TRIANGLE = GLFW_GAMEPAD_BUTTON_Y
    const val GLFW_HAT_CENTERED = 0
    const val GLFW_HAT_UP = 1
    const val GLFW_HAT_RIGHT = 2
    const val GLFW_HAT_DOWN = 4
    const val GLFW_HAT_LEFT = 8
    const val GLFW_HAT_RIGHT_UP = GLFW_HAT_RIGHT or GLFW_HAT_UP
    const val GLFW_HAT_RIGHT_DOWN = GLFW_HAT_RIGHT or GLFW_HAT_DOWN
    const val GLFW_HAT_LEFT_UP = GLFW_HAT_LEFT or GLFW_HAT_UP
    const val GLFW_HAT_LEFT_DOWN = GLFW_HAT_LEFT or GLFW_HAT_DOWN
    const val GLFW_JOYSTICK_1 = 0
    const val GLFW_JOYSTICK_2 = 1
    const val GLFW_JOYSTICK_3 = 2
    const val GLFW_JOYSTICK_4 = 3
    const val GLFW_JOYSTICK_5 = 4
    const val GLFW_JOYSTICK_6 = 5
    const val GLFW_JOYSTICK_7 = 6
    const val GLFW_JOYSTICK_8 = 7
    const val GLFW_JOYSTICK_9 = 8
    const val GLFW_JOYSTICK_10 = 9
    const val GLFW_JOYSTICK_11 = 10
    const val GLFW_JOYSTICK_12 = 11
    const val GLFW_JOYSTICK_13 = 12
    const val GLFW_JOYSTICK_14 = 13
    const val GLFW_JOYSTICK_15 = 14
    const val GLFW_JOYSTICK_16 = 15
    const val GLFW_JOYSTICK_LAST = GLFW_JOYSTICK_16
    const val GLFW_KEY_SPACE = 32
    const val GLFW_KEY_APOSTROPHE = 39
    const val GLFW_KEY_COMMA = 44
    const val GLFW_KEY_MINUS = 45
    const val GLFW_KEY_PERIOD = 46
    const val GLFW_KEY_SLASH = 47
    const val GLFW_KEY_0 = 48
    const val GLFW_KEY_1 = 49
    const val GLFW_KEY_2 = 50
    const val GLFW_KEY_3 = 51
    const val GLFW_KEY_4 = 52
    const val GLFW_KEY_5 = 53
    const val GLFW_KEY_6 = 54
    const val GLFW_KEY_7 = 55
    const val GLFW_KEY_8 = 56
    const val GLFW_KEY_9 = 57
    const val GLFW_KEY_SEMICOLON = 59
    const val GLFW_KEY_EQUAL = 61
    const val GLFW_KEY_A = 65
    const val GLFW_KEY_B = 66
    const val GLFW_KEY_C = 67
    const val GLFW_KEY_D = 68
    const val GLFW_KEY_E = 69
    const val GLFW_KEY_F = 70
    const val GLFW_KEY_G = 71
    const val GLFW_KEY_H = 72
    const val GLFW_KEY_I = 73
    const val GLFW_KEY_J = 74
    const val GLFW_KEY_K = 75
    const val GLFW_KEY_L = 76
    const val GLFW_KEY_M = 77
    const val GLFW_KEY_N = 78
    const val GLFW_KEY_O = 79
    const val GLFW_KEY_P = 80
    const val GLFW_KEY_Q = 81
    const val GLFW_KEY_R = 82
    const val GLFW_KEY_S = 83
    const val GLFW_KEY_T = 84
    const val GLFW_KEY_U = 85
    const val GLFW_KEY_V = 86
    const val GLFW_KEY_W = 87
    const val GLFW_KEY_X = 88
    const val GLFW_KEY_Y = 89
    const val GLFW_KEY_Z = 90
    const val GLFW_KEY_LEFT_BRACKET = 91
    const val GLFW_KEY_BACKSLASH = 92
    const val GLFW_KEY_RIGHT_BRACKET = 93
    const val GLFW_KEY_GRAVE_ACCENT = 96
    const val GLFW_KEY_WORLD_1 = 161
    const val GLFW_KEY_WORLD_2 = 162
    const val GLFW_KEY_ESCAPE = 256
    const val GLFW_KEY_ENTER = 257
    const val GLFW_KEY_TAB = 258
    const val GLFW_KEY_BACKSPACE = 259
    const val GLFW_KEY_INSERT = 260
    const val GLFW_KEY_DELETE = 261
    const val GLFW_KEY_RIGHT = 262
    const val GLFW_KEY_LEFT = 263
    const val GLFW_KEY_DOWN = 264
    const val GLFW_KEY_UP = 265
    const val GLFW_KEY_PAGE_UP = 266
    const val GLFW_KEY_PAGE_DOWN = 267
    const val GLFW_KEY_HOME = 268
    const val GLFW_KEY_END = 269
    const val GLFW_KEY_CAPS_LOCK = 280
    const val GLFW_KEY_SCROLL_LOCK = 281
    const val GLFW_KEY_NUM_LOCK = 282
    const val GLFW_KEY_PRINT_SCREEN = 283
    const val GLFW_KEY_PAUSE = 284
    const val GLFW_KEY_F1 = 290
    const val GLFW_KEY_F2 = 291
    const val GLFW_KEY_F3 = 292
    const val GLFW_KEY_F4 = 293
    const val GLFW_KEY_F5 = 294
    const val GLFW_KEY_F6 = 295
    const val GLFW_KEY_F7 = 296
    const val GLFW_KEY_F8 = 297
    const val GLFW_KEY_F9 = 298
    const val GLFW_KEY_F10 = 299
    const val GLFW_KEY_F11 = 300
    const val GLFW_KEY_F12 = 301
    const val GLFW_KEY_F13 = 302
    const val GLFW_KEY_F14 = 303
    const val GLFW_KEY_F15 = 304
    const val GLFW_KEY_F16 = 305
    const val GLFW_KEY_F17 = 306
    const val GLFW_KEY_F18 = 307
    const val GLFW_KEY_F19 = 308
    const val GLFW_KEY_F20 = 309
    const val GLFW_KEY_F21 = 310
    const val GLFW_KEY_F22 = 311
    const val GLFW_KEY_F23 = 312
    const val GLFW_KEY_F24 = 313
    const val GLFW_KEY_F25 = 314
    const val GLFW_KEY_KP_0 = 320
    const val GLFW_KEY_KP_1 = 321
    const val GLFW_KEY_KP_2 = 322
    const val GLFW_KEY_KP_3 = 323
    const val GLFW_KEY_KP_4 = 324
    const val GLFW_KEY_KP_5 = 325
    const val GLFW_KEY_KP_6 = 326
    const val GLFW_KEY_KP_7 = 327
    const val GLFW_KEY_KP_8 = 328
    const val GLFW_KEY_KP_9 = 329
    const val GLFW_KEY_KP_DECIMAL = 330
    const val GLFW_KEY_KP_DIVIDE = 331
    const val GLFW_KEY_KP_MULTIPLY = 332
    const val GLFW_KEY_KP_SUBTRACT = 333
    const val GLFW_KEY_KP_ADD = 334
    const val GLFW_KEY_KP_ENTER = 335
    const val GLFW_KEY_KP_EQUAL = 336
    const val GLFW_KEY_LEFT_SHIFT = 340
    const val GLFW_KEY_LEFT_CONTROL = 341
    const val GLFW_KEY_LEFT_ALT = 342
    const val GLFW_KEY_LEFT_SUPER = 343
    const val GLFW_KEY_RIGHT_SHIFT = 344
    const val GLFW_KEY_RIGHT_CONTROL = 345
    const val GLFW_KEY_RIGHT_ALT = 346
    const val GLFW_KEY_RIGHT_SUPER = 347
    const val GLFW_KEY_MENU = 348
    const val GLFW_KEY_LAST = GLFW_KEY_MENU
    const val GLFW_MOD_SHIFT = 0x0001
    const val GLFW_MOD_CONTROL = 0x0002
    const val GLFW_MOD_ALT = 0x0004
    const val GLFW_MOD_SUPER = 0x0008
    const val GLFW_MOD_CAPS_LOCK = 0x0010
    const val GLFW_MOD_NUM_LOCK = 0x0020
    const val GLFW_MOUSE_BUTTON_1 = 0
    const val GLFW_MOUSE_BUTTON_2 = 1
    const val GLFW_MOUSE_BUTTON_3 = 2
    const val GLFW_MOUSE_BUTTON_4 = 3
    const val GLFW_MOUSE_BUTTON_5 = 4
    const val GLFW_MOUSE_BUTTON_6 = 5
    const val GLFW_MOUSE_BUTTON_7 = 6
    const val GLFW_MOUSE_BUTTON_8 = 7
    const val GLFW_MOUSE_BUTTON_LAST = GLFW_MOUSE_BUTTON_8
    const val GLFW_MOUSE_BUTTON_LEFT = GLFW_MOUSE_BUTTON_1
    const val GLFW_MOUSE_BUTTON_RIGHT = GLFW_MOUSE_BUTTON_2
    const val GLFW_MOUSE_BUTTON_MIDDLE = GLFW_MOUSE_BUTTON_3
    const val GLFW_ARROW_CURSOR = 0x00036001
    const val GLFW_IBEAM_CURSOR = 0x00036002
    const val GLFW_CROSSHAIR_CURSOR = 0x00036003
    const val GLFW_POINTING_HAND_CURSOR = 0x00036004
    const val GLFW_RESIZE_EW_CURSOR = 0x00036005
    const val GLFW_RESIZE_NS_CURSOR = 0x00036006
    const val GLFW_RESIZE_NWSE_CURSOR = 0x00036007
    const val GLFW_RESIZE_NESW_CURSOR = 0x00036008
    const val GLFW_RESIZE_ALL_CURSOR = 0x00036009
    const val GLFW_NOT_ALLOWED_CURSOR = 0x0003600A
    const val GLFW_HRESIZE_CURSOR = GLFW_RESIZE_EW_CURSOR
    const val GLFW_VRESIZE_CURSOR = GLFW_RESIZE_NS_CURSOR
    const val GLFW_HAND_CURSOR = GLFW_POINTING_HAND_CURSOR

    fun glfwGetInputMode(window: GLFWWindow, mode: Int): Int =
        callFunc("glfwGetInputMode", Int::class, window, mode)

    fun glfwSetInputMode(window: GLFWWindow, mode: Int, value: Int) =
        callVoidFunc("glfwSetInputMode", window, mode, value)

    fun glfwRawMouseMotionSupported(): Int =
        callFunc("glfwRawMouseMotionSupported", Int::class)

    fun glfwGetKeyName(key: Int, scancode: Int): String =
        callPointerFunc("glfwGetKeyName", key, scancode).fetchString()

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
        GLFWCursor(callPointerFunc("glfwCreateCursor", image, xhot, yhot))

    fun glfwCreateStandardCursor(shape: Int): GLFWCursor =
        GLFWCursor(callPointerFunc("glfwCreateStandardCursor", shape))

    fun glfwDestroyCursor(cursor: GLFWCursor) =
        callVoidFunc("glfwDestroyCursor", cursor)

    fun glfwSetCursor(window: GLFWWindow, cursor: GLFWCursor) =
        callVoidFunc("glfwSetCursor", window, cursor)

    fun glfwSetKeyCallback(window: GLFWWindow, callback: GLFWKeyFun): MemorySegment =
        callPointerFunc("glfwSetKeyCallback", window, callback)

    fun glfwSetCharCallback(window: GLFWWindow, callback: GLFWCharFun): MemorySegment =
        callPointerFunc("glfwSetCharCallback", window, callback)

    fun glfwSetCharModsCallback(window: GLFWWindow, callback: GLFWCharFun): MemorySegment =
        callPointerFunc("glfwSetCharModsCallback", window, callback)

    fun glfwSetMouseButtonCallback(window: GLFWWindow, callback: GLFWMouseButtonFun): MemorySegment =
        callPointerFunc("glfwSetMouseButtonCallback", window, callback)

    fun glfwSetCursorPosCallback(window: GLFWWindow, callback: GLFWCursorPosFun): MemorySegment =
        callPointerFunc("glfwSetCursorPosCallback", window, callback)

    fun glfwSetCursorEnterCallback(window: GLFWWindow, callback: GLFWCursorEnterFun): MemorySegment =
        callPointerFunc("glfwSetCursorEnterCallback", window, callback)

    fun glfwSetScrollCallback(window: GLFWWindow, callback: GLFWScrollFun): MemorySegment =
        callPointerFunc("glfwSetScrollCallback", window, callback)

    fun glfwSetDropCallback(window: GLFWWindow, callback: GLFWDropFun): MemorySegment =
        callPointerFunc("glfwSetDropCallback", window, callback)

    fun glfwJoystickPresent(jid: Int): Int =
        callFunc("glfwJoystickPresent", Int::class, jid)

    fun glfwGetJoystickAxes(jid: Int): FloatArray {
        val count = HeapInt()
        val seg = callPointerFunc("glfwGetJoystickAxes", jid, count)
        return HeapFloatArray(count.value(), seg).value()
    }

    fun glfwGetJoystickButtons(jid: Int): ByteArray {
        val count = HeapInt()
        val seg = callPointerFunc("glfwGetJoystickButtons", jid, count)
        return HeapByteArray(count.value(), seg).value()
    }

    fun glfwGetJoystickHats(jid: Int): ByteArray {
        val count = HeapInt()
        val seg = callPointerFunc("glfwGetJoystickHats", jid, count)
        return HeapByteArray(count.value(), seg).value()
    }

    fun glfwGetJoystickName(jid: Int): String =
        callPointerFunc("glfwGetJoystickName", jid).fetchString()

    fun glfwGetJoystickGUID(jid: Int): String =
        callPointerFunc("glfwGetJoystickGUID", jid).fetchString()

    fun glfwSetJoystickUserPointer(jid: Int, pointer: MemorySegment) =
        callVoidFunc("glfwSetJoystickUserPointer", jid, pointer)

    fun glfwGetJoystickUserPointer(jid: Int): MemorySegment =
        callPointerFunc("glfwGetJoystickUserPointer", jid)

    fun glfwJoystickIsGamepad(jid: Int): Int =
        callFunc("glfwJoystickIsGamepad", Int::class, jid)

    fun glfwSetJoystickCallback(callback: GLFWJoystickFun): MemorySegment =
        callPointerFunc("glfwSetJoystickCallback", callback)

    fun glfwUpdateGamepadMappings(str: String): Int =
        callFunc("glfwUpdateGamepadMappings", Int::class, str.toCString())

    fun glfwGetGamepadName(jid: Int): String =
        callPointerFunc("glfwGetGamepadName", jid).fetchString()

    fun glfwGetGamepadState(jid: Int, state: GLFWGamepadState): Int =
        callFunc("glfwGetGamepadState", Int::class, jid, state)

    fun glfwSetClipboardString(window: GLFWWindow, str: String) =
        callVoidFunc("glfwSetClipboardString", window, str.toCString())

    fun glfwGetClipboardString(window: GLFWWindow): String =
        callPointerFunc("glfwGetClipboardString", window).fetchString()

    fun glfwGetTime(): Double =
        callFunc("glfwGetTime", Double::class)

    fun glfwSetTime(time: Double) =
        callVoidFunc("glfwSetTime", time)

    fun glfwGetTimerValue(): ULong =
        callFunc("glfwGetTimerValue", Long::class).toULong()

    fun glfwGetTimerFrequency(): ULong =
        callFunc("glfwGetTimerFrequency", Long::class).toULong()
}