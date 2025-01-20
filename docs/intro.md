---
sidebar_position: 1
---

# 项目结构

目前, 引擎由 [Native 部分](https://github.com/PrimogemStudio/openminecraft-binding)和 [JVM 部分](https://github.com/PrimogemStudio/openminecraft)组成

## Native 部分

目前使用 xmake 构建系统, 包含如下项目
- freetype
- harfbuzz
- xxhash
- openal
- meshoptimizer
- yoga
- stb
- shaderc
- glfw

## JVM 部分

使用 gradle 构建系统, Kotlin 2.0

### 构建需求

Java: 必须使用 jdk 21 或以上支持 FFM API 的版本