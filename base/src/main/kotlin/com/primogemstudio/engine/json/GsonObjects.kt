package com.primogemstudio.engine.json

import com.google.gson.GsonBuilder

object GsonObjects {
    val GSON = GsonBuilder().setPrettyPrinting().create()
}