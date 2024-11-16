package com.primogemstudio.engine.jmake

import com.primogemstudio.engine.i18n.Internationalization.tr
import java.io.File
import java.util.regex.Pattern

class CMakeProjectBuilder(
    private val projBase: File,
    private val build: File,
    private val msgHandler: (String, Double) -> Unit
) : ProjectBuilder {
    companion object {
        private const val CMAKE_CONF_STR = "-- Configuring done"
        private const val CMAKE_GEN_STR = "-- Generating done"
        private const val CMAKE_BUILD_FILE_STR = "-- Build files have been written to:"

        private val CMAKE_PROCESS_HINT = Pattern.compile("\\[(\u0020*)(?<status>.*)%] (?<msg>.*)")

        private const val CMAKE_BUILD_CXX_STR = "Building CXX object "
        private const val CMAKE_LINK_CXX_LIB_STR = "Linking CXX static library "
        private const val CMAKE_LINK_CXX_DLL_STR = "Linking CXX shared library "
        private const val CMAKE_LINK_CXX_EXE_STR = "Linking CXX executable "
        private const val CMAKE_BUILD_C_STR = "Building C object "
        private const val CMAKE_LINK_C_LIB_STR = "Linking C static library "
        private const val CMAKE_LINK_C_DLL_STR = "Linking C shared library "
        private const val CMAKE_LINK_C_EXE_STR = "Linking C executable "
        private const val CMAKE_BUILT_TARGET_STR = "Built target "
        private val PATTS = listOf(
            CMAKE_BUILD_CXX_STR, CMAKE_LINK_CXX_LIB_STR, CMAKE_LINK_CXX_DLL_STR, CMAKE_LINK_CXX_EXE_STR,
            CMAKE_BUILD_C_STR, CMAKE_LINK_C_LIB_STR, CMAKE_LINK_C_DLL_STR, CMAKE_LINK_C_EXE_STR,
            CMAKE_BUILT_TARGET_STR
        )
    }
    private val defines: MutableMap<String, Any> = mutableMapOf()
    private val configs: MutableMap<String, Any> = mutableMapOf()

    override fun checkEnv() {
        if (!File("/usr/bin/cmake").exists() || !File("/usr/bin/make").exists()) throw IllegalStateException(tr("exception.jmake.env_cmake.corrupt"))
    }

    override fun buildProject(): List<CommandProp> = listOf(
        CommandProp(build, mutableListOf("/usr/bin/cmake", "$projBase", "-G", "Unix Makefiles").apply {
            defines.forEach { (t, u) -> add("-D$t=$u") }
        }),
        CommandProp(build, listOf("/usr/bin/make", "clean")),
        CommandProp(build, listOf("/usr/bin/make", "-j${configs["JOBS"] ?: 1}"))
    )

    override fun addDefine(key: String, value: Any) {
        defines[key] = value
    }

    override fun config(key: String, value: Any) {
        configs[key] = value
    }

    override fun outputProcessor(data: String) {
        var datae = data
            .replace(CMAKE_CONF_STR, tr("jmake.replacement.cmake_config"))
            .replace(CMAKE_GEN_STR, tr("jmake.replacement.cmake_gen"))
            .replace(CMAKE_BUILD_FILE_STR, tr("jmake.replacement.cmake_build"))

        CMAKE_PROCESS_HINT.matcher(datae).apply {
            if (find()) {
                val prog = group("status").toInt()

                val msg = group("msg")
                var file: String? = null
                var type: String? = null
                PATTS.forEach {
                    if (type == null && msg.startsWith(it)) {
                        type = it
                        file = msg.replace(it, "")
                    }
                }

                msgHandler(
                    tr(
                        "jmake.objs.${
                            when (type) {
                                CMAKE_BUILD_CXX_STR -> "cpp"
                                CMAKE_BUILD_C_STR -> "c"
                                CMAKE_LINK_CXX_LIB_STR -> "cpp_lib"
                                CMAKE_LINK_C_LIB_STR -> "c_lib"
                                CMAKE_LINK_CXX_DLL_STR -> "cpp_dll"
                                CMAKE_LINK_C_DLL_STR -> "c_lib"
                                CMAKE_LINK_CXX_EXE_STR -> "cpp_exe"
                                CMAKE_LINK_C_EXE_STR -> "c_exe"
                                CMAKE_BUILT_TARGET_STR -> "target"
                                else -> "unknown"
                            }
                        }.name", prog, file
                    ), prog.toDouble() / 100
                )

                return@outputProcessor
            }
        }

        msgHandler(datae, -1.0)
    }
}