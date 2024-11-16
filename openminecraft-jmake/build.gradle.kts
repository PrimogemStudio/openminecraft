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

    implementation("org.apache.logging.log4j:log4j-api:${properties["openminecraft.log4j2_version"]}")
}

kotlin {
    jvmToolchain(17)
    compilerOptions {
        verbose = true
        allWarningsAsErrors = true
    }
}