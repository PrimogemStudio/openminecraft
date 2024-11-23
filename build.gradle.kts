plugins {
    kotlin("jvm").version("2.0.21")
}

version = "${properties["openminecraft.version"]}"
group = "${properties["openminecraft.group"]}"

allprojects {
    repositories {
        mavenCentral()
    }
}

dependencies {
    implementation(rootProject.project(":openminecraft-vkengine"))
    implementation(rootProject.project(":openminecraft-jmake"))
    implementation(rootProject.project(":openminecraft-base"))

    implementation("org.apache.logging.log4j:log4j-api:${properties["openminecraft.log4j2_version"]}")
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
    jvmToolchain(17)
    compilerOptions {
        verbose = true
        allWarningsAsErrors = true
    }
}