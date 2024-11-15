package com.primogemstudio.engine.i18n

import com.primogemstudio.engine.utils.LoggerFactory
import org.json.JSONObject
import java.util.*

object Internationalization {
    private val targetTranslations = mutableMapOf<String, MutableMap<String, String>>()
    private val logger = LoggerFactory.getLogger()

    init {
        Internationalization.javaClass.classLoader.resources("locale.json").forEach {
            JSONObject(it.openStream().readAllBytes().toString(Charsets.UTF_8)).apply {
                keys().forEach { k ->
                    val t = Internationalization.javaClass.classLoader.getResource(this[k].toString())
                    targetTranslations[k] = mutableMapOf()
                    logger.info("Processing $k -> ${this[k]}")
                    if (t != null) {
                        JSONObject(t.openStream().readAllBytes().toString(Charsets.UTF_8)).apply {
                            keys().forEach { kt ->
                                targetTranslations[k]?.set(kt, this[kt].toString())
                            }
                        }
                    } else logger.warn("Translation file not found: ${this[k]}")
                }
            }
        }
    }

    fun tr(key: String): String = targetTranslations[Locale.getDefault().toString()]?.get(key) ?: key
}