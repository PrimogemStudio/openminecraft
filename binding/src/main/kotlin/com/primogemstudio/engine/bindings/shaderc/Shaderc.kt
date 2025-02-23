package com.primogemstudio.engine.bindings.shaderc

import com.primogemstudio.engine.interfaces.NativeMethodCache.callFunc
import com.primogemstudio.engine.interfaces.NativeMethodCache.callPointerFunc
import com.primogemstudio.engine.interfaces.NativeMethodCache.callVoidFunc
import com.primogemstudio.engine.interfaces.fetchString
import com.primogemstudio.engine.interfaces.heap.HeapInt
import com.primogemstudio.engine.interfaces.heap.IHeapObject
import com.primogemstudio.engine.interfaces.toCString
import java.lang.foreign.MemorySegment
import java.nio.ByteBuffer

class shaderc_compiler_t(seg: MemorySegment) : IHeapObject(seg)
class shaderc_compile_options_t(seg: MemorySegment) : IHeapObject(seg)
class shaderc_compilation_result_t(seg: MemorySegment) : IHeapObject(seg)

object Shaderc {
    const val shaderc_target_env_vulkan: Int = 0
    const val shaderc_target_env_opengl: Int = 1
    const val shaderc_target_env_opengl_compat: Int = 2
    const val shaderc_target_env_webgpu: Int = 3
    const val shaderc_target_env_default: Int = 0
    const val shaderc_env_version_vulkan_1_0: Int = 4194304
    const val shaderc_env_version_vulkan_1_1: Int = 4198400
    const val shaderc_env_version_vulkan_1_2: Int = 4202496
    const val shaderc_env_version_vulkan_1_3: Int = 4206592
    const val shaderc_env_version_opengl_4_5: Int = 450
    const val shaderc_env_version_webgpu: Int = 451
    const val shaderc_spirv_version_1_0: Int = 65536
    const val shaderc_spirv_version_1_1: Int = 65792
    const val shaderc_spirv_version_1_2: Int = 66048
    const val shaderc_spirv_version_1_3: Int = 66304
    const val shaderc_spirv_version_1_4: Int = 66560
    const val shaderc_spirv_version_1_5: Int = 66816
    const val shaderc_spirv_version_1_6: Int = 67072
    const val shaderc_compilation_status_success: Int = 0
    const val shaderc_compilation_status_invalid_stage: Int = 1
    const val shaderc_compilation_status_compilation_error: Int = 2
    const val shaderc_compilation_status_internal_error: Int = 3
    const val shaderc_compilation_status_null_result_object: Int = 4
    const val shaderc_compilation_status_invalid_assembly: Int = 5
    const val shaderc_compilation_status_validation_error: Int = 6
    const val shaderc_compilation_status_transformation_error: Int = 7
    const val shaderc_compilation_status_configuration_error: Int = 8
    const val shaderc_source_language_glsl: Int = 0
    const val shaderc_source_language_hlsl: Int = 1
    const val shaderc_vertex_shader: Int = 0
    const val shaderc_fragment_shader: Int = 1
    const val shaderc_compute_shader: Int = 2
    const val shaderc_geometry_shader: Int = 3
    const val shaderc_tess_control_shader: Int = 4
    const val shaderc_tess_evaluation_shader: Int = 5
    const val shaderc_glsl_vertex_shader: Int = 0
    const val shaderc_glsl_fragment_shader: Int = 1
    const val shaderc_glsl_compute_shader: Int = 2
    const val shaderc_glsl_geometry_shader: Int = 3
    const val shaderc_glsl_tess_control_shader: Int = 4
    const val shaderc_glsl_tess_evaluation_shader: Int = 5
    const val shaderc_glsl_infer_from_source: Int = 6
    const val shaderc_glsl_default_vertex_shader: Int = 7
    const val shaderc_glsl_default_fragment_shader: Int = 8
    const val shaderc_glsl_default_compute_shader: Int = 9
    const val shaderc_glsl_default_geometry_shader: Int = 10
    const val shaderc_glsl_default_tess_control_shader: Int = 11
    const val shaderc_glsl_default_tess_evaluation_shader: Int = 12
    const val shaderc_spirv_assembly: Int = 13
    const val shaderc_raygen_shader: Int = 14
    const val shaderc_anyhit_shader: Int = 15
    const val shaderc_closesthit_shader: Int = 16
    const val shaderc_miss_shader: Int = 17
    const val shaderc_intersection_shader: Int = 18
    const val shaderc_callable_shader: Int = 19
    const val shaderc_glsl_raygen_shader: Int = 14
    const val shaderc_glsl_anyhit_shader: Int = 15
    const val shaderc_glsl_closesthit_shader: Int = 16
    const val shaderc_glsl_miss_shader: Int = 17
    const val shaderc_glsl_intersection_shader: Int = 18
    const val shaderc_glsl_callable_shader: Int = 19
    const val shaderc_glsl_default_raygen_shader: Int = 20
    const val shaderc_glsl_default_anyhit_shader: Int = 21
    const val shaderc_glsl_default_closesthit_shader: Int = 22
    const val shaderc_glsl_default_miss_shader: Int = 23
    const val shaderc_glsl_default_intersection_shader: Int = 24
    const val shaderc_glsl_default_callable_shader: Int = 25
    const val shaderc_task_shader: Int = 26
    const val shaderc_mesh_shader: Int = 27
    const val shaderc_glsl_task_shader: Int = 26
    const val shaderc_glsl_mesh_shader: Int = 27
    const val shaderc_glsl_default_task_shader: Int = 28
    const val shaderc_glsl_default_mesh_shader: Int = 29
    const val shaderc_profile_none: Int = 0
    const val shaderc_profile_core: Int = 1
    const val shaderc_profile_compatibility: Int = 2
    const val shaderc_profile_es: Int = 3
    const val shaderc_optimization_level_zero: Int = 0
    const val shaderc_optimization_level_size: Int = 1
    const val shaderc_optimization_level_performance: Int = 2
    const val shaderc_limit_max_lights: Int = 0
    const val shaderc_limit_max_clip_planes: Int = 1
    const val shaderc_limit_max_texture_units: Int = 2
    const val shaderc_limit_max_texture_coords: Int = 3
    const val shaderc_limit_max_vertex_attribs: Int = 4
    const val shaderc_limit_max_vertex_uniform_components: Int = 5
    const val shaderc_limit_max_varying_floats: Int = 6
    const val shaderc_limit_max_vertex_texture_image_units: Int = 7
    const val shaderc_limit_max_combined_texture_image_units: Int = 8
    const val shaderc_limit_max_texture_image_units: Int = 9
    const val shaderc_limit_max_fragment_uniform_components: Int = 10
    const val shaderc_limit_max_draw_buffers: Int = 11
    const val shaderc_limit_max_vertex_uniform_vectors: Int = 12
    const val shaderc_limit_max_varying_vectors: Int = 13
    const val shaderc_limit_max_fragment_uniform_vectors: Int = 14
    const val shaderc_limit_max_vertex_output_vectors: Int = 15
    const val shaderc_limit_max_fragment_input_vectors: Int = 16
    const val shaderc_limit_min_program_texel_offset: Int = 17
    const val shaderc_limit_max_program_texel_offset: Int = 18
    const val shaderc_limit_max_clip_distances: Int = 19
    const val shaderc_limit_max_compute_work_group_count_x: Int = 20
    const val shaderc_limit_max_compute_work_group_count_y: Int = 21
    const val shaderc_limit_max_compute_work_group_count_z: Int = 22
    const val shaderc_limit_max_compute_work_group_size_x: Int = 23
    const val shaderc_limit_max_compute_work_group_size_y: Int = 24
    const val shaderc_limit_max_compute_work_group_size_z: Int = 25
    const val shaderc_limit_max_compute_uniform_components: Int = 26
    const val shaderc_limit_max_compute_texture_image_units: Int = 27
    const val shaderc_limit_max_compute_image_uniforms: Int = 28
    const val shaderc_limit_max_compute_atomic_counters: Int = 29
    const val shaderc_limit_max_compute_atomic_counter_buffers: Int = 30
    const val shaderc_limit_max_varying_components: Int = 31
    const val shaderc_limit_max_vertex_output_components: Int = 32
    const val shaderc_limit_max_geometry_input_components: Int = 33
    const val shaderc_limit_max_geometry_output_components: Int = 34
    const val shaderc_limit_max_fragment_input_components: Int = 35
    const val shaderc_limit_max_image_units: Int = 36
    const val shaderc_limit_max_combined_image_units_and_fragment_outputs: Int = 37
    const val shaderc_limit_max_combined_shader_output_resources: Int = 38
    const val shaderc_limit_max_image_samples: Int = 39
    const val shaderc_limit_max_vertex_image_uniforms: Int = 40
    const val shaderc_limit_max_tess_control_image_uniforms: Int = 41
    const val shaderc_limit_max_tess_evaluation_image_uniforms: Int = 42
    const val shaderc_limit_max_geometry_image_uniforms: Int = 43
    const val shaderc_limit_max_fragment_image_uniforms: Int = 44
    const val shaderc_limit_max_combined_image_uniforms: Int = 45
    const val shaderc_limit_max_geometry_texture_image_units: Int = 46
    const val shaderc_limit_max_geometry_output_vertices: Int = 47
    const val shaderc_limit_max_geometry_total_output_components: Int = 48
    const val shaderc_limit_max_geometry_uniform_components: Int = 49
    const val shaderc_limit_max_geometry_varying_components: Int = 50
    const val shaderc_limit_max_tess_control_input_components: Int = 51
    const val shaderc_limit_max_tess_control_output_components: Int = 52
    const val shaderc_limit_max_tess_control_texture_image_units: Int = 53
    const val shaderc_limit_max_tess_control_uniform_components: Int = 54
    const val shaderc_limit_max_tess_control_total_output_components: Int = 55
    const val shaderc_limit_max_tess_evaluation_input_components: Int = 56
    const val shaderc_limit_max_tess_evaluation_output_components: Int = 57
    const val shaderc_limit_max_tess_evaluation_texture_image_units: Int = 58
    const val shaderc_limit_max_tess_evaluation_uniform_components: Int = 59
    const val shaderc_limit_max_tess_patch_components: Int = 60
    const val shaderc_limit_max_patch_vertices: Int = 61
    const val shaderc_limit_max_tess_gen_level: Int = 62
    const val shaderc_limit_max_viewports: Int = 63
    const val shaderc_limit_max_vertex_atomic_counters: Int = 64
    const val shaderc_limit_max_tess_control_atomic_counters: Int = 65
    const val shaderc_limit_max_tess_evaluation_atomic_counters: Int = 66
    const val shaderc_limit_max_geometry_atomic_counters: Int = 67
    const val shaderc_limit_max_fragment_atomic_counters: Int = 68
    const val shaderc_limit_max_combined_atomic_counters: Int = 69
    const val shaderc_limit_max_atomic_counter_bindings: Int = 70
    const val shaderc_limit_max_vertex_atomic_counter_buffers: Int = 71
    const val shaderc_limit_max_tess_control_atomic_counter_buffers: Int = 72
    const val shaderc_limit_max_tess_evaluation_atomic_counter_buffers: Int = 73
    const val shaderc_limit_max_geometry_atomic_counter_buffers: Int = 74
    const val shaderc_limit_max_fragment_atomic_counter_buffers: Int = 75
    const val shaderc_limit_max_combined_atomic_counter_buffers: Int = 76
    const val shaderc_limit_max_atomic_counter_buffer_size: Int = 77
    const val shaderc_limit_max_transform_feedback_buffers: Int = 78
    const val shaderc_limit_max_transform_feedback_interleaved_components: Int = 79
    const val shaderc_limit_max_cull_distances: Int = 80
    const val shaderc_limit_max_combined_clip_and_cull_distances: Int = 81
    const val shaderc_limit_max_samples: Int = 82
    const val shaderc_limit_max_mesh_output_vertices_nv: Int = 83
    const val shaderc_limit_max_mesh_output_primitives_nv: Int = 84
    const val shaderc_limit_max_mesh_work_group_size_x_nv: Int = 85
    const val shaderc_limit_max_mesh_work_group_size_y_nv: Int = 86
    const val shaderc_limit_max_mesh_work_group_size_z_nv: Int = 87
    const val shaderc_limit_max_task_work_group_size_x_nv: Int = 88
    const val shaderc_limit_max_task_work_group_size_y_nv: Int = 89
    const val shaderc_limit_max_task_work_group_size_z_nv: Int = 90
    const val shaderc_limit_max_mesh_view_count_nv: Int = 91
    const val shaderc_limit_max_mesh_output_vertices_ext: Int = 92
    const val shaderc_limit_max_mesh_output_primitives_ext: Int = 93
    const val shaderc_limit_max_mesh_work_group_size_x_ext: Int = 94
    const val shaderc_limit_max_mesh_work_group_size_y_ext: Int = 95
    const val shaderc_limit_max_mesh_work_group_size_z_ext: Int = 96
    const val shaderc_limit_max_task_work_group_size_x_ext: Int = 97
    const val shaderc_limit_max_task_work_group_size_y_ext: Int = 98
    const val shaderc_limit_max_task_work_group_size_z_ext: Int = 99
    const val shaderc_limit_max_mesh_view_count_ext: Int = 100
    const val shaderc_limit_max_dual_source_draw_buffers_ext: Int = 101
    const val shaderc_uniform_kind_image: Int = 0
    const val shaderc_uniform_kind_sampler: Int = 1
    const val shaderc_uniform_kind_texture: Int = 2
    const val shaderc_uniform_kind_buffer: Int = 3
    const val shaderc_uniform_kind_storage_buffer: Int = 4
    const val shaderc_uniform_kind_unordered_access_view: Int = 5
    const val shaderc_include_type_relative: Int = 0
    const val shaderc_include_type_standard: Int = 1

