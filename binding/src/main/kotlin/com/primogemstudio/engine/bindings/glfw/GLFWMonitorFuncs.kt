package com.primogemstudio.engine.bindings.glfw

import com.primogemstudio.engine.interfaces.NativeMethodCache.callFunc
import com.primogemstudio.engine.interfaces.NativeMethodCache.callPointerFunc
import com.primogemstudio.engine.interfaces.NativeMethodCache.callVoidFunc
import com.primogemstudio.engine.interfaces.fetchString
import com.primogemstudio.engine.interfaces.heap.HeapFloat
import com.primogemstudio.engine.interfaces.heap.HeapInt
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import com.primogemstudio.engine.interfaces.heap.IHeapVar
import com.primogemstudio.engine.interfaces.stub.IStub
import com.primogemstudio.engine.interfaces.toCPointerArray
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT
import java.lang.foreign.ValueLayout.JAVA_SHORT
import java.lang.invoke.MethodType

class GLFWMonitor(private val data: MemorySegment) : IHeapObject(data)
class GLFWVidMode(private val data: MemorySegment) : IHeapObject(data)

class GLFWGammaRamp(private val data: MemorySegment) : IHeapVar<MemorySegment> {
    constructor(red: Short, green: Short, blue: Short, size: Int) : this(
        Arena.ofAuto().allocate(
            MemoryLayout.structLayout(
                JAVA_SHORT, JAVA_SHORT, JAVA_SHORT, JAVA_INT
            )
        ).apply {
            this.set(JAVA_SHORT, 0, red)
            this.set(JAVA_SHORT, 2, green)
            this.set(JAVA_SHORT, 4, blue)
            this.set(JAVA_INT, 6, size)
        }
    )

    override fun ref(): MemorySegment = data
    override fun value(): MemorySegment = data
}

fun interface GLFWMonitorFun : IStub {
    fun call(monitor: GLFWMonitor, event: Int)
    fun call(monitor: MemorySegment, event: Int) = call(GLFWMonitor(monitor), event)
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

object GLFWMonitorFuncs {
    fun glfwGetMonitors(): Array<GLFWMonitor> {
        val count = HeapInt()
        return callPointerFunc("glfwGetMonitors", count).toCPointerArray(count.value())
            .map { GLFWMonitor(it) }
            .toTypedArray()
    }

    fun glfwGetPrimaryMonitor(): GLFWMonitor =
        GLFWMonitor(callPointerFunc("glfwGetPrimaryMonitor"))

    fun glfwGetMonitorPos(monitor: GLFWMonitor, xpos: HeapInt, ypos: HeapInt) =
        callVoidFunc("glfwGetMonitorPos", monitor, xpos, ypos)

    fun glfwGetMonitorWorkarea(monitor: GLFWMonitor, xpos: HeapInt, ypos: HeapInt, width: HeapInt, height: HeapInt) =
        callVoidFunc("glfwGetMonitorWorkarea", monitor, xpos, ypos, width, height)

    fun glfwGetMonitorPhysicalSize(monitor: GLFWMonitor, widthMM: HeapInt, heightMM: HeapInt) =
        callVoidFunc("glfwGetMonitorPhysicalSize", monitor, widthMM, heightMM)

    fun glfwGetMonitorContentScale(monitor: GLFWMonitor, xscale: HeapFloat, yscale: HeapFloat) =
        callVoidFunc("glfwGetMonitorContentScale", monitor, xscale, yscale)

    fun glfwGetMonitorName(monitor: GLFWMonitor): String =
        callPointerFunc("glfwGetMonitorName", monitor).fetchString()

    fun glfwSetMonitorUserPointer(monitor: GLFWMonitor, pointer: MemorySegment) =
        callVoidFunc("glfwSetMonitorUserPointer", monitor, pointer)

    fun glfwGetMonitorUserPointer(monitor: GLFWMonitor): MemorySegment =
        callPointerFunc("glfwGetMonitorUserPointer", monitor)

    fun glfwSetMonitorCallback(callback: GLFWMonitorFun): MemorySegment =
        callPointerFunc("glfwSetMonitorCallback", callback)

    fun glfwGetVideoModes(monitor: GLFWMonitor): Array<GLFWVidMode> {
        val count = HeapInt()
        return callPointerFunc("glfwGetVideoModes", monitor, count).toCPointerArray(count.value())
            .map { GLFWVidMode(it) }
            .toTypedArray()
    }

    fun glfwGetVideoMode(monitor: GLFWMonitor): GLFWVidMode =
        callFunc("glfwGetVideoMode", GLFWVidMode::class, monitor)

    fun glfwSetGamma(monitor: GLFWMonitor, gamma: Float) =
        callVoidFunc("glfwSetGamma", monitor, gamma)

    fun glfwGetGammaRamp(monitor: GLFWMonitor): GLFWGammaRamp =
        callFunc("glfwGetGammaRamp", GLFWGammaRamp::class, monitor)

    fun glfwSetGammaRamp(monitor: GLFWMonitor, ramp: GLFWGammaRamp) =
        callVoidFunc("glfwSetGammaRamp", monitor, ramp)
}