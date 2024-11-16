package com.primogemstudio.engine.jmake

import com.primogemstudio.engine.i18n.Internationalization.tr
import java.io.File

class CMakeProjectBuilder(private val projBase: File, private val build: File) : ProjectBuilder {
    private val defines: MutableMap<String, Any> = mutableMapOf()
    private val configs: MutableMap<String, Any> = mutableMapOf()
    override fun checkEnv() {
        if (!File("/usr/bin/cmake").exists() || !File("/usr/bin/make").exists()) throw IllegalStateException(tr("exception.jmake.env_cmake.corrupt"))
    }

    override fun buildProject(): List<CommandProp> = listOf(
        CommandProp(build, mutableListOf("/usr/bin/cmake", "$projBase", "-G", "Unix Makefiles").apply {
            defines.forEach { (t, u) -> add("-D$t=$u") }
        }),
        CommandProp(build, listOf("/usr/bin/make", "-j${configs["JOBS"] ?: 4}"))
    )

    override fun addDefine(key: String, value: Any) {
        defines[key] = value
    }

    override fun config(key: String, value: Any) {
        configs[key] = value
    }
}