    fun shaderc_compiler_initialize(): shaderc_compiler_t =
        shaderc_compiler_t(callPointerFunc("shaderc_compiler_initialize"))

    fun shaderc_compiler_release(compiler: shaderc_compiler_t) = callVoidFunc("shaderc_compiler_release", compiler)
    fun shaderc_compile_options_initialize(): shaderc_compile_options_t =
        shaderc_compile_options_t(callPointerFunc("shaderc_compile_options_initialize"))

    fun shaderc_compile_options_clone(src: shaderc_compile_options_t): shaderc_compile_options_t =
        shaderc_compile_options_t(callPointerFunc("shaderc_compile_options_clone", src))

    fun shaderc_compile_options_release(options: shaderc_compile_options_t) =
        callVoidFunc("shaderc_compile_options_release", options)

    fun shaderc_compile_options_add_macro_definition(options: shaderc_compile_options_t, name: String, value: String) =
        callVoidFunc(
            "shaderc_compile_options_add_macro_definition",
            options,
            name.toCString(),
            name.toByteArray().size,
            value.toCString(),
            value.toByteArray().size
        )

    fun shaderc_compile_options_set_source_language(options: shaderc_compile_options_t, lang: Int) =
        callVoidFunc("shaderc_compile_options_set_source_language", options, lang)

