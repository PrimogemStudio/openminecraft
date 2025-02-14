package com.primogemstudio.engine.interfaces

import com.primogemstudio.engine.i18n.Internationalization.tr
import com.primogemstudio.engine.interfaces.heap.IHeapVar
import com.primogemstudio.engine.interfaces.stub.IStub
import com.primogemstudio.engine.loader.Platform
import com.primogemstudio.engine.logging.LoggerFactory
import java.lang.foreign.*
import java.lang.foreign.ValueLayout.*
import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.util.*
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
object NativeMethodCache {
    init {
        Platform.init()
    }

    private val logger = LoggerFactory.getAsyncLogger()
    private val funcCache = mutableMapOf<String, MethodHandle>()
    private val stubCache = mutableMapOf<Any, MemorySegment>()
    private val linker = Linker.nativeLinker()
    private val symbolLookup = SymbolLookup.loaderLookup()

    private fun klassToLayout(klass: KClass<*>?): MemoryLayout? = klass.let {
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

    @OptIn(ExperimentalStdlibApi::class)
    fun <T : IStub> constructStub(klass: KClass<T>, callback: T): MemorySegment {
        if (stubCache.containsKey(callback)) return stubCache[callback]!!
        val content = callback.register()

        val argArr =
            content.second.parameterArray().map { klassToLayout(it.kotlin) }.map { it ?: ADDRESS }.toTypedArray()
        val fd = if (content.second.returnType() == Void.TYPE) {
            FunctionDescriptor.ofVoid(*argArr)
        } else {
            FunctionDescriptor.of(klassToLayout(content.second.returnType().kotlin), *argArr)
        }

        return linker.upcallStub(
            MethodHandles.foldArguments(
                MethodHandles.lookup().findVirtual(klass.java, content.first, content.second),
                MethodHandles.constant(klass.java, callback)
            ),
            fd,
            Arena.ofAuto()
        ).apply {
            stubCache[callback] = this
            logger.info(tr("engine.nativeloader.stub", klass.qualifiedName, this.address().toHexString()))
        }
    }

    fun callVoidFunc(name: String, provider: (String) -> MemorySegment, vararg args: Any) {
        callFunc(name, provider, Unit::class, *args)
    }

    fun callVoidFunc(name: String, vararg args: Any) {
        callFunc(name, Unit::class, *args)
    }

    fun callPointerFunc(name: String, provider: (String) -> MemorySegment, vararg args: Any): MemorySegment =
        callFunc(name, provider, MemorySegment::class, *args)

    fun callPointerFunc(name: String, vararg args: Any): MemorySegment =
        callFunc(name, MemorySegment::class, *args)

    fun <T : Any> callFunc(name: String, rettype: KClass<T>?, vararg args: Any): T {
        return callFunc(name, null, rettype, *args)
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun <T : Any> callFunc(
        name: String,
        provider: ((String) -> MemorySegment)?,
        rettype: KClass<T>?,
        vararg args: Any
    ): T {
        val argListNew = args.map {
            return@map when (it) {
                is IHeapVar<*> -> it.ref()
                else -> it
            }
        }

        if (!funcCache.containsKey(name)) {
            val rettypeDesc: MemoryLayout? = klassToLayout(rettype)
            val argDesc = args.map { klassToLayout(it::class) }.toTypedArray()

            val funcP = if (provider == null) symbolLookup.find(name) else Optional.of(provider(name))
            if (!funcP.isPresent) {
                throw NullPointerException(tr("engine.nativeloader.func.fail", name))
            }
            funcCache[name] = linker.downcallHandle(
                funcP.get(),
                if (rettypeDesc == null) FunctionDescriptor.ofVoid(*argDesc) else FunctionDescriptor.of(
                    rettypeDesc,
                    *argDesc
                )
            )
            logger.info(tr("engine.nativeloader.func", name, funcP.get().address().toHexString()))
        }

        return (if (args.isEmpty()) funcCache[name]!!.invoke() as T else funcCache[name]!!.invokeWithArguments(
            argListNew
        ) as T)
    }
}
