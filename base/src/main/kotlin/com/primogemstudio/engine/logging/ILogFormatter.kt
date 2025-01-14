package com.primogemstudio.engine.logging

import com.primogemstudio.engine.logging.LoggerBuffer.Type

interface ILogFormatter {
    fun format(type: Type, thrName: String, name: String, str: String, t: Throwable?): String
}