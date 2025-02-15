package com.primogemstudio.engine.graphics.data

import com.primogemstudio.engine.types.Version

data class ApplicationInfo(
    val appName: String,
    val appVersion: Version,
    val engineName: String,
    val engineVersion: Version
)
