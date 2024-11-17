package com.primogemstudio.engine.vk.shader

import org.lwjgl.util.shaderc.Shaderc.*

enum class ShaderLanguage(val data: Int) {
    Glsl(shaderc_source_language_glsl), Hlsl(shaderc_source_language_hlsl)
}

enum class ShaderType(val glslType: Int, val hlslType: Int) {
    Vertex(shaderc_glsl_vertex_shader, shaderc_vertex_shader),
    Fragment(shaderc_glsl_fragment_shader, shaderc_fragment_shader),
    Compute(shaderc_glsl_compute_shader, shaderc_compute_shader),
    Geometry(shaderc_glsl_geometry_shader, shaderc_geometry_shader),
    TessControl(shaderc_glsl_tess_control_shader, shaderc_tess_control_shader),
    TessEvaluation(shaderc_glsl_tess_evaluation_shader, shaderc_tess_evaluation_shader),
    RayGen(shaderc_glsl_raygen_shader, shaderc_raygen_shader),
    AnyHit(shaderc_glsl_anyhit_shader, shaderc_anyhit_shader),
    ClosesThit(shaderc_glsl_closesthit_shader, shaderc_closesthit_shader),
    Miss(shaderc_glsl_miss_shader, shaderc_miss_shader),
    Intersection(shaderc_glsl_intersection_shader, shaderc_intersection_shader),
    Callable(shaderc_glsl_callable_shader, shaderc_callable_shader)
}

class ShaderCompiler {
    private val compiler = shaderc_compiler_initialize()

    init {
        println(compiler)
        val option = shaderc_compile_options_initialize()
        shaderc_compile_options_set_warnings_as_errors(option)
        shaderc_compile_options_set_source_language(option, shaderc_source_language_glsl)
        val r = shaderc_compile_into_spv(
            compiler,
            "#version 140\nvoid main() {gl_FragColor0=vec4(1.0);}",
            shaderc_fragment_shader,
            "test",
            "main",
            option
        )
        println(shaderc_result_get_compilation_status(r))
        println(shaderc_result_get_error_message(r))
        println(shaderc_result_get_num_errors(r))
        println(shaderc_result_get_num_warnings(r))

    }
}