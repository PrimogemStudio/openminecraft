package com.primogemstudio.engine.loader

import com.primogemstudio.engine.exceptions.PlatformLibInitException
import com.primogemstudio.engine.i18n.Internationalization.tr
import com.primogemstudio.engine.json.GsonObjects
import com.primogemstudio.engine.logging.LoggerFactory
import com.primogemstudio.engine.resource.ResourceManager
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.*

enum class PlatformSystem(val id: String, val prefix: String, val suffix: String, val syslib: String) {
    Windows("windows", "", ".dll", "C:\\Windows\\System32"),
    Linux("linux", "lib", ".so", "/usr/lib"),
    MacOS("macos", "lib", ".dylib", "/System/Library"),
    FreeBSD("freebsd", "lib", ".so", "/lib"),
    Android("android", "lib", ".so", "/system/lib"),
    IOS("ios", "lib", ".dylib", "/System/Library"),
    OpenHarmony("harmony", "lib", ".so", "/usr/lib"),
    Unknown("unknown", "lib", ".so", "")
}

enum class PlatformArch(val id: String) {
    X64("x64"),
    X86("x86"),
    Arm64("arm64"),
    Arm32("arm32"),
    PowerPC("ppc64le"),
    RiscV("riscv64"),
    LoongArch("loong64"),
    Unknown("unknown")
}

object Platform {
    private val logger = LoggerFactory.getLogger()
    private val libStatus = mutableMapOf<String, Boolean>()
    fun libAvailable(name: String): Boolean = libStatus[name] == true

    val system = System.getProperty("os.name").lowercase(Locale.ROOT).let {
        if (it.contains("windows")) PlatformSystem.Windows
        else if (it.contains("linux")) PlatformSystem.Linux
        else if (it.contains("mac") || it.contains("osx")) PlatformSystem.MacOS
        else if (it.contains("freebsd")) PlatformSystem.FreeBSD
        else if (it.contains("android")) PlatformSystem.Android
        else if (it.contains("ios")) PlatformSystem.IOS
        else if (it.contains("harmony")) PlatformSystem.OpenHarmony
        else PlatformSystem.Unknown
    }

    val arch = System.getProperty("os.arch").lowercase(Locale.ROOT).let {
        when (it) {
            "x8664", "x86-64", "x86_64", "amd64", "ia32e", "em64t", "x64" -> PlatformArch.X64
            "x8632", "x86-32", "x86_32", "x86", "i86pc", "i386", "i486", "i586", "i686", "ia32", "x32" -> PlatformArch.X86
            "arm64", "aarch64" -> PlatformArch.Arm64
            "arm", "arm32" -> PlatformArch.Arm32
            "ppc64le", "powerpc64le", "ppc64", "powerpc64" -> {
                if (System.getProperty("sun.cpu.endian") == "little") PlatformArch.PowerPC
                else PlatformArch.Unknown
            }

            "riscv", "risc-v", "riscv64" -> PlatformArch.RiscV
            "loongarch64" -> PlatformArch.LoongArch
            else -> {
                if (it.startsWith("armv7")) PlatformArch.Arm32
                else if (it.startsWith("armv8") || it.startsWith("armv9")) PlatformArch.Arm64
                else PlatformArch.Unknown
            }
        }
    }

    fun libProvider(name: String): INativeLibSource = libProvider(name, system, arch)

    fun libProvider(name: String, system: PlatformSystem, arch: PlatformArch): INativeLibSource {
        return DefaultNativeLibSource(tr("engine.nativeloader.libname", name)).apply {
            push(
                NativeLibInfo(
                    tr("engine.nativeloader.libname.embedded", name),
                    ResourceManager.getResource("jar:assets/openmc_nativeloader/lib/${system.id}/${arch.id}/${system.prefix}$name${system.suffix}"),
                    isEmbedded = true,
                    isSystem = false
                )
            )
            push(
                NativeLibInfo(
                    tr("engine.nativeloader.libname.external", name),
                    try {
                        Files.newInputStream(Path.of("${system.prefix}$name${system.suffix}"))
                    } catch (_: Exception) {
                        null
                    },
                    isEmbedded = false,
                    isSystem = false
                )
            )
            push(
                NativeLibInfo(
                    tr("engine.nativeloader.libname.system", name),
                    try {
                        Files.newInputStream(Path.of(system.syslib, "${system.prefix}$name${system.suffix}"))
                    } catch (_: Exception) {
                        null
                    },
                    isEmbedded = false,
                    isSystem = true
                )
            )
        }
    }

    fun load(source: INativeLibSource): Boolean {
        logger.info(tr("engine.nativeloader.load", source.name()))

        val sourcefile = source.fetch()
        if (sourcefile == null) {
            logger.warn(tr("engine.nativeloader.load.nofile", source.name()))
            return false
        }

        val path = Files.createTempFile("openminecraftlib", system.suffix)
        Files.copy(sourcefile, path, StandardCopyOption.REPLACE_EXISTING)

        logger.info(tr("engine.nativeloader.load.copy", source.name(), path))

        try {
            System.load(path.toString())
        } catch (e: Throwable) {
            logger.error(tr("engine.nativeloader.load.fail", source.name()), e)
            return false
        }

        logger.info(tr("engine.nativeloader.load.success", source.name()))
        return true
    }

    fun init(): Boolean {
        val libst = GsonObjects.GSON.fromJson(
            ResourceManager.getResource("jar:assets/openmc_nativeloader/lib.json")?.readAllBytes()
                ?.toString(Charsets.UTF_8),
            NativeLibConfigModel::class.java
        )
        libst.required?.forEach {
            if (!load(libProvider(it))) {
                logger.error(tr("engine.nativeloader.load.requiredfail"))
                return false
            }
        }
        libst.optional?.forEach {
            libStatus[it] = load(libProvider(it))
        }
        return true
    }

    init {
        if (!init()) throw PlatformLibInitException()
    }
}