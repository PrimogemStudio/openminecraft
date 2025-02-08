package com.primogemstudio.engine.interfaces.heap

import java.io.Closeable
import java.lang.foreign.MemorySegment

interface IHeapVar<T> : Closeable {
    fun ref(): MemorySegment
    fun value(): T
    override fun close() {

    }
}
