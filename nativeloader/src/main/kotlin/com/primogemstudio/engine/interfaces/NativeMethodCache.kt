package com.primogemstudio.engine.interfaces

import com.primogemstudio.engine.i18n.Internationalization.tr
import com.primogemstudio.engine.logging.LoggerFactory
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.Linker
import java.lang.foreign.MemoryLayout
import java.lang.foreign.SymbolLookup
import java.lang.foreign.ValueLayout.*
import java.lang.invoke.MethodHandle
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
object NativeMethodCache {
    private val logger = LoggerFactory.getLogger()
    private val funcCache = mutableMapOf<String, MethodHandle>()
    private val linker = Linker.nativeLinker()
    private val symbolLookup = SymbolLookup.loaderLookup()

    @OptIn(ExperimentalStdlibApi::class)
    fun <T : Any> callFunc(name: String, rettype: KClass<T>?, vararg args: Any): T {
        if (funcCache.containsKey(name)) {
            return if (args.isEmpty()) funcCache[name]!!.invoke() as T else funcCache[name]!!.invokeWithArguments(args.toList()) as T
        }

        val rettypeDesc: MemoryLayout? = rettype.let {
            if (it == Boolean::class) return@let JAVA_BOOLEAN
            if (it == Byte::class) return@let JAVA_BYTE
            if (it == Char::class) return@let JAVA_CHAR
            if (it == Short::class) return@let JAVA_SHORT
            if (it == Int::class) return@let JAVA_INT
            if (it == Long::class) return@let JAVA_LONG
            if (it == Float::class) return@let JAVA_FLOAT
            if (it == Double::class) return@let JAVA_DOUBLE
            if (it == Nothing::class) return@let null
            return@let ADDRESS
        }
        val argDesc = args.map {
            if (it is Boolean) return@map JAVA_BOOLEAN
            if (it is Byte) return@map JAVA_BYTE
            if (it is Char) return@map JAVA_CHAR
            if (it is Short) return@map JAVA_SHORT
            if (it is Int) return@map JAVA_INT
            if (it is Long) return@map JAVA_LONG
            if (it is Float) return@map JAVA_FLOAT
            if (it is Double) return@map JAVA_DOUBLE
            return@map ADDRESS
        }.toTypedArray()

        val funcP = symbolLookup.find(name).get()
        funcCache[name] = linker.downcallHandle(
            funcP,
            if (rettypeDesc == null) FunctionDescriptor.ofVoid(*argDesc) else FunctionDescriptor.of(
                rettypeDesc,
                *argDesc
            )
        )
        logger.info(tr("engine.nativeloader.func", name, funcP.address().toHexString()))
        return if (args.isEmpty()) funcCache[name]!!.invoke() as T else funcCache[name]!!.invokeWithArguments(args.toList()) as T
    }
}