package com.primogemstudio.engine.loader

import java.io.InputStream

interface INativeLibSource {
    fun name(): String
    fun fetch(): InputStream?
    fun push(lib: NativeLibInfo)
    fun list(): List<NativeLibInfo>
}
