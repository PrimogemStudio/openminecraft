package com.primogemstudio.engine.ext

interface WrappedProcessI {
    fun waitForProcess(): Int
    fun exited(): Boolean
    fun processes(): Int
}