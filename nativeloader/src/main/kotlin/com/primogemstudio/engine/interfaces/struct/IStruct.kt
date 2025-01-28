package com.primogemstudio.engine.interfaces.struct

import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment

interface IStruct {
    fun layout(): MemoryLayout
    fun construct(seg: MemorySegment)
    fun allocateLocal(): MemorySegment = Arena.ofAuto().allocate(layout()).apply { construct(this) }
}