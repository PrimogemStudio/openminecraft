package com.primogemstudio.engine.graphics.backend.vk.shader

import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_compile_into_spv
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_compile_options_initialize
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_compile_options_release
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_compiler_initialize
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_glsl_vertex_shader
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_result_release
import com.primogemstudio.engine.resource.Identifier
import com.primogemstudio.engine.resource.ResourceManager

class ShaderCompilerVk {
    private val compiler = shaderc_compiler_initialize()

    fun compile(src: Identifier) {
        val options = shaderc_compile_options_initialize()

        val result = shaderc_compile_into_spv(
            compiler,
            ResourceManager(src)!!.readAllBytes().toString(Charsets.UTF_8),
            shaderc_glsl_vertex_shader,
            src.toString(),
            "main",
            options
        )

        shaderc_result_release(result)
        shaderc_compile_options_release(options)
    }
}