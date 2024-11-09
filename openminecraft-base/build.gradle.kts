plugins {
    kotlin("jvm")
}

group = "${properties["openminecraft.group"]}"
version = "${properties["openminecraft.version"]}"

dependencies {
    implementation("org.fusesource.jansi:jansi:${properties["openminecraft.jansi_version"]}")
    implementation("org.apache.logging.log4j:log4j-api:${properties["openminecraft.log4j2_version"]}")
    implementation("org.apache.logging.log4j:log4j-core:${properties["openminecraft.log4j2_version"]}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${properties["openminecraft.kotlin_version"]}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${properties["openminecraft.kotlinx_coroutines_core_version"]}")
}

kotlin {
    jvmToolchain(17)
    compilerOptions {
        verbose = true
        allWarningsAsErrors = true
    }
}