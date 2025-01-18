package com.primogemstudio.engine.interfaces

import com.primogemstudio.engine.interfaces.NativeMethodCache.callFunc
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout

fun MemorySegment.fetchCString(): String {
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

fun genCString(str: String): MemorySegment {
    val barr = str.toByteArray(Charsets.UTF_8)
    val seg = Arena.ofConfined().allocate(barr.size + 1L)
    seg.copyFrom(MemorySegment.ofArray(barr))
    seg.set(ValueLayout.JAVA_BYTE, barr.size.toLong(), 0)
    return seg
}