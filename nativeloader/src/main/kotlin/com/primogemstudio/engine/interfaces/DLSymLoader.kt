package com.primogemstudio.engine.interfaces

import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.MemorySegment
import java.lang.foreign.SymbolLookup
import java.lang.foreign.ValueLayout.ADDRESS
import java.util.*

class DLSymLoader : SymbolLookup {
    override fun find(name: String?): Optional<MemorySegment> {
        if (name == null) return Optional.empty()
        else {
            val seg = NativeMethodCache.linker.defaultLookup().find("dlsym")
            if (seg.isEmpty) return Optional.empty()
            return Optional.of(
                NativeMethodCache.linker.downcallHandle(
                    seg.get(),
                    FunctionDescriptor.of(ADDRESS, ADDRESS, ADDRESS)
                ).invoke(MemorySegment.ofAddress(0), name.toCString()) as MemorySegment
            )
        }
    }
}