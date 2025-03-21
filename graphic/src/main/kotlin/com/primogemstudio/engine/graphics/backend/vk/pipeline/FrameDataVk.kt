package com.primogemstudio.engine.graphics.backend.vk.pipeline

import com.primogemstudio.engine.bindings.vulkan.vk10.VkFence
import com.primogemstudio.engine.bindings.vulkan.vk10.VkSemaphore

data class FrameDataVk(
    val imageAvailableSemaphore: VkSemaphore,
    val renderFinishedSemaphore: VkSemaphore,
    val renderFinishedFence: VkFence,
    val imageAvailableFence: VkFence,
)
