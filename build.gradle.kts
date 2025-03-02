plugins {
    kotlin("jvm").version("2.0.21")
    id("application")
}

version = "${properties["openminecraft.version"]}"
group = "${properties["openminecraft.group"]}"

allprojects {
    repositories {
        mavenCentral()
    }
}

dependencies {
    implementation(rootProject.project(":openminecraft-graphic"))
    implementation(rootProject.project(":openminecraft-nativeloader"))
    implementation(rootProject.project(":openminecraft-base"))
    implementation(rootProject.project(":openminecraft-binding"))
    if (System.getProperty("openminecraft.graalvm.env") == "1") implementation(rootProject.project(":openminecraft-nativeimage"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${properties["openminecraft.kotlinx_coroutines_core_version"]}")
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    from (configurations.runtimeClasspath.get().flatMap {
        if (!it.toString().contains("lwjgl")) {
            if (it.isDirectory) listOf(it) else listOf(zipTree(it))
        }
        else listOf()
    })

    manifest {
        attributes["Main-Class"] = "com.primogemstudio.engine.MainWrappedKt"
        attributes["Enable-Native-Access"] = "ALL-UNNAMED"
    }
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

application {
    mainClass = "com.primogemstudio.engine.MainWrappedKt"
}
