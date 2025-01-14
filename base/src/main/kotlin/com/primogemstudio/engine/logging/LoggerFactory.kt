package com.primogemstudio.engine.logging


@Suppress("UNUSED")
object LoggerFactory {
    private val stackWalker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)

    fun getLogger(s: String): ILogger = SimpleLogger(s)
    fun getLogger(): ILogger = getLogger(stackWalker.callerClass.simpleName)
    fun getAsyncLogger(s: String): ILogger = AsyncLogger(s)
    fun getAsyncLogger(): ILogger = getAsyncLogger(stackWalker.callerClass.simpleName)
}