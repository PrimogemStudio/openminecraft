package com.primogemstudio.engine.foreign.stub

import java.lang.invoke.MethodType

interface IStub {
    fun register(): Pair<String, MethodType>
}