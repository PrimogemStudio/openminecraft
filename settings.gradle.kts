plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "openminecraft"

listOf("base", "graphic", "fonts", "nativeloader").forEach { n ->
    include(n)
    project(":$n").name = "openminecraft-$n"
}


