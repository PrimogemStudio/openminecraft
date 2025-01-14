package com.primogemstudio.engine.loader

enum class PlatformSystem(val id: String) {
    Windows("windows"),
    Linux("linux"),
    MacOS("macos"),
    FreeBSD("freebsd"),
    Android("android"),
    IOS("ios"),
    OpenHarmony("harmony")
}

enum class PlatformArch(val id: String) {
    X64("x64"),
    X86("x86"),
    Arm64("arm64"),
    Arm32("arm32"),
    PowerPC("ppc64le"),
    RiscV("riscv64"),
    LoongArch("loong64")
}