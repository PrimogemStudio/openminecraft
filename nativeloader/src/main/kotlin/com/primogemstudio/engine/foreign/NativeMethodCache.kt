package com.primogemstudio.engine.foreign

import com.primogemstudio.engine.foreign.invoke.PanamaNativeInvocation
import com.primogemstudio.engine.foreign.stub.IStub
import com.primogemstudio.engine.loader.plat.Platform
import java.lang.foreign.*
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
object NativeMethodCache {
    init {
        Platform.init()
    }

    val impl = PanamaNativeInvocation()

    @OptIn(ExperimentalStdlibApi::class)
    fun <T : IStub> constructStub(klass: KClass<T>, callback: T): MemorySegment = impl.constructStub(klass, callback)
    fun callVoidFunc(name: String, provider: (String) -> MemorySegment, vararg args: Any) =
        impl.callVoidFunc(name, provider, *args)

    fun callVoidFunc(name: String, vararg args: Any) = impl.callVoidFunc(name, *args)
    fun callPointerFunc(name: String, provider: (String) -> MemorySegment, vararg args: Any): MemorySegment =
        impl.callPointerFunc(name, provider, *args)

    fun callPointerFunc(name: String, vararg args: Any): MemorySegment = impl.callPointerFunc(name, *args)
    fun <T : Any> callFunc(name: String, rettype: KClass<T>?, vararg args: Any): T = impl.callFunc(name, rettype, *args)
    @OptIn(ExperimentalStdlibApi::class)
    fun <T : Any> callFunc(
        name: String,
        provider: ((String) -> MemorySegment)?,
        rettype: KClass<T>?,
        vararg args: Any
    ): T = impl.callFunc(name, provider, rettype, *args)
}
