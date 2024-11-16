package com.primogemstudio.engine.i18n

import com.primogemstudio.engine.i18n.Internationalization.tr
import java.io.PrintStream

object OutputOverride {
    lateinit var out: PrintStream
    lateinit var err: PrintStream
    fun init() {
        out = System.out
        err = System.err

        System.setOut(I18nPrintStream(out))
        System.setErr(I18nPrintStream(err))
    }
}

class I18nPrintStream(private val parent: PrintStream) : PrintStream(parent) {
    override fun println(i: String?) {
        parent.println(
            i?.replace("at", tr("exception.i18n.replacement.at"))
                ?.replace("Exception in thread", tr("exception.i18n.replacement.thread"))
                ?.replace("Stack trace", tr("exception.i18n.replacement.trace"))
        )
    }

    override fun print(i: String?) {
        parent.print(
            i?.replace("at", tr("exception.i18n.replacement.at"))
                ?.replace("Exception in thread", tr("exception.i18n.replacement.thread"))
                ?.replace("Stack trace", tr("exception.i18n.replacement.trace"))
        )
    }
}