package com.primogemstudio.engine.loader.vulkan

import com.primogemstudio.engine.i18n.Internationalization.tr
import com.primogemstudio.engine.loader.DefaultNativeLibSource
import com.primogemstudio.engine.loader.NativeLibInfo
import com.primogemstudio.engine.loader.Platform
import com.primogemstudio.engine.loader.PlatformSystem
import com.primogemstudio.engine.resource.ResourceManager

object VulkanLibLoader {
    fun source() {
        DefaultNativeLibSource(tr("engine.nativeloader.libname", "vulkan")).apply {
            when (Platform.system) {
                PlatformSystem.IOS, PlatformSystem.MacOS -> {
                    push(
                        NativeLibInfo(
                            tr("engine.nativeloader.libname.embedded", "vulkan"),
                            ResourceManager.getResource("jar:assets/openmc_nativeloader/lib/${Platform.system.id}/${Platform.arch.id}/${Platform.system.prefix}MoltenVK${Platform.system.suffix}"),
                            isEmbedded = true,
                            isSystem = false
                        )
                    )
                }

                else -> {}
            }
        }
    }
}