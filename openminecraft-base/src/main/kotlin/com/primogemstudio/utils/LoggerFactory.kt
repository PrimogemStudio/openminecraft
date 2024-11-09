package com.primogemstudio.utils

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Suppress("UNUSED")
object LoggerFactory {
    private val stackWalker = StackWalker.getInstance()
    fun getLogger(s: String): Logger = LogManager.getLogger(s)
    fun getLogger(): Logger = LogManager.getLogger(stackWalker.callerClass)
}