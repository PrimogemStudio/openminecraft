package com.primogemstudio.engine.logging

class SimpleLogger(private val name: String) : ILogger {
    override fun debug(str: String) {
        LoggerBuffer.log(LoggerBuffer.Type.DEBUG, name, str, null)
    }

    override fun info(str: String) {
        LoggerBuffer.log(LoggerBuffer.Type.INFO, name, str, null)
    }

    override fun warn(str: String) {
        LoggerBuffer.log(LoggerBuffer.Type.WARN, name, str, null)
    }

    override fun error(str: String) {
        LoggerBuffer.log(LoggerBuffer.Type.ERROR, name, str, null)
    }

    override fun error(str: String, t: Throwable) {
        LoggerBuffer.log(LoggerBuffer.Type.ERROR, name, str, t)
    }

    override fun fatal(str: String) {
        LoggerBuffer.log(LoggerBuffer.Type.FATAL, name, str, null)
    }

    override fun fatal(str: String, t: Throwable) {
        LoggerBuffer.log(LoggerBuffer.Type.FATAL, name, str, t)
    }
}