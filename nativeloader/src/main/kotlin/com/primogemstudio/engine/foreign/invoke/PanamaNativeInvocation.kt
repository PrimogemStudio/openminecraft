package com.primogemstudio.engine.foreign.invoke

import com.primogemstudio.engine.foreign.heap.IHeapVar
import com.primogemstudio.engine.foreign.stub.IStub
import com.primogemstudio.engine.i18n.Internationalization.tr
import com.primogemstudio.engine.logging.LoggerFactory
import java.lang.foreign.Arena
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.Linker
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.SymbolLookup
import java.lang.foreign.ValueLayout.ADDRESS
import java.lang.foreign.ValueLayout.JAVA_BOOLEAN
import java.lang.foreign.ValueLayout.JAVA_BYTE
import java.lang.foreign.ValueLayout.JAVA_CHAR
import java.lang.foreign.ValueLayout.JAVA_DOUBLE
import java.lang.foreign.ValueLayout.JAVA_FLOAT
import java.lang.foreign.ValueLayout.JAVA_INT
import java.lang.foreign.ValueLayout.JAVA_LONG
import java.lang.foreign.ValueLayout.JAVA_SHORT
import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.util.Optional
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class PanamaNativeInvocation : INativeInvocation {
    private val logger = LoggerFactory.getAsyncLogger()
    private val funcCache = mutableMapOf<String, MethodHandle>()
    private val stubCache = mutableMapOf<Any, MemorySegment>()
    val linker = Linker.nativeLinker()
    val dlsymLoader = DLSymLoader()
    private val symbolLookup = SymbolLookup.loaderLookup().or(dlsymLoader)

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
    override fun <T : IStub> constructStub(klass: KClass<T>, callback: T): MemorySegment {
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

    @OptIn(ExperimentalStdlibApi::class)
    override fun <T : Any> callFunc(
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