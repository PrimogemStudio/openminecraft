package com.primogemstudio.engine.vk.shader

import com.primogemstudio.engine.i18n.Internationalization.tr
import com.primogemstudio.engine.utils.LoggerFactory
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
    private val logger = LoggerFactory.getLogger()

    fun compile(
        source: String,
        filename: String,
        entrypoint: String,
        lang: ShaderLanguage,
        type: ShaderType,
        werror: Boolean = true
    ): VkShaderData {
        val option = shaderc_compile_options_initialize()
        if (werror) shaderc_compile_options_set_warnings_as_errors(option)
        shaderc_compile_options_set_source_language(option, lang.data)

        val r = shaderc_compile_into_spv(
            compiler,
            source,
            if (lang == ShaderLanguage.Glsl) type.glslType else type.hlslType,
            filename,
            entrypoint,
            option
        )

        shaderc_compile_options_release(option)

        logger.info(
            tr(
                "engine.shader.compile.result",
                shaderc_result_get_num_warnings(r),
                shaderc_result_get_num_errors(r),
                filename
            )
        )
        shaderc_result_get_error_message(r)?.split("\n")?.filter { it.isNotEmpty() }?.forEach {
            logger.error(it)
        }

        logger.info(
            tr(
                when (shaderc_result_get_compilation_status(r)) {
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
                }, filename
            )
        )

        return VkShaderData(r, shaderc_result_get_bytes(r))
    }
}