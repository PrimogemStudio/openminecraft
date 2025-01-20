---
sidebar_position: 1
---

# プロジェクト構造

現在、エンジンは[「Native部分」](https://github.com/PrimogemStudio/openminecraft-binding)と[「JVM部分」](https://github.com/PrimogemStudio/openminecraft)で構成されています。

## Nativeの部分

現在、以下のプロジェクトを含むXmakeビルドシステムを使用しています。
- freetype
- harfbuzz
- xxhash
- openal
- meshoptimizer
- yoga
- stb
- shaderc
- glfw

## JVMの部分

Gradleビルドシステム、Kotlin 2.0

### ビルド要件

Java: FFM APIをサポートするjdk 21以降