    fun shaderc_compile_options_set_generate_debug_info(options: shaderc_compile_options_t) =
        callVoidFunc("shaderc_compile_options_set_generate_debug_info", options)

    fun shaderc_compile_options_set_optimization_level(options: shaderc_compile_options_t, level: Int) =
        callVoidFunc("shaderc_compile_options_set_optimization_level", options, level)

    fun shaderc_compile_options_set_forced_version_profile(
        options: shaderc_compile_options_t,
        version: Int,
        profile: Int
    ) = callVoidFunc("shaderc_compile_options_set_forced_version_profile", options, version, profile)

    fun shaderc_compile_options_set_include_callbacks(
        options: shaderc_compile_options_t,
        resolve: IShadercIncludeResolve,
        resultReleaser: IShadercIncludeResultRelease,
        userData: MemorySegment
    ) = callVoidFunc("shaderc_compile_options_set_include_callbacks", options, resolve, resultReleaser, userData)

    fun shaderc_compile_options_set_suppress_warnings(options: shaderc_compile_options_t) =
        callVoidFunc("shaderc_compile_options_set_suppress_warnings", options)

    fun shaderc_compile_options_set_target_env(options: shaderc_compile_options_t, target: Int, version: Int) =
        callVoidFunc("shaderc_compile_options_set_target_env", options, target, version)

