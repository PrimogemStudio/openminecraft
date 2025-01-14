package com.primogemstudio.engine.vk

import org.lwjgl.vulkan.VkDebugUtilsMessengerCallbackDataEXT

enum class VkMessageSeverity {
    Verbose,
    Info,
    Warning,
    Error
}

enum class VkMessageType {
    General,
    Validation,
    Performance
}

interface VkDebugCallback {
    operator fun invoke(
        severity: VkMessageSeverity,
        type: VkMessageType,
        data: VkDebugUtilsMessengerCallbackDataEXT
    )
}