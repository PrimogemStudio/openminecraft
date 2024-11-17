package com.primogemstudio.engine.resource

import java.io.InputStream

object ResourceManager {
    fun getResources(p: String): List<InputStream> =
        ResourceManager::class.java.classLoader.getResources(p).toList().map { it.openStream() }

    fun getResource(p: String): InputStream? = ResourceManager::class.java.classLoader.getResourceAsStream(p)
}