    fun shaderc_compile_options_set_target_spirv(options: shaderc_compile_options_t, version: Int) =
        callVoidFunc("shaderc_compile_options_set_target_spirv", options, version)

    fun shaderc_compile_options_set_warnings_as_errors(options: shaderc_compile_options_t) =
        callVoidFunc("shaderc_compile_options_set_warnings_as_errors", options)

    fun shaderc_compile_options_set_limit(options: shaderc_compile_options_t, limit: Int, value: Int) =
        callVoidFunc("shaderc_compile_options_set_limit", options, limit, value)

    fun shaderc_compile_options_set_auto_bind_uniforms(options: shaderc_compile_options_t, autoBind: Boolean) =
        callVoidFunc("shaderc_compile_options_set_auto_bind_uniforms", options, autoBind)

    fun shaderc_compile_options_set_auto_combined_image_sampler(options: shaderc_compile_options_t, upgrade: Boolean) =
        callVoidFunc("shaderc_compile_options_set_auto_combined_image_sampler", options, upgrade)

    fun shaderc_compile_options_set_hlsl_io_mapping(options: shaderc_compile_options_t, hlslIomap: Boolean) =
        callVoidFunc("shaderc_compile_options_set_hlsl_io_mapping", options, hlslIomap)

    fun shaderc_compile_options_set_hlsl_offsets(options: shaderc_compile_options_t, hlslOffsets: Boolean) =
        callVoidFunc("shaderc_compile_options_set_hlsl_offsets", options, hlslOffsets)

