---
sidebar_position: 1
---

# Project structure

Currently, the engine consists of a [native part](https://github.com/PrimogemStudio/openminecraft-binding) and a [JVM part](https://github.com/PrimogemStudio/openminecraft)

## Native part

Currently using the Xmake build system, which includes the following projects
- freetype
- harfbuzz
- xxhash
- openal
- meshoptimizer
- yoga
- stb
- shaderc
- glfw

## JVM part

Gradle build system, Kotlin 2.0

### Build requirements

Java: jdk 21 or later that supports the FFM API