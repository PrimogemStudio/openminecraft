package com.primogemstudio.engine.loader

import java.util.*

enum class PlatformSystem(val id: String) {
    Windows("windows"),
    Linux("linux"),
    MacOS("macos"),
    FreeBSD("freebsd"),
    Android("android"),
    IOS("ios"),
    OpenHarmony("harmony"),
    Unknown("unknown")
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
}