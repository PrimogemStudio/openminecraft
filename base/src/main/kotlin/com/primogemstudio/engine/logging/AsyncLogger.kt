package com.primogemstudio.engine.logging

class AsyncLogger(private val name: String) : ILogger {
    override fun debug(str: String) {
        LoggerBuffer.logAsync(LoggerBuffer.Type.DEBUG, name, str, null)
    }

    override fun info(str: String) {
        LoggerBuffer.logAsync(LoggerBuffer.Type.INFO, name, str, null)
    }

    override fun warn(str: String) {
        LoggerBuffer.logAsync(LoggerBuffer.Type.WARN, name, str, null)
    }

    override fun error(str: String) {
        LoggerBuffer.logAsync(LoggerBuffer.Type.ERROR, name, str, null)
    }

    override fun error(str: String, t: Throwable) {
        LoggerBuffer.logAsync(LoggerBuffer.Type.ERROR, name, str, t)
    }

    override fun fatal(str: String) {
        LoggerBuffer.logAsync(LoggerBuffer.Type.FATAL, name, str, null)
    }

    override fun fatal(str: String, t: Throwable) {
        LoggerBuffer.logAsync(LoggerBuffer.Type.FATAL, name, str, t)
    }
}