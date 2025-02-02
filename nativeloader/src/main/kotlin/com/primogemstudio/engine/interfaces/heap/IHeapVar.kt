package com.primogemstudio.engine.interfaces.heap

import java.lang.foreign.MemorySegment
import java.io.Closeable

interface IHeapVar<T>: Closeable {
    fun ref(): MemorySegment
    fun value(): T
}