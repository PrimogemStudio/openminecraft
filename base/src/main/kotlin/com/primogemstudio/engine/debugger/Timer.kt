package com.primogemstudio.engine.debugger

import com.primogemstudio.engine.i18n.Internationalization.tr
import com.primogemstudio.engine.logging.LoggerFactory

val logger = LoggerFactory.getAsyncLogger()
val stackWalker = StackWalker.getInstance()

fun dbgTime(data: Long) {
    val currentFunc = Thread.currentThread().stackTrace[2].methodName
    when {
        data >= 1000000000 -> logger.info(tr("engine.debugger.timer.log.s", currentFunc, data / 1000000000f))
        data >= 1000000 -> logger.info(tr("engine.debugger.timer.log.ms", currentFunc, data / 1000000f))
        data >= 1000 -> logger.info(tr("engine.debugger.timer.log.us", currentFunc, data / 1000f))
        else -> logger.info(tr("engine.debugger.timer.log.ns", currentFunc, data / 1f))
    }
}

inline fun time(func: () -> Unit) {
    val st = System.nanoTime()
    func()
    val tgt = System.nanoTime()
    dbgTime(tgt - st)
}

inline fun <T> time(func: () -> T): T {
    val st = System.nanoTime()
    val result = func()
    val tgt = System.nanoTime()
    dbgTime(tgt - st)
    return result
}