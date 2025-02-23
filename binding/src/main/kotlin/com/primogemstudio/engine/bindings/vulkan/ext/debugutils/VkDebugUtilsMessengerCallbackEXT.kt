package com.primogemstudio.engine.bindings.vulkan.ext.debugutils

import com.primogemstudio.engine.foreign.stub.IStub
import java.lang.foreign.MemorySegment
import java.lang.invoke.MethodType

fun interface VkDebugUtilsMessengerCallbackEXT : IStub {
    fun call(severity: Int, type: Int, callbackData: VkDebugUtilsMessengerCallbackDataEXT, user: MemorySegment): Int
    fun call(severity: Int, type: Int, callbackData: MemorySegment, user: MemorySegment): Int = call(
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
                Int::class.java,
                Int::class.java,
                Int::class.java,
                MemorySegment::class.java,
                MemorySegment::class.java
            )
        )
}