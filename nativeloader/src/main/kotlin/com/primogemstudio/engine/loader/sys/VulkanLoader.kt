package com.primogemstudio.engine.loader.sys

import com.primogemstudio.engine.loader.BundledNativeLib
import com.primogemstudio.engine.loader.INativeLib
import com.primogemstudio.engine.loader.MultiNativeLib
import com.primogemstudio.engine.loader.SystemNativeLib
import com.primogemstudio.engine.loader.plat.Platform
import com.primogemstudio.engine.loader.plat.PlatformSystem
import com.primogemstudio.engine.resource.Identifier
import com.primogemstudio.engine.resource.ResourceManager
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.exists

object VulkanLoader {
    fun source(): MultiNativeLib = MultiNativeLib(
        "vulkan",
        mutableListOf<INativeLib>().apply {
            when (Platform.system) {
                PlatformSystem.IOS, PlatformSystem.MacOS -> {
                    add(
                        BundledNativeLib(
                            "vulkan",
                            ResourceManager(
                                Identifier(
                                    namespace = "openmc_nativeloader",
                                    path = "lib/${Platform.system.id}/${Platform.arch.id}/${
                                    System.mapLibraryName("MoltenVK")
                                    }"
                                )
                            )
                        )
                    )
                    add(SystemNativeLib("vulkan", "vulkan.1"))
                }

                PlatformSystem.Windows -> {
                    val path = Path.of("C:\\Windows\\System32\\vulkan-1.dll")
                    if (!path.exists()) {
                        val thr = ResourceManager(
                            Identifier(
                                namespace = "openmc_nativeloader",
                                path = "lib/${Platform.system.id}/${Platform.arch.id}/vulkan.dll"
                            )
                        )
                        if (thr != null) Files.copy(thr, path)
                    }

                    add(SystemNativeLib("vulkan", "vulkan-1"))
                }

                else -> add(SystemNativeLib("vulkan", "vulkan"))
            }
        }
    )
}