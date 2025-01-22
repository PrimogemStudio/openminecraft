package com.primogemstudio.engine.loader.sys

import com.primogemstudio.engine.i18n.Internationalization.tr
import com.primogemstudio.engine.loader.*
import com.primogemstudio.engine.resource.ResourceManager

object VulkanLibLoader {
    fun source(): INativeLibSource = DefaultNativeLibSource(tr("engine.nativeloader.libname", "vulkan")).apply {
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
                    push(
                        NativeLibInfo(
                            tr("engine.nativeloader.libname.system", "vulkan"),
                            extName = "vulkan.1",
                            isEmbedded = false,
                            isSystem = true
                        )
                    )
                }
                PlatformSystem.Windows -> {
                    push(
                        NativeLibInfo(
                            tr("engine.nativeloader.libname.system", "vulkan"),
                            extName = "vulkan-1",
                            isEmbedded = false,
                            isSystem = true
                        )
                    )
                }

                else -> {
                    push(
                        NativeLibInfo(
                            tr("engine.nativeloader.libname.system", "vulkan"),
                            extName = "vulkan",
                            isEmbedded = false,
                            isSystem = true
                        )
                    )
                }
            }
    }
}