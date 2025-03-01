package com.primogemstudio.engine.vk.shader

import com.primogemstudio.engine.graphics.backend.vk.shader.ShaderLanguage
import com.primogemstudio.engine.graphics.backend.vk.shader.ShaderType
import com.primogemstudio.engine.i18n.Internationalization.tr
import com.primogemstudio.engine.logging.LoggerFactory
import org.lwjgl.util.shaderc.Shaderc.*

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
            "${tr("engine.shader.types.${type.type}")}${
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
            }"
        )

        return VkShaderData(r, shaderc_result_get_bytes(r))
    }
}