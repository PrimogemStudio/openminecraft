package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_APPLICATION_INFO
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_BIND_SPARSE_INFO
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_BUFFER_VIEW_CREATE_INFO
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_EVENT_CREATE_INFO
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_FENCE_CREATE_INFO
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_IMAGE_CREATE_INFO
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_MAPPED_MEMORY_RANGE
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_PIPELINE_CACHE_CREATE_INFO
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_PIPELINE_INPUT_ASSEMBLY_STATE_CREATE_INFO
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_PIPELINE_RASTERIZATION_STATE_CREATE_INFO
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_PIPELINE_TESSELLATION_STATE_CREATE_INFO
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_PIPELINE_VERTEX_INPUT_STATE_CREATE_INFO
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_QUERY_POOL_CREATE_INFO
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_SEMAPHORE_CREATE_INFO
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_SUBMIT_INFO
import com.primogemstudio.engine.interfaces.*
import com.primogemstudio.engine.interfaces.NativeMethodCache.callFunc
import com.primogemstudio.engine.interfaces.NativeMethodCache.callPointerFunc
import com.primogemstudio.engine.interfaces.NativeMethodCache.callVoidFunc
import com.primogemstudio.engine.interfaces.heap.HeapInt
import com.primogemstudio.engine.interfaces.heap.HeapLong
import com.primogemstudio.engine.interfaces.heap.IHeapVar
import com.primogemstudio.engine.interfaces.struct.*
import com.primogemstudio.engine.loader.Platform.sizetLength
import com.primogemstudio.engine.types.Result
import org.joml.Vector2i
import org.joml.Vector3i
import org.joml.Vector4f
import org.joml.Vector4i
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*
import java.nio.ByteBuffer

data class VkApplicationInfo(
    private val next: IStruct? = null,
    private val appName: String,
    private val appVersion: Int,
    private val engineName: String,
    private val engineVersion: Int,
    private val apiVersion: Int
) : IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS_UNALIGNED,
        ADDRESS_UNALIGNED,
        JAVA_LONG,
        ADDRESS_UNALIGNED,
        JAVA_LONG,
        JAVA_LONG
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_APPLICATION_INFO)
        seg.set(ADDRESS, 8 * 1, next?.pointer()?: MemorySegment.NULL)
        seg.set(ADDRESS, 8 * 1 + sizetLength() * 1L, appName.toCString())
        seg.set(JAVA_INT, 8 * 1 + sizetLength() * 2L, appVersion)
        seg.set(ADDRESS, 8 * 2 + sizetLength() * 2L, engineName.toCString())
        seg.set(JAVA_INT, 8 * 2 + sizetLength() * 3L, engineVersion)
        seg.set(JAVA_INT, 8 * 3 + sizetLength() * 3L, apiVersion)
    }
}

data class VkInstanceCreateInfo(
    private val next: IStruct? = null,
    private val flag: Int = 0,
    private val appInfo: VkApplicationInfo,
    private val layers: List<String> = listOf(),
    private val extensions: List<String> = listOf(),
) : IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        appInfo.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG,
        ADDRESS
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO)
        seg.set(ADDRESS, 8 * 1, next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, 8 * 1 + sizetLength() * 1L, flag)
        seg.set(ADDRESS, 8 * 2 + sizetLength() * 1L, appInfo.pointer())
        seg.set(JAVA_INT, 8 * 2 + sizetLength() * 2L, layers.size)
        seg.set(ADDRESS, 8 * 3 + sizetLength() * 2L, layers.toTypedArray().toCStrArray())
        seg.set(JAVA_INT, 8 * 3 + sizetLength() * 3L, extensions.size)
        seg.set(ADDRESS, 8 * 4 + sizetLength() * 3L, extensions.toTypedArray().toCStrArray())
    }
}

class VkInstance(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg
}

class VkPhysicalDevice(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg
}

class VkPhysicalDeviceFeatures : IHeapVar<MemorySegment> {
    private val seg = Arena.ofAuto().allocate(MemoryLayout.structLayout(
        *Array<MemoryLayout>(55) { _ -> JAVA_INT }
    ))

    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    fun featureAt(i: Int): Boolean = seg.get(JAVA_INT, 4L * i) == 1
    fun setFeature(i: Int, enabled: Boolean) = seg.set(JAVA_INT, 4L * i, if (enabled) 1 else 0)
}

class VkFormatProperties : IHeapVar<MemorySegment> {
    private val seg = Arena.ofAuto().allocate(MemoryLayout.structLayout(
        *Array<MemoryLayout>(3) { _ -> JAVA_INT }
    ))

    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val linearTilingFeatures: Int get() = seg.get(JAVA_INT, 0)
    val optimalTilingFeatures: Int get() = seg.get(JAVA_INT, 4)
    val bufferFeatures: Int get() = seg.get(JAVA_INT, 8)
}

class VkImageFormatProperties : IHeapVar<MemorySegment> {
    private val seg = Arena.ofAuto().allocate(
        MemoryLayout.structLayout(
            JAVA_FLOAT, JAVA_FLOAT, JAVA_FLOAT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_LONG
        )
    )

    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val maxExtent: Vector3i get() = Vector3i(seg.get(JAVA_INT, 0), seg.get(JAVA_INT, 4), seg.get(JAVA_INT, 8))
    val maxMipLevels: UInt get() = seg.get(JAVA_INT, 12).toUInt()
    val maxArrayLayers: UInt get() = seg.get(JAVA_INT, 16).toUInt()
    val sampleCounts: Int get() = seg.get(JAVA_INT, 20)
    val maxResourceSize: Long get() = seg.get(JAVA_LONG, 24)
}

