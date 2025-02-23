package com.primogemstudio.engine.foreign.heap

import com.primogemstudio.engine.loader.plat.Platform.sizetLength
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.ADDRESS

class HeapPointerArray<T : IHeapVar<*>>(
    val length: Int,
    private val seg: MemorySegment,
    private val constructor: ((MemorySegment) -> T)?
) : IHeapVar<Array<MemorySegment>> {
    constructor(length: Int, constructor: ((MemorySegment) -> T)?) : this(
        length,
        Arena.ofAuto().allocate(length * sizetLength() * 1L),
        constructor
    )

    constructor(length: Int) : this(length, { TODO("Unsupported operation!") })
    constructor(data: Array<T>, constructor: ((MemorySegment) -> T)?) : this(
        data.size,
        Arena.ofAuto().allocate(data.size * sizetLength() * 1L),
        constructor
    ) {
        for (i in data.indices) {
            this[i] = data[i]
        }
    }

    constructor(data: Array<T>) : this(data, { TODO("Unsupported operation!") })

    override fun ref(): MemorySegment = seg
    override fun value(): Array<MemorySegment> = (0..<length).map { getRaw(it) }.toTypedArray()

    private fun getRaw(idx: Int): MemorySegment = seg.get(ADDRESS, sizetLength() * 1L * idx)
    operator fun get(idx: Int): T? = constructor?.invoke(seg.get(ADDRESS, sizetLength() * 1L * idx))
    operator fun set(idx: Int, value: T) = seg.set(ADDRESS, sizetLength() * 1L * idx, value.ref())
}