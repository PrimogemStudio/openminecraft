package com.primogemstudio.engine.interfaces.struct

import com.primogemstudio.engine.interfaces.heap.IHeapVar
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment

abstract class IStruct(layout: MemoryLayout?) : IHeapVar<MemorySegment> {
    private val arena = Arena.ofAuto()
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

    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg
}
