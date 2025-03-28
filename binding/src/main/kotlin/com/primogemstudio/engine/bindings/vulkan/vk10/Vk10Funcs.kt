package com.primogemstudio.engine.bindings.vulkan.vk10

import com.primogemstudio.engine.bindings.vulkan.memory.VkAllocationCallbacks
import com.primogemstudio.engine.foreign.NativeMethodCache.callFunc
import com.primogemstudio.engine.foreign.NativeMethodCache.callPointerFunc
import com.primogemstudio.engine.foreign.NativeMethodCache.callVoidFunc
import com.primogemstudio.engine.foreign.heap.*
import com.primogemstudio.engine.foreign.toCString
import com.primogemstudio.engine.foreign.toPointerArray
import com.primogemstudio.engine.foreign.unbox
import com.primogemstudio.engine.loader.plat.Platform.sizetLength
import com.primogemstudio.engine.types.Result
import org.joml.Vector2i
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.ADDRESS
import java.lang.foreign.ValueLayout.JAVA_INT
import java.nio.ByteBuffer
import kotlin.math.min

class VkInstance(seg: MemorySegment) : IHeapObject(seg)
class VkPhysicalDevice(seg: MemorySegment) : IHeapObject(seg)
class VkDevice(seg: MemorySegment) : IHeapObject(seg)
class VkQueue(seg: MemorySegment) : IHeapObject(seg)
class VkFence(seg: MemorySegment) : IHeapObject(seg)
class VkSemaphore(seg: MemorySegment) : IHeapObject(seg)
class VkCommandBuffer(seg: MemorySegment) : IHeapObject(seg)
class VkDeviceMemory(seg: MemorySegment) : IHeapObject(seg)
class VkBuffer(seg: MemorySegment) : IHeapObject(seg)
class VkImage(seg: MemorySegment) : IHeapObject(seg)
class VkEvent(seg: MemorySegment) : IHeapObject(seg)
class VkQueryPool(seg: MemorySegment) : IHeapObject(seg)
class VkBufferView(seg: MemorySegment) : IHeapObject(seg)
class VkImageView(seg: MemorySegment) : IHeapObject(seg)
class VkShaderModule(seg: MemorySegment) : IHeapObject(seg)
class VkPipelineCache(seg: MemorySegment) : IHeapObject(seg)
class VkPipeline(seg: MemorySegment) : IHeapObject(seg)
class VkPipelineLayout(seg: MemorySegment) : IHeapObject(seg)
class VkRenderPass(seg: MemorySegment) : IHeapObject(seg)
class VkDescriptorSetLayout(seg: MemorySegment) : IHeapObject(seg)
class VkSampler(seg: MemorySegment) : IHeapObject(seg)
class VkDescriptorPool(seg: MemorySegment) : IHeapObject(seg)
class VkDescriptorSet(seg: MemorySegment) : IHeapObject(seg)
class VkCommandPool(seg: MemorySegment) : IHeapObject(seg)
class VkFramebuffer(seg: MemorySegment) : IHeapObject(seg)

