package com.primogemstudio.engine.foreign.invoke

import com.primogemstudio.engine.foreign.toCString
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.Linker
import java.lang.foreign.MemorySegment
import java.lang.foreign.SymbolLookup
import java.lang.foreign.ValueLayout.ADDRESS
import java.util.*

class DLSymLoader : SymbolLookup {
    override fun find(name: String?): Optional<MemorySegment> {
        if (name == null) return Optional.empty()
        else {
            val seg = Linker.nativeLinker().defaultLookup().find("dlsym")
            if (seg.isEmpty) return Optional.empty()

            val targetseg = Linker.nativeLinker().downcallHandle(
                seg.get(),
                FunctionDescriptor.of(ADDRESS, ADDRESS, ADDRESS)
            ).invoke(MemorySegment.ofAddress(0), name.toCString()) as MemorySegment
            return if (targetseg.address() == 0L) Optional.empty() else Optional.of(targetseg)
        }
    }
}