package com.primogemstudio.engine.i18n

import com.primogemstudio.engine.json.GsonObjects
import com.primogemstudio.engine.logging.LoggerFactory
import com.primogemstudio.engine.resource.ResourceManager
import java.util.*

object Internationalization {
    private val targetTranslations = mutableMapOf<String, MutableMap<String, String>>()
    private val logger = LoggerFactory.getLogger()

    private val localeList = mutableListOf(
        "jar:assets/openmc/locale.json",
        "jar:assets/openmc_graphic/locale.json",
        "jar:assets/openmc_nativeloader/locale.json"
    )

    init {
        load()
    }

    private fun load() {
        targetTranslations.clear()
        logger.info("Available locale files: ${localeList.filter { ResourceManager.getResource(it) != null }}")
        localeList.mapNotNull { ResourceManager.getResource(it) }.forEach {
            GsonObjects.GSON.fromJson(it.readAllBytes().toString(Charsets.UTF_8), Map::class.java).forEach { (k, v) ->
                val t = ResourceManager.getResource(v.toString())
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
    }

    fun append(path: String) {
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
