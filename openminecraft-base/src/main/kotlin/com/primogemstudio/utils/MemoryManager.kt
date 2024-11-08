package com.primogemstudio.utils

import sun.misc.Unsafe
import kotlin.reflect.jvm.isAccessible

object MemoryManager {
    val unsafe = Unsafe::class.constructors.first().apply {
        isAccessible = true
    }.call()
    fun test() {
        var i = unsafe.allocateMemory(4)
        unsafe.freeMemory(i)

        while (true) {
            println("${unsafe.getByte(i)}")
            i++
        }
    }
}