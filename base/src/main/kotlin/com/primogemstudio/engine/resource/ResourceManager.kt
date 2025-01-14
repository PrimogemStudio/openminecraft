package com.primogemstudio.engine.resource

import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path

object ResourceManager {
    fun getResources(p: String): List<InputStream> =
        if (p.startsWith("jar:")) ResourceManager::class.java.classLoader.getResources(p.substring(4)).toList()
            .map { it.openStream() } else listOf(Files.newInputStream(Path.of(p)))

    fun getResource(p: String): InputStream? =
        if (p.startsWith("jar:")) ResourceManager::class.java.classLoader.getResourceAsStream(p.substring(4)) else Files.newInputStream(
            Path.of(p)
        )
}