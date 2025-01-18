package com.primogemstudio.engine.interfaces.heap

import java.lang.foreign.MemorySegment

interface IHeapVar<T> {
    fun ref(): MemorySegment
    fun value(): T
}