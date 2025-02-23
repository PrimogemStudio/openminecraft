package com.primogemstudio.engine.graphics.backend.vk

import java.io.Closeable

interface IReinitable : Closeable {
    fun reinit()
}