class VkPhysicalDeviceLimits(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val maxImageDimension1D: UInt get() = seg.get(JAVA_INT, 0).toUInt()
    val maxImageDimension2D: UInt get() = seg.get(JAVA_INT, 4).toUInt()
    val maxImageDimension3D: UInt get() = seg.get(JAVA_INT, 8).toUInt()
    val maxImageDimensionCube: UInt get() = seg.get(JAVA_INT, 12).toUInt()
    val maxImageArrayLayers: UInt get() = seg.get(JAVA_INT, 16).toUInt()
    val maxTexelBufferElements: UInt get() = seg.get(JAVA_INT, 20).toUInt()
    val maxUniformBufferRange: UInt get() = seg.get(JAVA_INT, 24).toUInt()
    val maxStorageBufferRange: UInt get() = seg.get(JAVA_INT, 28).toUInt()
    val maxPushConstantsSize: UInt get() = seg.get(JAVA_INT, 32).toUInt()
    val maxMemoryAllocationCount: UInt get() = seg.get(JAVA_INT, 36).toUInt()
    val maxSamplerAllocationCount: UInt get() = seg.get(JAVA_INT, 40).toUInt()
    val bufferImageGranularity: Long get() = seg.get(JAVA_LONG, 48)
    val sparseAddressSpaceSize: Long get() = seg.get(JAVA_LONG, 56)
    val maxBoundDescriptorSets: UInt get() = seg.get(JAVA_INT, 64).toUInt()
    val maxPerStageDescriptorSamplers: UInt get() = seg.get(JAVA_INT, 68).toUInt()
    val maxPerStageDescriptorUniformBuffers: UInt get() = seg.get(JAVA_INT, 72).toUInt()
    val maxPerStageDescriptorStorageBuffers: UInt get() = seg.get(JAVA_INT, 76).toUInt()
    val maxPerStageDescriptorSampledImages: UInt get() = seg.get(JAVA_INT, 80).toUInt()
    val maxPerStageDescriptorStorageImages: UInt get() = seg.get(JAVA_INT, 84).toUInt()
    val maxPerStageDescriptorInputAttachments: UInt get() = seg.get(JAVA_INT, 88).toUInt()
    val maxPerStageResources: UInt get() = seg.get(JAVA_INT, 92).toUInt()
    val maxDescriptorSetSamplers: UInt get() = seg.get(JAVA_INT, 96).toUInt()
    val maxDescriptorSetUniformBuffers: UInt get() = seg.get(JAVA_INT, 100).toUInt()
    val maxDescriptorSetUniformBuffersDynamic: UInt get() = seg.get(JAVA_INT, 104).toUInt()
    val maxDescriptorSetStorageBuffers: UInt get() = seg.get(JAVA_INT, 108).toUInt()
    val maxDescriptorSetStorageBuffersDynamic: UInt get() = seg.get(JAVA_INT, 112).toUInt()
    val maxDescriptorSetSampledImages: UInt get() = seg.get(JAVA_INT, 116).toUInt()
    val maxDescriptorSetStorageImages: UInt get() = seg.get(JAVA_INT, 120).toUInt()
    val maxDescriptorSetInputAttachments: UInt get() = seg.get(JAVA_INT, 124).toUInt()
    val maxVertexInputAttributes: UInt get() = seg.get(JAVA_INT, 128).toUInt()
    val maxVertexInputBindings: UInt get() = seg.get(JAVA_INT, 132).toUInt()
    val maxVertexInputAttributeOffset: UInt get() = seg.get(JAVA_INT, 136).toUInt()
    val maxVertexInputBindingStride: UInt get() = seg.get(JAVA_INT, 140).toUInt()
    val maxVertexOutputComponents: UInt get() = seg.get(JAVA_INT, 144).toUInt()
    val maxTessellationGenerationLevel: UInt get() = seg.get(JAVA_INT, 148).toUInt()
    val maxTessellationPatchSize: UInt get() = seg.get(JAVA_INT, 152).toUInt()
    val maxTessellationControlPerVertexInputComponents: UInt get() = seg.get(JAVA_INT, 156).toUInt()
    val maxTessellationControlPerVertexOutputComponents: UInt get() = seg.get(JAVA_INT, 160).toUInt()
    val maxTessellationControlPerPatchOutputComponents: UInt get() = seg.get(JAVA_INT, 164).toUInt()
    val maxTessellationControlTotalOutputComponents: UInt get() = seg.get(JAVA_INT, 168).toUInt()
    val maxTessellationEvaluationInputComponents: UInt get() = seg.get(JAVA_INT, 172).toUInt()
    val maxTessellationEvaluationOutputComponents: UInt get() = seg.get(JAVA_INT, 176).toUInt()
    val maxGeometryShaderInvocations: UInt get() = seg.get(JAVA_INT, 180).toUInt()
    val maxGeometryInputComponents: UInt get() = seg.get(JAVA_INT, 184).toUInt()
    val maxGeometryOutputComponents: UInt get() = seg.get(JAVA_INT, 188).toUInt()
    val maxGeometryOutputVertices: UInt get() = seg.get(JAVA_INT, 192).toUInt()
    val maxGeometryTotalOutputComponents: UInt get() = seg.get(JAVA_INT, 196).toUInt()
    val maxFragmentInputComponents: UInt get() = seg.get(JAVA_INT, 200).toUInt()
    val maxFragmentOutputAttachments: UInt get() = seg.get(JAVA_INT, 204).toUInt()
    val maxFragmentDualSrcAttachments: UInt get() = seg.get(JAVA_INT, 208).toUInt()
    val maxFragmentCombinedOutputResources: UInt get() = seg.get(JAVA_INT, 212).toUInt()
    val maxComputeSharedMemorySize: UInt get() = seg.get(JAVA_INT, 216).toUInt()
    val maxComputeWorkGroupCount: IntArray get() = seg.asSlice(220, 12).toArray(JAVA_INT)
    val maxComputeWorkGroupInvocations: UInt get() = seg.get(JAVA_INT, 232).toUInt()
    val maxComputeWorkGroupSize: IntArray get() = seg.asSlice(236, 12).toArray(JAVA_INT)
    val subPixelPrecisionBits: UInt get() = seg.get(JAVA_INT, 248).toUInt()
    val subTexelPrecisionBits: UInt get() = seg.get(JAVA_INT, 252).toUInt()
    val mipmapPrecisionBits: UInt get() = seg.get(JAVA_INT, 256).toUInt()
    val maxDrawIndexedIndexValue: UInt get() = seg.get(JAVA_INT, 260).toUInt()
    val maxDrawIndirectCount: UInt get() = seg.get(JAVA_INT, 264).toUInt()
    val maxSamplerLodBias: Float get() = seg.get(JAVA_FLOAT, 268)
    val maxSamplerAnisotropy: Float get() = seg.get(JAVA_FLOAT, 272)
    val maxViewports: UInt get() = seg.get(JAVA_INT, 276).toUInt()
    val maxViewportDimensions: IntArray get() = seg.asSlice(280, 8).toArray(JAVA_INT)
    val viewportBoundsRange: FloatArray get() = seg.asSlice(288, 8).toArray(JAVA_FLOAT)
    val viewportSubPixelBits: Int get() = seg.get(JAVA_INT, 296)
    val minMemoryMapAlignment: Long get() = seg.get(JAVA_LONG, 304)
    val minTexelBufferOffsetAlignment: Long get() = seg.get(JAVA_LONG, 312)
    val minUniformBufferOffsetAlignment: Long get() = seg.get(JAVA_LONG, 320)
    val minStorageBufferOffsetAlignment: Long get() = seg.get(JAVA_LONG, 328)
    val minTexelOffset: UInt get() = seg.get(JAVA_INT, 336).toUInt()
    val maxTexelOffset: UInt get() = seg.get(JAVA_INT, 340).toUInt()
    val minTexelGatherOffset: UInt get() = seg.get(JAVA_INT, 344).toUInt()
    val maxTexelGatherOffset: UInt get() = seg.get(JAVA_INT, 348).toUInt()
    val minInterpolationOffset: Float get() = seg.get(JAVA_FLOAT, 352)
    val maxInterpolationOffset: Float get() = seg.get(JAVA_FLOAT, 356)
    val subPixelInterpolationOffsetBits: UInt get() = seg.get(JAVA_INT, 360).toUInt()
    val maxFramebufferWidth: UInt get() = seg.get(JAVA_INT, 364).toUInt()
    val maxFramebufferHeight: UInt get() = seg.get(JAVA_INT, 368).toUInt()
    val maxFramebufferLayers: UInt get() = seg.get(JAVA_INT, 372).toUInt()
    val framebufferColorSampleCounts: Int get() = seg.get(JAVA_INT, 376)
    val framebufferDepthSampleCounts: Int get() = seg.get(JAVA_INT, 380)
    val framebufferStencilSampleCounts: Int get() = seg.get(JAVA_INT, 384)
    val framebufferNoAttachmentsSampleCounts: Int get() = seg.get(JAVA_INT, 388)
    val maxColorAttachments: UInt get() = seg.get(JAVA_INT, 392).toUInt()
    val sampledImageColorSampleCounts: Int get() = seg.get(JAVA_INT, 396)
    val sampledImageIntegerSampleCounts: Int get() = seg.get(JAVA_INT, 400)
    val sampledImageDepthSampleCounts: Int get() = seg.get(JAVA_INT, 404)
    val sampledImageStencilSampleCounts: Int get() = seg.get(JAVA_INT, 408)
    val storageImageSampleCounts: Int get() = seg.get(JAVA_INT, 412)
    val maxSampleMaskWords: UInt get() = seg.get(JAVA_INT, 416).toUInt()
    val timestampComputeAndGraphics: Boolean get() = seg.get(JAVA_INT, 420) != 0
    val timestampPeriod: Float get() = seg.get(JAVA_FLOAT, 424)
    val maxClipDistances: UInt get() = seg.get(JAVA_INT, 428).toUInt()
    val maxCullDistances: UInt get() = seg.get(JAVA_INT, 432).toUInt()
    val maxCombinedClipAndCullDistances: UInt get() = seg.get(JAVA_INT, 436).toUInt()
    val discreteQueuePriorities: UInt get() = seg.get(JAVA_INT, 440).toUInt()
    val pointSizeRange: FloatArray get() = seg.asSlice(444, 8).toArray(JAVA_FLOAT)
    val lineWidthRange: FloatArray get() = seg.asSlice(452, 8).toArray(JAVA_FLOAT)
    val pointSizeGranularity: Float get() = seg.get(JAVA_FLOAT, 460)
    val lineWidthGranularity: Float get() = seg.get(JAVA_FLOAT, 464)
    val strictLines: Boolean get() = seg.get(JAVA_INT, 468) != 0
    val standardSampleLocations: Boolean get() = seg.get(JAVA_INT, 472) != 0
    val optimalBufferCopyOffsetAlignment: Long get() = seg.get(JAVA_LONG, 480)
    val optimalBufferCopyRowPitchAlignment: Long get() = seg.get(JAVA_LONG, 488)
    val nonCoherentAtomSize: Long get() = seg.get(JAVA_LONG, 496)
}

class VkPhysicalDeviceSparseProperties(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val residencyStandard2DBlockShape: Boolean get() = seg.get(JAVA_INT, 0) != 0
    val residencyStandard2DMultisampleBlockShape: Boolean get() = seg.get(JAVA_INT, 4) != 0
    val residencyStandard3DBlockShape: Boolean get() = seg.get(JAVA_INT, 8) != 0
    val residencyAlignedMipSize: Boolean get() = seg.get(JAVA_INT, 12) != 0
    val residencyNonResidentStrict: Boolean get() = seg.get(JAVA_INT, 16) != 0
}

class VkPhysicalDeviceProperties : IHeapVar<MemorySegment> {
    private val seg = Arena.ofAuto().allocate(
        MemoryLayout.structLayout(
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            JAVA_INT,
            MemoryLayout.paddingLayout(256),
            MemoryLayout.paddingLayout(16 + 4),
            MemoryLayout.paddingLayout(504),
            MemoryLayout.paddingLayout(24),
        )
    )

    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val apiVersion: Int get() = seg.get(JAVA_INT, 0)
    val driverVersion: Int get() = seg.get(JAVA_INT, 4)
    val vendorId: Int get() = seg.get(JAVA_INT, 8)
    val deviceID: Int get() = seg.get(JAVA_INT, 12)
    val deviceType: Int get() = seg.get(JAVA_INT, 16)
    val deviceName: String get() = seg.asSlice(20, 256).fetchString()
    val pipelineCacheUUID: ByteArray get() = seg.asSlice(276, 16).toArray(JAVA_BYTE)
    val limits: VkPhysicalDeviceLimits get() = VkPhysicalDeviceLimits(seg.asSlice(296, 504))
    val sparseProperties: VkPhysicalDeviceSparseProperties
        get() = VkPhysicalDeviceSparseProperties(
            seg.asSlice(
                800,
                20
            )
        )
}

