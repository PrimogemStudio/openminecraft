package com.primogemstudio.engine.interfaces

import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment

interface IStruct {
    fun layout(): MemoryLayout
    fun construct(seg: MemorySegment)
}