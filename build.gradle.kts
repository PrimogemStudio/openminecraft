plugins {
    kotlin("jvm") version "1.9.23"
}

version = "${properties["openminecraft.version"]}"
group = "com.primogemstudio"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.lwjgl:lwjgl-bom:${properties["openminecraft.lwjgl_version"]}"))

    implementation("org.lwjgl", "lwjgl")
    implementation("org.lwjgl", "lwjgl-freetype")
    implementation("org.lwjgl", "lwjgl-glfw")
    implementation("org.lwjgl", "lwjgl-harfbuzz")
    implementation("org.lwjgl", "lwjgl-nfd")
    implementation("org.lwjgl", "lwjgl-openal")
    implementation("org.lwjgl", "lwjgl-shaderc")
    implementation("org.lwjgl", "lwjgl-stb")
    implementation("org.lwjgl", "lwjgl-vma")
    implementation("org.lwjgl", "lwjgl-vulkan")
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
        runtimeOnly("org.lwjgl", "lwjgl", classifier = it)
        runtimeOnly("org.lwjgl", "lwjgl-freetype", classifier = it)
        runtimeOnly("org.lwjgl", "lwjgl-glfw", classifier = it)
        runtimeOnly("org.lwjgl", "lwjgl-nfd", classifier = it)
        runtimeOnly("org.lwjgl", "lwjgl-openal", classifier = it)
        runtimeOnly("org.lwjgl", "lwjgl-shaderc", classifier = it)
        runtimeOnly("org.lwjgl", "lwjgl-stb", classifier = it)
        runtimeOnly("org.lwjgl", "lwjgl-vma", classifier = it)
        if (it == "natives-macos" || it == "natives-macos-arm64") runtimeOnly("org.lwjgl", "lwjgl-vulkan", classifier = it)
    }

    implementation("org.joml", "joml", "${properties["openminecraft.joml_version"]}")
    implementation("org.joml", "joml-primitives", "${properties["openminecraft.joml_primitives_version"]}")
    implementation(rootProject.project(":openminecraft-base"))
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    archiveBaseName = "OpenMinecraft"

    from (configurations.runtimeClasspath.get().map {
        if (it.isDirectory) it else zipTree(it)
    })

    manifest {
        attributes["Main-Class"] = "com.primogemstudio.MainKt"
    }
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}