class VkQueueFamilyProperties(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val queueFlags: Int get() = seg.get(JAVA_INT, 0)
    val queueCount: Int get() = seg.get(JAVA_INT, 4)
    val timestampValidBits: Int get() = seg.get(JAVA_INT, 8)
    val minImageTransferGranularity: Vector3i
        get() = Vector3i(
            seg.get(JAVA_INT, 12),
            seg.get(JAVA_INT, 16),
            seg.get(JAVA_INT, 20)
        )
}

class VkMemoryType(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val propertyFlags: Int get() = seg.get(JAVA_INT, 0)
    val heapIndex: Int get() = seg.get(JAVA_INT, 4)
}

class VkMemoryHeap(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val size: Long get() = seg.get(JAVA_LONG, 0)
    val flags: Int get() = seg.get(JAVA_INT, 8)
}

class VkPhysicalDeviceMemoryProperties : IHeapVar<MemorySegment> {
    private val seg: MemorySegment = Arena.ofAuto().allocate(
        MemoryLayout.structLayout(
            JAVA_INT,
            MemoryLayout.paddingLayout(256),
            JAVA_INT,
            MemoryLayout.paddingLayout(256)
        )
    )

    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val memoryTypeCount: UInt =
        seg.get(JAVA_INT, 0).toUInt()

    fun memoryType(idx: Int): VkMemoryType = VkMemoryType(seg.asSlice(4L + idx * 8L, 8))

    val memoryHeapCount: UInt =
        seg.get(JAVA_INT, 260).toUInt()

    fun memoryHeap(idx: Int): VkMemoryHeap = VkMemoryHeap(seg.asSlice(264L + idx * 16L, 16))
}

data class VkDeviceQueueCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val queueFamilyIndex: Int,
    private val queuePriorities: FloatArrayStruct
) : IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_INT,
        JAVA_INT,
        JAVA_LONG,
        ADDRESS
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer()?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(JAVA_INT, sizetLength() + 12L, queueFamilyIndex)
        seg.set(JAVA_INT, sizetLength() + 16L, queuePriorities.arr.size)
        seg.set(ADDRESS, sizetLength() + 24L, queuePriorities.pointer())
    }
}

data class VkDeviceCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val queueCreateInfos: ArrayStruct<VkDeviceQueueCreateInfo>,
    private val enabledLayers: List<String> = listOf(),
    private val enabledExtensions: List<String> = listOf(),
    private val features: VkPhysicalDeviceFeatures = VkPhysicalDeviceFeatures()
) : IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        queueCreateInfos.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_INT,
        JAVA_INT,
        ADDRESS,
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG,
        ADDRESS,
        ADDRESS
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer()?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(JAVA_INT, sizetLength() + 12L, queueCreateInfos.arr.size)
        seg.set(ADDRESS, sizetLength() + 16L, queueCreateInfos.pointer())
        seg.set(JAVA_INT, sizetLength() * 2 + 16L, enabledLayers.size)
        seg.set(ADDRESS, sizetLength() * 2 + 24L, enabledLayers.toTypedArray().toCStrArray())
        seg.set(JAVA_INT, sizetLength() * 3 + 24L, enabledExtensions.size)
        seg.set(ADDRESS, sizetLength() * 3 + 32L, enabledExtensions.toTypedArray().toCStrArray())
        seg.set(ADDRESS, sizetLength() * 4 + 32L, features.ref())
    }
}

class VkDevice(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg
}

class VkExtensionProperties(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val extensionName: String get() = seg.get(ADDRESS, 0).fetchString()
    val version: Int get() = seg.get(JAVA_INT, 256)
}

class VkLayerProperties(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val layerName: String get() = seg.get(ADDRESS, 0).fetchString()
    val specVersion: Int get() = seg.get(JAVA_INT, 256)
    val implementationVersion: Int get() = seg.get(JAVA_INT, 260)
    val description: String get() = seg.get(ADDRESS, 264).fetchString()
}

class VkQueue(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg
}

class VkFence(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg
}

class VkSemaphore(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg
}

class VkCommandBuffer(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg
}

data class VkSubmitInfo(
    private val next: IStruct? = null, 
    private val waitSemaphores: PointerArrayStruct<VkSemaphore>,
    private val waitDstStageMask: IntArrayStruct,
    private val commandBuffers: PointerArrayStruct<VkCommandBuffer>,
    private val signalSemaphores: PointerArrayStruct<VkSemaphore>
): IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        waitSemaphores.close()
        commandBuffers.close()
        signalSemaphores.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG,
        ADDRESS,
        ADDRESS,
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG,
        ADDRESS
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_SUBMIT_INFO)
        seg.set(ADDRESS, 8, next?.pointer()?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, waitSemaphores.arr.size)
        seg.set(ADDRESS, sizetLength() + 16L, waitSemaphores.pointer())
        seg.set(ADDRESS, sizetLength() * 2 + 16L, waitDstStageMask.pointer())
        seg.set(JAVA_INT, sizetLength() * 3 + 16L, commandBuffers.arr.size)
        seg.set(ADDRESS, sizetLength() * 3 + 24L, commandBuffers.pointer())
        seg.set(JAVA_INT, sizetLength() * 4 + 24L, signalSemaphores.arr.size)
        seg.set(ADDRESS, sizetLength() * 4 + 32L, signalSemaphores.pointer())
    }
}

class VkDeviceMemory(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg
}

data class VkMemoryAllocateInfo(
    private val next: IStruct? = null, 
    private val allocationSize: Long, 
    private val typeIndex: Int
): IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG,
        JAVA_LONG
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer()?: MemorySegment.NULL)
        seg.set(JAVA_LONG, sizetLength() + 8L, allocationSize)
        seg.set(JAVA_INT, sizetLength() + 16L, typeIndex)
    }
}

data class VkMappedMemoryRange(
    private val next: IStruct? = null, 
    private val memory: VkDeviceMemory, 
    private val offset: Long, 
    private val length: Long
): IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        ADDRESS,
        JAVA_LONG,
        JAVA_LONG
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_MAPPED_MEMORY_RANGE)
        seg.set(ADDRESS, 8, next?.pointer()?: MemorySegment.NULL)
        seg.set(ADDRESS, sizetLength() + 8L, memory.ref())
        seg.set(JAVA_LONG, sizetLength() * 2 + 8L, offset)
        seg.set(JAVA_LONG, sizetLength() * 2 + 16L, length)
    }
}

class VkBuffer(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg
}

class VkImage(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg
}

class VkMemoryRequirements: IHeapVar<MemorySegment> {
    private val seg: MemorySegment = Arena.ofAuto().allocate(
        MemoryLayout.structLayout(
            JAVA_LONG, 
            JAVA_LONG, 
            JAVA_INT
        )
    )

    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val size: Long get() = seg.get(JAVA_LONG, 0)
    val alignment: Long get() = seg.get(JAVA_LONG, 8)
    val type: UInt get() = seg.get(JAVA_INT, 16).toUInt()
}

class VkSparseImageFormatProperties(private val seg: MemorySegment): IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val aspectMask: Int get() = seg.get(JAVA_INT, 0)
    val imageGranularity: Vector3i get() = Vector3i(seg.get(JAVA_INT, 4), seg.get(JAVA_INT, 8), seg.get(JAVA_INT, 12))
    val flags: Int get() = seg.get(JAVA_INT, 16)
}

class VkSparseImageMemoryRequirements(private val seg: MemorySegment): IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val formatProperties: VkSparseImageFormatProperties get() = VkSparseImageFormatProperties(seg.asSlice(0, 20))
    val imageMipTailFirstLod: UInt get() = seg.get(JAVA_INT, 20).toUInt()
    val imageMipTailSize: Long get() = seg.get(JAVA_LONG, 24)
    val imageMipTailOffset: Long get() = seg.get(JAVA_LONG, 32)
    val imageMipTailStride: Long get() = seg.get(JAVA_LONG, 40)
}

data class VkSparseMemoryBind(
    private val resourceOffset: Long, 
    private val size: Long, 
    private val memory: VkDeviceMemory, 
    private val memoryOffset: Long, 
    private val flags: Int
): IStruct() {
    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG, 
        JAVA_INT
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_LONG, 0, resourceOffset)
        seg.set(JAVA_LONG, 8, size)
        seg.set(ADDRESS, 16, memory.ref())
        seg.set(JAVA_LONG, sizetLength() + 16L, memoryOffset)
        seg.set(JAVA_INT, sizetLength() + 24L, flags)
    }
}

data class VkImageSubresource(
    private val aspectMask: Int,
    private val mipmap: Int,
    private val arrayLayer: Int
): IStruct() {
    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_INT,
        JAVA_INT,
        JAVA_INT
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, aspectMask)
        seg.set(JAVA_INT, 4, mipmap)
        seg.set(JAVA_INT, 8, arrayLayer)
    }
}

