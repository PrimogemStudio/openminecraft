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

    properties["openminecraft.lwjgl_version"].toString().let { version ->
        properties["openminecraft.lwjgl_mods"].toString().split(",").forEach { mod ->
            implementation("org.lwjgl", mod, version)

            if (mod != "lwjgl-harfbuzz") properties["openminecraft.lwjgl_natives"].toString().split(",").forEach {
                if (mod == "lwjgl-vulkan") {
                    if (it == "natives-macos" || it == "natives-macos-arm64") runtimeOnly(
                        "org.lwjgl",
                        mod,
                        version,
                        classifier = it
                    )
                } else runtimeOnly("org.lwjgl", mod, version, classifier = it)
            }
        }
    }
    implementation("org.apache.logging.log4j:log4j-api:${properties["openminecraft.log4j2_version"]}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${properties["openminecraft.kotlinx_coroutines_core_version"]}")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
    compilerOptions {
        verbose = true
        allWarningsAsErrors = true
    }
}