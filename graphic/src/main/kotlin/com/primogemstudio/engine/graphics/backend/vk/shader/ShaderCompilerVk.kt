package com.primogemstudio.engine.graphics.backend.vk.shader

import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_anyhit_shader
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_callable_shader
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_closesthit_shader
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_compilation_status_compilation_error
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_compilation_status_configuration_error
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_compilation_status_internal_error
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_compilation_status_invalid_assembly
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_compilation_status_invalid_stage
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_compilation_status_null_result_object
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_compilation_status_success
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_compilation_status_transformation_error
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_compilation_status_validation_error
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_compile_into_spv
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_compile_options_initialize
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_compile_options_release
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_compile_options_set_source_language
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_compiler_initialize
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_compute_shader
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_fragment_shader
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_geometry_shader
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_glsl_anyhit_shader
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_glsl_callable_shader
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_glsl_closesthit_shader
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_glsl_compute_shader
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_glsl_fragment_shader
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_glsl_geometry_shader
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_glsl_intersection_shader
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_glsl_miss_shader
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_glsl_raygen_shader
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_glsl_tess_control_shader
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_glsl_tess_evaluation_shader
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_glsl_vertex_shader
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_intersection_shader
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_miss_shader
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_raygen_shader
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_result_get_bytes
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_result_get_compilation_status
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_result_get_error_message
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_result_get_length
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_result_release
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_source_language_glsl
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_source_language_hlsl
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_tess_control_shader
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_tess_evaluation_shader
import com.primogemstudio.engine.bindings.shaderc.Shaderc.shaderc_vertex_shader
import com.primogemstudio.engine.foreign.heap.HeapByteArray
import com.primogemstudio.engine.graphics.IShaderCompiler
import com.primogemstudio.engine.graphics.backend.vk.BackendRendererVk
import com.primogemstudio.engine.i18n.Internationalization.tr
import com.primogemstudio.engine.logging.LoggerFactory
import com.primogemstudio.engine.resource.Identifier
import com.primogemstudio.engine.resource.ResourceManager

enum class ShaderLanguage(val data: Int) {
    Glsl(shaderc_source_language_glsl), Hlsl(shaderc_source_language_hlsl)
}

enum class ShaderType(val glslType: Int, val hlslType: Int, val type: String) {
    Vertex(shaderc_glsl_vertex_shader, shaderc_vertex_shader, "vertex"),
    Fragment(shaderc_glsl_fragment_shader, shaderc_fragment_shader, "frag"),
    Compute(shaderc_glsl_compute_shader, shaderc_compute_shader, "compute"),
    Geometry(shaderc_glsl_geometry_shader, shaderc_geometry_shader, "geometry"),
    TessControl(shaderc_glsl_tess_control_shader, shaderc_tess_control_shader, "tess_control"),
    TessEvaluation(shaderc_glsl_tess_evaluation_shader, shaderc_tess_evaluation_shader, "tess_evaluation"),
    RayGen(shaderc_glsl_raygen_shader, shaderc_raygen_shader, "ray_gen"),
    AnyHit(shaderc_glsl_anyhit_shader, shaderc_anyhit_shader, "any_hit"),
    ClosestHit(shaderc_glsl_closesthit_shader, shaderc_closesthit_shader, "closest_hit"),
    Miss(shaderc_glsl_miss_shader, shaderc_miss_shader, "miss"),
    Intersection(shaderc_glsl_intersection_shader, shaderc_intersection_shader, "intersection"),
    Callable(shaderc_glsl_callable_shader, shaderc_callable_shader, "callable")
}

class ShaderCompilerVk(
    private val renderer: BackendRendererVk
) : IShaderCompiler {
    private val compiler = shaderc_compiler_initialize()
    private val logger = LoggerFactory.getAsyncLogger()
    var type: ShaderType = ShaderType.Vertex
    var lang: ShaderLanguage = ShaderLanguage.Glsl

    override fun compile(src: Identifier): ShaderModuleVk {
        val l = lang.data
        val glslT = type.glslType
        val hlslT = type.hlslType
        val t = type.type

        val options = shaderc_compile_options_initialize()
        shaderc_compile_options_set_source_language(options, l)

        val result = shaderc_compile_into_spv(
            compiler,
            ResourceManager(src)!!.readAllBytes().toString(Charsets.UTF_8),
            if (lang == ShaderLanguage.Glsl) glslT else hlslT,
            src.toString(),
            "main",
            options
        )

        shaderc_result_get_error_message(result).split("\n").filter { it.isNotEmpty() }.forEach {
            logger.error(it)
        }

        logger.info(
            "${tr("engine.shader.types.$t")}${
                tr(
                    when (shaderc_result_get_compilation_status(result)) {
                        shaderc_compilation_status_success -> "engine.shader.compile_status.success"
                        shaderc_compilation_status_invalid_stage -> "engine.shader.compile_status.invalid_stage"
                        shaderc_compilation_status_compilation_error -> "engine.shader.compile_status.compile_error"
                        shaderc_compilation_status_internal_error -> "engine.shader.compile_status.internal_error"
                        shaderc_compilation_status_null_result_object -> "engine.shader.compile_status.null_result_object"
                        shaderc_compilation_status_invalid_assembly -> "engine.shader.compile_status.invalid_assembly"
                        shaderc_compilation_status_validation_error -> "engine.shader.compile_status.validation_error"
                        shaderc_compilation_status_transformation_error -> "engine.shader.compile_status.transform_error"
                        shaderc_compilation_status_configuration_error -> "engine.shader.compile_status.config_error"
                        else -> "null"
                    }, src.toString()
                )
            }"
        )

        shaderc_compile_options_release(options)

        return ShaderModuleVk(renderer, HeapByteArray(shaderc_result_get_length(result).toInt()).apply {
            ref().copyFrom(shaderc_result_get_bytes(result))
            shaderc_result_release(result)
        })
    }
}