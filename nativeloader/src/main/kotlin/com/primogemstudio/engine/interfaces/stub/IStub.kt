package com.primogemstudio.engine.interfaces.stub

import java.lang.invoke.MethodType

interface IStub {
    fun register(): Pair<String, MethodType>
}