object Vk10Funcs {
    const val VK_SUCCESS: Int = 0
    const val VK_NOT_READY: Int = 1
    const val VK_TIMEOUT: Int = 2
    const val VK_EVENT_SET: Int = 3
    const val VK_EVENT_RESET: Int = 4
    const val VK_INCOMPLETE: Int = 5
    const val VK_ERROR_OUT_OF_HOST_MEMORY: Int = -1
    const val VK_ERROR_OUT_OF_DEVICE_MEMORY: Int = -2
    const val VK_ERROR_INITIALIZATION_FAILED: Int = -3
    const val VK_ERROR_DEVICE_LOST: Int = -4
    const val VK_ERROR_MEMORY_MAP_FAILED: Int = -5
    const val VK_ERROR_LAYER_NOT_PRESENT: Int = -6
    const val VK_ERROR_EXTENSION_NOT_PRESENT: Int = -7
    const val VK_ERROR_FEATURE_NOT_PRESENT: Int = -8
    const val VK_ERROR_INCOMPATIBLE_DRIVER: Int = -9
    const val VK_ERROR_TOO_MANY_OBJECTS: Int = -10
    const val VK_ERROR_FORMAT_NOT_SUPPORTED: Int = -11
    const val VK_ERROR_FRAGMENTED_POOL: Int = -12
    const val VK_ERROR_UNKNOWN: Int = -13
    const val VK_STRUCTURE_TYPE_APPLICATION_INFO: Int = 0
    const val VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO: Int = 1
    const val VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO: Int = 2
    const val VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO: Int = 3
    const val VK_STRUCTURE_TYPE_SUBMIT_INFO: Int = 4
    const val VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO: Int = 5
    const val VK_STRUCTURE_TYPE_MAPPED_MEMORY_RANGE: Int = 6
    const val VK_STRUCTURE_TYPE_BIND_SPARSE_INFO: Int = 7
    const val VK_STRUCTURE_TYPE_FENCE_CREATE_INFO: Int = 8
    const val VK_STRUCTURE_TYPE_SEMAPHORE_CREATE_INFO: Int = 9
    const val VK_STRUCTURE_TYPE_EVENT_CREATE_INFO: Int = 10
    const val VK_STRUCTURE_TYPE_QUERY_POOL_CREATE_INFO: Int = 11
    const val VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO: Int = 12
    const val VK_STRUCTURE_TYPE_BUFFER_VIEW_CREATE_INFO: Int = 13
    const val VK_STRUCTURE_TYPE_IMAGE_CREATE_INFO: Int = 14
    const val VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO: Int = 15
    const val VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO: Int = 16
    const val VK_STRUCTURE_TYPE_PIPELINE_CACHE_CREATE_INFO: Int = 17
    const val VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO: Int = 18
    const val VK_STRUCTURE_TYPE_PIPELINE_VERTEX_INPUT_STATE_CREATE_INFO: Int = 19
    const val VK_STRUCTURE_TYPE_PIPELINE_INPUT_ASSEMBLY_STATE_CREATE_INFO: Int = 20
    const val VK_STRUCTURE_TYPE_PIPELINE_TESSELLATION_STATE_CREATE_INFO: Int = 21
    const val VK_STRUCTURE_TYPE_PIPELINE_VIEWPORT_STATE_CREATE_INFO: Int = 22
    const val VK_STRUCTURE_TYPE_PIPELINE_RASTERIZATION_STATE_CREATE_INFO: Int = 23
    const val VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO: Int = 24
    const val VK_STRUCTURE_TYPE_PIPELINE_DEPTH_STENCIL_STATE_CREATE_INFO: Int = 25
    const val VK_STRUCTURE_TYPE_PIPELINE_COLOR_BLEND_STATE_CREATE_INFO: Int = 26
    const val VK_STRUCTURE_TYPE_PIPELINE_DYNAMIC_STATE_CREATE_INFO: Int = 27
    const val VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO: Int = 28
    const val VK_STRUCTURE_TYPE_COMPUTE_PIPELINE_CREATE_INFO: Int = 29
    const val VK_STRUCTURE_TYPE_PIPELINE_LAYOUT_CREATE_INFO: Int = 30
    const val VK_STRUCTURE_TYPE_SAMPLER_CREATE_INFO: Int = 31
    const val VK_STRUCTURE_TYPE_DESCRIPTOR_SET_LAYOUT_CREATE_INFO: Int = 32
    const val VK_STRUCTURE_TYPE_DESCRIPTOR_POOL_CREATE_INFO: Int = 33
    const val VK_STRUCTURE_TYPE_DESCRIPTOR_SET_ALLOCATE_INFO: Int = 34
    const val VK_STRUCTURE_TYPE_WRITE_DESCRIPTOR_SET: Int = 35
    const val VK_STRUCTURE_TYPE_COPY_DESCRIPTOR_SET: Int = 36
    const val VK_STRUCTURE_TYPE_FRAMEBUFFER_CREATE_INFO: Int = 37
    const val VK_STRUCTURE_TYPE_RENDER_PASS_CREATE_INFO: Int = 38
    const val VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO: Int = 39
    const val VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO: Int = 40
    const val VK_STRUCTURE_TYPE_COMMAND_BUFFER_INHERITANCE_INFO: Int = 41
    const val VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO: Int = 42
    const val VK_STRUCTURE_TYPE_RENDER_PASS_BEGIN_INFO: Int = 43
    const val VK_STRUCTURE_TYPE_BUFFER_MEMORY_BARRIER: Int = 44
    const val VK_STRUCTURE_TYPE_IMAGE_MEMORY_BARRIER: Int = 45
    const val VK_STRUCTURE_TYPE_MEMORY_BARRIER: Int = 46
    const val VK_STRUCTURE_TYPE_LOADER_INSTANCE_CREATE_INFO: Int = 47
    const val VK_STRUCTURE_TYPE_LOADER_DEVICE_CREATE_INFO: Int = 48
    const val VK_PIPELINE_CACHE_HEADER_VERSION_ONE: Int = 1
    const val VK_ACCESS_INDIRECT_COMMAND_READ_BIT: Int = 1
    const val VK_ACCESS_INDEX_READ_BIT: Int = 2
    const val VK_ACCESS_VERTEX_ATTRIBUTE_READ_BIT: Int = 4
    const val VK_ACCESS_UNIFORM_READ_BIT: Int = 8
    const val VK_ACCESS_INPUT_ATTACHMENT_READ_BIT: Int = 16
    const val VK_ACCESS_SHADER_READ_BIT: Int = 32
    const val VK_ACCESS_SHADER_WRITE_BIT: Int = 64
    const val VK_ACCESS_COLOR_ATTACHMENT_READ_BIT: Int = 128
    const val VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT: Int = 256
    const val VK_ACCESS_DEPTH_STENCIL_ATTACHMENT_READ_BIT: Int = 512
    const val VK_ACCESS_DEPTH_STENCIL_ATTACHMENT_WRITE_BIT: Int = 1024
    const val VK_ACCESS_TRANSFER_READ_BIT: Int = 2048
    const val VK_ACCESS_TRANSFER_WRITE_BIT: Int = 4096
    const val VK_ACCESS_HOST_READ_BIT: Int = 8192
    const val VK_ACCESS_HOST_WRITE_BIT: Int = 16384
    const val VK_ACCESS_MEMORY_READ_BIT: Int = 32768
    const val VK_ACCESS_MEMORY_WRITE_BIT: Int = 65536
    const val VK_IMAGE_LAYOUT_UNDEFINED: Int = 0
    const val VK_IMAGE_LAYOUT_GENERAL: Int = 1
    const val VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL: Int = 2
    const val VK_IMAGE_LAYOUT_DEPTH_STENCIL_ATTACHMENT_OPTIMAL: Int = 3
    const val VK_IMAGE_LAYOUT_DEPTH_STENCIL_READ_ONLY_OPTIMAL: Int = 4
    const val VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL: Int = 5
    const val VK_IMAGE_LAYOUT_TRANSFER_SRC_OPTIMAL: Int = 6
    const val VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL: Int = 7
    const val VK_IMAGE_LAYOUT_PREINITIALIZED: Int = 8
    const val VK_IMAGE_ASPECT_COLOR_BIT: Int = 1
    const val VK_IMAGE_ASPECT_DEPTH_BIT: Int = 2
    const val VK_IMAGE_ASPECT_STENCIL_BIT: Int = 4
    const val VK_IMAGE_ASPECT_METADATA_BIT: Int = 8
    const val VK_OBJECT_TYPE_UNKNOWN: Int = 0
    const val VK_OBJECT_TYPE_INSTANCE: Int = 1
    const val VK_OBJECT_TYPE_PHYSICAL_DEVICE: Int = 2
    const val VK_OBJECT_TYPE_DEVICE: Int = 3
    const val VK_OBJECT_TYPE_QUEUE: Int = 4
    const val VK_OBJECT_TYPE_SEMAPHORE: Int = 5
    const val VK_OBJECT_TYPE_COMMAND_BUFFER: Int = 6
    const val VK_OBJECT_TYPE_FENCE: Int = 7
    const val VK_OBJECT_TYPE_DEVICE_MEMORY: Int = 8
    const val VK_OBJECT_TYPE_BUFFER: Int = 9
    const val VK_OBJECT_TYPE_IMAGE: Int = 10
    const val VK_OBJECT_TYPE_EVENT: Int = 11
    const val VK_OBJECT_TYPE_QUERY_POOL: Int = 12
    const val VK_OBJECT_TYPE_BUFFER_VIEW: Int = 13
    const val VK_OBJECT_TYPE_IMAGE_VIEW: Int = 14
    const val VK_OBJECT_TYPE_SHADER_MODULE: Int = 15
    const val VK_OBJECT_TYPE_PIPELINE_CACHE: Int = 16
    const val VK_OBJECT_TYPE_PIPELINE_LAYOUT: Int = 17
    const val VK_OBJECT_TYPE_RENDER_PASS: Int = 18
    const val VK_OBJECT_TYPE_PIPELINE: Int = 19
    const val VK_OBJECT_TYPE_DESCRIPTOR_SET_LAYOUT: Int = 20
    const val VK_OBJECT_TYPE_SAMPLER: Int = 21
    const val VK_OBJECT_TYPE_DESCRIPTOR_POOL: Int = 22
    const val VK_OBJECT_TYPE_DESCRIPTOR_SET: Int = 23
    const val VK_OBJECT_TYPE_FRAMEBUFFER: Int = 24
    const val VK_OBJECT_TYPE_COMMAND_POOL: Int = 25
    const val VK_VENDOR_ID_KHRONOS: Int = 65536
    const val VK_VENDOR_ID_VIV: Int = 65537
    const val VK_VENDOR_ID_VSI: Int = 65538
    const val VK_VENDOR_ID_KAZAN: Int = 65539
    const val VK_VENDOR_ID_CODEPLAY: Int = 65540
    const val VK_VENDOR_ID_MESA: Int = 65541
    const val VK_VENDOR_ID_POCL: Int = 65542
    const val VK_VENDOR_ID_MOBILEYE: Int = 65543
    const val VK_SYSTEM_ALLOCATION_SCOPE_COMMAND: Int = 0
    const val VK_SYSTEM_ALLOCATION_SCOPE_OBJECT: Int = 1
    const val VK_SYSTEM_ALLOCATION_SCOPE_CACHE: Int = 2
    const val VK_SYSTEM_ALLOCATION_SCOPE_DEVICE: Int = 3
    const val VK_SYSTEM_ALLOCATION_SCOPE_INSTANCE: Int = 4
    const val VK_INTERNAL_ALLOCATION_TYPE_EXECUTABLE: Int = 0
    const val VK_FORMAT_UNDEFINED: Int = 0
    const val VK_FORMAT_R4G4_UNORM_PACK8: Int = 1
    const val VK_FORMAT_R4G4B4A4_UNORM_PACK16: Int = 2
    const val VK_FORMAT_B4G4R4A4_UNORM_PACK16: Int = 3
    const val VK_FORMAT_R5G6B5_UNORM_PACK16: Int = 4
    const val VK_FORMAT_B5G6R5_UNORM_PACK16: Int = 5
    const val VK_FORMAT_R5G5B5A1_UNORM_PACK16: Int = 6
    const val VK_FORMAT_B5G5R5A1_UNORM_PACK16: Int = 7
    const val VK_FORMAT_A1R5G5B5_UNORM_PACK16: Int = 8
    const val VK_FORMAT_R8_UNORM: Int = 9
    const val VK_FORMAT_R8_SNORM: Int = 10
    const val VK_FORMAT_R8_USCALED: Int = 11
    const val VK_FORMAT_R8_SSCALED: Int = 12
    const val VK_FORMAT_R8_UINT: Int = 13
    const val VK_FORMAT_R8_SINT: Int = 14
    const val VK_FORMAT_R8_SRGB: Int = 15
    const val VK_FORMAT_R8G8_UNORM: Int = 16
    const val VK_FORMAT_R8G8_SNORM: Int = 17
    const val VK_FORMAT_R8G8_USCALED: Int = 18
    const val VK_FORMAT_R8G8_SSCALED: Int = 19
    const val VK_FORMAT_R8G8_UINT: Int = 20
    const val VK_FORMAT_R8G8_SINT: Int = 21
    const val VK_FORMAT_R8G8_SRGB: Int = 22
    const val VK_FORMAT_R8G8B8_UNORM: Int = 23
    const val VK_FORMAT_R8G8B8_SNORM: Int = 24
    const val VK_FORMAT_R8G8B8_USCALED: Int = 25
    const val VK_FORMAT_R8G8B8_SSCALED: Int = 26
    const val VK_FORMAT_R8G8B8_UINT: Int = 27
    const val VK_FORMAT_R8G8B8_SINT: Int = 28
    const val VK_FORMAT_R8G8B8_SRGB: Int = 29
    const val VK_FORMAT_B8G8R8_UNORM: Int = 30
    const val VK_FORMAT_B8G8R8_SNORM: Int = 31
    const val VK_FORMAT_B8G8R8_USCALED: Int = 32
    const val VK_FORMAT_B8G8R8_SSCALED: Int = 33
    const val VK_FORMAT_B8G8R8_UINT: Int = 34
    const val VK_FORMAT_B8G8R8_SINT: Int = 35
    const val VK_FORMAT_B8G8R8_SRGB: Int = 36
    const val VK_FORMAT_R8G8B8A8_UNORM: Int = 37
    const val VK_FORMAT_R8G8B8A8_SNORM: Int = 38
    const val VK_FORMAT_R8G8B8A8_USCALED: Int = 39
    const val VK_FORMAT_R8G8B8A8_SSCALED: Int = 40
    const val VK_FORMAT_R8G8B8A8_UINT: Int = 41
    const val VK_FORMAT_R8G8B8A8_SINT: Int = 42
    const val VK_FORMAT_R8G8B8A8_SRGB: Int = 43
    const val VK_FORMAT_B8G8R8A8_UNORM: Int = 44
    const val VK_FORMAT_B8G8R8A8_SNORM: Int = 45
    const val VK_FORMAT_B8G8R8A8_USCALED: Int = 46
    const val VK_FORMAT_B8G8R8A8_SSCALED: Int = 47
    const val VK_FORMAT_B8G8R8A8_UINT: Int = 48
    const val VK_FORMAT_B8G8R8A8_SINT: Int = 49
    const val VK_FORMAT_B8G8R8A8_SRGB: Int = 50
    const val VK_FORMAT_A8B8G8R8_UNORM_PACK32: Int = 51
    const val VK_FORMAT_A8B8G8R8_SNORM_PACK32: Int = 52
    const val VK_FORMAT_A8B8G8R8_USCALED_PACK32: Int = 53
    const val VK_FORMAT_A8B8G8R8_SSCALED_PACK32: Int = 54
    const val VK_FORMAT_A8B8G8R8_UINT_PACK32: Int = 55
    const val VK_FORMAT_A8B8G8R8_SINT_PACK32: Int = 56
    const val VK_FORMAT_A8B8G8R8_SRGB_PACK32: Int = 57
    const val VK_FORMAT_A2R10G10B10_UNORM_PACK32: Int = 58
    const val VK_FORMAT_A2R10G10B10_SNORM_PACK32: Int = 59
    const val VK_FORMAT_A2R10G10B10_USCALED_PACK32: Int = 60
    const val VK_FORMAT_A2R10G10B10_SSCALED_PACK32: Int = 61
    const val VK_FORMAT_A2R10G10B10_UINT_PACK32: Int = 62
    const val VK_FORMAT_A2R10G10B10_SINT_PACK32: Int = 63
    const val VK_FORMAT_A2B10G10R10_UNORM_PACK32: Int = 64
    const val VK_FORMAT_A2B10G10R10_SNORM_PACK32: Int = 65
    const val VK_FORMAT_A2B10G10R10_USCALED_PACK32: Int = 66
    const val VK_FORMAT_A2B10G10R10_SSCALED_PACK32: Int = 67
    const val VK_FORMAT_A2B10G10R10_UINT_PACK32: Int = 68
    const val VK_FORMAT_A2B10G10R10_SINT_PACK32: Int = 69
    const val VK_FORMAT_R16_UNORM: Int = 70
    const val VK_FORMAT_R16_SNORM: Int = 71
    const val VK_FORMAT_R16_USCALED: Int = 72
    const val VK_FORMAT_R16_SSCALED: Int = 73
    const val VK_FORMAT_R16_UINT: Int = 74
    const val VK_FORMAT_R16_SINT: Int = 75
    const val VK_FORMAT_R16_SFLOAT: Int = 76
    const val VK_FORMAT_R16G16_UNORM: Int = 77
    const val VK_FORMAT_R16G16_SNORM: Int = 78
    const val VK_FORMAT_R16G16_USCALED: Int = 79
    const val VK_FORMAT_R16G16_SSCALED: Int = 80
    const val VK_FORMAT_R16G16_UINT: Int = 81
    const val VK_FORMAT_R16G16_SINT: Int = 82
    const val VK_FORMAT_R16G16_SFLOAT: Int = 83
    const val VK_FORMAT_R16G16B16_UNORM: Int = 84
    const val VK_FORMAT_R16G16B16_SNORM: Int = 85
    const val VK_FORMAT_R16G16B16_USCALED: Int = 86
    const val VK_FORMAT_R16G16B16_SSCALED: Int = 87
    const val VK_FORMAT_R16G16B16_UINT: Int = 88
    const val VK_FORMAT_R16G16B16_SINT: Int = 89
    const val VK_FORMAT_R16G16B16_SFLOAT: Int = 90
    const val VK_FORMAT_R16G16B16A16_UNORM: Int = 91
    const val VK_FORMAT_R16G16B16A16_SNORM: Int = 92
    const val VK_FORMAT_R16G16B16A16_USCALED: Int = 93
    const val VK_FORMAT_R16G16B16A16_SSCALED: Int = 94
    const val VK_FORMAT_R16G16B16A16_UINT: Int = 95
    const val VK_FORMAT_R16G16B16A16_SINT: Int = 96
    const val VK_FORMAT_R16G16B16A16_SFLOAT: Int = 97
    const val VK_FORMAT_R32_UINT: Int = 98
    const val VK_FORMAT_R32_SINT: Int = 99
    const val VK_FORMAT_R32_SFLOAT: Int = 100
    const val VK_FORMAT_R32G32_UINT: Int = 101
    const val VK_FORMAT_R32G32_SINT: Int = 102
    const val VK_FORMAT_R32G32_SFLOAT: Int = 103
    const val VK_FORMAT_R32G32B32_UINT: Int = 104
    const val VK_FORMAT_R32G32B32_SINT: Int = 105
    const val VK_FORMAT_R32G32B32_SFLOAT: Int = 106
    const val VK_FORMAT_R32G32B32A32_UINT: Int = 107
    const val VK_FORMAT_R32G32B32A32_SINT: Int = 108
    const val VK_FORMAT_R32G32B32A32_SFLOAT: Int = 109
    const val VK_FORMAT_R64_UINT: Int = 110
    const val VK_FORMAT_R64_SINT: Int = 111
    const val VK_FORMAT_R64_SFLOAT: Int = 112
    const val VK_FORMAT_R64G64_UINT: Int = 113
    const val VK_FORMAT_R64G64_SINT: Int = 114
    const val VK_FORMAT_R64G64_SFLOAT: Int = 115
    const val VK_FORMAT_R64G64B64_UINT: Int = 116
    const val VK_FORMAT_R64G64B64_SINT: Int = 117
    const val VK_FORMAT_R64G64B64_SFLOAT: Int = 118
    const val VK_FORMAT_R64G64B64A64_UINT: Int = 119
    const val VK_FORMAT_R64G64B64A64_SINT: Int = 120
    const val VK_FORMAT_R64G64B64A64_SFLOAT: Int = 121
    const val VK_FORMAT_B10G11R11_UFLOAT_PACK32: Int = 122
    const val VK_FORMAT_E5B9G9R9_UFLOAT_PACK32: Int = 123
    const val VK_FORMAT_D16_UNORM: Int = 124
    const val VK_FORMAT_X8_D24_UNORM_PACK32: Int = 125
    const val VK_FORMAT_D32_SFLOAT: Int = 126
    const val VK_FORMAT_S8_UINT: Int = 127
    const val VK_FORMAT_D16_UNORM_S8_UINT: Int = 128
    const val VK_FORMAT_D24_UNORM_S8_UINT: Int = 129
    const val VK_FORMAT_D32_SFLOAT_S8_UINT: Int = 130
    const val VK_FORMAT_BC1_RGB_UNORM_BLOCK: Int = 131
    const val VK_FORMAT_BC1_RGB_SRGB_BLOCK: Int = 132
    const val VK_FORMAT_BC1_RGBA_UNORM_BLOCK: Int = 133
    const val VK_FORMAT_BC1_RGBA_SRGB_BLOCK: Int = 134
    const val VK_FORMAT_BC2_UNORM_BLOCK: Int = 135
    const val VK_FORMAT_BC2_SRGB_BLOCK: Int = 136
    const val VK_FORMAT_BC3_UNORM_BLOCK: Int = 137
    const val VK_FORMAT_BC3_SRGB_BLOCK: Int = 138
    const val VK_FORMAT_BC4_UNORM_BLOCK: Int = 139
    const val VK_FORMAT_BC4_SNORM_BLOCK: Int = 140
    const val VK_FORMAT_BC5_UNORM_BLOCK: Int = 141
    const val VK_FORMAT_BC5_SNORM_BLOCK: Int = 142
    const val VK_FORMAT_BC6H_UFLOAT_BLOCK: Int = 143
    const val VK_FORMAT_BC6H_SFLOAT_BLOCK: Int = 144
    const val VK_FORMAT_BC7_UNORM_BLOCK: Int = 145
    const val VK_FORMAT_BC7_SRGB_BLOCK: Int = 146
    const val VK_FORMAT_ETC2_R8G8B8_UNORM_BLOCK: Int = 147
    const val VK_FORMAT_ETC2_R8G8B8_SRGB_BLOCK: Int = 148
    const val VK_FORMAT_ETC2_R8G8B8A1_UNORM_BLOCK: Int = 149
    const val VK_FORMAT_ETC2_R8G8B8A1_SRGB_BLOCK: Int = 150
    const val VK_FORMAT_ETC2_R8G8B8A8_UNORM_BLOCK: Int = 151
    const val VK_FORMAT_ETC2_R8G8B8A8_SRGB_BLOCK: Int = 152
    const val VK_FORMAT_EAC_R11_UNORM_BLOCK: Int = 153
    const val VK_FORMAT_EAC_R11_SNORM_BLOCK: Int = 154
    const val VK_FORMAT_EAC_R11G11_UNORM_BLOCK: Int = 155
    const val VK_FORMAT_EAC_R11G11_SNORM_BLOCK: Int = 156
    const val VK_FORMAT_ASTC_4x4_UNORM_BLOCK: Int = 157
    const val VK_FORMAT_ASTC_4x4_SRGB_BLOCK: Int = 158
    const val VK_FORMAT_ASTC_5x4_UNORM_BLOCK: Int = 159
    const val VK_FORMAT_ASTC_5x4_SRGB_BLOCK: Int = 160
    const val VK_FORMAT_ASTC_5x5_UNORM_BLOCK: Int = 161
    const val VK_FORMAT_ASTC_5x5_SRGB_BLOCK: Int = 162
    const val VK_FORMAT_ASTC_6x5_UNORM_BLOCK: Int = 163
    const val VK_FORMAT_ASTC_6x5_SRGB_BLOCK: Int = 164
    const val VK_FORMAT_ASTC_6x6_UNORM_BLOCK: Int = 165
    const val VK_FORMAT_ASTC_6x6_SRGB_BLOCK: Int = 166
    const val VK_FORMAT_ASTC_8x5_UNORM_BLOCK: Int = 167
    const val VK_FORMAT_ASTC_8x5_SRGB_BLOCK: Int = 168
    const val VK_FORMAT_ASTC_8x6_UNORM_BLOCK: Int = 169
    const val VK_FORMAT_ASTC_8x6_SRGB_BLOCK: Int = 170
    const val VK_FORMAT_ASTC_8x8_UNORM_BLOCK: Int = 171
    const val VK_FORMAT_ASTC_8x8_SRGB_BLOCK: Int = 172
    const val VK_FORMAT_ASTC_10x5_UNORM_BLOCK: Int = 173
    const val VK_FORMAT_ASTC_10x5_SRGB_BLOCK: Int = 174
    const val VK_FORMAT_ASTC_10x6_UNORM_BLOCK: Int = 175
    const val VK_FORMAT_ASTC_10x6_SRGB_BLOCK: Int = 176
    const val VK_FORMAT_ASTC_10x8_UNORM_BLOCK: Int = 177
    const val VK_FORMAT_ASTC_10x8_SRGB_BLOCK: Int = 178
    const val VK_FORMAT_ASTC_10x10_UNORM_BLOCK: Int = 179
    const val VK_FORMAT_ASTC_10x10_SRGB_BLOCK: Int = 180
    const val VK_FORMAT_ASTC_12x10_UNORM_BLOCK: Int = 181
    const val VK_FORMAT_ASTC_12x10_SRGB_BLOCK: Int = 182
    const val VK_FORMAT_ASTC_12x12_UNORM_BLOCK: Int = 183
    const val VK_FORMAT_ASTC_12x12_SRGB_BLOCK: Int = 184
    const val VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT: Int = 1
    const val VK_FORMAT_FEATURE_STORAGE_IMAGE_BIT: Int = 2
    const val VK_FORMAT_FEATURE_STORAGE_IMAGE_ATOMIC_BIT: Int = 4
    const val VK_FORMAT_FEATURE_UNIFORM_TEXEL_BUFFER_BIT: Int = 8
    const val VK_FORMAT_FEATURE_STORAGE_TEXEL_BUFFER_BIT: Int = 16
    const val VK_FORMAT_FEATURE_STORAGE_TEXEL_BUFFER_ATOMIC_BIT: Int = 32
    const val VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT: Int = 64
    const val VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BIT: Int = 128
    const val VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BLEND_BIT: Int = 256
    const val VK_FORMAT_FEATURE_DEPTH_STENCIL_ATTACHMENT_BIT: Int = 512
    const val VK_FORMAT_FEATURE_BLIT_SRC_BIT: Int = 1024
    const val VK_FORMAT_FEATURE_BLIT_DST_BIT: Int = 2048
    const val VK_FORMAT_FEATURE_SAMPLED_IMAGE_FILTER_LINEAR_BIT: Int = 4096
    const val VK_IMAGE_CREATE_SPARSE_BINDING_BIT: Int = 1
    const val VK_IMAGE_CREATE_SPARSE_RESIDENCY_BIT: Int = 2
    const val VK_IMAGE_CREATE_SPARSE_ALIASED_BIT: Int = 4
    const val VK_IMAGE_CREATE_MUTABLE_FORMAT_BIT: Int = 8
    const val VK_IMAGE_CREATE_CUBE_COMPATIBLE_BIT: Int = 16
    const val VK_SAMPLE_COUNT_1_BIT: Int = 1
    const val VK_SAMPLE_COUNT_2_BIT: Int = 2
    const val VK_SAMPLE_COUNT_4_BIT: Int = 4
    const val VK_SAMPLE_COUNT_8_BIT: Int = 8
    const val VK_SAMPLE_COUNT_16_BIT: Int = 16
    const val VK_SAMPLE_COUNT_32_BIT: Int = 32
    const val VK_SAMPLE_COUNT_64_BIT: Int = 64
    const val VK_IMAGE_TILING_OPTIMAL: Int = 0
    const val VK_IMAGE_TILING_LINEAR: Int = 1
    const val VK_IMAGE_TYPE_1D: Int = 0
    const val VK_IMAGE_TYPE_2D: Int = 1
    const val VK_IMAGE_TYPE_3D: Int = 2
    const val VK_IMAGE_USAGE_TRANSFER_SRC_BIT: Int = 1
    const val VK_IMAGE_USAGE_TRANSFER_DST_BIT: Int = 2
    const val VK_IMAGE_USAGE_SAMPLED_BIT: Int = 4
    const val VK_IMAGE_USAGE_STORAGE_BIT: Int = 8
    const val VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT: Int = 16
    const val VK_IMAGE_USAGE_DEPTH_STENCIL_ATTACHMENT_BIT: Int = 32
    const val VK_IMAGE_USAGE_TRANSIENT_ATTACHMENT_BIT: Int = 64
    const val VK_IMAGE_USAGE_INPUT_ATTACHMENT_BIT: Int = 128
    const val VK_MEMORY_HEAP_DEVICE_LOCAL_BIT: Int = 1
    const val VK_MEMORY_PROPERTY_DEVICE_LOCAL_BIT: Int = 1
    const val VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT: Int = 2
    const val VK_MEMORY_PROPERTY_HOST_COHERENT_BIT: Int = 4
    const val VK_MEMORY_PROPERTY_HOST_CACHED_BIT: Int = 8
    const val VK_MEMORY_PROPERTY_LAZILY_ALLOCATED_BIT: Int = 16
    const val VK_PHYSICAL_DEVICE_TYPE_OTHER: Int = 0
    const val VK_PHYSICAL_DEVICE_TYPE_INTEGRATED_GPU: Int = 1
    const val VK_PHYSICAL_DEVICE_TYPE_DISCRETE_GPU: Int = 2
    const val VK_PHYSICAL_DEVICE_TYPE_VIRTUAL_GPU: Int = 3
    const val VK_PHYSICAL_DEVICE_TYPE_CPU: Int = 4
    const val VK_QUEUE_GRAPHICS_BIT: Int = 1
    const val VK_QUEUE_COMPUTE_BIT: Int = 2
    const val VK_QUEUE_TRANSFER_BIT: Int = 4
    const val VK_QUEUE_SPARSE_BINDING_BIT: Int = 8
    const val VK_PIPELINE_STAGE_TOP_OF_PIPE_BIT: Int = 1
    const val VK_PIPELINE_STAGE_DRAW_INDIRECT_BIT: Int = 2
    const val VK_PIPELINE_STAGE_VERTEX_INPUT_BIT: Int = 4
    const val VK_PIPELINE_STAGE_VERTEX_SHADER_BIT: Int = 8
    const val VK_PIPELINE_STAGE_TESSELLATION_CONTROL_SHADER_BIT: Int = 16
    const val VK_PIPELINE_STAGE_TESSELLATION_EVALUATION_SHADER_BIT: Int = 32
    const val VK_PIPELINE_STAGE_GEOMETRY_SHADER_BIT: Int = 64
    const val VK_PIPELINE_STAGE_FRAGMENT_SHADER_BIT: Int = 128
    const val VK_PIPELINE_STAGE_EARLY_FRAGMENT_TESTS_BIT: Int = 256
    const val VK_PIPELINE_STAGE_LATE_FRAGMENT_TESTS_BIT: Int = 512
    const val VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT: Int = 1024
    const val VK_PIPELINE_STAGE_COMPUTE_SHADER_BIT: Int = 2048
    const val VK_PIPELINE_STAGE_TRANSFER_BIT: Int = 4096
    const val VK_PIPELINE_STAGE_BOTTOM_OF_PIPE_BIT: Int = 8192
    const val VK_PIPELINE_STAGE_HOST_BIT: Int = 16384
    const val VK_PIPELINE_STAGE_ALL_GRAPHICS_BIT: Int = 32768
    const val VK_PIPELINE_STAGE_ALL_COMMANDS_BIT: Int = 65536
    const val VK_SPARSE_MEMORY_BIND_METADATA_BIT: Int = 1
    const val VK_SPARSE_IMAGE_FORMAT_SINGLE_MIPTAIL_BIT: Int = 1
    const val VK_SPARSE_IMAGE_FORMAT_ALIGNED_MIP_SIZE_BIT: Int = 2
    const val VK_SPARSE_IMAGE_FORMAT_NONSTANDARD_BLOCK_SIZE_BIT: Int = 4
    const val VK_FENCE_CREATE_SIGNALED_BIT: Int = 1
    const val VK_QUERY_PIPELINE_STATISTIC_INPUT_ASSEMBLY_VERTICES_BIT: Int = 1
    const val VK_QUERY_PIPELINE_STATISTIC_INPUT_ASSEMBLY_PRIMITIVES_BIT: Int = 2
    const val VK_QUERY_PIPELINE_STATISTIC_VERTEX_SHADER_INVOCATIONS_BIT: Int = 4
    const val VK_QUERY_PIPELINE_STATISTIC_GEOMETRY_SHADER_INVOCATIONS_BIT: Int = 8
    const val VK_QUERY_PIPELINE_STATISTIC_GEOMETRY_SHADER_PRIMITIVES_BIT: Int = 16
    const val VK_QUERY_PIPELINE_STATISTIC_CLIPPING_INVOCATIONS_BIT: Int = 32
    const val VK_QUERY_PIPELINE_STATISTIC_CLIPPING_PRIMITIVES_BIT: Int = 64
    const val VK_QUERY_PIPELINE_STATISTIC_FRAGMENT_SHADER_INVOCATIONS_BIT: Int = 128
    const val VK_QUERY_PIPELINE_STATISTIC_TESSELLATION_CONTROL_SHADER_PATCHES_BIT: Int = 256
    const val VK_QUERY_PIPELINE_STATISTIC_TESSELLATION_EVALUATION_SHADER_INVOCATIONS_BIT: Int = 512
    const val VK_QUERY_PIPELINE_STATISTIC_COMPUTE_SHADER_INVOCATIONS_BIT: Int = 1024
    const val VK_QUERY_TYPE_OCCLUSION: Int = 0
    const val VK_QUERY_TYPE_PIPELINE_STATISTICS: Int = 1
    const val VK_QUERY_TYPE_TIMESTAMP: Int = 2
    const val VK_QUERY_RESULT_64_BIT: Int = 1
    const val VK_QUERY_RESULT_WAIT_BIT: Int = 2
    const val VK_QUERY_RESULT_WITH_AVAILABILITY_BIT: Int = 4
    const val VK_QUERY_RESULT_PARTIAL_BIT: Int = 8
    const val VK_BUFFER_CREATE_SPARSE_BINDING_BIT: Int = 1
    const val VK_BUFFER_CREATE_SPARSE_RESIDENCY_BIT: Int = 2
    const val VK_BUFFER_CREATE_SPARSE_ALIASED_BIT: Int = 4
    const val VK_BUFFER_USAGE_TRANSFER_SRC_BIT: Int = 1
    const val VK_BUFFER_USAGE_TRANSFER_DST_BIT: Int = 2
    const val VK_BUFFER_USAGE_UNIFORM_TEXEL_BUFFER_BIT: Int = 4
    const val VK_BUFFER_USAGE_STORAGE_TEXEL_BUFFER_BIT: Int = 8
    const val VK_BUFFER_USAGE_UNIFORM_BUFFER_BIT: Int = 16
    const val VK_BUFFER_USAGE_STORAGE_BUFFER_BIT: Int = 32
    const val VK_BUFFER_USAGE_INDEX_BUFFER_BIT: Int = 64
    const val VK_BUFFER_USAGE_VERTEX_BUFFER_BIT: Int = 128
    const val VK_BUFFER_USAGE_INDIRECT_BUFFER_BIT: Int = 256
    const val VK_SHARING_MODE_EXCLUSIVE: Int = 0
    const val VK_SHARING_MODE_CONCURRENT: Int = 1
    const val VK_COMPONENT_SWIZZLE_IDENTITY: Int = 0
    const val VK_COMPONENT_SWIZZLE_ZERO: Int = 1
    const val VK_COMPONENT_SWIZZLE_ONE: Int = 2
    const val VK_COMPONENT_SWIZZLE_R: Int = 3
    const val VK_COMPONENT_SWIZZLE_G: Int = 4
    const val VK_COMPONENT_SWIZZLE_B: Int = 5
    const val VK_COMPONENT_SWIZZLE_A: Int = 6
    const val VK_IMAGE_VIEW_TYPE_1D: Int = 0
    const val VK_IMAGE_VIEW_TYPE_2D: Int = 1
    const val VK_IMAGE_VIEW_TYPE_3D: Int = 2
    const val VK_IMAGE_VIEW_TYPE_CUBE: Int = 3
    const val VK_IMAGE_VIEW_TYPE_1D_ARRAY: Int = 4
    const val VK_IMAGE_VIEW_TYPE_2D_ARRAY: Int = 5
    const val VK_IMAGE_VIEW_TYPE_CUBE_ARRAY: Int = 6
    const val VK_BLEND_FACTOR_ZERO: Int = 0
    const val VK_BLEND_FACTOR_ONE: Int = 1
    const val VK_BLEND_FACTOR_SRC_COLOR: Int = 2
    const val VK_BLEND_FACTOR_ONE_MINUS_SRC_COLOR: Int = 3
    const val VK_BLEND_FACTOR_DST_COLOR: Int = 4
    const val VK_BLEND_FACTOR_ONE_MINUS_DST_COLOR: Int = 5
    const val VK_BLEND_FACTOR_SRC_ALPHA: Int = 6
    const val VK_BLEND_FACTOR_ONE_MINUS_SRC_ALPHA: Int = 7
    const val VK_BLEND_FACTOR_DST_ALPHA: Int = 8
    const val VK_BLEND_FACTOR_ONE_MINUS_DST_ALPHA: Int = 9
    const val VK_BLEND_FACTOR_CONSTANT_COLOR: Int = 10
    const val VK_BLEND_FACTOR_ONE_MINUS_CONSTANT_COLOR: Int = 11
    const val VK_BLEND_FACTOR_CONSTANT_ALPHA: Int = 12
    const val VK_BLEND_FACTOR_ONE_MINUS_CONSTANT_ALPHA: Int = 13
    const val VK_BLEND_FACTOR_SRC_ALPHA_SATURATE: Int = 14
    const val VK_BLEND_FACTOR_SRC1_COLOR: Int = 15
    const val VK_BLEND_FACTOR_ONE_MINUS_SRC1_COLOR: Int = 16
    const val VK_BLEND_FACTOR_SRC1_ALPHA: Int = 17
    const val VK_BLEND_FACTOR_ONE_MINUS_SRC1_ALPHA: Int = 18
    const val VK_BLEND_OP_ADD: Int = 0
    const val VK_BLEND_OP_SUBTRACT: Int = 1
    const val VK_BLEND_OP_REVERSE_SUBTRACT: Int = 2
    const val VK_BLEND_OP_MIN: Int = 3
    const val VK_BLEND_OP_MAX: Int = 4
    const val VK_COLOR_COMPONENT_R_BIT: Int = 1
    const val VK_COLOR_COMPONENT_G_BIT: Int = 2
    const val VK_COLOR_COMPONENT_B_BIT: Int = 4
    const val VK_COLOR_COMPONENT_A_BIT: Int = 8
    const val VK_COMPARE_OP_NEVER: Int = 0
    const val VK_COMPARE_OP_LESS: Int = 1
    const val VK_COMPARE_OP_EQUAL: Int = 2
    const val VK_COMPARE_OP_LESS_OR_EQUAL: Int = 3
    const val VK_COMPARE_OP_GREATER: Int = 4
    const val VK_COMPARE_OP_NOT_EQUAL: Int = 5
    const val VK_COMPARE_OP_GREATER_OR_EQUAL: Int = 6
    const val VK_COMPARE_OP_ALWAYS: Int = 7
    const val VK_PIPELINE_CREATE_DISABLE_OPTIMIZATION_BIT: Int = 1
    const val VK_PIPELINE_CREATE_ALLOW_DERIVATIVES_BIT: Int = 2
    const val VK_PIPELINE_CREATE_DERIVATIVE_BIT: Int = 4
    const val VK_SHADER_STAGE_VERTEX_BIT: Int = 1
    const val VK_SHADER_STAGE_TESSELLATION_CONTROL_BIT: Int = 2
    const val VK_SHADER_STAGE_TESSELLATION_EVALUATION_BIT: Int = 4
    const val VK_SHADER_STAGE_GEOMETRY_BIT: Int = 8
    const val VK_SHADER_STAGE_FRAGMENT_BIT: Int = 16
    const val VK_SHADER_STAGE_COMPUTE_BIT: Int = 32
    const val VK_SHADER_STAGE_ALL_GRAPHICS: Int = 31
    const val VK_SHADER_STAGE_ALL: Int = Int.MAX_VALUE
    const val VK_CULL_MODE_NONE: Int = 0
    const val VK_CULL_MODE_FRONT_BIT: Int = 1
    const val VK_CULL_MODE_BACK_BIT: Int = 2
    const val VK_CULL_MODE_FRONT_AND_BACK: Int = 3
    const val VK_DYNAMIC_STATE_VIEWPORT: Int = 0
    const val VK_DYNAMIC_STATE_SCISSOR: Int = 1
    const val VK_DYNAMIC_STATE_LINE_WIDTH: Int = 2
    const val VK_DYNAMIC_STATE_DEPTH_BIAS: Int = 3
    const val VK_DYNAMIC_STATE_BLEND_CONSTANTS: Int = 4
    const val VK_DYNAMIC_STATE_DEPTH_BOUNDS: Int = 5
    const val VK_DYNAMIC_STATE_STENCIL_COMPARE_MASK: Int = 6
    const val VK_DYNAMIC_STATE_STENCIL_WRITE_MASK: Int = 7
    const val VK_DYNAMIC_STATE_STENCIL_REFERENCE: Int = 8
    const val VK_FRONT_FACE_COUNTER_CLOCKWISE: Int = 0
    const val VK_FRONT_FACE_CLOCKWISE: Int = 1
    const val VK_VERTEX_INPUT_RATE_VERTEX: Int = 0
    const val VK_VERTEX_INPUT_RATE_INSTANCE: Int = 1
    const val VK_PRIMITIVE_TOPOLOGY_POINT_LIST: Int = 0
    const val VK_PRIMITIVE_TOPOLOGY_LINE_LIST: Int = 1
    const val VK_PRIMITIVE_TOPOLOGY_LINE_STRIP: Int = 2
    const val VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST: Int = 3
    const val VK_PRIMITIVE_TOPOLOGY_TRIANGLE_STRIP: Int = 4
    const val VK_PRIMITIVE_TOPOLOGY_TRIANGLE_FAN: Int = 5
    const val VK_PRIMITIVE_TOPOLOGY_LINE_LIST_WITH_ADJACENCY: Int = 6
    const val VK_PRIMITIVE_TOPOLOGY_LINE_STRIP_WITH_ADJACENCY: Int = 7
    const val VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST_WITH_ADJACENCY: Int = 8
    const val VK_PRIMITIVE_TOPOLOGY_TRIANGLE_STRIP_WITH_ADJACENCY: Int = 9
    const val VK_PRIMITIVE_TOPOLOGY_PATCH_LIST: Int = 10
    const val VK_POLYGON_MODE_FILL: Int = 0
    const val VK_POLYGON_MODE_LINE: Int = 1
    const val VK_POLYGON_MODE_POINT: Int = 2
    const val VK_STENCIL_OP_KEEP: Int = 0
    const val VK_STENCIL_OP_ZERO: Int = 1
    const val VK_STENCIL_OP_REPLACE: Int = 2
    const val VK_STENCIL_OP_INCREMENT_AND_CLAMP: Int = 3
    const val VK_STENCIL_OP_DECREMENT_AND_CLAMP: Int = 4
    const val VK_STENCIL_OP_INVERT: Int = 5
    const val VK_STENCIL_OP_INCREMENT_AND_WRAP: Int = 6
    const val VK_STENCIL_OP_DECREMENT_AND_WRAP: Int = 7
    const val VK_LOGIC_OP_CLEAR: Int = 0
    const val VK_LOGIC_OP_AND: Int = 1
    const val VK_LOGIC_OP_AND_REVERSE: Int = 2
    const val VK_LOGIC_OP_COPY: Int = 3
    const val VK_LOGIC_OP_AND_INVERTED: Int = 4
    const val VK_LOGIC_OP_NO_OP: Int = 5
    const val VK_LOGIC_OP_XOR: Int = 6
    const val VK_LOGIC_OP_OR: Int = 7
    const val VK_LOGIC_OP_NOR: Int = 8
    const val VK_LOGIC_OP_EQUIVALENT: Int = 9
    const val VK_LOGIC_OP_INVERT: Int = 10
    const val VK_LOGIC_OP_OR_REVERSE: Int = 11
    const val VK_LOGIC_OP_COPY_INVERTED: Int = 12
    const val VK_LOGIC_OP_OR_INVERTED: Int = 13
    const val VK_LOGIC_OP_NAND: Int = 14
    const val VK_LOGIC_OP_SET: Int = 15
    const val VK_BORDER_COLOR_FLOAT_TRANSPARENT_BLACK: Int = 0
    const val VK_BORDER_COLOR_INT_TRANSPARENT_BLACK: Int = 1
    const val VK_BORDER_COLOR_FLOAT_OPAQUE_BLACK: Int = 2
    const val VK_BORDER_COLOR_INT_OPAQUE_BLACK: Int = 3
    const val VK_BORDER_COLOR_FLOAT_OPAQUE_WHITE: Int = 4
    const val VK_BORDER_COLOR_INT_OPAQUE_WHITE: Int = 5
    const val VK_FILTER_NEAREST: Int = 0
    const val VK_FILTER_LINEAR: Int = 1
    const val VK_SAMPLER_ADDRESS_MODE_REPEAT: Int = 0
    const val VK_SAMPLER_ADDRESS_MODE_MIRRORED_REPEAT: Int = 1
    const val VK_SAMPLER_ADDRESS_MODE_CLAMP_TO_EDGE: Int = 2
    const val VK_SAMPLER_ADDRESS_MODE_CLAMP_TO_BORDER: Int = 3
    const val VK_SAMPLER_MIPMAP_MODE_NEAREST: Int = 0
    const val VK_SAMPLER_MIPMAP_MODE_LINEAR: Int = 1
    const val VK_DESCRIPTOR_POOL_CREATE_FREE_DESCRIPTOR_SET_BIT: Int = 1
    const val VK_DESCRIPTOR_TYPE_SAMPLER: Int = 0
    const val VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER: Int = 1
    const val VK_DESCRIPTOR_TYPE_SAMPLED_IMAGE: Int = 2
    const val VK_DESCRIPTOR_TYPE_STORAGE_IMAGE: Int = 3
    const val VK_DESCRIPTOR_TYPE_UNIFORM_TEXEL_BUFFER: Int = 4
    const val VK_DESCRIPTOR_TYPE_STORAGE_TEXEL_BUFFER: Int = 5
    const val VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER: Int = 6
    const val VK_DESCRIPTOR_TYPE_STORAGE_BUFFER: Int = 7
    const val VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER_DYNAMIC: Int = 8
    const val VK_DESCRIPTOR_TYPE_STORAGE_BUFFER_DYNAMIC: Int = 9
    const val VK_DESCRIPTOR_TYPE_INPUT_ATTACHMENT: Int = 10
    const val VK_ATTACHMENT_DESCRIPTION_MAY_ALIAS_BIT: Int = 1
    const val VK_ATTACHMENT_LOAD_OP_LOAD: Int = 0
    const val VK_ATTACHMENT_LOAD_OP_CLEAR: Int = 1
    const val VK_ATTACHMENT_LOAD_OP_DONT_CARE: Int = 2
    const val VK_ATTACHMENT_STORE_OP_STORE: Int = 0
    const val VK_ATTACHMENT_STORE_OP_DONT_CARE: Int = 1
    const val VK_DEPENDENCY_BY_REGION_BIT: Int = 1
    const val VK_PIPELINE_BIND_POINT_GRAPHICS: Int = 0
    const val VK_PIPELINE_BIND_POINT_COMPUTE: Int = 1
    const val VK_COMMAND_POOL_CREATE_TRANSIENT_BIT: Int = 1
    const val VK_COMMAND_POOL_CREATE_RESET_COMMAND_BUFFER_BIT: Int = 2
    const val VK_COMMAND_POOL_RESET_RELEASE_RESOURCES_BIT: Int = 1
    const val VK_COMMAND_BUFFER_LEVEL_PRIMARY: Int = 0
    const val VK_COMMAND_BUFFER_LEVEL_SECONDARY: Int = 1
    const val VK_COMMAND_BUFFER_USAGE_ONE_TIME_SUBMIT_BIT: Int = 1
    const val VK_COMMAND_BUFFER_USAGE_RENDER_PASS_CONTINUE_BIT: Int = 2
    const val VK_COMMAND_BUFFER_USAGE_SIMULTANEOUS_USE_BIT: Int = 4
    const val VK_QUERY_CONTROL_PRECISE_BIT: Int = 1
    const val VK_COMMAND_BUFFER_RESET_RELEASE_RESOURCES_BIT: Int = 1
    const val VK_INDEX_TYPE_UINT16: Int = 0
    const val VK_INDEX_TYPE_UINT32: Int = 1
    const val VK_STENCIL_FACE_FRONT_BIT: Int = 1
    const val VK_STENCIL_FACE_BACK_BIT: Int = 2
    const val VK_STENCIL_FACE_FRONT_AND_BACK: Int = 3
    const val VK_STENCIL_FRONT_AND_BACK: Int = 3
    const val VK_SUBPASS_CONTENTS_INLINE: Int = 0
    const val VK_SUBPASS_CONTENTS_SECONDARY_COMMAND_BUFFERS: Int = 1
    val VK_API_VERSION_1_0: Int = VK_MAKE_API_VERSION(0, 1, 0, 0)
    const val VK_HEADER_VERSION: Int = 289
    const val VK_NULL_HANDLE: Long = 0L
    const val VK_MAX_PHYSICAL_DEVICE_NAME_SIZE: Int = 256
    const val VK_UUID_SIZE: Int = 16
    const val VK_LUID_SIZE: Int = 8
    const val VK_MAX_EXTENSION_NAME_SIZE: Int = 256
    const val VK_MAX_DESCRIPTION_SIZE: Int = 256
    const val VK_MAX_MEMORY_TYPES: Int = 32
    const val VK_MAX_MEMORY_HEAPS: Int = 16
    const val VK_REMAINING_MIP_LEVELS: Int = -1
    const val VK_REMAINING_ARRAY_LAYERS: Int = -1
    const val VK_ATTACHMENT_UNUSED: Int = -1
    const val VK_TRUE: Int = 1
    const val VK_FALSE: Int = 0
    const val VK_QUEUE_FAMILY_IGNORED: Int = -1
    const val VK_QUEUE_FAMILY_EXTERNAL: Int = -2
    const val VK_SUBPASS_EXTERNAL: Int = -1
    const val VK_MAX_DEVICE_GROUP_SIZE: Int = 32
    const val VK_MAX_DRIVER_NAME_SIZE: Int = 256
    const val VK_MAX_DRIVER_INFO_SIZE: Int = 256
    const val VK_LOD_CLAMP_NONE: Float = 1000.0f
    const val VK_WHOLE_SIZE: Long = -1L

