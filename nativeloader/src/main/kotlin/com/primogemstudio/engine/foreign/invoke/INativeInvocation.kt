package com.primogemstudio.engine.foreign.invoke

import com.primogemstudio.engine.foreign.stub.IStub
import java.lang.foreign.MemorySegment
import kotlin.reflect.KClass

interface INativeInvocation {
    fun <T : IStub> constructStub(klass: KClass<T>, callback: T): MemorySegment
    fun <T : Any> callFunc(
        name: String,
        provider: ((String) -> MemorySegment)?,
        rettype: KClass<T>?,
        vararg args: Any
    ): T

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
}