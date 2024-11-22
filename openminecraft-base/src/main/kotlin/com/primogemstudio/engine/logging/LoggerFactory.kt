package com.primogemstudio.engine.logging

import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.apache.logging.log4j.Marker
import org.apache.logging.log4j.message.Message
import org.apache.logging.log4j.spi.AbstractLogger


@Suppress("UNUSED")
object LoggerFactory {
    private val stackWalker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
    fun getLogger(s: String): Logger = TestLogger(LogManager.getLogger(s))
    fun getLogger(): Logger = getLogger(stackWalker.callerClass.simpleName)
}

class TestLogger(private val parent: Logger) : AbstractLogger() {
    override fun getLevel(): Level = Level.ALL

    override fun isEnabled(level: Level?, marker: Marker?, message: Message?, t: Throwable?): Boolean = true
    override fun isEnabled(level: Level?, marker: Marker?, message: CharSequence?, t: Throwable?): Boolean = true
    override fun isEnabled(level: Level?, marker: Marker?, message: Any?, t: Throwable?): Boolean = true
    override fun isEnabled(level: Level?, marker: Marker?, message: String?, t: Throwable?): Boolean = true
    override fun isEnabled(level: Level?, marker: Marker?, message: String?): Boolean = true
    override fun isEnabled(level: Level?, marker: Marker?, message: String?, vararg params: Any?): Boolean = true
    override fun isEnabled(level: Level?, marker: Marker?, message: String?, p0: Any?): Boolean = true
    override fun isEnabled(level: Level?, marker: Marker?, message: String?, p0: Any?, p1: Any?): Boolean = true
    override fun isEnabled(level: Level?, marker: Marker?, message: String?, p0: Any?, p1: Any?, p2: Any?): Boolean =
        true

    override fun isEnabled(
        level: Level?,
        marker: Marker?,
        message: String?,
        p0: Any?,
        p1: Any?,
        p2: Any?,
        p3: Any?
    ): Boolean = true

    override fun isEnabled(
        level: Level?,
        marker: Marker?,
        message: String?,
        p0: Any?,
        p1: Any?,
        p2: Any?,
        p3: Any?,
        p4: Any?
    ): Boolean = true

    override fun isEnabled(
        level: Level?,
        marker: Marker?,
        message: String?,
        p0: Any?,
        p1: Any?,
        p2: Any?,
        p3: Any?,
        p4: Any?,
        p5: Any?
    ): Boolean = true

    override fun isEnabled(
        level: Level?,
        marker: Marker?,
        message: String?,
        p0: Any?,
        p1: Any?,
        p2: Any?,
        p3: Any?,
        p4: Any?,
        p5: Any?,
        p6: Any?
    ): Boolean = true

    override fun isEnabled(
        level: Level?,
        marker: Marker?,
        message: String?,
        p0: Any?,
        p1: Any?,
        p2: Any?,
        p3: Any?,
        p4: Any?,
        p5: Any?,
        p6: Any?,
        p7: Any?
    ): Boolean = true

    override fun isEnabled(
        level: Level?,
        marker: Marker?,
        message: String?,
        p0: Any?,
        p1: Any?,
        p2: Any?,
        p3: Any?,
        p4: Any?,
        p5: Any?,
        p6: Any?,
        p7: Any?,
        p8: Any?
    ): Boolean = true

    override fun isEnabled(
        level: Level?,
        marker: Marker?,
        message: String?,
        p0: Any?,
        p1: Any?,
        p2: Any?,
        p3: Any?,
        p4: Any?,
        p5: Any?,
        p6: Any?,
        p7: Any?,
        p8: Any?,
        p9: Any?
    ): Boolean = true

    override fun logMessage(fqcn: String?, level: Level?, marker: Marker?, message: Message?, t: Throwable?) {
        parent.logMessage(level, marker, fqcn, t?.stackTrace?.get(0), message, t)
        println(message)
    }
}