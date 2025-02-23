package com.primogemstudio.engine.bindings.shaderc

import com.primogemstudio.engine.interfaces.align
import com.primogemstudio.engine.interfaces.cacheOffsets
import com.primogemstudio.engine.interfaces.fetchString
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import com.primogemstudio.engine.interfaces.toCString
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.ADDRESS
import java.lang.foreign.ValueLayout.JAVA_INT

class ShadercIncludeResult(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val LAYOUT = MemoryLayout.structLayout(
            ADDRESS,
            ADDRESS,
            ADDRESS,
            ADDRESS,
            ADDRESS
        ).align()

        private val OFFSETS = LAYOUT.cacheOffsets()
    }

    var sourceName: String
        get() = seg.get(ADDRESS, OFFSETS[0]).fetchString()
        set(value) {
            seg.set(ADDRESS, OFFSETS[0], value.toCString())
            seg.set(JAVA_INT, OFFSETS[1], value.length)
        }
    var content: String
        get() = seg.get(ADDRESS, OFFSETS[2]).fetchString()
        set(value) {
            seg.set(ADDRESS, OFFSETS[2], value.toCString())
            seg.set(JAVA_INT, OFFSETS[3], value.length)
        }
    var userData: MemorySegment
        get() = seg.get(ADDRESS, OFFSETS[4])
        set(value) = seg.set(ADDRESS, OFFSETS[4], value)
}