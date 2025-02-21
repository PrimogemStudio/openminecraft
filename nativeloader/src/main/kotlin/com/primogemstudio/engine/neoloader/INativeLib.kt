package com.primogemstudio.engine.neoloader

import com.primogemstudio.engine.neoloader.plat.Platform
import com.primogemstudio.engine.resource.ResourceManager
import java.io.File

interface INativeLib {
    @Throws(UnsatisfiedLinkError::class)
    fun load(): Boolean

    companion object {
        fun default(name: String): INativeLib = MultiNativeLib(
            name,
            listOf(
                LocalNativeLib(name, File(System.mapLibraryName(name))),
                BundledNativeLib(
                    name,
                    ResourceManager.getResource(
                        "jar:assets/openmc_nativeloader/lib/${Platform.system.id}/${Platform.arch.id}/${
                            System.mapLibraryName(name)
                        }"
                    )
                ),
                SystemNativeLib(name, name)
            )
        )
    }
}