package com.primogemstudio.engine.interfaces

import com.primogemstudio.engine.interfaces.NativeMethodCache.callFunc
import com.primogemstudio.engine.loader.Platform.sizetLength
import com.primogemstudio.engine.logging.LoggerFactory
import java.lang.foreign.*
import java.lang.foreign.ValueLayout.*
import kotlin.math.max

val logger = LoggerFactory.getLogger()
fun MemorySegment.fetchString(): String {
    val buf = reinterpret(callFunc("strlen", Int::class, this).toLong() + 1).asByteBuffer()
    val bList = mutableListOf<Byte>()
    var chr: Byte

    while (true) {
        chr = buf.get()
        if (chr == 0x00.toByte()) break

        bList.add(chr)
    }

    return String(bList.toByteArray(), Charsets.UTF_8)
}

fun String.toCString(): MemorySegment {
    val barr = toByteArray(Charsets.UTF_8)
    val seg = Arena.ofAuto().allocate(barr.size + 1L)
    seg.copyFrom(MemorySegment.ofArray(barr))
    seg.set(ValueLayout.JAVA_BYTE, barr.size.toLong(), 0)
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

inline fun <T> MemorySegment.fromCStructArray(length: Int, structLength: Int, constructor: (MemorySegment) -> T): List<T> = (0 ..< length).map { this.asSlice(it * structLength * 1L, structLength * 1L) }.map { constructor(it) }

fun MemorySegment.toCPointerArray(length: Int): Array<MemorySegment> = (0 ..< length).map { this.reinterpret(length * sizetLength() * 1L).get(ADDRESS, it * sizetLength() * 1L) }.toTypedArray()
fun MemorySegment.toCFloatArray(length: Int): FloatArray = (0 ..< length).map { this.reinterpret(length * 4L).get(JAVA_FLOAT, it * 4L) }.toFloatArray()
fun MemorySegment.toCByteArray(length: Int): ByteArray = (0 ..< length).map { this.reinterpret(length * 1L).get(JAVA_BYTE, it * 1L) }.toByteArray()

fun StructLayout.cacheOffsets(): LongArray =
    (0..<this.memberLayouts().size).map { this.byteOffset(MemoryLayout.PathElement.groupElement(it.toLong())) }
        .toLongArray()

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