    fun shaderc_compile_options_set_binding_base(options: shaderc_compile_options_t, kind: Int, base: Int) =
        callVoidFunc("shaderc_compile_options_set_binding_base", options, kind, base)

    fun shaderc_compile_options_set_binding_base_for_stage(
        options: shaderc_compile_options_t,
        shaderKind: Int,
        kind: Int,
        base: Int
    ) = callVoidFunc("shaderc_compile_options_set_binding_base_for_stage", options, shaderKind, kind, base)

    fun shaderc_compile_options_set_preserve_bindings(options: shaderc_compile_options_t, preserveBindings: Boolean) =
        callVoidFunc("shaderc_compile_options_set_preserve_bindings", options, preserveBindings)

    fun shaderc_compile_options_set_auto_map_locations(options: shaderc_compile_options_t, autoMap: Boolean) =
        callVoidFunc("shaderc_compile_options_set_auto_map_locations", options, autoMap)

    fun shaderc_compile_options_set_hlsl_register_set_and_binding_for_stage(
        options: shaderc_compile_options_t,
        shaderKind: Int,
        reg: String,
        binding: String
    ) = callVoidFunc(
        "shaderc_compile_options_set_hlsl_register_set_and_binding_for_stage",
        options,
        shaderKind,
        reg.toCString(),
        binding.toCString()
    )

    fun shaderc_compile_options_set_hlsl_register_set_and_binding(
        options: shaderc_compile_options_t,
        reg: String,
        set: String,
        binding: String
    ) = callVoidFunc(
        "shaderc_compile_options_set_hlsl_register_set_and_binding",
        options,
        reg.toCString(),
        set.toCString(),
        binding.toCString()
    )

    fun shaderc_compile_options_set_hlsl_functionality1(options: shaderc_compile_options_t, enable: Boolean) =
        callVoidFunc("shaderc_compile_options_set_hlsl_functionality1", options, enable)

    fun shaderc_compile_options_set_hlsl_16bit_types(options: shaderc_compile_options_t, enable: Boolean) =
        callVoidFunc("shaderc_compile_options_set_hlsl_16bit_types", options, enable)

