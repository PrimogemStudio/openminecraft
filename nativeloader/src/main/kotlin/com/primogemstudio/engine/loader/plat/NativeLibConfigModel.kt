package com.primogemstudio.engine.loader.plat

data class NativeLibConfigModel(
    private val rawData: Map<String, Any>
) {
    val required: List<String> = (rawData["required"] as List<*>).map { it.toString() }
    val optional: List<String> = (rawData["optional"] as List<*>).map { it.toString() }
}