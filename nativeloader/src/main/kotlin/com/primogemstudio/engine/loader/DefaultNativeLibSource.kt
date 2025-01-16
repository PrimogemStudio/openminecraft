package com.primogemstudio.engine.loader

import java.io.InputStream

class DefaultNativeLibSource(private val name: String) : INativeLibSource {
    private val libStack = mutableListOf<NativeLibInfo>()
    override fun push(lib: NativeLibInfo) {
        libStack.add(lib)
    }

    override fun list(): List<NativeLibInfo> = libStack.toList()
    override fun name(): String = name
    override fun fetch(): InputStream? = libStack.firstOrNull()?.source
}