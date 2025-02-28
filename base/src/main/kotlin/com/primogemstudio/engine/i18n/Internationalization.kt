package com.primogemstudio.engine.i18n

import com.primogemstudio.engine.json.GsonObjects
import com.primogemstudio.engine.logging.LoggerFactory
import com.primogemstudio.engine.resource.Identifier
import com.primogemstudio.engine.resource.ResourceManager
import java.util.*

object Internationalization {
    private val targetTranslations = mutableMapOf<String, MutableMap<String, String>>()
    private val logger = LoggerFactory.getAsyncLogger()

    private val localeList = mutableListOf(
        Identifier(namespace = "openmc", path = "locale.json"),
        Identifier(namespace = "openmc_graphic", path = "locale.json"),
        Identifier(namespace = "openmc_nativeloader", path = "locale.json")
    )

    init {
        load()
    }

    private fun load() {
        targetTranslations.clear()
        logger.info("Available locale files: ${localeList.filter { ResourceManager(it) != null }}")
        localeList.mapNotNull { ResourceManager(it) }.forEach {
            GsonObjects.GSON.fromJson(it.readAllBytes().toString(Charsets.UTF_8), Map::class.java).forEach { (k, v) ->
                val t = ResourceManager(Identifier.parse(v.toString()))
                if (!targetTranslations.containsKey(k)) targetTranslations[k.toString()] = mutableMapOf()
                logger.info("Processing $k -> $v")
                if (t != null) {
                    GsonObjects.GSON.fromJson(t.readAllBytes().toString(Charsets.UTF_8), Map::class.java)
                        .forEach { (ki, vi) ->
                            targetTranslations[k]?.set(ki.toString(), vi.toString())
                        }
                } else logger.warn("Translation file not found: $v")
            }
        }
        logger.info("Current locale: ${Locale.getDefault()}")
        System.getProperties().forEach { k, v -> 
            logger.info("$k = $v")
        }
    }

    fun append(path: Identifier) {
        localeList.add(path)
        load()
    }

    fun tr(key: String): String =
        targetTranslations[Locale.getDefault().toString()]?.get(key) ?: targetTranslations["en_US"]?.get(key) ?: key

    fun tr(key: String, vararg args: Any?): String =
        try {
            targetTranslations[Locale.getDefault().toString()]?.get(key)?.format(*args)
                ?: targetTranslations["en_US"]?.get(key)?.format(*args) ?: key
        } catch (e: Exception) {
            "$key ${args.toList()}"
        }
}
