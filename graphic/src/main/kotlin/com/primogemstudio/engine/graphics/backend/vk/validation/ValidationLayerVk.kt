package com.primogemstudio.engine.graphics.backend.vk.validation

import com.primogemstudio.engine.bindings.vulkan.ext.debugutils.*
import com.primogemstudio.engine.bindings.vulkan.ext.debugutils.VkEXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT
import com.primogemstudio.engine.bindings.vulkan.ext.debugutils.VkEXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_SEVERITY_INFO_BIT_EXT
import com.primogemstudio.engine.bindings.vulkan.ext.debugutils.VkEXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_SEVERITY_VERBOSE_BIT_EXT
import com.primogemstudio.engine.bindings.vulkan.ext.debugutils.VkEXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT
import com.primogemstudio.engine.bindings.vulkan.ext.debugutils.VkEXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT
import com.primogemstudio.engine.bindings.vulkan.ext.debugutils.VkEXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_TYPE_PERFORMANCE_BIT_EXT
import com.primogemstudio.engine.bindings.vulkan.ext.debugutils.VkEXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT
import com.primogemstudio.engine.bindings.vulkan.ext.debugutils.VkEXTDebugUtils.vkCreateDebugUtilsMessengerEXT
import com.primogemstudio.engine.bindings.vulkan.ext.debugutils.VkEXTDebugUtils.vkDestroyDebugUtilsMessengerEXT
import com.primogemstudio.engine.bindings.vulkan.utils.toFullErr
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_SUCCESS
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkEnumerateInstanceExtensionProperties
import com.primogemstudio.engine.bindings.vulkan.vk10.VkInstance
import com.primogemstudio.engine.bindings.vulkan.vk10.VkLayerProperties
import com.primogemstudio.engine.graphics.backend.vk.BackendRendererVk
import com.primogemstudio.engine.i18n.Internationalization.tr
import com.primogemstudio.engine.logging.LoggerFactory
import java.io.Closeable
import java.lang.foreign.MemorySegment

class ValidationLayerVk(private val exts: Array<VkLayerProperties>) : Closeable {
    private val logger = LoggerFactory.getAsyncLogger()
    private var debugMessager: VkDebugUtilsMessengerEXT? = null
    private lateinit var instance: VkInstance
    private fun hasValidationLayer(): Boolean =
        exts.map { l -> l.layerName }.contains("VK_LAYER_KHRONOS_validation") && vkEnumerateInstanceExtensionProperties(
            "VK_LAYER_KHRONOS_validation"
        ).let { d ->
        d.match(
            { it.map { ext -> ext.extensionName }.contains(VkEXTDebugUtils.VK_EXT_DEBUG_UTILS_EXTENSION_NAME) },
            { false })
    }

    private val enabled = hasValidationLayer()

    fun layerArg(): Array<String> = if (enabled) arrayOf("VK_LAYER_KHRONOS_validation") else arrayOf()
    fun appendExt(ext: Array<String>): Array<String> = ext.toMutableList()
        .apply { if (enabled) add(VkEXTDebugUtils.VK_EXT_DEBUG_UTILS_EXTENSION_NAME) }
        .toTypedArray()

    private fun logActual(severity: Int, type: Int, data: VkDebugUtilsMessengerCallbackDataEXT) {
        val typestr = tr(
            when (type) {
                VK_DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT -> "engine.renderer.backend_vk.type.general"
                VK_DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT -> "engine.renderer.backend_vk.type.validation"
                VK_DEBUG_UTILS_MESSAGE_TYPE_PERFORMANCE_BIT_EXT -> "engine.renderer.backend_vk.type.performance"
                else -> "engine.renderer.backend_vk.type.general"
            }
        )

        val msg = tr("engine.renderer.backend_vk.log", typestr, data.message)

        when (severity) {
            VK_DEBUG_UTILS_MESSAGE_SEVERITY_VERBOSE_BIT_EXT -> logger.debug(msg)
            VK_DEBUG_UTILS_MESSAGE_SEVERITY_INFO_BIT_EXT -> logger.info(msg)
            VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT -> logger.warn(msg)
            VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT -> logger.error(msg)
            else -> logger.info(msg)
        }
    }

    private val debuggerCreateInfo = VkDebugUtilsMessengerCreateInfoEXT().apply {
        messageSeverity = VK_DEBUG_UTILS_MESSAGE_SEVERITY_VERBOSE_BIT_EXT or
                VK_DEBUG_UTILS_MESSAGE_SEVERITY_INFO_BIT_EXT or
                VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT or
                VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT
        messageType = VK_DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT or
                VK_DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT or
                VK_DEBUG_UTILS_MESSAGE_TYPE_PERFORMANCE_BIT_EXT
        callback = VkDebugUtilsMessengerCallbackEXT { severity, type, callbackData, _ ->
            logActual(severity, type, callbackData)
            VK_SUCCESS
        }
    }

    fun preInstanceAttach(): VkDebugUtilsMessengerCreateInfoEXT =
        if (enabled) debuggerCreateInfo else VkDebugUtilsMessengerCreateInfoEXT(MemorySegment.NULL)

    fun instanceAttach(renderer: BackendRendererVk) {
        if (!enabled) return
        this.instance = renderer.instance
        vkCreateDebugUtilsMessengerEXT(instance, debuggerCreateInfo, null).match(
            { debugMessager = it },
            { logger.warn(tr(toFullErr("exception.renderer.backend_vk.validation", it))) })
    }

    override fun close() {
        if (!enabled) return
        if (debugMessager != null) vkDestroyDebugUtilsMessengerEXT(instance, debugMessager!!, null)
    }
}