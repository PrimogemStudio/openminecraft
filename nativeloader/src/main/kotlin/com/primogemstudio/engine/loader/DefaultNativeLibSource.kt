package com.primogemstudio.engine.loader

import java.io.InputStream

class DefaultNativeLibSource(private val name: String) : INativeLibSource {
    private val libStack = mutableListOf<NativeLibInfo>()
    private var embedded = true
    private var system = true

    override fun push(lib: NativeLibInfo) {
        libStack.add(lib)
    }

    override fun list(): List<NativeLibInfo> = libStack.toList()
    override fun needEmbeddedLib(e: Boolean) {
        embedded = e
    }

    override fun needSystemLib(e: Boolean) {
        system = e
    }

    override fun name(): String = name
    override fun fetch(): InputStream? =
        libStack.firstOrNull { (embedded || !it.isEmbedded) || (system || !it.isSystem) }?.source

    override fun fetchName(): String? =
        libStack.firstOrNull { (embedded || !it.isEmbedded) || (system || !it.isSystem) }?.extName
}