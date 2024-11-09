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