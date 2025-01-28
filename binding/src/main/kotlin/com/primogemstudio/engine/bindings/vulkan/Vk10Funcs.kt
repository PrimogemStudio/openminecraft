package com.primogemstudio.engine.bindings.vulkan

import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_APPLICATION_INFO
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_SUBMIT_INFO
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_MAPPED_MEMORY_RANGE
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_BIND_SPARSE_INFO
import com.primogemstudio.engine.bindings.vulkan.Vk10Funcs.VK_STRUCTURE_TYPE_FENCE_CREATE_INFO
import com.primogemstudio.engine.interfaces.NativeMethodCache.callFunc
import com.primogemstudio.engine.interfaces.NativeMethodCache.callPointerFunc
import com.primogemstudio.engine.interfaces.NativeMethodCache.callVoidFunc
import com.primogemstudio.engine.interfaces.fetchString
import com.primogemstudio.engine.interfaces.heap.*
import com.primogemstudio.engine.interfaces.struct.*
import com.primogemstudio.engine.interfaces.toCString
import com.primogemstudio.engine.interfaces.toCStrArray
import com.primogemstudio.engine.interfaces.toCPointerArray
import com.primogemstudio.engine.interfaces.toCStructArray
import com.primogemstudio.engine.interfaces.fromCStructArray
import com.primogemstudio.engine.loader.Platform.sizetLength
import org.joml.Vector3i
import java.lang.foreign.Arena
import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

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
    private val queuePriorities: List<Float>
) : IStruct() {
    init {
        construct(seg)
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
        seg.set(JAVA_INT, sizetLength() + 16L, queuePriorities.size)
        seg.set(
            ADDRESS,
            sizetLength() + 24L,
            Arena.ofAuto().allocateArray(JAVA_FLOAT, *queuePriorities.toFloatArray())
        )
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
    private val waitSemaphores: List<VkSemaphore>, 
    private val waitDstStageMask: List<Int>, 
    private val commandBuffers: List<VkCommandBuffer>, 
    private val signalSemaphores: List<VkSemaphore>
): IStruct() {
    init {
        construct(seg)
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
        seg.set(JAVA_INT, sizetLength() + 8L, waitSemaphores.size)
        seg.set(ADDRESS, sizetLength() + 16L, waitSemaphores.toTypedArray().toCStructArray())
        seg.set(ADDRESS, sizetLength() * 2 + 16L, Arena.ofAuto().allocateArray(JAVA_INT, *waitDstStageMask.toIntArray()))
        seg.set(JAVA_INT, sizetLength() * 3 + 16L, commandBuffers.size)
        seg.set(ADDRESS, sizetLength() * 3 + 24L, commandBuffers.toTypedArray().toCStructArray())
        seg.set(JAVA_INT, sizetLength() * 4 + 24L, signalSemaphores.size)
        seg.set(ADDRESS, sizetLength() * 4 + 32L, signalSemaphores.toTypedArray().toCStructArray())
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
    private val waitSemaphores: List<VkSemaphore>, 
    private val bufferBinds: ArrayStruct<VkSparseBufferMemoryBindInfo>, 
    private val imageOpaqueBinds: ArrayStruct<VkSparseImageOpaqueMemoryBindInfo>,
    private val imageBinds: ArrayStruct<VkSparseImageMemoryBindInfo>,
    private val signalSemaphores: List<VkSemaphore>
): IStruct() {
    init {
        construct(seg)
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
        seg.set(JAVA_INT, sizetLength() + 8L, waitSemaphores.size)
        seg.set(ADDRESS, sizetLength() + 16L, waitSemaphores.toTypedArray().toCStructArray())
        seg.set(JAVA_INT, sizetLength() * 2 + 16L, bufferBinds.arr.size)
        seg.set(ADDRESS, sizetLength() * 2 + 24L, bufferBinds.pointer())
        seg.set(JAVA_INT, sizetLength() * 3 + 24L, imageOpaqueBinds.arr.size)
        seg.set(ADDRESS, sizetLength() * 3 + 30L, imageOpaqueBinds.pointer())
        seg.set(JAVA_INT, sizetLength() * 4 + 30L, imageBinds.arr.size)
        seg.set(ADDRESS, sizetLength() * 4 + 38L, imageBinds.pointer())
        seg.set(JAVA_INT, sizetLength() * 5 + 38L, signalSemaphores.size)
        seg.set(ADDRESS, sizetLength() * 5 + 46L, signalSemaphores.toTypedArray().toCStructArray())
    }
}

data class VkFenceCreateInfo(
    private val next: IStruct? = null,
    private val flags: Int = 0
): IStruct() {
    init {
        construct(seg)
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
    ): Pair<VkInstance, Int> =
        Arena.ofAuto().allocate(ADDRESS).run {
            val retCode = callFunc("vkCreateInstance", Int::class, createInfo, allocator?.pointer()?: MemorySegment.NULL, this)
            Pair(VkInstance(get(ADDRESS, 0)), retCode)
        }

    fun vkDestroyInstance(instance: VkInstance, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroyInstance", instance, allocator?.pointer()?: MemorySegment.NULL)

    fun vkEnumeratePhysicalDevices(instance: VkInstance): Pair<Array<VkPhysicalDevice>, Int> {
        val count = HeapInt()
        callFunc("vkEnumeratePhysicalDevices", Int::class, instance, count, MemorySegment.NULL).apply {
            if (this != VK_SUCCESS) return Pair(arrayOf(), this)
        }
        val seg = Arena.ofAuto().allocate(sizetLength() * count.value() * 1L)
        callFunc("vkEnumeratePhysicalDevices", Int::class, instance, count, seg).apply {
            if (this != VK_SUCCESS) return Pair(arrayOf(), this)
        }

        return Pair(seg.toCPointerArray(count.value()).map { VkPhysicalDevice(it) }.toTypedArray(), VK_SUCCESS)
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
    ): Pair<VkImageFormatProperties, Int> =
        VkImageFormatProperties().let {
            Pair(
                it,
                callFunc(
                    "vkGetPhysicalDeviceImageFormatProperties",
                    Int::class,
                    physicalDevice,
                    format,
                    type,
                    tiling,
                    usage,
                    flags,
                    it
                )
            )
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
    ): Pair<VkDevice, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc("vkCreateDevice", Int::class, physicalDevice, createInfo, allocator?.pointer()?: MemorySegment.NULL, seg)
        return Pair(VkDevice(seg.get(ADDRESS, 0)), retCode)
    }

    fun vkDestroyDevice(device: VkDevice, allocator: VkAllocationCallbacks?) =
        callVoidFunc("vkDestroyDevice", device, allocator?.pointer()?: MemorySegment.NULL)

    fun vkEnumerateInstanceExtensionProperties(layerName: String): Pair<Array<VkExtensionProperties>, Int> {
        val count = HeapInt()
        callFunc("vkEnumerateInstanceExtensionProperties", Int::class, layerName, count, MemorySegment.NULL).apply {
            if (this != VK_SUCCESS) return Pair(arrayOf(), this)
        }
        val seg = Arena.ofAuto().allocate(260L * count.value())
        callFunc("vkEnumerateInstanceExtensionProperties", Int::class, layerName, count, seg).apply {
            if (this != VK_SUCCESS) return Pair(arrayOf(), this)
        }
        return Pair(seg.fromCStructArray(count.value(), 260, { VkExtensionProperties(it) }).toTypedArray(), VK_SUCCESS)
    }

    fun vkEnumerateDeviceExtensionProperties(physicalDevice: VkPhysicalDevice, layerName: String): Pair<Array<VkExtensionProperties>, Int> {
        val count = HeapInt()
        callFunc("vkEnumerateDeviceExtensionProperties", Int::class, physicalDevice, layerName, count, MemorySegment.NULL).apply {
            if (this != VK_SUCCESS) return Pair(arrayOf(), this)
        }
        val seg = Arena.ofAuto().allocate(260L * count.value())
        callFunc("vkEnumerateDeviceExtensionProperties", Int::class, physicalDevice, layerName, count, seg).apply {
            if (this != VK_SUCCESS) return Pair(arrayOf(), this)
        }
        return Pair(seg.fromCStructArray(count.value(), 260, { VkExtensionProperties(it) }).toTypedArray(), VK_SUCCESS)
    }

    fun vkEnumerateInstanceLayerProperties(): Pair<Array<VkLayerProperties>, Int> {
        val count = HeapInt()
        callFunc("vkEnumerateInstanceLayerProperties", Int::class, count, MemorySegment.NULL).apply {
            if (this != VK_SUCCESS) return Pair(arrayOf(), this)
        }
        val seg = Arena.ofAuto().allocate(520L * count.value())
        callFunc("vkEnumerateInstanceLayerProperties", Int::class, count, seg).apply {
            if (this != VK_SUCCESS) return Pair(arrayOf(), this)
        }
        return Pair(seg.fromCStructArray(count.value(), 520, { VkLayerProperties(it) }).toTypedArray(), VK_SUCCESS)
    }

    fun vkEnumerateDeviceLayerProperties(physicalDevice: VkPhysicalDevice): Pair<Array<VkLayerProperties>, Int> {
        val count = HeapInt()
        callFunc("vkEnumerateDeviceLayerProperties", Int::class, physicalDevice, count, MemorySegment.NULL).apply {
            if (this != VK_SUCCESS) return Pair(arrayOf(), this)
        }
        val seg = Arena.ofAuto().allocate(520L * count.value())
        callFunc("vkEnumerateDeviceLayerProperties", Int::class, physicalDevice, count, seg).apply {
            if (this != VK_SUCCESS) return Pair(arrayOf(), this)
        }
        return Pair(seg.fromCStructArray(count.value(), 520, { VkLayerProperties(it) }).toTypedArray(), VK_SUCCESS)
    }

    fun vkGetDeviceQueue(device: VkDevice, queueFamilyIndex: Int, queueIndex: Int): Pair<VkQueue, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc("vkGetDeviceQueue", Int::class, device, queueFamilyIndex, queueIndex, seg)
        return Pair(VkQueue(seg.get(ADDRESS, 0)), retCode)
    }

    fun vkQueueSubmit(queue: VkQueue, submits: ArrayStruct<VkSubmitInfo>, fence: VkFence): Int = 
        callFunc("vkQueueSubmit", Int::class, queue, submits.arr.size, submits.pointer(), fence)

    fun vkQueueWaitIdle(queue: VkQueue): Int =
        callFunc("vkQueueWaitIdle", Int::class, queue)

    fun vkDeviceWaitIdle(device: VkDevice): Int =
        callFunc("vkDeviceWaitIdle", Int::class, device)

    fun vkAllocateMemory(device: VkDevice, allocateInfo: VkMemoryAllocateInfo, allocator: VkAllocationCallbacks?): Pair<VkDeviceMemory, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc("vkAllocateMemory", Int::class, device, allocateInfo, allocator?.pointer()?: MemorySegment.NULL, seg)
        return Pair(VkDeviceMemory(seg.get(ADDRESS, 0)), retCode)
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

    fun vkCreateFence(device: VkDevice, createInfo: VkFenceCreateInfo, allocator: VkAllocationCallbacks?): Pair<VkFence, Int> {
        val seg = Arena.ofAuto().allocate(ADDRESS)
        val retCode = callFunc("vkCreateFence", Int::class, device, createInfo, allocator?.pointer()?: MemorySegment.NULL, seg)
        return Pair(VkFence(seg.get(ADDRESS, 0)), retCode)
    }
}
