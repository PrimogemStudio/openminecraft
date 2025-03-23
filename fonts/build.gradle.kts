plugins {
    kotlin("jvm")
}

group = "${properties["openminecraft.group"]}"
version = "${properties["openminecraft.version"]}"

repositories {
    mavenCentral()
}

dependencies {
    implementation(rootProject.project(":openminecraft-base"))
    implementation(rootProject.project(":openminecraft-binding"))
    implementation(rootProject.project(":openminecraft-nativeloader"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${properties["openminecraft.kotlinx_coroutines_core_version"]}")
}

tasks.test {
    useJUnitPlatform()
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