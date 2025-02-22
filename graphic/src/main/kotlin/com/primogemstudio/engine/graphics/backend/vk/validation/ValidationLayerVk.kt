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
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkEnumerateInstanceLayerProperties
import com.primogemstudio.engine.bindings.vulkan.vk10.VkInstance
import com.primogemstudio.engine.i18n.Internationalization.tr
import com.primogemstudio.engine.logging.LoggerFactory
import java.io.Closeable

class ValidationLayerVk : Closeable {
    private val logger = LoggerFactory.getAsyncLogger()
    private var debugMessager: VkDebugUtilsMessengerEXT? = null
    private lateinit var instance: VkInstance
    private fun hasValidationLayer(): Boolean = vkEnumerateInstanceLayerProperties().let { d ->
        d.match({ it.map { l -> l.layerName }.contains("VK_LAYER_KHRONOS_validation") }, { false })
    } && vkEnumerateInstanceExtensionProperties("VK_LAYER_KHRONOS_validation").let { d ->
        d.match(
            { it.map { ext -> ext.extensionName }.contains(VkEXTDebugUtils.VK_EXT_DEBUG_UTILS_EXTENSION_NAME) },
            { false })
    }

    fun layerArg(): Array<String> = if (hasValidationLayer()) arrayOf("VK_LAYER_KHRONOS_validation") else arrayOf()
    fun appendExt(ext: Array<String>): Array<String> = ext.toMutableList()
        .apply { if (hasValidationLayer()) add(VkEXTDebugUtils.VK_EXT_DEBUG_UTILS_EXTENSION_NAME) }
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

    fun instanceAttach(instance: VkInstance) {
        this.instance = instance
        vkCreateDebugUtilsMessengerEXT(instance, VkDebugUtilsMessengerCreateInfoEXT().apply {
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
        }, null).match(
            { debugMessager = it },
            { logger.warn(tr(toFullErr("exception.renderer.backend_vk.validation", it))) })
    }

    override fun close() {
        if (debugMessager != null) vkDestroyDebugUtilsMessengerEXT(instance, debugMessager!!, null)
    }
}