package com.primogemstudio.engine.logging

import org.fusesource.jansi.Ansi
import java.text.SimpleDateFormat
import java.util.*

class SimpleFormatter : ILogFormatter {
    private val dateFormat = SimpleDateFormat("HH:mm:ss")
    override fun format(type: LoggerBuffer.Type, thrName: String, name: String, str: String, t: Throwable?): String {
        return Ansi.ansi()
            .fgBlue().a("[${dateFormat.format(Date(System.currentTimeMillis()))}] ")
            .let {
                return@let when (type) {
                    LoggerBuffer.Type.DEBUG -> it.fgBlue()
                    LoggerBuffer.Type.INFO -> it.fgGreen()
                    LoggerBuffer.Type.WARN -> it.fgYellow()
                    LoggerBuffer.Type.ERROR -> it.fgRed()
                    LoggerBuffer.Type.FATAL -> it.fgBrightRed()
                }
            }.a("[$thrName/$type] ")
            .fgCyan().a("($name) ")
            .reset().let {
                return@let when (type) {
                    LoggerBuffer.Type.ERROR -> it.fgRed()
                    LoggerBuffer.Type.FATAL -> it.fgBrightRed()
                    else -> it
                }
            }.a(str)
            .reset().toString()
    }
}