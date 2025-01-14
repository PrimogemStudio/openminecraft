plugins {
    kotlin("jvm")
}

group = "${properties["openminecraft.group"]}"
version = "${properties["openminecraft.version"]}"

dependencies {
    implementation(rootProject.project(":base"))

    properties["openminecraft.lwjgl_version"].toString().apply {
        properties["openminecraft.lwjgl_mods"].toString().split(",").forEach { mod ->
            implementation("org.lwjgl", mod, this)

            if (mod != "lwjgl-harfbuzz") properties["openminecraft.lwjgl_natives"].toString().split(",").forEach {
                if (mod == "lwjgl-vulkan") {
                    if (it == "natives-macos" || it == "natives-macos-arm64") runtimeOnly(
                        "org.lwjgl",
                        mod,
                        this,
                        classifier = it
                    )
                } else runtimeOnly("org.lwjgl", mod, this, classifier = it)
            }
        }
    }

    implementation("org.joml", "joml", "${properties["openminecraft.joml_version"]}")
    implementation("org.joml", "joml-primitives", "${properties["openminecraft.joml_primitives_version"]}")

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
