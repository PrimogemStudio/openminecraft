package com.primogemstudio.engine.i18n

import com.primogemstudio.engine.resource.ResourceManager
import com.primogemstudio.engine.utils.LoggerFactory
import org.json.JSONObject
import java.util.*

object Internationalization {
    private val targetTranslations = mutableMapOf<String, MutableMap<String, String>>()
    private val logger = LoggerFactory.getLogger()

    init {
        ResourceManager.getResources("jar:locale.json").forEach {
            JSONObject(it.readAllBytes().toString(Charsets.UTF_8)).apply {
                keys().forEach { k ->
                    val t = ResourceManager.getResource(this[k].toString())
                    if (!targetTranslations.containsKey(k)) targetTranslations[k] = mutableMapOf()
                    logger.info("Processing $k -> ${this[k]}")
                    if (t != null) {
                        JSONObject(t.readAllBytes().toString(Charsets.UTF_8)).apply {
                            keys().forEach { kt ->
                                targetTranslations[k]?.set(kt, this[kt].toString())
                            }
                        }
                    } else logger.warn("Translation file not found: ${this[k]}")
                }
            }
        }
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