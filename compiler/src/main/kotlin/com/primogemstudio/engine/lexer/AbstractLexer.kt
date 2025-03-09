package com.primogemstudio.engine.lexer

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

abstract class AbstractLexer(i: InputStream) : BufferedReader(InputStreamReader(i)) {
    private val cache = CharArray(1)
    protected var state: Int = 0

    init {

    }

    protected fun nextChr(): Char = cache.apply { if (read(this) < 0) throw IllegalStateException() }[0]
    abstract fun nextToken()
}