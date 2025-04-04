package com.primogemstudio.engine.foreign

import com.primogemstudio.engine.foreign.heap.HeapPointerArray
import com.primogemstudio.engine.foreign.heap.HeapStructArray
import com.primogemstudio.engine.foreign.heap.IHeapObject
import com.primogemstudio.engine.foreign.heap.IHeapVar
import com.primogemstudio.engine.loader.plat.Platform.sizetLength
import java.lang.foreign.*
import java.lang.foreign.ValueLayout.ADDRESS
import java.lang.foreign.ValueLayout.JAVA_BYTE
import kotlin.math.max

fun MemorySegment.fetchString(): String {
    val buf = reinterpret(Long.MAX_VALUE)
    var chr: Byte

    var idx = 0L
    while (true) {
        chr = buf.get(JAVA_BYTE, idx)
        if (chr == 0x00.toByte()) break
        idx++
    }
    val barr = ByteArray(idx.toInt())
    MemorySegment.copy(buf, JAVA_BYTE, 0, barr, 0, idx.toInt())

    return String(barr, Charsets.UTF_8)
}

fun String.toCString(): MemorySegment {
    val barr = toByteArray(Charsets.UTF_8)
    val seg = Arena.ofAuto().allocate(barr.size + 1L)
    seg.copyFrom(MemorySegment.ofArray(barr))
    seg.set(JAVA_BYTE, barr.size.toLong(), 0)
    return seg
}

fun Array<String>.toCStrArray(): MemorySegment {
    return Arena.ofAuto().allocate(size * sizetLength() * 1L).apply {
        var i = 0
        forEach {
            set(ADDRESS, i * sizetLength() * 1L, it.toCString())
            i++
        }
    }
}

fun MemorySegment.unbox(): MemorySegment = this.get(ADDRESS, 0)
fun MemorySegment.unbox(layout: MemoryLayout): MemorySegment = this.get(ADDRESS, 0).reinterpret(layout.byteSize())
fun MemorySegment.toPointerArray(length: Int): Array<MemorySegment> =
    HeapPointerArray<IHeapObject>(length, this.reinterpret(length * sizetLength() * 1L), null).value()

fun StructLayout.cacheOffsets(): LongArray =
    (0..<this.memberLayouts().size).map { this.byteOffset(MemoryLayout.PathElement.groupElement(it.toLong())) }
        .toLongArray()

fun <T : IHeapVar<*>> Array<T>.toCStructArray(layout: MemoryLayout): HeapStructArray<T> =
    HeapStructArray<T>(layout, this.size).apply {
        for (i in this@toCStructArray.indices) {
            this[i] = this@toCStructArray[i]
        }
    }

private fun checkLayoutAlign(e: StructLayout): Long = e.memberLayouts().maxOfOrNull {
    if (it is StructLayout) checkLayoutAlign(it) else it.byteSize()
} ?: 1

fun StructLayout.align(): StructLayout {
    val elements = mutableListOf<Long>()
    var size = 0L
    var alignment = 1L

    for (e in this.memberLayouts()) {
        var ali = e.byteSize()

        if (e is StructLayout) {
            ali = checkLayoutAlign(e)
        }

        if (e is PaddingLayout) {
            ali = 1
        }

        alignment = max(ali, alignment)

        if (size % ali != 0L) {
            elements[elements.size - 1] += ali - size % ali
            elements.add(e.byteSize())
            size += e.byteSize() + ali - size % ali
        } else {
            elements.add(e.byteSize())
            size += e.byteSize()
        }
    }

    if (size % alignment != 0L) {
        elements[elements.size - 1] += alignment - size % alignment
    }

    return MemoryLayout.structLayout(
        *elements.map { MemoryLayout.paddingLayout(it) }.toTypedArray()
    )
}
