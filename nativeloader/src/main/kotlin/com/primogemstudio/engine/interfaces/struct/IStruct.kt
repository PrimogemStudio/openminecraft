package com.primogemstudio.engine.interfaces.struct

import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.io.Closeable

abstract class IStruct(layout: MemoryLayout?): Closeable {
    private val arena = Arena.ofConfined()
    protected val seg: MemorySegment

    init {
        seg = arena.allocate(layout?: layout())
    }

    constructor(): this(null) {}

    abstract fun layout(): MemoryLayout
    abstract fun construct(seg: MemorySegment)
    fun arena(): Arena = arena
    fun update() {
        seg.fill(0x00.toByte())
        construct(seg)
    }
    fun pointer(): MemorySegment = seg
    override fun close() = arena.close()
}
