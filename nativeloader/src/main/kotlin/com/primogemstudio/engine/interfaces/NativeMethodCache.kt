package com.primogemstudio.engine.interfaces

import com.primogemstudio.engine.i18n.Internationalization.tr
import com.primogemstudio.engine.interfaces.heap.IHeapVar
import com.primogemstudio.engine.interfaces.struct.IStruct
import com.primogemstudio.engine.interfaces.stub.IStub
import com.primogemstudio.engine.logging.LoggerFactory
import java.lang.foreign.*
import java.lang.foreign.ValueLayout.*
import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
object NativeMethodCache {
    private val logger = LoggerFactory.getLogger()
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
            Arena.ofConfined()
        ).apply {
            stubCache[callback] = this
            logger.info(tr("engine.nativeloader.stub", klass.qualifiedName, this.address().toHexString()))
        }
    }

    fun callVoidFunc(name: String, vararg args: Any) {
        callFunc(name, Unit::class, *args)
    }

    fun callPointerFunc(name: String, vararg args: Any): MemorySegment =
        callFunc(name, MemorySegment::class, *args)

    @OptIn(ExperimentalStdlibApi::class)
    fun <T : Any> callFunc(name: String, rettype: KClass<T>?, vararg args: Any): T {
        val argListNew = args.map {
            return@map when (it) {
                is IHeapVar<*> -> it.ref()
                is IStruct -> Arena.ofConfined().allocate(it.layout()).apply { it.construct(this) }
                else -> it
            }
        }

        if (!funcCache.containsKey(name)) {
            val rettypeDesc: MemoryLayout? = klassToLayout(rettype)
            val argDesc = args.map { klassToLayout(it::class) }.toTypedArray()

            val funcP = symbolLookup.find(name).get()
            funcCache[name] = linker.downcallHandle(
                funcP,
                if (rettypeDesc == null) FunctionDescriptor.ofVoid(*argDesc) else FunctionDescriptor.of(
                    rettypeDesc,
                    *argDesc
                )
            )
            logger.info(tr("engine.nativeloader.func", name, funcP.address().toHexString()))
        }

        return if (args.isEmpty()) funcCache[name]!!.invoke() as T else funcCache[name]!!.invokeWithArguments(argListNew) as T
    }
}