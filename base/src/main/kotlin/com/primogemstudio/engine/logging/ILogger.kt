package com.primogemstudio.engine.logging

interface ILogger {
    fun debug(str: String)
    fun info(str: String)
    fun warn(str: String)
    fun error(str: String)
    fun error(str: String, t: Throwable)
    fun fatal(str: String)
    fun fatal(str: String, t: Throwable)
}