plugins {
    kotlin("jvm")
}

group = "${properties["openminecraft.group"]}"
version = "${properties["openminecraft.version"]}"

dependencies {
    implementation("org.fusesource.jansi:jansi:${properties["openminecraft.jansi_version"]}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${properties["openminecraft.kotlin_version"]}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${properties["openminecraft.kotlinx_coroutines_core_version"]}")
    implementation("org.json:json:${properties["openminecraft.json_version"]}")
    implementation("com.google.code.gson:gson:${properties["openminecraft.gson_version"]}")
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