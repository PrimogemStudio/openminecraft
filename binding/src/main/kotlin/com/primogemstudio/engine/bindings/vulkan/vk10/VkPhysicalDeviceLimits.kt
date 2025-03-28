package com.primogemstudio.engine.bindings.vulkan.vk10

import com.primogemstudio.engine.foreign.heap.IHeapObject
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout.*

class VkPhysicalDeviceLimits(private val seg: MemorySegment) : IHeapObject(seg) {
    companion object {
        val size = 504L
    }

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