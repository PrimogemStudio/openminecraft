package com.primogemstudio.engine.neoloader.sys

import com.primogemstudio.engine.neoloader.BundledNativeLib
import com.primogemstudio.engine.neoloader.INativeLib
import com.primogemstudio.engine.neoloader.MultiNativeLib
import com.primogemstudio.engine.neoloader.SystemNativeLib
import com.primogemstudio.engine.neoloader.plat.Platform
import com.primogemstudio.engine.neoloader.plat.PlatformSystem
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
                            ResourceManager.getResource(
                                "jar:assets/openmc_nativeloader/lib/${Platform.system.id}/${Platform.arch.id}/${
                                    System.mapLibraryName("MoltenVK")
                                }"
                            )
                        )
                    )
                    add(SystemNativeLib("vulkan", "vulkan.1"))
                }

                PlatformSystem.Windows -> {
                    val path = Path.of("C:\\Windows\\System32\\vulkan-1.dll")
                    if (!path.exists()) {
                        val thr =
                            ResourceManager.getResource("jar:assets/openmc_nativeloader/lib/${Platform.system.id}/${Platform.arch.id}/vulkan.dll")
                        if (thr != null) Files.copy(thr, path)
                    }

                    add(SystemNativeLib("vulkan", "vulkan-1"))
                }

                else -> add(SystemNativeLib("vulkan", "vulkan"))
            }
        }
    )
}