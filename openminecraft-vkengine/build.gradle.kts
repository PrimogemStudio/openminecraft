plugins {
    kotlin("jvm")
}

group = "${properties["openminecraft.group"]}"
version = "${properties["openminecraft.version"]}"

dependencies {
    implementation(rootProject.project(":openminecraft-base"))

    properties["openminecraft.lwjgl_version"].toString().apply {
        listOf(
            "lwjgl",
            "lwjgl-freetype",
            "lwjgl-glfw",
            "lwjgl-harfbuzz",
            "lwjgl-nfd",
            "lwjgl-openal",
            "lwjgl-shaderc",
            "lwjgl-stb",
            "lwjgl-vulkan"
        ).forEach { mod ->
            implementation("org.lwjgl", mod, this)

            if (mod != "lwjgl-harfbuzz") listOf(
                "natives-windows",
                "natives-windows-x86",
                "natives-windows-arm64",
                "natives-linux",
                "natives-linux-riscv64",
                "natives-linux-ppc64le",
                "natives-linux-arm32",
                "natives-linux-arm64",
                "natives-freebsd",
                "natives-macos",
                "natives-macos-arm64"
            ).forEach {
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

    implementation("org.apache.logging.log4j:log4j-api:${properties["openminecraft.log4j2_version"]}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${properties["openminecraft.kotlinx_coroutines_core_version"]}")
    implementation(rootProject.project(":openminecraft-base"))
}

kotlin {
    jvmToolchain(17)
    compilerOptions {
        verbose = true
        allWarningsAsErrors = true
    }
}