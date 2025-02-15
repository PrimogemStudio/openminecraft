package com.primogemstudio.engine.bindings.vulkan.utils

import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_ERROR_DEVICE_LOST
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_ERROR_EXTENSION_NOT_PRESENT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_ERROR_FEATURE_NOT_PRESENT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_ERROR_FORMAT_NOT_SUPPORTED
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_ERROR_FRAGMENTED_POOL
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_ERROR_INCOMPATIBLE_DRIVER
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_ERROR_INITIALIZATION_FAILED
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_ERROR_LAYER_NOT_PRESENT
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_ERROR_MEMORY_MAP_FAILED
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_ERROR_OUT_OF_DEVICE_MEMORY
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_ERROR_OUT_OF_HOST_MEMORY
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_ERROR_TOO_MANY_OBJECTS
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_EVENT_RESET
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_EVENT_SET
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_INCOMPLETE
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_MAKE_VERSION
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_NOT_READY
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_SUCCESS
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_TIMEOUT
import com.primogemstudio.engine.i18n.Internationalization.tr
import com.primogemstudio.engine.types.Version

fun Version.toVkVersion(): Int = VK_MAKE_VERSION(this.major.toInt(), this.minor.toInt(), this.patch.toInt())
fun Int.toVkError(): String = when (this) {
    VK_SUCCESS -> "success"
    VK_NOT_READY -> "not_ready"
    VK_TIMEOUT -> "timeout"
    VK_EVENT_SET -> "event_set"
    VK_EVENT_RESET -> "event_reset"
    VK_INCOMPLETE -> "incomplete"
    VK_ERROR_OUT_OF_HOST_MEMORY -> "out_of_host_memory"
    VK_ERROR_OUT_OF_DEVICE_MEMORY -> "out_of_device_memory"
    VK_ERROR_INITIALIZATION_FAILED -> "init_failed"
    VK_ERROR_DEVICE_LOST -> "device_lost"
    VK_ERROR_MEMORY_MAP_FAILED -> "memory_map_failed"
    VK_ERROR_LAYER_NOT_PRESENT -> "layer_not_present"
    VK_ERROR_EXTENSION_NOT_PRESENT -> "ext_not_present"
    VK_ERROR_FEATURE_NOT_PRESENT -> "feature_not_present"
    VK_ERROR_INCOMPATIBLE_DRIVER -> "incomp_driver"
    VK_ERROR_TOO_MANY_OBJECTS -> "too_many_objects"
    VK_ERROR_FORMAT_NOT_SUPPORTED -> "format_not_supported"
    VK_ERROR_FRAGMENTED_POOL -> "fragmented_pool"
    else -> "unknown_error"
}

@OptIn(ExperimentalStdlibApi::class)
fun toFullErr(str: String, code: Int): String =
    tr(str, code.toHexString(), tr("engine.renderer.backend_vk.status." + code.toVkError()))