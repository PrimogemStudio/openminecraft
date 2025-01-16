package com.primogemstudio.engine.loader

import java.io.InputStream

data class NativeLibInfo(
    val name: String,
    val source: InputStream?,
    val isEmbedded: Boolean,
    val isSystem: Boolean
)