data class VkSparseImageMemoryBind(
    private val subresource: VkImageSubresource,
    private val offset: Vector3i,
    private val extent: Vector3i,
    private val memory: VkDeviceMemory,
    private val memoryOffset: Long,
    private val flags: Int
): IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        subresource.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        MemoryLayout.paddingLayout(12),
        MemoryLayout.paddingLayout(12),
        MemoryLayout.paddingLayout(12 + 4),
        ADDRESS,
        JAVA_LONG,
        JAVA_LONG
    )

    override fun construct(seg: MemorySegment) {
        subresource.construct(seg.asSlice(0, 12))
        seg.set(JAVA_INT, 12, offset.x)
        seg.set(JAVA_INT, 16, offset.y)
        seg.set(JAVA_INT, 20, offset.z)
        seg.set(JAVA_INT, 24, extent.x)
        seg.set(JAVA_INT, 28, extent.y)
        seg.set(JAVA_INT, 32, extent.z)
        seg.set(ADDRESS, 40, memory.ref())
        seg.set(JAVA_LONG, 48, memoryOffset)
        seg.set(JAVA_INT, 56, flags)
    }
}

data class VkSparseBufferMemoryBindInfo(
    private val buffer: VkBuffer, 
    private val binds: ArrayStruct<VkSparseMemoryBind>
): IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        binds.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        ADDRESS,
        JAVA_LONG,
        ADDRESS
    )

    override fun construct(seg: MemorySegment) {
        seg.set(ADDRESS, 0, buffer.ref())
        seg.set(JAVA_INT, 8, binds.arr.size)
        seg.set(ADDRESS, 16, binds.pointer())
    }
}

data class VkSparseImageOpaqueMemoryBindInfo(
    private val buffer: VkBuffer, 
    private val binds: ArrayStruct<VkSparseImageMemoryBind>
): IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        binds.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        ADDRESS,
        JAVA_LONG,
        ADDRESS
    )

    override fun construct(seg: MemorySegment) {
        seg.set(ADDRESS, 0, buffer.ref())
        seg.set(JAVA_INT, 8, binds.arr.size)
        seg.set(ADDRESS, 16, binds.pointer())
    }
}

data class VkSparseImageMemoryBindInfo(
    private val buffer: VkBuffer,
    private val binds: ArrayStruct<VkSparseImageMemoryBind>
): IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        binds.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        ADDRESS,
        JAVA_LONG,
        ADDRESS
    )

    override fun construct(seg: MemorySegment) {
        seg.set(ADDRESS, 0, buffer.ref())
        seg.set(JAVA_INT, 8, binds.arr.size)
        seg.set(ADDRESS, 16, binds.pointer())
    }
}

data class VkBindSparseInfo(
    private val next: IStruct? = null, 
    private val waitSemaphores: PointerArrayStruct<VkSemaphore>,
    private val bufferBinds: ArrayStruct<VkSparseBufferMemoryBindInfo>, 
    private val imageOpaqueBinds: ArrayStruct<VkSparseImageOpaqueMemoryBindInfo>,
    private val imageBinds: ArrayStruct<VkSparseImageMemoryBindInfo>,
    private val signalSemaphores: PointerArrayStruct<VkSemaphore>
): IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        waitSemaphores.close()
        bufferBinds.close()
        imageOpaqueBinds.close()
        imageBinds.close()
        signalSemaphores.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG,
        ADDRESS
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_BIND_SPARSE_INFO)
        seg.set(ADDRESS, 8, next?.pointer()?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, waitSemaphores.arr.size)
        seg.set(ADDRESS, sizetLength() + 16L, waitSemaphores.pointer())
        seg.set(JAVA_INT, sizetLength() * 2 + 16L, bufferBinds.arr.size)
        seg.set(ADDRESS, sizetLength() * 2 + 24L, bufferBinds.pointer())
        seg.set(JAVA_INT, sizetLength() * 3 + 24L, imageOpaqueBinds.arr.size)
        seg.set(ADDRESS, sizetLength() * 3 + 30L, imageOpaqueBinds.pointer())
        seg.set(JAVA_INT, sizetLength() * 4 + 30L, imageBinds.arr.size)
        seg.set(ADDRESS, sizetLength() * 4 + 38L, imageBinds.pointer())
        seg.set(JAVA_INT, sizetLength() * 5 + 38L, signalSemaphores.arr.size)
        seg.set(ADDRESS, sizetLength() * 5 + 46L, signalSemaphores.pointer())
    }
}

data class VkFenceCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0
): IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_FENCE_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer()?: MemorySegment.NULL)
        seg.set(JAVA_INT, 16, flags)
    }
}

data class VkSemaphoreCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0
): IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_SEMAPHORE_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer()?: MemorySegment.NULL)
        seg.set(JAVA_INT, 16, flags)
    }
}

data class VkEventCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0
): IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_LONG
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_EVENT_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer()?: MemorySegment.NULL)
        seg.set(JAVA_INT, 16, flags)
    }
}

class VkEvent(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg
}

class VkQueryPool(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg
}

data class VkQueryPoolCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0, 
    private val queryType: Int, 
    private val queryCount: UInt, 
    private val pipelineStatistics: Int
): IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG, 
        ADDRESS,
        JAVA_INT, 
        JAVA_INT,
        JAVA_INT, 
        JAVA_INT
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_QUERY_POOL_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer()?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(JAVA_INT, sizetLength() + 16L, queryType)
        seg.set(JAVA_INT, sizetLength() + 24L, queryCount.toInt())
        seg.set(JAVA_INT, sizetLength() + 32L, pipelineStatistics)
    }
}

data class VkBufferCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0, 
    private val size: Long, 
    private val usage: Int, 
    private val sharingMode: Int, 
    private val queueFamilyIndices: IntArrayStruct
): IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        queueFamilyIndices.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG, 
        ADDRESS, 
        JAVA_LONG, 
        JAVA_LONG, 
        JAVA_INT, 
        JAVA_INT, 
        JAVA_LONG, 
        ADDRESS
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer()?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(JAVA_LONG, sizetLength() + 16L, size)
        seg.set(JAVA_INT, sizetLength() + 24L, usage)
        seg.set(JAVA_INT, sizetLength() + 28L, sharingMode)
        seg.set(JAVA_INT, sizetLength() + 32L, queueFamilyIndices.arr.size)
        seg.set(ADDRESS, sizetLength() + 40L, queueFamilyIndices.pointer())
    }
}

class VkBufferView(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg
}

class VkBufferViewCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0, 
    private val buffer: VkBuffer, 
    private val format: Int, 
    private val offset: Long, 
    private val range: Long
): IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG, 
        ADDRESS,
        JAVA_LONG, 
        JAVA_LONG, 
        JAVA_LONG, 
        JAVA_LONG, 
        JAVA_LONG
    )
    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_BUFFER_VIEW_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer()?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(ADDRESS, sizetLength() + 16L, buffer.ref())
        seg.set(JAVA_INT, sizetLength() + 24L, format)
        seg.set(JAVA_LONG, sizetLength() + 32L, offset)
        seg.set(JAVA_LONG, sizetLength() + 40L, range)
    }
}

class VkImageCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0, 
    private val imageType: Int, 
    private val format: Int, 
    private val extent: Vector3i, 
    private val mipLevels: Int, 
    private val arrayLayers: Int, 
    private val samples: Int, 
    private val tiling: Int, 
    private val usage: Int, 
    private val sharingMode: Int, 
    private val queueFamilyIndices: IntArrayStruct, 
    private val initialLayout: Int
): IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        queueFamilyIndices.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG, 
        ADDRESS,
        JAVA_INT, 
        JAVA_INT, 
        JAVA_LONG, 
        JAVA_INT, JAVA_INT, JAVA_INT, 
        JAVA_INT, 
        JAVA_INT, 
        JAVA_INT, 
        JAVA_INT, 
        JAVA_INT, 
        JAVA_INT, 
        JAVA_LONG, 
        ADDRESS, 
        JAVA_LONG
    )
    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_IMAGE_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer()?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(JAVA_INT, sizetLength() + 12L, imageType)
        seg.set(JAVA_INT, sizetLength() + 16L, format)
        seg.set(JAVA_INT, sizetLength() + 20L, extent.x)
        seg.set(JAVA_INT, sizetLength() + 24L, extent.y)
        seg.set(JAVA_INT, sizetLength() + 28L, extent.z)

        seg.set(JAVA_INT, sizetLength() + 32L, mipLevels)
        seg.set(JAVA_INT, sizetLength() + 36L, arrayLayers)
        seg.set(JAVA_INT, sizetLength() + 40L, samples)
        seg.set(JAVA_INT, sizetLength() + 44L, tiling)
        seg.set(JAVA_INT, sizetLength() + 48L, usage)
        seg.set(JAVA_INT, sizetLength() + 52L, sharingMode)
        seg.set(JAVA_INT, sizetLength() + 56L, queueFamilyIndices.arr.size)
        seg.set(ADDRESS, sizetLength() + 64L, queueFamilyIndices.pointer())
        seg.set(JAVA_INT, sizetLength() * 2 + 64L, initialLayout)
    }
}

class VkSubresourceLayout(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg

    val offset: Long get() = seg.get(JAVA_LONG, 0)
    val size: Long get() = seg.get(JAVA_LONG, 8)
    val rowPitch: Long get() = seg.get(JAVA_LONG, 16)
    val arrayPitch: Long get() = seg.get(JAVA_LONG, 24)
    val depthPitch: Long get() = seg.get(JAVA_LONG, 32)
}