    fun VK_MAKE_API_VERSION(
        variant: Int,
        major: Int,
        minor: Int,
        patch: Int
    ): Int {
        return variant shl 29 or (major shl 22) or (minor shl 12) or patch
    }

    fun VK_API_VERSION_VARIANT(version: Int): Int {
        return version ushr 29
    }

    fun VK_API_VERSION_MAJOR(version: Int): Int {
        return version ushr 22 and 127
    }

    fun VK_API_VERSION_MINOR(version: Int): Int {
        return version ushr 12 and 1023
    }

    fun VK_API_VERSION_PATCH(version: Int): Int {
        return version and 4095
    }

    fun VK_MAKE_VERSION(
        major: Int,
        minor: Int,
        patch: Int
    ): Int {
        return major shl 22 or (minor shl 12) or patch
    }

    fun VK_VERSION_MAJOR(version: Int): Int {
        return version ushr 22
    }

    fun VK_VERSION_MINOR(version: Int): Int {
        return version ushr 12 and 1023
    }

    fun VK_VERSION_PATCH(version: Int): Int {
        return version and 4095
    }

    fun vkCreateInstance(
        createInfo: VkInstanceCreateInfo,
        allocator: VkAllocationCallbacks?
    ): Result<VkInstance, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc("vkCreateInstance", Int::class, createInfo, allocator?.ref() ?: MemorySegment.NULL, seg)
        return if (retCode == 0) Result.success(VkInstance(seg.unbox())) else Result.fail(retCode)
    }

    fun vkDestroyInstance(instance: VkInstance, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroyInstance", instance, allocator?.ref() ?: MemorySegment.NULL)

    fun vkEnumeratePhysicalDevices(instance: VkInstance): Result<Array<VkPhysicalDevice>, Int> {
        val count = HeapInt()
        callFunc("vkEnumeratePhysicalDevices", Int::class, instance, count, MemorySegment.NULL).apply {
            if (this != VK_SUCCESS) return Result.fail(this)
        }
        val seg = Arena.ofAuto().allocate(sizetLength() * count.value() * 1L)
        callFunc("vkEnumeratePhysicalDevices", Int::class, instance, count, seg).apply {
            if (this != VK_SUCCESS) return Result.fail(this)
        }

        return Result.success(seg.toPointerArray(count.value()).map { VkPhysicalDevice(it) }.toTypedArray())
    }

    fun vkGetPhysicalDeviceFeatures(physicalDevice: VkPhysicalDevice): VkPhysicalDeviceFeatures =
        VkPhysicalDeviceFeatures().apply { callVoidFunc("vkGetPhysicalDeviceFeatures", physicalDevice, this) }

    fun vkGetPhysicalDeviceFormatProperties(physicalDevice: VkPhysicalDevice, format: Int): VkFormatProperties =
        VkFormatProperties().apply { callVoidFunc("vkGetPhysicalDeviceFormatProperties", physicalDevice, format, this) }

    fun vkGetPhysicalDeviceImageFormatProperties(
        physicalDevice: VkPhysicalDevice,
        format: Int,
        type: Int,
        tiling: Int,
        usage: Int,
        flags: Int
    ): Result<VkImageFormatProperties, Int> {
        val prop = VkImageFormatProperties()
        val retCode = callFunc(
            "vkGetPhysicalDeviceImageFormatProperties",
            Int::class,
            physicalDevice,
            format,
            type,
            tiling,
            usage,
            flags,
            prop
        )

        return if (retCode == VK_SUCCESS) Result.success(prop) else Result.fail(retCode)
    }

    fun vkGetPhysicalDeviceProperties(physicalDevice: VkPhysicalDevice): VkPhysicalDeviceProperties {
        val seg = Arena.ofAuto().allocate(VkPhysicalDeviceProperties.LAYOUT)
        callVoidFunc("vkGetPhysicalDeviceProperties", physicalDevice, seg)
        return VkPhysicalDeviceProperties(seg)
    }

    fun vkGetPhysicalDeviceQueueFamilyProperties(
        physicalDevice: VkPhysicalDevice
    ): Array<VkQueueFamilyProperties> {
        val count = HeapInt()
        callVoidFunc("vkGetPhysicalDeviceQueueFamilyProperties", physicalDevice, count, MemorySegment.NULL)

        val sarr = HeapStructArray<VkQueueFamilyProperties>(VkQueueFamilyProperties.LAYOUT, count.value())
        callVoidFunc("vkGetPhysicalDeviceQueueFamilyProperties", physicalDevice, count, sarr.ref())

        return (0..<count.value()).map { VkQueueFamilyProperties(sarr[it]) }.toTypedArray()
    }

    fun vkGetPhysicalDeviceMemoryProperties(physicalDevice: VkPhysicalDevice): VkPhysicalDeviceMemoryProperties =
        VkPhysicalDeviceMemoryProperties().apply {
            callVoidFunc(
                "vkGetPhysicalDeviceMemoryProperties",
                physicalDevice,
                this
            )
        }

    fun vkGetInstanceProcAddr(instance: VkInstance, name: String): MemorySegment =
        callPointerFunc("vkGetInstanceProcAddr", instance, name.toCString())

    fun vkGetDeviceProcAddr(device: VkDevice, name: String): MemorySegment =
        callPointerFunc("vkGetDeviceProcAddr", device, name.toCString())

    fun vkCreateDevice(
        physicalDevice: VkPhysicalDevice,
        createInfo: VkDeviceCreateInfo,
        allocator: VkAllocationCallbacks?
    ): Result<VkDevice, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc(
            "vkCreateDevice",
            Int::class,
            physicalDevice,
            createInfo,
            allocator?.ref() ?: MemorySegment.NULL,
            seg
        )
        return if (retCode == VK_SUCCESS) Result.success(VkDevice(seg.unbox())) else Result.fail(retCode)
    }

    fun vkDestroyDevice(device: VkDevice, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroyDevice", device, allocator?.ref() ?: MemorySegment.NULL)

    fun vkEnumerateInstanceExtensionProperties(layerName: String): Result<Array<VkExtensionProperties>, Int> {
        val count = HeapInt()
        callFunc(
            "vkEnumerateInstanceExtensionProperties",
            Int::class,
            layerName.toCString(),
            count,
            MemorySegment.NULL
        ).apply {
            if (this != VK_SUCCESS) return Result.fail(this)
        }
        val sarr = HeapStructArray<VkExtensionProperties>(VkExtensionProperties.LAYOUT, count.value())
        callFunc("vkEnumerateInstanceExtensionProperties", Int::class, layerName.toCString(), count, sarr.ref()).apply {
            if (this != VK_SUCCESS) return Result.fail(this)
        }

        return Result.success((0..<count.value()).map { VkExtensionProperties(sarr[it]) }.toTypedArray())
    }

    fun vkEnumerateDeviceExtensionProperties(
        physicalDevice: VkPhysicalDevice,
        layerName: String?
    ): Result<Array<VkExtensionProperties>, Int> {
        val count = HeapInt()
        callFunc(
            "vkEnumerateDeviceExtensionProperties",
            Int::class,
            physicalDevice,
            layerName?.toCString() ?: MemorySegment.NULL,
            count,
            MemorySegment.NULL
        ).apply {
            if (this != VK_SUCCESS) return Result.fail(this)
        }
        val sarr = HeapStructArray<VkExtensionProperties>(VkExtensionProperties.LAYOUT, count.value())
        callFunc(
            "vkEnumerateDeviceExtensionProperties",
            Int::class,
            physicalDevice,
            layerName?.toCString() ?: MemorySegment.NULL,
            count,
            sarr.ref()
        ).apply {
            if (this != VK_SUCCESS) return Result.fail(this)
        }
        return Result.success((0..<count.value()).map { VkExtensionProperties(sarr[it]) }.toTypedArray())
    }

    fun vkEnumerateInstanceLayerProperties(): Result<Array<VkLayerProperties>, Int> {
        val count = HeapInt()
        callFunc("vkEnumerateInstanceLayerProperties", Int::class, count, MemorySegment.NULL).apply {
            if (this != VK_SUCCESS) return Result.fail(this)
        }
        val sarr = HeapStructArray<VkLayerProperties>(VkLayerProperties.LAYOUT, count.value())
        callFunc("vkEnumerateInstanceLayerProperties", Int::class, count, sarr).apply {
            if (this != VK_SUCCESS) return Result.fail(this)
        }
        return Result.success((0..<count.value()).map { VkLayerProperties(sarr[it]) }.toTypedArray())
    }

    fun vkEnumerateDeviceLayerProperties(physicalDevice: VkPhysicalDevice): Result<Array<VkLayerProperties>, Int> {
        val count = HeapInt()
        callFunc("vkEnumerateDeviceLayerProperties", Int::class, physicalDevice, count, MemorySegment.NULL).apply {
            if (this != VK_SUCCESS) return Result.fail(this)
        }
        val sarr = HeapStructArray<VkLayerProperties>(VkLayerProperties.LAYOUT, count.value())
        callFunc("vkEnumerateDeviceLayerProperties", Int::class, physicalDevice, count, sarr).apply {
            if (this != VK_SUCCESS) return Result.fail(this)
        }
        return Result.success((0..<count.value()).map { VkLayerProperties(sarr[it]) }.toTypedArray())
    }

    fun vkGetDeviceQueue(device: VkDevice, queueFamilyIndex: Int, queueIndex: Int): VkQueue {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        callFunc("vkGetDeviceQueue", Int::class, device, queueFamilyIndex, queueIndex, seg)
        return VkQueue(seg.unbox())
    }

    fun vkQueueSubmit(queue: VkQueue, submits: HeapStructArray<VkSubmitInfo>, fence: VkFence): Int =
        callFunc("vkQueueSubmit", Int::class, queue, submits.length, submits, fence)

    fun vkQueueWaitIdle(queue: VkQueue): Int =
        callFunc("vkQueueWaitIdle", Int::class, queue)

    fun vkDeviceWaitIdle(device: VkDevice): Int =
        callFunc("vkDeviceWaitIdle", Int::class, device)

    fun vkAllocateMemory(device: VkDevice, allocateInfo: VkMemoryAllocateInfo, allocator: VkAllocationCallbacks?): Result<VkDeviceMemory, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode =
            callFunc("vkAllocateMemory", Int::class, device, allocateInfo, allocator?.ref() ?: MemorySegment.NULL, seg)
        return if (retCode == VK_SUCCESS) Result.success(VkDeviceMemory(seg.unbox())) else Result.fail(retCode)
    }

    fun vkFreeMemory(device: VkDevice, memory: VkDeviceMemory, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkFreeMemory", device, memory, allocator?.ref() ?: MemorySegment.NULL)

    fun vkMapMemory(
        device: VkDevice,
        memory: VkDeviceMemory,
        offset: Long,
        size: Long,
        flags: Int
    ): Result<ByteBuffer, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc("vkMapMemory", Int::class, device, memory, offset, size, flags, seg)
        return if (retCode == VK_SUCCESS) Result.success(
            seg.unbox().reinterpret(size).asByteBuffer()
        ) else Result.fail(retCode)
    }

    fun vkUnmapMemory(device: VkDevice, memory: VkDeviceMemory) = callVoidFunc("vkUnmapMemory", device, memory)

    fun vkFlushMappedMemoryRanges(device: VkDevice, ranges: HeapStructArray<VkMappedMemoryRange>): Int =
        callFunc("vkFlushMappedMemoryRanges", Int::class, device, ranges.length, ranges)

    fun vkInvalidateMappedMemoryRanges(device: VkDevice, ranges: HeapStructArray<VkMappedMemoryRange>): Int =
        callFunc("vkInvalidateMappedMemoryRanges", Int::class, device, ranges.length, ranges)

    fun vkGetDeviceMemoryCommitment(device: VkDevice, memory: VkDeviceMemory, committedMemoryInBytes: HeapLong) =
        callVoidFunc("vkGetDeviceMemoryCommitment", device, memory, committedMemoryInBytes)

    fun vkBindBufferMemory(device: VkDevice, buffer: VkBuffer, memory: VkDeviceMemory, offset: Long): Int =
        callFunc("vkBindBufferMemory", Int::class, device, buffer, memory, offset)

    fun vkBindImageMemory(device: VkDevice, image: VkImage, memory: VkDeviceMemory, offset: Long): Int =
        callFunc("vkBindImageMemory", Int::class, device, image, memory, offset)

    fun vkGetBufferMemoryRequirements(device: VkDevice, buffer: VkBuffer): VkMemoryRequirements =
        VkMemoryRequirements().apply { callVoidFunc("vkGetBufferMemoryRequirements", device, buffer, this) }

    fun vkGetImageMemoryRequirements(device: VkDevice, image: VkImage): VkMemoryRequirements =
        VkMemoryRequirements().apply { callVoidFunc("vkGetImageMemoryRequirements", device, image, this) }

    fun vkGetImageSparseMemoryRequirements(device: VkDevice, image: VkImage): Array<VkSparseImageMemoryRequirements> {
        val count = HeapInt()
        callVoidFunc("vkGetImageSparseMemoryRequirements", device, image, count, MemorySegment.NULL)
        val sarr =
            HeapStructArray<VkSparseImageMemoryRequirements>(VkSparseImageMemoryRequirements.LAYOUT, count.value())
        callVoidFunc("vkGetImageSparseMemoryRequirements", device, image, count, sarr.ref())
        return (0..<count.value()).map { VkSparseImageMemoryRequirements(sarr[it]) }.toTypedArray()
    }

    fun vkGetPhysicalDeviceSparseImageFormatProperties(
        physicalDevice: VkPhysicalDevice,
        format: Int,
        type: Int,
        samples: Int,
        usage: Int,
        tiling: Int
    ): Array<VkSparseImageMemoryRequirements> {
        val count = HeapInt()
        callVoidFunc("vkGetPhysicalDeviceSparseImageFormatProperties", physicalDevice, format, type, samples, usage, tiling, count, MemorySegment.NULL)
        val sarr =
            HeapStructArray<VkSparseImageMemoryRequirements>(VkSparseImageMemoryRequirements.LAYOUT, count.value())
        callVoidFunc(
            "vkGetPhysicalDeviceSparseImageFormatProperties",
            physicalDevice,
            format,
            type,
            samples,
            usage,
            tiling,
            count,
            sarr.ref()
        )
        return (0..<count.value()).map { VkSparseImageMemoryRequirements(sarr[it]) }.toTypedArray()
    }

    fun vkQueueBindSparse(queue: VkQueue, bindInfo: HeapStructArray<VkBindSparseInfo>, fence: VkFence): Int =
        callFunc("vkQueueBindSparse", Int::class, queue, bindInfo.length, bindInfo, fence)

    fun vkCreateFence(device: VkDevice, createInfo: VkFenceCreateInfo, allocator: VkAllocationCallbacks?): Result<VkFence, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode =
            callFunc("vkCreateFence", Int::class, device, createInfo, allocator?.ref() ?: MemorySegment.NULL, seg)
        return if (retCode == VK_SUCCESS) Result.success(VkFence(seg.unbox())) else Result.fail(retCode)
    }

    fun vkDestroyFence(device: VkDevice, fence: VkFence, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroyFence", device, fence, allocator?.ref() ?: MemorySegment.NULL)

    fun vkResetFences(device: VkDevice, fences: HeapPointerArray<VkFence>): Int =
        callFunc("vkResetFences", Int::class, device, fences.length, fences)

    fun vkGetFenceStatus(device: VkDevice, fence: VkFence): Int =
        callFunc("vkGetFenceStatus", Int::class, device, fence)

    fun vkWaitForFences(device: VkDevice, fences: HeapPointerArray<VkFence>, waitAll: Boolean, timeout: Long): Int =
        callFunc("vkWaitForFences", Int::class, device, fences.length, fences, if (waitAll) 1 else 0, timeout)

    fun vkCreateSemaphore(device: VkDevice, createInfo: VkSemaphoreCreateInfo, allocator: VkAllocationCallbacks?): Result<VkSemaphore, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode =
            callFunc("vkCreateSemaphore", Int::class, device, createInfo, allocator?.ref() ?: MemorySegment.NULL, seg)
        return if (retCode == VK_SUCCESS) Result.success(VkSemaphore(seg.unbox())) else Result.fail(retCode)
    }

    fun vkDestroySemaphore(device: VkDevice, semaphore: VkSemaphore, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroySemaphore", device, semaphore, allocator?.ref() ?: MemorySegment.NULL)

    fun vkCreateEvent(device: VkDevice, createInfo: VkEventCreateInfo, allocator: VkAllocationCallbacks?): Result<VkEvent, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode =
            callFunc("vkCreateEvent", Int::class, device, createInfo, allocator?.ref() ?: MemorySegment.NULL, seg)
        return if (retCode == VK_SUCCESS) Result.success(VkEvent(seg.unbox())) else Result.fail(retCode)
    }

    fun vkDestroyEvent(device: VkDevice, event: VkEvent, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroyEvent", device, event, allocator?.ref() ?: MemorySegment.NULL)

    fun vkGetEventStatus(device: VkDevice, event: VkEvent): Int =
        callFunc("vkGetEventStatus", Int::class, device, event)

    fun vkSetEvent(device: VkDevice, event: VkEvent): Int =
        callFunc("vkSetEvent", Int::class, device, event)

    fun vkResetEvent(device: VkDevice, event: VkEvent): Int =
        callFunc("vkResetEvent", Int::class, device, event)

    fun vkCreateQueryPool(device: VkDevice, createInfo: VkQueryPoolCreateInfo, allocator: VkAllocationCallbacks?): Result<VkQueryPool, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode =
            callFunc("vkCreateQueryPool", Int::class, device, createInfo, allocator?.ref() ?: MemorySegment.NULL)
        return if (retCode == VK_SUCCESS) Result.success(VkQueryPool(seg.unbox())) else Result.fail(retCode)
    }

    fun vkDestroyQueryPool(device: VkDevice, queryPool: VkQueryPool, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroyQueryPool", device, queryPool, allocator?.ref() ?: MemorySegment.NULL)

    fun <T> vkGetQueryPoolResults(device: VkDevice, queryPool: VkQueryPool, firstQuery: Int, queryCount: Int, data: IHeapVar<T>, stride: Long, flags: Int): Int =
        callFunc("vkGetQueryPoolResults", Int::class, device, queryPool, firstQuery, queryCount, data, stride, flags)

    fun vkCreateBuffer(device: VkDevice, createInfo: VkBufferCreateInfo, allocator: VkAllocationCallbacks?): Result<VkBuffer, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode =
            callFunc("vkCreateBuffer", Int::class, device, createInfo, allocator?.ref() ?: MemorySegment.NULL, seg)
        return if (retCode == VK_SUCCESS) Result.success(VkBuffer(seg.unbox())) else Result.fail(retCode)
    }

    fun vkDestroyBuffer(device: VkDevice, buffer: VkBuffer, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroyBuffer", device, buffer, allocator?.ref() ?: MemorySegment.NULL)

    fun vkCreateBufferView(device: VkDevice, createInfo: VkBufferViewCreateInfo, allocator: VkAllocationCallbacks?): Result<VkBufferView, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode =
            callFunc("vkCreateBufferView", Int::class, device, createInfo, allocator?.ref() ?: MemorySegment.NULL, seg)
        return if (retCode == VK_SUCCESS) Result.success(VkBufferView(seg.unbox())) else Result.fail(retCode)
    }

    fun vkDestroyBufferView(device: VkDevice, bufferView: VkBufferView, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroyBufferView", device, bufferView, allocator?.ref() ?: MemorySegment.NULL)

    fun vkCreateImage(device: VkDevice, createInfo: VkImageCreateInfo, allocator: VkAllocationCallbacks?): Result<VkImage, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode =
            callFunc("vkCreateImage", Int::class, device, createInfo, allocator?.ref() ?: MemorySegment.NULL, seg)
        return if (retCode == VK_SUCCESS) Result.success(VkImage(seg.unbox())) else Result.fail(retCode)
    }

    fun vkDestroyImage(device: VkDevice, image: VkImage, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroyImage", device, image, allocator?.ref() ?: MemorySegment.NULL)

    fun vkGetImageSubresourceLayout(device: VkDevice, image: VkImage, subresource: VkImageSubresource): VkSubresourceLayout {
        val seg = Arena.ofAuto().allocate(40L)
        callVoidFunc("vkGetImageSubresourceLayout", device, image, subresource, seg)
        return VkSubresourceLayout(seg)
    }

    fun vkCreateImageView(device: VkDevice, createInfo: VkImageViewCreateInfo, allocator: VkAllocationCallbacks?): Result<VkImageView, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode =
            callFunc("vkCreateImageView", Int::class, device, createInfo, allocator?.ref() ?: MemorySegment.NULL, seg)
        return if (retCode == VK_SUCCESS) Result.success(VkImageView(seg.unbox())) else Result.fail(retCode)
    }

    fun vkDestroyImageView(device: VkDevice, imageView: VkImageView, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroyImageView", device, imageView, allocator?.ref() ?: MemorySegment.NULL)

    fun vkCreateShaderModule(device: VkDevice, createInfo: VkShaderModuleCreateInfo, allocator: VkAllocationCallbacks?): Result<VkShaderModule, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc(
            "vkCreateShaderModule",
            Int::class,
            device,
            createInfo,
            allocator?.ref() ?: MemorySegment.NULL,
            seg
        )
        return if (retCode == VK_SUCCESS) Result.success(VkShaderModule(seg.unbox())) else Result.fail(retCode)
    }

    fun vkDestroyShaderModule(device: VkDevice, shaderModule: VkShaderModule, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroyShaderModule", device, shaderModule, allocator?.ref() ?: MemorySegment.NULL)

    fun vkCreatePipelineCache(device: VkDevice, createInfo: VkPipelineCacheCreateInfo, allocator: VkAllocationCallbacks?): Result<VkPipelineCache, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc(
            "vkCreatePipelineCache",
            Int::class,
            device,
            createInfo,
            allocator?.ref() ?: MemorySegment.NULL,
            seg
        )
        return if (retCode == VK_SUCCESS) Result.success(VkPipelineCache(seg.unbox())) else Result.fail(retCode)
    }

    fun vkDestroyPipelineCache(device: VkDevice, pipelineCache: VkPipelineCache, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroyPipelineCache", device, pipelineCache, allocator?.ref() ?: MemorySegment.NULL)

    fun vkGetPipelineCacheData(device: VkDevice, pipelineCache: VkPipelineCache): Result<ByteBuffer, Int> {
        val size = HeapInt()
        callFunc("vkGetPipelineCacheData", Int::class, device, pipelineCache, size, MemorySegment.NULL).apply {
            if (this != VK_SUCCESS) return Result.fail(this)
        }
        val seg = Arena.ofAuto().allocate(size.value().toLong())
        callFunc("vkGetPipelineCacheData", Int::class, device, pipelineCache, size, seg).apply {
            if (this != VK_SUCCESS) return Result.fail(this)
        }
        return Result.success(seg.asByteBuffer())
    }

    fun vkMergePipelineCaches(
        device: VkDevice,
        dstCache: VkPipelineCache,
        srcCaches: HeapPointerArray<VkPipelineCache>
    ): Int =
        callFunc("vkMergePipelineCaches", Int::class, device, dstCache, srcCaches.length, srcCaches)

    fun vkCreateGraphicsPipelines(
        device: VkDevice,
        pipelineCache: VkPipelineCache,
        createInfo: HeapStructArray<VkGraphicsPipelineCreateInfo>,
        allocator: VkAllocationCallbacks?
    ): Result<Array<VkPipeline>, Int> {
        val parr = HeapPointerArray<VkPipeline>(createInfo.length)
        val retCode = callFunc(
            "vkCreateGraphicsPipelines",
            Int::class,
            device,
            pipelineCache,
            createInfo.length,
            createInfo,
            allocator?.ref() ?: MemorySegment.NULL,
            parr
        )
        return if (retCode == VK_SUCCESS) Result.success(
            parr.value().map { VkPipeline(it) }.toTypedArray()
        ) else Result.fail(retCode)
    }

    fun vkCreateComputePipelines(
        device: VkDevice,
        pipelineCache: VkPipelineCache,
        createInfo: HeapStructArray<VkComputePipelineCreateInfo>,
        allocator: VkAllocationCallbacks?
    ): Result<Array<VkPipeline>, Int> {
        val parr = HeapPointerArray<VkPipeline>(createInfo.length)
        val retCode = callFunc(
            "vkCreateComputePipelines",
            Int::class,
            device,
            pipelineCache,
            createInfo.length,
            createInfo,
            allocator?.ref() ?: MemorySegment.NULL,
            parr
        )
        return if (retCode == VK_SUCCESS) Result.success(
            parr.value().map { VkPipeline(it) }.toTypedArray()
        ) else Result.fail(retCode)
    }

    fun vkDestroyPipeline(device: VkDevice, pipeline: VkPipeline, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroyPipeline", device, pipeline, allocator?.ref() ?: MemorySegment.NULL)

    fun vkCreatePipelineLayout(device: VkDevice, createInfo: VkPipelineLayoutCreateInfo, allocator: VkAllocationCallbacks?): Result<VkPipelineLayout, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc(
            "vkCreatePipelineLayout",
            Int::class,
            device,
            createInfo,
            allocator?.ref() ?: MemorySegment.NULL,
            seg
        )
        return if (retCode == VK_SUCCESS) Result.success(VkPipelineLayout(seg.unbox())) else Result.fail(retCode)
    }

    fun vkDestroyPipelineLayout(device: VkDevice, pipelineLayout: VkPipelineLayout, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroyPipelineLayout", device, pipelineLayout, allocator?.ref() ?: MemorySegment.NULL)

    fun vkCreateSampler(
        device: VkDevice,
        createInfo: VkSamplerCreateInfo,
        allocator: VkAllocationCallbacks?
    ): Result<VkSampler, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode =
            callFunc("vkCreateSampler", Int::class, device, createInfo, allocator?.ref() ?: MemorySegment.NULL)
        return if (retCode == VK_SUCCESS) Result.success(VkSampler(seg.unbox())) else Result.fail(retCode)
    }

    fun vkDestroySampler(device: VkDevice, sampler: VkSampler, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroySampler", device, sampler, allocator?.ref() ?: MemorySegment.NULL)

    fun vkCreateDescriptorSetLayout(
        device: VkDevice,
        createInfo: VkDescriptorSetLayoutCreateInfo,
        allocator: VkAllocationCallbacks?
    ): Result<VkDescriptorSetLayout, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode =
            callFunc(
                "vkCreateDescriptorSetLayout",
                Int::class,
                device,
                createInfo,
                allocator?.ref() ?: MemorySegment.NULL
            )
        return if (retCode == VK_SUCCESS) Result.success(VkDescriptorSetLayout(seg.unbox())) else Result.fail(
            retCode
        )
    }

    fun vkDestroyDescriptorSetLayout(
        device: VkDevice,
        layout: VkDescriptorSetLayout,
        allocator: VkAllocationCallbacks?
    ) =
        callVoidFunc("vkDestroyDescriptorSetLayout", device, layout, allocator?.ref() ?: MemorySegment.NULL)

    fun vkCreateDescriptorPool(
        device: VkDevice,
        createInfo: VkDescriptorPoolCreateInfo,
        allocator: VkAllocationCallbacks?
    ): Result<VkDescriptorPool, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc(
            "vkCreateDescriptorPool",
            Int::class,
            device,
            createInfo,
            allocator?.ref() ?: MemorySegment.NULL
        )
        return if (retCode == VK_SUCCESS) Result.success(
            VkDescriptorPool(
                seg.get(
                    ADDRESS,
                    0
                )
            )
        ) else Result.fail(retCode)
    }

    fun vkDestroyDescriptorPool(device: VkDevice, pool: VkDescriptorPool, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroyDescriptorPool", device, pool, allocator?.ref() ?: MemorySegment.NULL)

    fun vkResetDescriptorPool(device: VkDevice, pool: VkDescriptorPool, flags: Int): Int =
        callFunc("vkResetDescriptorPool", Int::class, device, pool, flags)

    fun vkAllocateDescriptorSets(
        device: VkDevice,
        createInfo: VkDescriptorSetAllocateInfo
    ): Result<Array<VkDescriptorSet>, Int> {
        val parr = HeapPointerArray<VkDescriptorSet>(createInfo.count())
        val retCode = callFunc("vkAllocateDescriptorSets", Int::class, device, createInfo, parr)
        return if (retCode == VK_SUCCESS) Result.success(
            parr.value().map { VkDescriptorSet(it) }.toTypedArray()
        ) else Result.fail(retCode)
    }

    fun vkFreeDescriptorSets(device: VkDevice, sets: HeapPointerArray<VkDescriptorSet>): Int =
        callFunc("vkFreeDescriptorSets", Int::class, device, sets.length, sets)

    fun vkUpdateDescriptorSets(
        device: VkDevice,
        writes: HeapStructArray<VkWriteDescriptorSet>,
        copies: HeapStructArray<VkCopyDescriptorSet>
    ): Int =
        callFunc(
            "vkUpdateDescriptorSets",
            Int::class,
            device,
            writes.length,
            copies.length,
            writes,
            copies
        )

    fun vkCreateFramebuffer(
        device: VkDevice,
        createInfo: VkFramebufferCreateInfo,
        allocator: VkAllocationCallbacks?
    ): Result<VkFramebuffer, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc(
            "vkCreateFramebuffer",
            Int::class,
            device,
            createInfo,
            allocator?.ref() ?: MemorySegment.NULL,
            seg
        )
        return if (retCode == VK_SUCCESS) Result.success(VkFramebuffer(seg.unbox())) else Result.fail(retCode)
    }

    fun vkDestroyFramebuffer(device: VkDevice, framebuffer: VkFramebuffer, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroyFramebuffer", device, framebuffer, allocator?.ref() ?: MemorySegment.NULL)

    fun vkCreateRenderPass(
        device: VkDevice,
        createInfo: VkRenderPassCreateInfo,
        allocator: VkAllocationCallbacks?
    ): Result<VkRenderPass, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode =
            callFunc("vkCreateRenderPass", Int::class, device, createInfo, allocator?.ref() ?: MemorySegment.NULL, seg)
        return if (retCode == VK_SUCCESS) Result.success(VkRenderPass(seg.unbox())) else Result.fail(retCode)
    }

    fun vkDestroyRenderPass(device: VkDevice, renderPass: VkRenderPass, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroyRenderPass", device, renderPass, allocator?.ref() ?: MemorySegment.NULL)

    fun vkGetRenderAreaGranularity(device: VkDevice, renderPass: VkRenderPass): Vector2i {
        val seg = Arena.ofAuto().allocate(4 * 2)
        callVoidFunc("vkGetRenderAreaGranularity", device, renderPass, seg)
        return Vector2i(seg.get(JAVA_INT, 0), seg.get(JAVA_INT, 4))
    }

    fun vkCreateCommandPool(
        device: VkDevice,
        createInfo: VkCommandPoolCreateInfo,
        allocator: VkAllocationCallbacks?
    ): Result<VkCommandPool, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc(
            "vkCreateCommandPool",
            Int::class,
            device,
            createInfo,
            allocator?.ref() ?: MemorySegment.NULL,
            seg
        )
        return if (retCode == VK_SUCCESS) Result.success(VkCommandPool(seg.unbox())) else Result.fail(retCode)
    }

    fun vkDestroyCommandPool(device: VkDevice, commandPool: VkCommandPool, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroyCommandPool", device, commandPool, allocator?.ref() ?: MemorySegment.NULL)

    fun vkResetCommandPool(device: VkDevice, commandPool: VkCommandPool, flags: Int): Int =
        callFunc("vkResetCommandPool", Int::class, device, commandPool, flags)

    fun vkAllocateCommandBuffers(
        device: VkDevice,
        createInfo: VkCommandBufferAllocateInfo
    ): Result<Array<VkCommandBuffer>, Int> {
        val parr = HeapPointerArray<VkCommandBuffer>(createInfo.count())
        val retCode = callFunc("vkAllocateCommandBuffers", Int::class, device, createInfo, parr)
        return if (retCode == VK_SUCCESS) Result.success(
            parr.value().map { VkCommandBuffer(it) }.toTypedArray()
        ) else Result.fail(retCode)
    }

    fun vkFreeCommandBuffers(
        device: VkDevice,
        commandPool: VkCommandPool,
        commandBuffer: HeapPointerArray<VkCommandBuffer>
    ) =
        callVoidFunc("vkFreeCommandBuffers", device, commandPool, commandBuffer.length, commandBuffer)

    fun vkBeginCommandBuffer(commandBuffer: VkCommandBuffer, beginInfo: VkCommandBufferBeginInfo): Int =
        callFunc("vkBeginCommandBuffer", Int::class, commandBuffer, beginInfo)

    fun vkEndCommandBuffer(commandBuffer: VkCommandBuffer): Int =
        callFunc("vkEndCommandBuffer", Int::class, commandBuffer)

    fun vkResetCommandBuffer(commandBuffer: VkCommandBuffer, flags: Int): Int =
        callFunc("vkResetCommandBuffer", Int::class, commandBuffer, flags)

    fun vkCmdBindPipeline(commandBuffer: VkCommandBuffer, pipelineBindPoint: Int, pipeline: VkPipeline) =
        callVoidFunc("vkCmdBindPipeline", commandBuffer, pipelineBindPoint, pipeline)

    fun vkCmdSetViewport(commandBuffer: VkCommandBuffer, firstViewport: Int, viewports: HeapStructArray<VkViewport>) =
        callVoidFunc("vkCmdSetViewport", commandBuffer, firstViewport, viewports.length, viewports)

    fun vkCmdSetScissor(commandBuffer: VkCommandBuffer, firstScissor: Int, scissors: HeapStructArray<VkRect2D>) =
        callVoidFunc("vkCmdSetScissor", commandBuffer, firstScissor, scissors.length, scissors)

    fun vkCmdSetLineWidth(commandBuffer: VkCommandBuffer, lineWidth: Int) =
        callVoidFunc("vkCmdSetLineWidth", commandBuffer, lineWidth)

    fun vkCmdSetDepthBias(
        commandBuffer: VkCommandBuffer,
        depthBiasConstantFactor: Float,
        depthBiasClamp: Float,
        depthBiasSlopeFactor: Float
    ) =
        callVoidFunc("vkCmdSetDepthBias", commandBuffer, depthBiasConstantFactor, depthBiasClamp, depthBiasSlopeFactor)

    fun vkCmdSetBlendConstants(commandBuffer: VkCommandBuffer, blendConstants: HeapFloatArray) =
        callVoidFunc("vkCmdSetBlendConstants", commandBuffer, blendConstants)

    fun vkCmdSetDepthBounds(commandBuffer: VkCommandBuffer, minDepthBounds: Float, maxDepthBounds: Float) =
        callVoidFunc("vkCmdSetDepthBounds", commandBuffer, minDepthBounds, maxDepthBounds)

    fun vkCmdSetStencilCompareMask(commandBuffer: VkCommandBuffer, faceMask: Int, compareMask: Int) =
        callVoidFunc("vkCmdSetStencilCompareMask", commandBuffer, faceMask, compareMask)

    fun vkCmdSetStencilWriteMask(commandBuffer: VkCommandBuffer, faceMask: Int, writeMask: Int) =
        callVoidFunc("vkCmdSetStencilWriteMask", commandBuffer, faceMask, writeMask)

    fun vkCmdSetStencilReference(commandBuffer: VkCommandBuffer, faceMask: Int, reference: Int) =
        callVoidFunc("vkCmdSetStencilReference", commandBuffer, faceMask, reference)

    fun vkCmdBindDescriptorSets(
        commandBuffer: VkCommandBuffer,
        pipelineBindPoint: Int,
        layout: VkPipelineLayout,
        firstSet: Int,
        sets: HeapPointerArray<VkDescriptorSet>,
        dynamicOffsets: HeapIntArray
    ) =
        callVoidFunc(
            "vkCmdBindDescriptorSets",
            commandBuffer,
            pipelineBindPoint,
            layout,
            firstSet,
            sets.length,
            sets,
            dynamicOffsets.length,
            dynamicOffsets
        )

    fun vkCmdBindIndexBuffer(commandBuffer: VkCommandBuffer, buffer: VkBuffer, offset: Long, indexType: Int) =
        callVoidFunc("vkCmdBindIndexBuffer", commandBuffer, buffer, offset, indexType)

    fun vkCmdBindVertexBuffers(
        commandBuffer: VkCommandBuffer,
        firstBinding: Int,
        buffers: HeapPointerArray<VkBuffer>,
        offsets: HeapLongArray
    ) =
        callVoidFunc(
            "vkCmdBindVertexBuffers",
            commandBuffer,
            firstBinding,
            min(buffers.length, offsets.length),
            buffers,
            offsets
        )

    fun vkCmdDraw(
        commandBuffer: VkCommandBuffer,
        vertexCount: Int,
        instanceCount: Int,
        firstVertex: Int,
        firstInstance: Int
    ) =
        callVoidFunc("vkCmdDraw", commandBuffer, vertexCount, instanceCount, firstVertex, firstInstance)

    fun vkCmdDrawIndexed(
        commandBuffer: VkCommandBuffer,
        indexCount: Int,
        instanceCount: Int,
        firstIndex: Int,
        vertexOffset: Int,
        firstInstance: Int
    ) =
        callVoidFunc(
            "vkCmdDrawIndexed",
            commandBuffer,
            indexCount,
            instanceCount,
            firstIndex,
            vertexOffset,
            firstInstance
        )

    fun vkCmdDrawIndirect(commandBuffer: VkCommandBuffer, buffer: VkBuffer, offset: Long, drawCount: Int, stride: Int) =
        callVoidFunc("vkCmdDrawIndirect", commandBuffer, buffer, offset, drawCount, stride)

    fun vkCmdDrawIndexedIndirect(
        commandBuffer: VkCommandBuffer,
        buffer: VkBuffer,
        offset: Long,
        drawCount: Int,
        stride: Int
    ) =
        callVoidFunc("vkCmdDrawIndexedIndirect", commandBuffer, buffer, offset, drawCount, stride)

    fun vkCmdDispatch(commandBuffer: VkCommandBuffer, groupCountX: Int, groupCountY: Int, groupCountZ: Int) =
        callVoidFunc("vkCmdDispatch", commandBuffer, groupCountX, groupCountY, groupCountZ)

    fun vkCmdDispatchIndirect(commandBuffer: VkCommandBuffer, buffer: VkBuffer, offset: Long) =
        callVoidFunc("vkCmdDispatchIndirect", commandBuffer, buffer, offset)

    fun vkCmdCopyBuffer(
        commandBuffer: VkCommandBuffer,
        srcBuffer: VkBuffer,
        dstBuffer: VkBuffer,
        regions: HeapStructArray<VkBufferCopy>
    ) =
        callVoidFunc("vkCmdCopyBuffer", commandBuffer, srcBuffer, dstBuffer, regions.length, regions)

    fun vkCmdCopyImage(
        commandBuffer: VkCommandBuffer,
        srcImage: VkImage,
        srcImageLayout: Int,
        dstImage: VkImage,
        dstImageLayout: Int,
        regions: HeapStructArray<VkImageCopy>
    ) =
        callVoidFunc(
            "vkCmdCopyImage",
            commandBuffer,
            srcImage,
            srcImageLayout,
            dstImage,
            dstImageLayout,
            regions.length,
            regions
        )

    fun vkCmdBlitImage(
        commandBuffer: VkCommandBuffer,
        srcImage: VkImage,
        srcImageLayout: Int,
        dstImage: VkImage,
        dstImageLayout: Int,
        regions: HeapStructArray<VkImageBlit>,
        filter: Int
    ) =
        callVoidFunc(
            "vkCmdBlitImage",
            commandBuffer,
            srcImage,
            srcImageLayout,
            dstImage,
            dstImageLayout,
            regions.length,
            regions,
            filter
        )

    fun vkCmdCopyBufferToImage(
        commandBuffer: VkCommandBuffer,
        srcBuffer: VkBuffer,
        dstImage: VkImage,
        dstImageLayout: Int,
        regions: HeapPointerArray<VkBufferImageCopy>
    ) =
        callVoidFunc(
            "vkCmdCopyBufferToImage",
            commandBuffer,
            srcBuffer,
            dstImage,
            dstImageLayout,
            regions.length,
            regions
        )

    fun vkCmdCopyImageToBuffer(
        commandBuffer: VkCommandBuffer,
        srcImage: VkImage,
        srcImageLayout: Int,
        dstBuffer: VkBuffer,
        regions: HeapPointerArray<VkBufferImageCopy>
    ) =
        callVoidFunc(
            "vkCmdCopyImageToBuffer",
            commandBuffer,
            srcImage,
            srcImageLayout,
            dstBuffer,
            regions.length,
            regions
        )

    fun vkCmdUpdateBuffer(commandBuffer: VkCommandBuffer, dstBuffer: VkBuffer, dstOffset: Long, data: MemorySegment) =
        callVoidFunc("vkCmdUpdateBuffer", commandBuffer, dstBuffer, dstOffset, data)

    fun vkCmdFillBuffer(commandBuffer: VkCommandBuffer, dstBuffer: VkBuffer, dstOffset: Long, size: Long, data: Int) =
        callVoidFunc("vkCmdFillBuffer", commandBuffer, dstBuffer, dstOffset, size, data)

    fun vkCmdClearColorImage(
        commandBuffer: VkCommandBuffer,
        image: VkImage,
        imageLayout: Int,
        color: VkClearColorValue,
        ranges: HeapStructArray<VkImageSubresourceRange>
    ) =
        callVoidFunc("vkCmdClearColorImage", commandBuffer, image, imageLayout, color, ranges.length, ranges)

    fun vkCmdClearDepthStencilImage(
        commandBuffer: VkCommandBuffer,
        image: VkImage,
        imageLayout: Int,
        depthStencil: VkClearDepthStencilValue,
        ranges: HeapStructArray<VkImageSubresourceRange>
    ) =
        callVoidFunc(
            "vkCmdClearDepthStencilImage",
            commandBuffer,
            image,
            imageLayout,
            depthStencil,
            ranges.length,
            ranges
        )

    fun vkCmdClearAttachments(
        commandBuffer: VkCommandBuffer,
        attachments: HeapStructArray<VkClearAttachment>,
        rects: HeapStructArray<VkClearRect>
    ) =
        callVoidFunc(
            "vkCmdClearAttachments",
            commandBuffer,
            attachments.length,
            attachments,
            rects.length,
            rects
        )

    fun vkCmdResolveImage(
        commandBuffer: VkCommandBuffer,
        srcImage: VkImage,
        srcImageLayout: Int,
        dstImage: VkImage,
        dstImageLayout: Int,
        regions: HeapStructArray<VkImageResolve>
    ) =
        callVoidFunc(
            "vkCmdResolveImage",
            commandBuffer,
            srcImage,
            srcImageLayout,
            dstImage,
            dstImageLayout,
            regions.length,
            regions
        )

    fun vkCmdSetEvent(commandBuffer: VkCommandBuffer, event: VkEvent, stageMask: Int) =
        callVoidFunc("vkCmdSetEvent", commandBuffer, event, stageMask)

    fun vkCmdResetEvent(commandBuffer: VkCommandBuffer, event: VkEvent, stageMask: Int) =
        callVoidFunc("vkCmdResetEvent", commandBuffer, event, stageMask)

    fun vkCmdWaitEvents(
        commandBuffer: VkCommandBuffer,
        events: HeapPointerArray<VkEvent>,
        srcStageMask: Int,
        dstStageMask: Int,
        memoryBarriers: HeapStructArray<VkMemoryBarrier>,
        bufferMemoryBarriers: HeapStructArray<VkBufferMemoryBarrier>,
        imageMemoryBarriers: HeapStructArray<VkImageMemoryBarrier>
    ) =
        callVoidFunc(
            "vkCmdWaitEvents",
            commandBuffer,
            events.length,
            events,
            srcStageMask,
            dstStageMask,
            memoryBarriers.length,
            memoryBarriers,
            bufferMemoryBarriers.length,
            bufferMemoryBarriers,
            imageMemoryBarriers.length,
            imageMemoryBarriers
        )

    fun vkCmdPipelineBarrier(
        commandBuffer: VkCommandBuffer,
        srcStageMask: Int,
        dstStageMask: Int,
        memoryBarriers: HeapStructArray<VkMemoryBarrier>,
        bufferMemoryBarriers: HeapStructArray<VkBufferMemoryBarrier>,
        imageMemoryBarriers: HeapStructArray<VkImageMemoryBarrier>
    ) =
        callVoidFunc(
            "vkCmdPipelineBarrier",
            commandBuffer,
            srcStageMask,
            dstStageMask,
            memoryBarriers.length,
            memoryBarriers,
            bufferMemoryBarriers.length,
            bufferMemoryBarriers,
            imageMemoryBarriers.length,
            imageMemoryBarriers
        )

    fun vkCmdBeginQuery(commandBuffer: VkCommandBuffer, queryPool: VkQueryPool, query: Int, flags: Int) =
        callVoidFunc("vkCmdBeginQuery", commandBuffer, queryPool, query, flags)

    fun vkCmdEndQuery(commandBuffer: VkCommandBuffer, queryPool: VkQueryPool, query: Int) =
        callVoidFunc("vkCmdEndQuery", commandBuffer, queryPool, query)

    fun vkCmdResetQueryPool(commandBuffer: VkCommandBuffer, queryPool: VkQueryPool, firstQuery: Int, queryCount: Int) =
        callVoidFunc("vkCmdResetQueryPool", commandBuffer, queryPool, firstQuery, queryCount)

    fun vkCmdWriteTimestamp(commandBuffer: VkCommandBuffer, pipelineStage: Int, queryPool: VkQueryPool, query: Int) =
        callVoidFunc("vkCmdWriteTimestamp", commandBuffer, pipelineStage, queryPool, query)

    fun vkCmdCopyQueryPoolResults(
        commandBuffer: VkCommandBuffer,
        queryPool: VkQueryPool,
        firstQuery: Int,
        queryCount: Int,
        dstBuffer: VkBuffer,
        dstOffset: Long,
        stride: Long,
        flags: Int
    ) =
        callVoidFunc(
            "vkCmdCopyQueryPoolResults",
            commandBuffer,
            queryPool,
            firstQuery,
            queryCount,
            dstBuffer,
            dstOffset,
            stride,
            flags
        )

    fun vkCmdPushConstants(
        commandBuffer: VkCommandBuffer,
        layout: VkPipelineLayout,
        stageFlags: Int,
        offset: Int,
        values: MemorySegment
    ) =
        callVoidFunc("vkCmdPushConstants", commandBuffer, layout, stageFlags, offset, values)

    fun vkCmdBeginRenderPass(commandBuffer: VkCommandBuffer, renderPassBegin: VkRenderPassBeginInfo, contents: Int) =
        callVoidFunc("vkCmdBeginRenderPass", commandBuffer, renderPassBegin, contents)

    fun vkCmdNextSubpass(commandBuffer: VkCommandBuffer, contents: Int) =
        callVoidFunc("vkCmdNextSubpass", commandBuffer, contents)

    fun vkCmdEndRenderPass(commandBuffer: VkCommandBuffer) =
        callVoidFunc("vkCmdEndRenderPass", commandBuffer)

    fun vkCmdExecuteCommands(commandBuffer: VkCommandBuffer, commandBuffers: HeapPointerArray<VkCommandBuffer>) =
        callVoidFunc("vkCmdExecuteCommands", commandBuffer, commandBuffers.length, commandBuffers)


}
