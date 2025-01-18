package com.primogemstudio.engine.interfaces

import java.lang.invoke.MethodType

interface IStub {
    fun register(): Pair<String, MethodType>
}