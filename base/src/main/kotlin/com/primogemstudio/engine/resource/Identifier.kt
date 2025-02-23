package com.primogemstudio.engine.resource

enum class ResourceLocation(val prefix: String) {
    BUNDLED(""),
    EXTERNAL("<external>/")
}

data class Identifier(
    val type: ResourceLocation = ResourceLocation.BUNDLED,
    val namespace: String,
    val path: String
) {
    override fun toString(): String = "${type.prefix}$namespace:$path"
    fun toPath(): String = "${if (namespace == "<external>") "" else "assets/$namespace/"}$path"

    companion object {
        fun parse(s: String): Identifier = Identifier(
            type = if (s.contains(":")) ResourceLocation.BUNDLED else ResourceLocation.EXTERNAL,
            namespace = s.split(":").let { if (it.size >= 2) it[it.size - 2] else "<external>" },
            path = s.split(":").let { it[it.size - 1] }
        )
    }
}