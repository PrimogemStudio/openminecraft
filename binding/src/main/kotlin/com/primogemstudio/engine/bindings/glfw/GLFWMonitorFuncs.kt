package com.primogemstudio.engine.bindings.glfw

import com.primogemstudio.engine.foreign.NativeMethodCache.callFunc
import com.primogemstudio.engine.foreign.NativeMethodCache.callPointerFunc
import com.primogemstudio.engine.foreign.NativeMethodCache.callVoidFunc
import com.primogemstudio.engine.foreign.fetchString
import com.primogemstudio.engine.foreign.heap.HeapFloat
import com.primogemstudio.engine.foreign.heap.HeapInt
import com.primogemstudio.engine.foreign.heap.IHeapObject
import com.primogemstudio.engine.foreign.heap.IHeapVar
import com.primogemstudio.engine.foreign.stub.IStub
import com.primogemstudio.engine.foreign.toPointerArray
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
        return callPointerFunc("glfwGetMonitors", count).toPointerArray(count.value())
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
        return callPointerFunc("glfwGetVideoModes", monitor, count).toPointerArray(count.value())
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