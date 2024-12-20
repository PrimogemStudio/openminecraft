package com.primogemstudio.engine.ext

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
class MultiWrappedProcess(
    private val processes: List<ProcessBuilder>,
    private val concurrentCount: Int,
    private val textProcessor: (String) -> Unit,
    private val details: List<String>
) : WrappedProcessI {
    private val processList = Array<WrappedProcess?>(processes.size) { null }
    private var procPointer: Int = 0

    init {
        GlobalScope.launch {
            while (!exited()) {
                val c = running()
                if (c < concurrentCount) {
                    val newTop = (procPointer + (concurrentCount - c)).coerceAtMost(processes())
                    for (idx in procPointer..<newTop) {
                        processList[idx] = WrappedProcess(processes[idx].start(), textProcessor)
                    }

                    procPointer = newTop
                }
            }
        }
    }

    private var lastRemaining = processes()

    override fun waitForProcess(): Int {
        while (!exited()) {
            val fini = procPointer - running()
            if (lastRemaining != fini && fini >= 0) {
                textProcessor("$fini&${processes()}&${details[fini]}")
                lastRemaining = fini
            }
            Thread.sleep(100)
        }
        textProcessor("${processes()}&${processes()}")
        return processList.mapNotNull { it }.map { it.waitForProcess() }.firstOrNull { it != 0 } ?: 0
    }

    override fun processes(): Int = processes.size
    override fun exited(): Boolean {
        processList.forEach {
            if (it == null) return@exited false
            if (it.exited() && it.waitForProcess() != 0) return@exited true

            if (!it.exited()) return@exited false
        }

        return true
    }

    private fun running(): Int {
        return processList.mapNotNull { it }.filter { !it.exited() }.size
    }
}