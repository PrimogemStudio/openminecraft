package com.primogemstudio.engine.bindings.glfw

import com.primogemstudio.engine.interfaces.NativeMethodCache.callFunc
import com.primogemstudio.engine.interfaces.NativeMethodCache.callVoidFunc
import com.primogemstudio.engine.interfaces.fetchCString
import com.primogemstudio.engine.interfaces.heap.HeapFloat
import com.primogemstudio.engine.interfaces.heap.HeapInt
import com.primogemstudio.engine.interfaces.heap.HeapMutRefArray
import com.primogemstudio.engine.interfaces.heap.IHeapVar
import com.primogemstudio.engine.interfaces.stub.IStub
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.JAVA_INT
import java.lang.foreign.ValueLayout.JAVA_SHORT
import java.lang.invoke.MethodType

class GLFWMonitor(private val data: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = data
    override fun value(): MemorySegment = data
}

class GLFWVidMode(private val data: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = data
    override fun value(): MemorySegment = data
}

class GLFWGammaRamp(private val data: MemorySegment) : IHeapVar<MemorySegment> {
    constructor(red: Short, green: Short, blue: Short, size: Int) : this(
        Arena.ofConfined().allocate(
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
    fun glfwGetMonitors(count: HeapInt): Array<GLFWMonitor> =
        HeapMutRefArray(
            callFunc("glfwGetMonitors", MemorySegment::class, count),
            count.value()
        ).value().map { GLFWMonitor(it) }.toTypedArray()

    fun glfwGetPrimaryMonitor(): GLFWMonitor =
        GLFWMonitor(callFunc("glfwGetPrimaryMonitor", MemorySegment::class))

    fun glfwGetMonitorPos(monitor: GLFWMonitor, xpos: HeapInt, ypos: HeapInt) =
        callVoidFunc("glfwGetMonitorPos", monitor, xpos, ypos)

    fun glfwGetMonitorWorkarea(monitor: GLFWMonitor, xpos: HeapInt, ypos: HeapInt, width: HeapInt, height: HeapInt) =
        callVoidFunc("glfwGetMonitorWorkarea", monitor, xpos, ypos, width, height)

    fun glfwGetMonitorPhysicalSize(monitor: GLFWMonitor, widthMM: HeapInt, heightMM: HeapInt) =
        callVoidFunc("glfwGetMonitorPhysicalSize", monitor, widthMM, heightMM)

    fun glfwGetMonitorContentScale(monitor: GLFWMonitor, xscale: HeapFloat, yscale: HeapFloat) =
        callVoidFunc("glfwGetMonitorContentScale", monitor, xscale, yscale)

    fun glfwGetMonitorName(monitor: GLFWMonitor): String =
        callFunc("glfwGetMonitorName", MemorySegment::class, monitor).fetchCString()

    fun glfwSetMonitorUserPointer(monitor: GLFWMonitor, pointer: MemorySegment) =
        callVoidFunc("glfwSetMonitorUserPointer", monitor, pointer)

    fun glfwGetMonitorUserPointer(monitor: GLFWMonitor): MemorySegment =
        callFunc("glfwGetMonitorUserPointer", MemorySegment::class, monitor)

    fun glfwSetMonitorCallback(callback: GLFWMonitorFun): MemorySegment =
        callFunc("glfwSetMonitorCallback", MemorySegment::class, callback)

    fun glfwGetVideoModes(monitor: GLFWMonitor, count: HeapInt): Array<GLFWVidMode> =
        HeapMutRefArray(
            callFunc("glfwGetVideoModes", MemorySegment::class, monitor, count),
            count.value()
        ).value().map { GLFWVidMode(it) }.toTypedArray()

    fun glfwGetVideoMode(monitor: GLFWMonitor): GLFWVidMode =
        callFunc("glfwGetVideoMode", GLFWVidMode::class, monitor)

    fun glfwSetGamma(monitor: GLFWMonitor, gamma: Float) =
        callVoidFunc("glfwSetGamma", monitor, gamma)

    fun glfwGetGammaRamp(monitor: GLFWMonitor): GLFWGammaRamp =
        callFunc("glfwGetGammaRamp", GLFWGammaRamp::class, monitor)

    fun glfwSetGammaRamp(monitor: GLFWMonitor, ramp: GLFWGammaRamp) =
        callVoidFunc("glfwSetGammaRamp", monitor, ramp)
}