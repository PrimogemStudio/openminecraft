plugins {
    kotlin("jvm")
}

group = "${properties["openminecraft.group"]}"
version = "${properties["openminecraft.version"]}"

dependencies {
    implementation(rootProject.project(":openminecraft-base"))

    properties["openminecraft.lwjgl_version"].toString().apply {
        implementation("org.lwjgl", "lwjgl", this)
        implementation("org.lwjgl", "lwjgl-freetype", this)
        implementation("org.lwjgl", "lwjgl-glfw", this)
        implementation("org.lwjgl", "lwjgl-harfbuzz", this)
        implementation("org.lwjgl", "lwjgl-nfd", this)
        implementation("org.lwjgl", "lwjgl-openal", this)
        implementation("org.lwjgl", "lwjgl-shaderc", this)
        implementation("org.lwjgl", "lwjgl-stb", this)
        implementation("org.lwjgl", "lwjgl-vma", this)
        implementation("org.lwjgl", "lwjgl-vulkan", this)
        listOf(
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
            runtimeOnly("org.lwjgl", "lwjgl", this, classifier = it)
            runtimeOnly("org.lwjgl", "lwjgl-freetype", this, classifier = it)
            runtimeOnly("org.lwjgl", "lwjgl-glfw", this, classifier = it)
            runtimeOnly("org.lwjgl", "lwjgl-nfd", this, classifier = it)
            runtimeOnly("org.lwjgl", "lwjgl-openal", this, classifier = it)
            runtimeOnly("org.lwjgl", "lwjgl-shaderc", this, classifier = it)
            runtimeOnly("org.lwjgl", "lwjgl-stb", this, classifier = it)
            runtimeOnly("org.lwjgl", "lwjgl-vma", this, classifier = it)
            if (it == "natives-macos" || it == "natives-macos-arm64") runtimeOnly("org.lwjgl", "lwjgl-vulkan", this, classifier = it)
        }
    }

    implementation("org.joml", "joml", "${properties["openminecraft.joml_version"]}")
    implementation("org.joml", "joml-primitives", "${properties["openminecraft.joml_primitives_version"]}")

    implementation("org.apache.logging.log4j:log4j-api:${properties["openminecraft.log4j2_version"]}")
}

kotlin {
    jvmToolchain(17)
    compilerOptions {
        verbose = true
        allWarningsAsErrors = true
    }
}