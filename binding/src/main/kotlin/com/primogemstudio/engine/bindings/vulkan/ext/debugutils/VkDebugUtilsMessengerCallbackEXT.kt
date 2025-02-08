package com.primogemstudio.engine.bindings.vulkan.ext.debugutils

import com.primogemstudio.engine.interfaces.stub.IStub
import java.lang.foreign.MemorySegment
import java.lang.invoke.MethodType

fun interface VkDebugUtilsMessengerCallbackEXT : IStub {
    fun call(severity: Int, type: Int, callbackData: VkDebugUtilsMessengerCallbackDataEXT, user: MemorySegment)
    fun call(severity: Int, type: Int, callbackData: MemorySegment, user: MemorySegment) = call(
        severity, type, VkDebugUtilsMessengerCallbackDataEXT(
            callbackData.reinterpret(
                VkDebugUtilsMessengerCallbackDataEXT.LAYOUT.byteSize()
            )
        ), user
    )

    override fun register(): Pair<String, MethodType> =
        Pair(
            "call",
            MethodType.methodType(
                Void.TYPE,
                Int::class.java,
                Int::class.java,
                MemorySegment::class.java,
                MemorySegment::class.java
            )
        )
}