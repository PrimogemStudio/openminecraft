package com.primogemstudio.engine.ext

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
class WrappedProcess(val proc: Process, val textProcessor: (String) -> Unit) : WrappedProcessI {
    init {
        GlobalScope.launch {
            Thread.currentThread().name = "Process #${proc.pid()}"
            val r = proc.inputReader(Charsets.UTF_8)
            while (proc.isAlive) {
                try {
                    r.readLine()?.let {
                        textProcessor(it)
                    }
                } catch (_: Exception) {
                }
            }
        }

        GlobalScope.launch {
            Thread.currentThread().name = "Process #${proc.pid()}"
            val r = proc.errorReader(Charsets.UTF_8)
            while (proc.isAlive) {
                try {
                    r.readLine()?.let {
                        textProcessor(it)
                    }
                } catch (_: Exception) {
                }
            }
        }
    }

    override fun waitForProcess(): Int {
        while (proc.isAlive) {
            proc.isAlive
        }

        return proc.exitValue()
    }

    override fun processes(): Int = 1

    override fun exited(): Boolean = !proc.isAlive
}