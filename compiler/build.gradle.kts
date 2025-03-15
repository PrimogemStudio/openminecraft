plugins {
    kotlin("jvm")
}

group = "${properties["openminecraft.group"]}"
version = "${properties["openminecraft.version"]}"

dependencies {
    implementation("org.ow2.asm:asm-tree:${properties["openminecraft.asm_version"]}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${properties["openminecraft.kotlinx_coroutines_core_version"]}")
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        verbose = true
        allWarningsAsErrors = true
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}