class VkImageSubresourceRange(
    private val aspectMask: Int, 
    private val baseMipLevel: Int, 
    private val levelCount: Int, 
    private val baseArrayLayer: Int, 
    private val layerCount: Int
): IStruct() {
    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_INT, 
        JAVA_INT, 
        JAVA_INT, 
        JAVA_INT, 
        JAVA_INT
    )
    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, aspectMask)
        seg.set(JAVA_INT, 4, baseMipLevel)
        seg.set(JAVA_INT, 8, levelCount)
        seg.set(JAVA_INT, 12, baseArrayLayer)
        seg.set(JAVA_INT, 16, layerCount)
    }
}

class VkImageViewCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0, 
    private val image: VkImage, 
    private val viewType: Int,
    private val format: Int, 
    private val components: Vector4i, 
    private val range: VkImageSubresourceRange
): IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        range.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG, 
        ADDRESS,
        JAVA_LONG, 
        JAVA_LONG, 
        JAVA_INT, 
        JAVA_INT,
        JAVA_INT, JAVA_INT, JAVA_INT, JAVA_INT, 
        MemoryLayout.paddingLayout(20 + 4)
    )
    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer()?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(ADDRESS, sizetLength() + 16L, image.ref())
        seg.set(JAVA_INT, sizetLength() + 24L, viewType)
        seg.set(JAVA_INT, sizetLength() + 28L, format)

        seg.set(JAVA_INT, sizetLength() + 32L, components.x)
        seg.set(JAVA_INT, sizetLength() + 36L, components.y)
        seg.set(JAVA_INT, sizetLength() + 40L, components.z)
        seg.set(JAVA_INT, sizetLength() + 44L, components.w)

        range.construct(seg.asSlice(sizetLength() + 48L, 16L))
    }
}

class VkImageView(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg
}

class VkShaderModuleCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0, 
    private val code: ByteArrayStruct
): IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        code.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG, 
        ADDRESS,
        JAVA_LONG, 
        ADDRESS, 
        ADDRESS
    )
    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer()?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(JAVA_INT, sizetLength() + 16L, code.arr.size)
        seg.set(ADDRESS, sizetLength() * 2 + 16L, code.pointer())
    }
}

class VkShaderModule(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg
}

class VkPipelineCacheCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0, 
    private val initialData: ByteArrayStruct
): IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        initialData.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG, 
        ADDRESS,
        JAVA_LONG, 
        ADDRESS, 
        ADDRESS
    )
    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_PIPELINE_CACHE_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer()?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(JAVA_INT, sizetLength() + 16L, initialData.arr.size)
        seg.set(ADDRESS, sizetLength() * 2 + 16L, initialData.pointer())
    }
}

class VkPipelineCache(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg
}

class VkSpecializationMapEntry(
    private val constantId: Int,
    private val offset: Int,
    private val size: Long
) : IStruct() {
    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_INT,
        JAVA_INT,
        JAVA_LONG
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, constantId)
        seg.set(JAVA_INT, 4, offset)
        seg.set(JAVA_LONG, 8, size)
    }
}

class VkSpecializationInfo(
    private val mapEntries: ArrayStruct<VkSpecializationMapEntry>,
    private val data: ByteArrayStruct
) : IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        mapEntries.close()
        data.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        ADDRESS,
        ADDRESS
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, mapEntries.arr.size)
        seg.set(ADDRESS, 8, mapEntries.pointer())
        seg.set(JAVA_INT, sizetLength() + 8L, data.arr.size)
        seg.set(ADDRESS, sizetLength() * 2 + 8L, data.pointer())
    }
}

class VkPipelineShaderStageCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val stage: Int,
    private val module: VkShaderModule,
    private val name: String,
    private val specializationInfo: VkSpecializationInfo
) : IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        specializationInfo.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_INT,
        JAVA_INT,
        JAVA_LONG,
        ADDRESS,
        ADDRESS
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(JAVA_INT, sizetLength() + 12L, stage)
        seg.set(ADDRESS, sizetLength() + 16L, module.ref())
        seg.set(ADDRESS, sizetLength() + 24L, name.toCString())
        seg.set(ADDRESS, sizetLength() * 2 + 24L, specializationInfo.pointer())
    }
}

class VkVertexInputBindingDescription(
    private val binding: Int,
    private val stride: Int,
    private val inputRate: Int
) : IStruct() {
    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_INT,
        JAVA_INT,
        JAVA_INT
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, binding)
        seg.set(JAVA_INT, 4, stride)
        seg.set(JAVA_INT, 8, inputRate)
    }
}

class VkVertexInputAttributeDescription(
    private val location: Int,
    private val binding: Int,
    private val format: Int,
    private val position: Int
) : IStruct() {
    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, location)
        seg.set(JAVA_INT, 4, binding)
        seg.set(JAVA_INT, 8, format)
        seg.set(JAVA_INT, 12, position)
    }
}

class VkPipelineVertexInputStateCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val bindings: ArrayStruct<VkVertexInputBindingDescription>,
    private val attributes: ArrayStruct<VkVertexInputAttributeDescription>
) : IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        bindings.close()
        attributes.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_INT,
        JAVA_INT,
        ADDRESS,
        JAVA_LONG,
        ADDRESS
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_PIPELINE_VERTEX_INPUT_STATE_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(JAVA_INT, sizetLength() + 12L, bindings.arr.size)
        seg.set(ADDRESS, sizetLength() + 16L, bindings.pointer())
        seg.set(JAVA_INT, sizetLength() * 2 + 16L, attributes.arr.size)
        seg.set(ADDRESS, sizetLength() * 2 + 24L, attributes.pointer())
    }
}

class VkPipelineInputAssemblyStateCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val topology: Int,
    private val primitiveRestartEnable: Boolean
) : IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_INT,
        JAVA_INT,
        JAVA_LONG
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_PIPELINE_INPUT_ASSEMBLY_STATE_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(JAVA_INT, sizetLength() + 12L, topology)
        seg.set(JAVA_INT, sizetLength() + 16L, if (primitiveRestartEnable) 1 else 0)
    }
}

class VkPipelineTessellationStateCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val patchControlPoints: Int,
) : IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_INT,
        JAVA_INT
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_PIPELINE_TESSELLATION_STATE_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(JAVA_INT, sizetLength() + 12L, patchControlPoints)
    }
}

class VkRect2D(
    private val offset: Vector2i,
    private val extent: Vector2i
) : IStruct() {
    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, offset.x)
        seg.set(JAVA_INT, 4, offset.y)
        seg.set(JAVA_INT, 8, extent.x)
        seg.set(JAVA_INT, 12, extent.y)
    }
}

class VkViewport(
    private val x: Float,
    private val y: Float,
    private val width: Float,
    private val height: Float,
    private val minDepth: Float,
    private val maxDepth: Float
) : IStruct() {
    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_FLOAT,
        JAVA_FLOAT,
        JAVA_FLOAT,
        JAVA_FLOAT,
        JAVA_FLOAT,
        JAVA_FLOAT
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_FLOAT, 0, x)
        seg.set(JAVA_FLOAT, 4, y)
        seg.set(JAVA_FLOAT, 8, width)
        seg.set(JAVA_FLOAT, 12, height)
        seg.set(JAVA_FLOAT, 16, minDepth)
        seg.set(JAVA_FLOAT, 20, maxDepth)
    }
}

class VkPipelineViewportStateCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val viewports: ArrayStruct<VkViewport>,
    private val scissors: ArrayStruct<VkRect2D>
) : IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        viewports.close()
        scissors.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_INT,
        JAVA_INT,
        ADDRESS,
        JAVA_LONG,
        ADDRESS
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_PIPELINE_TESSELLATION_STATE_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(JAVA_INT, sizetLength() + 12L, viewports.arr.size)
        seg.set(ADDRESS, sizetLength() + 16L, viewports.pointer())
        seg.set(JAVA_INT, sizetLength() * 2 + 16L, scissors.arr.size)
        seg.set(ADDRESS, sizetLength() * 2 + 24L, scissors.pointer())
    }
}

class VkPipelineRasterizationStateCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val depthClampEnable: Boolean,
    private val rasterizerDiscardEnable: Boolean,
    private val polygonMode: Int,
    private val cullMode: Int,
    private val frontFace: Int,
    private val depthBiasEnable: Boolean,
    private val depthBiasConstantFactor: Float,
    private val depthBiasClamp: Float,
    private val depthBiasSlopeFactor: Float,
    private val lineWidth: Float
) : IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_FLOAT,
        JAVA_FLOAT,
        JAVA_FLOAT,
        JAVA_FLOAT,
        MemoryLayout.paddingLayout(4)
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_PIPELINE_RASTERIZATION_STATE_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(JAVA_INT, sizetLength() + 12L, if (depthClampEnable) 1 else 0)
        seg.set(JAVA_INT, sizetLength() + 16L, if (rasterizerDiscardEnable) 1 else 0)
        seg.set(JAVA_INT, sizetLength() + 20L, polygonMode)
        seg.set(JAVA_INT, sizetLength() + 24L, cullMode)
        seg.set(JAVA_INT, sizetLength() + 28L, frontFace)
        seg.set(JAVA_INT, sizetLength() + 32L, if (depthBiasEnable) 1 else 0)
        seg.set(JAVA_FLOAT, sizetLength() + 36L, depthBiasConstantFactor)
        seg.set(JAVA_FLOAT, sizetLength() + 40L, depthBiasClamp)
        seg.set(JAVA_FLOAT, sizetLength() + 44L, depthBiasSlopeFactor)
        seg.set(JAVA_FLOAT, sizetLength() + 48L, lineWidth)
    }
}

class VkPipelineMultisampleStateCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val rasterizationSamples: Int,
    private val sampleShadingEnable: Boolean,
    private val minSampleShading: Float,
    private val sampleMask: IntArrayStruct,
    private val alphaToCoverageEnable: Boolean,
    private val alphaToOneEnable: Boolean
) : IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        sampleMask.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_FLOAT,
        ADDRESS,
        JAVA_INT,
        JAVA_INT
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(JAVA_INT, sizetLength() + 12L, rasterizationSamples)
        seg.set(JAVA_INT, sizetLength() + 16L, if (sampleShadingEnable) 1 else 0)
        seg.set(JAVA_FLOAT, sizetLength() + 20L, minSampleShading)
        seg.set(ADDRESS, sizetLength() + 24L, sampleMask.pointer())
        seg.set(JAVA_INT, sizetLength() * 2 + 24L, if (alphaToCoverageEnable) 1 else 0)
        seg.set(JAVA_INT, sizetLength() * 2 + 28L, if (alphaToOneEnable) 1 else 0)
    }
}

class VkStencilOpState(
    private val failOp: Int,
    private val passOp: Int,
    private val depthFailOp: Int,
    private val compareOp: Int,
    private val compareMask: Int,
    private val writeMask: Int,
    private val reference: Int
) : IStruct() {
    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, failOp)
        seg.set(JAVA_INT, 4, passOp)
        seg.set(JAVA_INT, 8, depthFailOp)
        seg.set(JAVA_INT, 12, compareOp)
        seg.set(JAVA_INT, 16, compareMask)
        seg.set(JAVA_INT, 20, writeMask)
        seg.set(JAVA_INT, 24, reference)
    }
}

class VkPipelineDepthStencilStateCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val depthTestEnable: Boolean,
    private val depthWriteEnable: Boolean,
    private val depthCompareOp: Int,
    private val depthBoundsTestEnable: Boolean,
    private val stencilTestEnable: Boolean,
    private val front: VkStencilOpState,
    private val back: VkStencilOpState,
    private val minDepthBounds: Float,
    private val maxDepthBounds: Float
) : IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        front.close()
        back.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        MemoryLayout.paddingLayout(28 + 4),
        MemoryLayout.paddingLayout(28 + 4),
        JAVA_FLOAT,
        JAVA_FLOAT
    )
    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(JAVA_INT, sizetLength() + 12L, if (depthTestEnable) 1 else 0)
        seg.set(JAVA_INT, sizetLength() + 16L, if (depthWriteEnable) 1 else 0)
        seg.set(JAVA_INT, sizetLength() + 20L, depthCompareOp)
        seg.set(JAVA_INT, sizetLength() + 24L, if (depthBoundsTestEnable) 1 else 0)
        seg.set(JAVA_INT, sizetLength() + 28L, if (stencilTestEnable) 1 else 0)
        front.construct(seg.asSlice(sizetLength() + 32L, 28))
        back.construct(seg.asSlice(sizetLength() + 60L, 28))
        seg.set(JAVA_FLOAT, sizetLength() + 88L, minDepthBounds)
        seg.set(JAVA_FLOAT, sizetLength() + 92L, maxDepthBounds)
    }
}

class VkPipelineColorBlendAttachmentState(
    private val blendEnable: Boolean,
    private val srcColorBlendFactor: Int,
    private val dstColorBlendFactor: Int,
    private val colorBlendOp: Int,
    private val srcAlphaBlendFactor: Int,
    private val dstAlphaBlendFactor: Int,
    private val alphaBlendOp: Int,
    private val colorWriteMask: Int
) : IStruct() {
    init {
        construct(seg)
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, if (blendEnable) 1 else 0)
        seg.set(JAVA_INT, 4, srcColorBlendFactor)
        seg.set(JAVA_INT, 8, dstColorBlendFactor)
        seg.set(JAVA_INT, 12, colorBlendOp)
        seg.set(JAVA_INT, 16, srcAlphaBlendFactor)
        seg.set(JAVA_INT, 20, dstAlphaBlendFactor)
        seg.set(JAVA_INT, 24, alphaBlendOp)
        seg.set(JAVA_INT, 28, colorWriteMask)
    }
}

class VkPipelineColorBlendStateCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val logicOpEnable: Boolean,
    private val logicOp: Int,
    private val attachments: ArrayStruct<VkPipelineColorBlendAttachmentState>,
    private val blendConstants: Vector4f
) : IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        attachments.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        JAVA_INT,
        ADDRESS,
        JAVA_FLOAT,
        JAVA_FLOAT,
        JAVA_FLOAT,
        JAVA_FLOAT
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(JAVA_INT, sizetLength() + 12L, if (logicOpEnable) 1 else 0)
        seg.set(JAVA_INT, sizetLength() + 16L, logicOp)
        seg.set(JAVA_INT, sizetLength() + 20L, attachments.arr.size)
        seg.set(ADDRESS, sizetLength() + 24L, attachments.pointer())
        seg.set(JAVA_FLOAT, sizetLength() * 2 + 24L, blendConstants.x)
        seg.set(JAVA_FLOAT, sizetLength() * 2 + 28L, blendConstants.y)
        seg.set(JAVA_FLOAT, sizetLength() * 2 + 32L, blendConstants.z)
        seg.set(JAVA_FLOAT, sizetLength() * 2 + 36L, blendConstants.w)
    }
}

class VkPipelineDynamicStateCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val dynamicStates: IntArrayStruct
) : IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_INT,
        JAVA_INT,
        ADDRESS
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(JAVA_INT, sizetLength() + 12L, dynamicStates.arr.size)
        seg.set(ADDRESS, sizetLength() + 16L, dynamicStates.pointer())
    }
}

class VkPipeline(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg
}

class VkPipelineLayout(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg
}

class VkRenderPass(private val seg: MemorySegment) : IHeapVar<MemorySegment> {
    override fun ref(): MemorySegment = seg
    override fun value(): MemorySegment = seg
}

class VkGraphicsPipelineCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0,
    private val stages: ArrayStruct<VkPipelineShaderStageCreateInfo>,
    private val vertex: VkPipelineVertexInputStateCreateInfo,
    private val inputAssembly: VkPipelineInputAssemblyStateCreateInfo,
    private val tessellation: VkPipelineTessellationStateCreateInfo,
    private val viewport: VkPipelineViewportStateCreateInfo,
    private val rasterization: VkPipelineRasterizationStateCreateInfo,
    private val multisample: VkPipelineMultisampleStateCreateInfo,
    private val depthStencil: VkPipelineDepthStencilStateCreateInfo,
    private val colorBlend: VkPipelineColorBlendStateCreateInfo,
    private val dynamic: VkPipelineDynamicStateCreateInfo,
    private val layout: VkPipelineLayout,
    private val renderPass: VkRenderPass,
    private val subpass: Int,
    private val basePipeline: VkPipeline,
    private val basePipelineIndex: Int
) : IStruct() {
    init {
        construct(seg)
    }

    override fun close() {
        next?.close()
        stages.close()
        vertex.close()
        inputAssembly.close()
        tessellation.close()
        viewport.close()
        rasterization.close()
        multisample.close()
        depthStencil.close()
        colorBlend.close()
        dynamic.close()
        super.close()
    }

    override fun layout(): MemoryLayout = MemoryLayout.structLayout(
        JAVA_LONG,
        ADDRESS,
        JAVA_INT,
        JAVA_INT,
        ADDRESS,
        ADDRESS,
        ADDRESS,
        ADDRESS,
        ADDRESS,
        ADDRESS,
        ADDRESS,
        ADDRESS,
        ADDRESS,
        ADDRESS,
        JAVA_LONG,
        JAVA_LONG,
        JAVA_LONG,
        JAVA_LONG,
        JAVA_LONG
    )

    override fun construct(seg: MemorySegment) {
        seg.set(JAVA_INT, 0, VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO)
        seg.set(ADDRESS, 8, next?.pointer() ?: MemorySegment.NULL)
        seg.set(JAVA_INT, sizetLength() + 8L, flags)
        seg.set(JAVA_INT, sizetLength() + 12L, stages.arr.size)
        seg.set(ADDRESS, sizetLength() + 16L, stages.pointer())
        seg.set(ADDRESS, sizetLength() * 2 + 16L, vertex.pointer())
        seg.set(ADDRESS, sizetLength() * 3 + 16L, inputAssembly.pointer())
        seg.set(ADDRESS, sizetLength() * 4 + 16L, tessellation.pointer())
        seg.set(ADDRESS, sizetLength() * 5 + 16L, viewport.pointer())
        seg.set(ADDRESS, sizetLength() * 6 + 16L, rasterization.pointer())
        seg.set(ADDRESS, sizetLength() * 7 + 16L, multisample.pointer())
        seg.set(ADDRESS, sizetLength() * 8 + 16L, depthStencil.pointer())
        seg.set(ADDRESS, sizetLength() * 9 + 16L, colorBlend.pointer())
        seg.set(ADDRESS, sizetLength() * 10 + 16L, dynamic.pointer())
        seg.set(ADDRESS, sizetLength() * 11 + 16L, layout.ref())
        seg.set(ADDRESS, sizetLength() * 11 + 24L, renderPass.ref())
        seg.set(JAVA_INT, sizetLength() * 11 + 32L, subpass)
        seg.set(ADDRESS, sizetLength() * 11 + 40L, basePipeline.ref())
        seg.set(JAVA_INT, sizetLength() * 11 + 48L, basePipelineIndex)
    }
}

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
        val retCode = callFunc("vkCreateInstance", Int::class, createInfo, allocator?.pointer()?: MemorySegment.NULL, seg)
        return if (retCode == 0) Result.success(VkInstance(seg.get(ADDRESS, 0))) else Result.fail(retCode)
    }

    fun vkDestroyInstance(instance: VkInstance, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroyInstance", instance, allocator?.pointer()?: MemorySegment.NULL)

    fun vkEnumeratePhysicalDevices(instance: VkInstance): Result<Array<VkPhysicalDevice>, Int> {
        val count = HeapInt()
        callFunc("vkEnumeratePhysicalDevices", Int::class, instance, count, MemorySegment.NULL).apply {
            if (this != VK_SUCCESS) return Result.fail(this)
        }
        val seg = Arena.ofAuto().allocate(sizetLength() * count.value() * 1L)
        callFunc("vkEnumeratePhysicalDevices", Int::class, instance, count, seg).apply {
            if (this != VK_SUCCESS) return Result.fail(this)
        }

        return Result.success(seg.toCPointerArray(count.value()).map { VkPhysicalDevice(it) }.toTypedArray())
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

    fun vkGetPhysicalDeviceProperties(physicalDevice: VkPhysicalDevice): VkPhysicalDeviceProperties =
        VkPhysicalDeviceProperties().apply { callVoidFunc("vkGetPhysicalDeviceProperties", physicalDevice, this) }

    fun vkGetPhysicalDeviceQueueFamilyProperties(
        physicalDevice: VkPhysicalDevice
    ): Array<VkQueueFamilyProperties> {
        val count = HeapInt()
        callVoidFunc("vkGetPhysicalDeviceQueueFamilyProperties", physicalDevice, count, MemorySegment.NULL)

        val seg = Arena.ofAuto().allocate(24L * count.value())
        callVoidFunc("vkGetPhysicalDeviceQueueFamilyProperties", physicalDevice, count, seg)

        return seg.fromCStructArray(count.value(), 24, { VkQueueFamilyProperties(it) }).toTypedArray()
    }

    fun vkGetPhysicalDeviceMemoryProperties(physicalDevice: VkPhysicalDevice): VkPhysicalDeviceMemoryProperties =
        VkPhysicalDeviceMemoryProperties().apply { callVoidFunc("vkGetPhysicalDeviceMemoryProperties", physicalDevice) }

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
        val retCode = callFunc("vkCreateDevice", Int::class, physicalDevice, createInfo, allocator?.pointer()?: MemorySegment.NULL, seg)
        return if (retCode == VK_SUCCESS) Result.success(VkDevice(seg.get(ADDRESS, 0))) else Result.fail(retCode)
    }

    fun vkDestroyDevice(device: VkDevice, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroyDevice", device, allocator?.pointer()?: MemorySegment.NULL)

    fun vkEnumerateInstanceExtensionProperties(layerName: String): Result<Array<VkExtensionProperties>, Int> {
        val count = HeapInt()
        callFunc("vkEnumerateInstanceExtensionProperties", Int::class, layerName, count, MemorySegment.NULL).apply {
            if (this != VK_SUCCESS) return Result.fail(this)
        }
        val seg = Arena.ofAuto().allocate(260L * count.value())
        callFunc("vkEnumerateInstanceExtensionProperties", Int::class, layerName, count, seg).apply {
            if (this != VK_SUCCESS) return Result.fail(this)
        }
        return Result.success(seg.fromCStructArray(count.value(), 260, { VkExtensionProperties(it) }).toTypedArray())
    }

    fun vkEnumerateDeviceExtensionProperties(physicalDevice: VkPhysicalDevice, layerName: String): Result<Array<VkExtensionProperties>, Int> {
        val count = HeapInt()
        callFunc("vkEnumerateDeviceExtensionProperties", Int::class, physicalDevice, layerName, count, MemorySegment.NULL).apply {
            if (this != VK_SUCCESS) return Result.fail(this)
        }
        val seg = Arena.ofAuto().allocate(260L * count.value())
        callFunc("vkEnumerateDeviceExtensionProperties", Int::class, physicalDevice, layerName, count, seg).apply {
            if (this != VK_SUCCESS) return Result.fail(this)
        }
        return Result.success(seg.fromCStructArray(count.value(), 260, { VkExtensionProperties(it) }).toTypedArray())
    }

    fun vkEnumerateInstanceLayerProperties(): Result<Array<VkLayerProperties>, Int> {
        val count = HeapInt()
        callFunc("vkEnumerateInstanceLayerProperties", Int::class, count, MemorySegment.NULL).apply {
            if (this != VK_SUCCESS) return Result.fail(this)
        }
        val seg = Arena.ofAuto().allocate(520L * count.value())
        callFunc("vkEnumerateInstanceLayerProperties", Int::class, count, seg).apply {
            if (this != VK_SUCCESS) return Result.fail(this)
        }
        return Result.success(seg.fromCStructArray(count.value(), 520, { VkLayerProperties(it) }).toTypedArray())
    }

    fun vkEnumerateDeviceLayerProperties(physicalDevice: VkPhysicalDevice): Result<Array<VkLayerProperties>, Int> {
        val count = HeapInt()
        callFunc("vkEnumerateDeviceLayerProperties", Int::class, physicalDevice, count, MemorySegment.NULL).apply {
            if (this != VK_SUCCESS) return Result.fail(this)
        }
        val seg = Arena.ofAuto().allocate(520L * count.value())
        callFunc("vkEnumerateDeviceLayerProperties", Int::class, physicalDevice, count, seg).apply {
            if (this != VK_SUCCESS) return Result.fail(this)
        }
        return Result.success(seg.fromCStructArray(count.value(), 520, { VkLayerProperties(it) }).toTypedArray())
    }

    fun vkGetDeviceQueue(device: VkDevice, queueFamilyIndex: Int, queueIndex: Int): Result<VkQueue, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc("vkGetDeviceQueue", Int::class, device, queueFamilyIndex, queueIndex, seg)
        return if (retCode == VK_SUCCESS) Result.success(VkQueue(seg.get(ADDRESS, 0))) else Result.fail(retCode)
    }

    fun vkQueueSubmit(queue: VkQueue, submits: ArrayStruct<VkSubmitInfo>, fence: VkFence): Int = 
        callFunc("vkQueueSubmit", Int::class, queue, submits.arr.size, submits.pointer(), fence)

    fun vkQueueWaitIdle(queue: VkQueue): Int =
        callFunc("vkQueueWaitIdle", Int::class, queue)

    fun vkDeviceWaitIdle(device: VkDevice): Int =
        callFunc("vkDeviceWaitIdle", Int::class, device)

    fun vkAllocateMemory(device: VkDevice, allocateInfo: VkMemoryAllocateInfo, allocator: VkAllocationCallbacks?): Result<VkDeviceMemory, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc("vkAllocateMemory", Int::class, device, allocateInfo, allocator?.pointer()?: MemorySegment.NULL, seg)
        return if (retCode == VK_SUCCESS) Result.success(VkDeviceMemory(seg.get(ADDRESS, 0))) else Result.fail(retCode)
    }

    fun vkFreeMemory(device: VkDevice, memory: VkDeviceMemory, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkFreeMemory", device, memory, allocator?.pointer()?: MemorySegment.NULL)

    fun vkMapMemory(device: VkDevice, memory: VkDeviceMemory, offset: Long, size: Long, flags: Int, data: MemorySegment): Int {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        seg.set(ADDRESS, 0, data)
        return callFunc("vkMapMemory", Int::class, device, memory, offset, size, flags, seg)
    }

    fun vkFlushMappedMemoryRanges(device: VkDevice, ranges: ArrayStruct<VkMappedMemoryRange>): Int =
        callFunc("vkFlushMappedMemoryRanges", Int::class, device, ranges.arr.size, ranges.pointer())

    fun vkInvalidateMappedMemoryRanges(device: VkDevice, ranges: ArrayStruct<VkMappedMemoryRange>): Int =
        callFunc("vkInvalidateMappedMemoryRanges", Int::class, device, ranges.arr.size, ranges.pointer())

    fun vkGetDeviceMemoryCommitment(device: VkDevice, memory: VkDeviceMemory, committedMemoryInBytes: HeapLong) =
        callVoidFunc("vkGetDeviceMemoryCommitment", device, memory, committedMemoryInBytes)

    fun vkBindBufferMemory(device: VkDevice, buffer: VkBuffer, memory: VkDeviceMemory, offset: Long): Int =
        callFunc("vkBindBufferMemory", Int::class, device, buffer, memory, offset)

    fun vkBindImageMemory(device: VkDevice, image: VkImage, memory: VkDeviceMemory, offset: Long): Int =
        callFunc("vkBindImageMemory", Int::class, device, image, memory, offset)

    fun vkGetImageMemoryRequirements(device: VkDevice, image: VkImage): VkMemoryRequirements =
        VkMemoryRequirements().apply { callVoidFunc("vkGetImageMemoryRequirements", device, image, this) }

    fun vkGetImageSparseMemoryRequirements(device: VkDevice, image: VkImage): Array<VkSparseImageMemoryRequirements> {
        val count = HeapInt()
        callVoidFunc("vkGetImageSparseMemoryRequirements", device, image, count, MemorySegment.NULL)
        val seg = Arena.ofAuto().allocate(48L * count.value())
        callVoidFunc("vkGetImageSparseMemoryRequirements", device, image, count, seg)
        return seg.fromCStructArray(count.value(), 48, { VkSparseImageMemoryRequirements(it) }).toTypedArray()
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
        val seg = Arena.ofAuto().allocate(48L * count.value())
        callVoidFunc("vkGetPhysicalDeviceSparseImageFormatProperties", physicalDevice, format, type, samples, usage, tiling, count, seg)
        return seg.fromCStructArray(count.value(), 48, { VkSparseImageMemoryRequirements(it) }).toTypedArray()
    }

    fun vkQueueBindSparse(queue: VkQueue, bindInfo: ArrayStruct<VkBindSparseInfo>, fence: VkFence): Int =
        callFunc("vkQueueBindSparse", Int::class, queue, bindInfo.arr.size, bindInfo.pointer(), fence)

    fun vkCreateFence(device: VkDevice, createInfo: VkFenceCreateInfo, allocator: VkAllocationCallbacks?): Result<VkFence, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc("vkCreateFence", Int::class, device, createInfo, allocator?.pointer()?: MemorySegment.NULL, seg)
        return if (retCode == VK_SUCCESS) Result.success(VkFence(seg.get(ADDRESS, 0))) else Result.fail(retCode)
    }

    fun vkDestroyFence(device: VkDevice, fence: VkFence, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroyFence", device, fence, allocator?.pointer()?: MemorySegment.NULL)

    fun vkResetFences(device: VkDevice, fence: VkFence): Int =
        callFunc("vkResetFences", Int::class, device, 1, fence)

    fun vkResetFences(device: VkDevice, fences: PointerArrayStruct<VkFence>): Int =
        callFunc("vkResetFences", Int::class, device, fences.arr.size, fences)

    fun vkGetFenceStatus(device: VkDevice, fence: VkFence): Int =
        callFunc("vkGetFenceStatus", Int::class, device, fence)

    fun vkWaitForFences(device: VkDevice, fences: PointerArrayStruct<VkFence>, waitAll: Boolean, timeout: Long): Int =
        callFunc("vkWaitForFences", Int::class, device, fences.arr.size, fences.pointer(), if (waitAll) 1 else 0, timeout)

    fun vkCreateSemaphore(device: VkDevice, createInfo: VkSemaphoreCreateInfo, allocator: VkAllocationCallbacks?): Result<VkSemaphore, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc("vkCreateSemaphore", Int::class, device, createInfo, allocator?.pointer()?: MemorySegment.NULL, seg)
        return if (retCode == VK_SUCCESS) Result.success(VkSemaphore(seg.get(ADDRESS, 0))) else Result.fail(retCode)
    }

    fun vkDestroySemaphore(device: VkDevice, semaphore: VkSemaphore, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroySemaphore", device, semaphore, allocator?.pointer()?: MemorySegment.NULL)

    fun vkCreateEvent(device: VkDevice, createInfo: VkEventCreateInfo, allocator: VkAllocationCallbacks?): Result<VkEvent, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc("vkCreateEvent", Int::class, device, createInfo, allocator?.pointer()?: MemorySegment.NULL, seg)
        return if (retCode == VK_SUCCESS) Result.success(VkEvent(seg.get(ADDRESS, 0))) else Result.fail(retCode)
    }

    fun vkDestroyEvent(device: VkDevice, event: VkEvent, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroyEvent", device, event, allocator?.pointer()?: MemorySegment.NULL)

    fun vkGetEventStatus(device: VkDevice, event: VkEvent): Int =
        callFunc("vkGetEventStatus", Int::class, device, event)

    fun vkSetEvent(device: VkDevice, event: VkEvent): Int =
        callFunc("vkSetEvent", Int::class, device, event)

    fun vkResetEvent(device: VkDevice, event: VkEvent): Int =
        callFunc("vkResetEvent", Int::class, device, event)

    fun vkCreateQueryPool(device: VkDevice, createInfo: VkQueryPoolCreateInfo, allocator: VkAllocationCallbacks?): Result<VkQueryPool, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc("vkCreateQueryPool", Int::class, device, createInfo, allocator?.pointer()?: MemorySegment.NULL)
        return if (retCode == VK_SUCCESS) Result.success(VkQueryPool(seg.get(ADDRESS, 0))) else Result.fail(retCode)
    }

    fun vkDestroyQueryPool(device: VkDevice, queryPool: VkQueryPool, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroyQueryPool", device, queryPool, allocator?.pointer()?: MemorySegment.NULL)

    fun <T> vkGetQueryPoolResults(device: VkDevice, queryPool: VkQueryPool, firstQuery: Int, queryCount: Int, data: IHeapVar<T>, stride: Long, flags: Int): Int =
        callFunc("vkGetQueryPoolResults", Int::class, device, queryPool, firstQuery, queryCount, data, stride, flags)

    fun vkCreateBuffer(device: VkDevice, createInfo: VkBufferCreateInfo, allocator: VkAllocationCallbacks?): Result<VkBuffer, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc("vkCreateBuffer", Int::class, device, createInfo, allocator?.pointer()?: MemorySegment.NULL, seg)
        return if (retCode == VK_SUCCESS) Result.success(VkBuffer(seg.get(ADDRESS, 0))) else Result.fail(retCode)
    }

    fun vkDestroyBuffer(device: VkDevice, buffer: VkBuffer, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroyBuffer", device, buffer, allocator?.pointer()?: MemorySegment.NULL)

    fun vkCreateBufferView(device: VkDevice, createInfo: VkBufferViewCreateInfo, allocator: VkAllocationCallbacks?): Result<VkBufferView, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc("vkCreateBufferView", Int::class, device, createInfo, allocator?.pointer()?: MemorySegment.NULL, seg)
        return if (retCode == VK_SUCCESS) Result.success(VkBufferView(seg.get(ADDRESS, 0))) else Result.fail(retCode)
    }

    fun vkDestroyBufferView(device: VkDevice, bufferView: VkBufferView, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroyBufferView", device, bufferView, allocator?.pointer()?: MemorySegment.NULL)

    fun vkCreateImage(device: VkDevice, createInfo: VkImageCreateInfo, allocator: VkAllocationCallbacks?): Result<VkImage, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc("vkCreateImage", Int::class, device, createInfo, allocator?.pointer()?: MemorySegment.NULL, seg)
        return if (retCode == VK_SUCCESS) Result.success(VkImage(seg.get(ADDRESS, 0))) else Result.fail(retCode)
    }

    fun vkDestroyImage(device: VkDevice, image: VkImage, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroyImage", device, image, allocator?.pointer()?: MemorySegment.NULL)

    fun vkGetImageSubresourceLayout(device: VkDevice, image: VkImage, subresource: VkImageSubresource): VkSubresourceLayout {
        val seg = Arena.ofAuto().allocate(40L)
        callVoidFunc("vkGetImageSubresourceLayout", device, image, subresource, seg)
        return VkSubresourceLayout(seg)
    }

    fun vkCreateImageView(device: VkDevice, createInfo: VkImageViewCreateInfo, allocator: VkAllocationCallbacks?): Result<VkImageView, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc("vkCreateImageView", Int::class, device, createInfo, allocator?.pointer()?: MemorySegment.NULL, seg)
        return if (retCode == VK_SUCCESS) Result.success(VkImageView(seg.get(ADDRESS, 0))) else Result.fail(retCode)
    }

    fun vkDestroyImageView(device: VkDevice, imageView: VkImageView, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroyImageView", device, imageView, allocator?.pointer()?: MemorySegment.NULL)

    fun vkCreateShaderModule(device: VkDevice, createInfo: VkShaderModuleCreateInfo, allocator: VkAllocationCallbacks?): Result<VkShaderModule, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc("vkCreateShaderModule", Int::class, device, createInfo, allocator?.pointer()?: MemorySegment.NULL, seg)
        return if (retCode == VK_SUCCESS) Result.success(VkShaderModule(seg.get(ADDRESS, 0))) else Result.fail(retCode)
    }

    fun vkDestroyShaderModule(device: VkDevice, shaderModule: VkShaderModule, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroyShaderModule", device, shaderModule, allocator?.pointer()?: MemorySegment.NULL)

    fun vkCreatePipelineCache(device: VkDevice, createInfo: VkPipelineCacheCreateInfo, allocator: VkAllocationCallbacks?): Result<VkPipelineCache, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc("vkCreatePipelineCache", Int::class, device, createInfo, allocator?.pointer()?: MemorySegment.NULL, seg)
        return if (retCode == VK_SUCCESS) Result.success(VkPipelineCache(seg.get(ADDRESS, 0))) else Result.fail(retCode)
    }

    fun vkDestroyPipelineCache(device: VkDevice, pipelineCache: VkPipelineCache, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroyPipelineCache", device, pipelineCache, allocator?.pointer()?: MemorySegment.NULL)

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

    fun vkMergePipelineCaches(device: VkDevice, dstCache: VkPipelineCache, srcCaches: PointerArrayStruct<VkPipelineCache>): Int =
        callFunc("vkMergePipelineCaches", Int::class, device, dstCache, srcCaches.arr.size, srcCaches.pointer())
}
