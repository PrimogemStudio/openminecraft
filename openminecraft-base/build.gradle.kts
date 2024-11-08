plugins {
    kotlin("jvm")
}

group = "com.primogemstudio"
version = "${properties["openminecraft.version"]}"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.fusesource.jansi:jansi:${properties["openminecraft.jansi_version"]}")
    implementation("org.apache.logging.log4j:log4j-api:${properties["openminecraft.log4j2_version"]}")
    implementation("org.apache.logging.log4j:log4j-core:${properties["openminecraft.log4j2_version"]}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${properties["openminecraft.kotlin_version"]}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${properties["openminecraft.kotlinx_coroutines_core_version"]}")
}

kotlin {
    jvmToolchain(17)
}