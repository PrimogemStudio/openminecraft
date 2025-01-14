plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "openminecraft"
include("base")
include("graphic")
include("fonts")
include("nativeloader")