    fun shaderc_compile_options_set_vulkan_rules_relaxed(options: shaderc_compile_options_t, enable: Boolean) =
        callVoidFunc("shaderc_compile_options_set_vulkan_rules_relaxed", options, enable)

    fun shaderc_compile_options_set_invert_y(options: shaderc_compile_options_t, enable: Boolean) =
        callVoidFunc("shaderc_compile_options_set_invert_y", options, enable)

    fun shaderc_compile_options_set_nan_clamp(options: shaderc_compile_options_t, enable: Boolean) =
        callVoidFunc("shaderc_compile_options_set_nan_clamp", options, enable)

    fun shaderc_compile_into_spv(
        compiler: shaderc_compiler_t,
        source: String,
        shaderKind: Int,
        fileName: String,
        entryPoint: String,
        options: shaderc_compile_options_t
    ): shaderc_compilation_result_t = shaderc_compilation_result_t(
        callPointerFunc(
            "shaderc_compile_into_spv",
            compiler,
            source.toCString(),
            source.toByteArray().size.toLong(),
            shaderKind,
            fileName.toCString(),
            entryPoint.toCString(),
            options
        )
    )

    fun shaderc_compile_into_spv_assembly(
        compiler: shaderc_compiler_t,
        source: String,
        shaderKind: Int,
        fileName: String,
        entryPoint: String,
        options: shaderc_compile_options_t
    ): shaderc_compilation_result_t = shaderc_compilation_result_t(
        callPointerFunc(
            "shaderc_compile_into_spv_assembly",
            compiler,
            source.toCString(),
            source.toByteArray().size.toLong(),
            shaderKind,
            fileName.toCString(),
            entryPoint.toCString(),
            options
        )
    )

    fun shaderc_compile_into_preprocessed_text(
        compiler: shaderc_compiler_t,
        source: String,
        shaderKind: Int,
        fileName: String,
        entryPoint: String,
        options: shaderc_compile_options_t
    ): shaderc_compilation_result_t = shaderc_compilation_result_t(
        callPointerFunc(
            "shaderc_compile_into_preprocessed_text",
            compiler,
            source.toCString(),
            source.toByteArray().size.toLong(),
            shaderKind,
            fileName.toCString(),
            entryPoint.toCString(),
            options
        )
    )

    fun shaderc_assemble_into_spv(compiler: shaderc_compiler_t, source: String, options: shaderc_compile_options_t) =
        callVoidFunc("shaderc_assemble_into_spv", compiler, source, source.toByteArray().size.toLong(), options)

    fun shaderc_result_release(result: shaderc_compilation_result_t) = callVoidFunc("shaderc_result_release", result)
    fun shaderc_result_get_length(result: shaderc_compilation_result_t): Long =
        callFunc("shaderc_result_get_length", Long::class, result)

    fun shaderc_result_get_num_warnings(result: shaderc_compilation_result_t): Long =
        callFunc("shaderc_result_get_num_warnings", Long::class, result)

    fun shaderc_result_get_num_errors(result: shaderc_compilation_result_t): Long =
        callFunc("shaderc_result_get_num_errors", Long::class, result)

    fun shaderc_result_get_compilation_status(result: shaderc_compilation_result_t): Int =
        callFunc("shaderc_result_get_compilation_status", Int::class, result)

    fun shaderc_result_get_bytes(result: shaderc_compilation_result_t): ByteBuffer =
        callPointerFunc("shaderc_result_get_bytes", result).reinterpret(shaderc_result_get_length(result))
            .asByteBuffer()

    fun shaderc_result_get_error_message(result: shaderc_compilation_result_t): String =
        callPointerFunc("shaderc_result_get_error_message", result).fetchString()

    fun shaderc_get_spv_version(version: HeapInt, revision: HeapInt) =
        callVoidFunc("shaderc_get_spv_version", version, revision)

    fun shaderc_parse_version_profile(str: String, version: HeapInt, profile: HeapInt): Boolean =
        callFunc("shaderc_parse_version_profile", Boolean::class, str.toCString(), version, profile)

}