package com.primogemstudio.engine.jmake

import com.primogemstudio.engine.ext.WrappedProcess
import java.io.File

data class CommandProp(
    val runPath: File,
    val commandArgs: List<String>
) {
    fun toProcess(proc: (String) -> Unit): WrappedProcess =
        WrappedProcess(ProcessBuilder().command(commandArgs).directory(runPath).start(), proc)
}

interface ProjectBuilder {
    fun checkEnv()
    fun buildProject(): List<CommandProp>
    fun addDefine(key: String, value: Any)
    fun config(key: String, value: Any)
    fun outputProcessor(data: String)
}