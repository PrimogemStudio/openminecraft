package com.primogemstudio.engine.graphics.backend.vk

import com.primogemstudio.engine.bindings.vulkan.utils.toFullErr
import com.primogemstudio.engine.bindings.vulkan.utils.toVkVersion
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.VK_API_VERSION_1_0
import com.primogemstudio.engine.bindings.vulkan.vk10.Vk10Funcs.vkCreateInstance
import com.primogemstudio.engine.bindings.vulkan.vk10.VkApplicationInfo
import com.primogemstudio.engine.bindings.vulkan.vk10.VkInstance
import com.primogemstudio.engine.bindings.vulkan.vk10.VkInstanceCreateInfo
import com.primogemstudio.engine.graphics.IRenderer
import com.primogemstudio.engine.graphics.data.ApplicationInfo
import com.primogemstudio.engine.types.Version

class BackendRendererVk(override val gameInfo: ApplicationInfo) : IRenderer {
    private lateinit var instance: VkInstance

    init {
        vkCreateInstance(
            VkInstanceCreateInfo().apply {
                appInfo = VkApplicationInfo().apply {
                    appName = gameInfo.appName
                    appVersion = gameInfo.appVersion.toVkVersion()
                    engineName = gameInfo.engineName
                    engineVersion = gameInfo.engineVersion.toVkVersion()
                    apiVersion = VK_API_VERSION_1_0
                }
            },
            null
        ).match(
            { instance = it },
            { throw IllegalStateException(toFullErr("exception.renderer.backend_vk.instance", it)) })
    }

    override fun version(): Version {
        TODO("Not yet implemented")
    }

    override fun driver(): String {
        TODO("Not yet implemented")
    }
}