package com.primogemstudio.engine.loader

import com.primogemstudio.engine.loader.plat.Platform
import com.primogemstudio.engine.resource.Identifier
import com.primogemstudio.engine.resource.ResourceManager
import java.io.File

interface INativeLib {
    @Throws(UnsatisfiedLinkError::class)
    fun load(): Boolean

    companion object {
        fun default(name: String): INativeLib = MultiNativeLib(
            name,
            listOf(
                SystemNativeLib(name, name),
                LocalNativeLib(name, File(System.mapLibraryName(name))),
                BundledNativeLib(
                    name,
                    ResourceManager(
                        Identifier(
                            namespace = "openmc_nativeloader",
                            path = "lib/${Platform.system.id}/${Platform.arch.id}/${
                                System.mapLibraryName(name)
                            }"
                        )
                    )
                )
            )
        )
    }
}