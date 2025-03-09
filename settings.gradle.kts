plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "openminecraft"

listOf("base", "graphic", "fonts", "nativeloader", "binding", "compiler").forEach { n ->
    include(n)
    project(":$n").name = "openminecraft-$n"
}

System.setProperty("openminecraft.graalvm.env", try {
    println("[OpenMinecraft] Runtime Foreign Access class: " + Class.forName("org.graalvm.nativeimage.hosted.RuntimeForeignAccess"))
    println("[OpenMinecraft] enableing compat")
    include("nativeimage")
    project(":nativeimage").name = "openminecraft-nativeimage"
    "1"
}
catch (e: Exception) {
    println("[OpenMinecraft] Not GraalVM environment, disabling compat")
    "0"
})
