package com.primogemstudio.engine.bindings

import com.primogemstudio.engine.interfaces.NativeMethodCache.callFunc
import java.lang.foreign.MemorySegment

object LibC {
    fun strlen(seg: MemorySegment): Int = callFunc("strlen", Int::class, seg)
}