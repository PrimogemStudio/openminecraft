package com.primogemstudio.engine.resource

import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path

object ResourceManager {
    fun getResource(p: Identifier): InputStream? =
        if (p.type == ResourceLocation.BUNDLED) ResourceManager::class.java.classLoader.getResourceAsStream(p.toPath()) else Files.newInputStream(
            Path.of(p.toPath())
        )
}