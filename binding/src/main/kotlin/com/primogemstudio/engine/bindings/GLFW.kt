package com.primogemstudio.engine.bindings

import com.primogemstudio.engine.interfaces.NativeMethodCache.callFunc
import com.primogemstudio.engine.interfaces.fetchCString
import com.primogemstudio.engine.interfaces.heap.HeapInt
import java.lang.foreign.MemorySegment

object GLFW {
    fun glfwInit(): Boolean = callFunc("glfwInit", Boolean::class)
    fun glfwGetVersionString(): String = callFunc("glfwGetVersionString", MemorySegment::class).fetchCString()
    fun glfwGetVersion(major: HeapInt, minor: HeapInt, rev: HeapInt) =
        callFunc("glfwGetVersion", Unit::class, major, minor, rev)
}