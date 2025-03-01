package com.primogemstudio.engine.graphics

import com.primogemstudio.engine.resource.Identifier

interface IShaderCompiler {
    fun compile(src: Identifier): IShader
}