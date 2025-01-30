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
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    from (configurations.runtimeClasspath.get().map {
        if (!it.toString().contains("lwjgl")) {
            if (it.isDirectory) it else zipTree(it)
        }
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
