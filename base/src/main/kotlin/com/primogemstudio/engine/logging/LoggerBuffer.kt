package com.primogemstudio.engine.logging

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object LoggerBuffer {
    var formatter = SimpleFormatter()
    val logHistory = mutableListOf<LogLine>()

    @OptIn(DelicateCoroutinesApi::class)
    fun logAsync(type: Type, name: String, str: String, t: Throwable?) {
        val thrName = Thread.currentThread().name
        GlobalScope.launch {
            println(formatter.format(type, thrName, name, str, t))
            logHistory.add(LogLine(type, thrName, name, str, t))
            t?.printStackTrace()
        }
    }

    fun log(type: Type, name: String, str: String, t: Throwable?) {
        val thrName = Thread.currentThread().name
        println(formatter.format(type, thrName, name, str, t))
        logHistory.add(LogLine(type, thrName, name, str, t))
        t?.printStackTrace()
    }

    enum class Type {
        DEBUG,
        INFO,
        WARN,
        ERROR,
        FATAL
    }

    data class LogLine(
        val type: Type,
        val thrName: String,
        val name: String,
        val content: String,
        val err: Throwable?
    )
}