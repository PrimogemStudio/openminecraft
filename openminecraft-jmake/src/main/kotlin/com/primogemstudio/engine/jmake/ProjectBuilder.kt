package com.primogemstudio.engine.jmake

import com.primogemstudio.engine.ext.MultiWrappedProcess
import com.primogemstudio.engine.ext.WrappedProcess
import com.primogemstudio.engine.ext.WrappedProcessI
import java.io.File

interface CommandPropI {
    fun toProcess(proc: (String) -> Unit): WrappedProcessI
    fun builder(): ProcessBuilder
}

data class CommandProp(
    val runPath: File,
    val commandArgs: List<String>
) : CommandPropI {
    override fun toProcess(proc: (String) -> Unit): WrappedProcess =
        WrappedProcess(builder().start(), proc).apply {
            println(commandArgs)
        }

    override fun builder(): ProcessBuilder = ProcessBuilder().command(commandArgs).directory(runPath)
}

data class MultiCommandProp(
    val props: List<CommandProp>,
    val concurrent: Int,
    val details: List<String>
) : CommandPropI {
    override fun toProcess(proc: (String) -> Unit): WrappedProcessI =
        MultiWrappedProcess(props.map { it.builder() }, concurrent, proc, details)

    override fun builder(): ProcessBuilder {
        TODO("Not yet implemented")
    }
}

interface ProjectBuilder {
    fun checkEnv()
    fun buildProject(): List<CommandPropI>
    fun addDefine(key: String, value: Any)
    fun config(key: String, value: Any)
    fun outputProcessor(data: String)
}