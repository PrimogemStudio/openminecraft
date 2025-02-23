package com.primogemstudio.engine.foreign.heap

interface IHeapArray<T> : IHeapVar<T> {
    val length: Int
}