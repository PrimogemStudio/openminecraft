package com.primogemstudio.engine.foreign.invoke

import com.primogemstudio.engine.foreign.stub.IStub
import java.lang.foreign.MemorySegment
import kotlin.reflect.KClass

class DummyNativeInvocation : INativeInvocation {
    override fun <T : IStub> constructStub(
        klass: KClass<T>,
        callback: T
    ): MemorySegment {
        TODO("Not yet implemented")
    }

    override fun <T : Any> callFunc(
        name: String,
        provider: ((String) -> MemorySegment)?,
        rettype: KClass<T>?,
        vararg args: Any
    ): T {
        TODO("Not yet implemented")
    }
}