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
    implementation(rootProject.project(":openminecraft-base"))

    implementation("org.apache.logging.log4j:log4j-api:${properties["openminecraft.log4j2_version"]}")
    implementation("org.fusesource.jansi:jansi:${properties["openminecraft.jansi_version"]}")
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    archiveBaseName = "OpenMinecraft"

    from (configurations.runtimeClasspath.get().map {
        if (it.isDirectory) it else zipTree(it)
    })

    manifest {
        attributes["Main-Class"] = "com.primogemstudio.engine.MainWrappedKt"
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