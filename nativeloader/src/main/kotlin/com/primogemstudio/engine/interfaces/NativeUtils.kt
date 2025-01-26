package com.primogemstudio.engine.interfaces

import com.primogemstudio.engine.loader.Platform.sizetLength
import com.primogemstudio.engine.interfaces.NativeMethodCache.callFunc
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.logging.LoggerFactory
import java.lang.foreign.ValueLayout.ADDRESS
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout

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
    val seg = Arena.ofConfined().allocate(barr.size + 1L)
    seg.copyFrom(MemorySegment.ofArray(barr))
    seg.set(ValueLayout.JAVA_BYTE, barr.size.toLong(), 0)
    return seg
}

fun IStruct?.allocate(): MemorySegment = this?.allocateLocal() ?: MemorySegment.NULL

fun Array<String>.toCStrArray(): MemorySegment {
    return Arena.ofConfined().allocate(size * sizetLength() * 1L).apply {
        var i = 0
        forEach {
            set(ADDRESS, i * sizetLength() * 1L, it.toCString())
            i++
        }
    }
}