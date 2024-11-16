package com.primogemstudio.engine.jmake

import com.primogemstudio.engine.i18n.Internationalization.tr
import java.io.File
import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.FileVisitor
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.attribute.BasicFileAttributes

class JMakeProject(val projPath: File) {
    lateinit var rootPath: File
    lateinit var buildPath: File
    lateinit var builder: ProjectBuilder

    fun prepareBuild() {
        rootPath = projPath.toPath().resolve(".jmake").toFile()
        rootPath.mkdirs()
        buildPath = rootPath.toPath().resolve("build").toFile()
        buildPath.mkdirs()

        builder = CMakeProjectBuilder(projPath, buildPath)
        builder.checkEnv()
    }

    fun build() {
        builder.config("JOBS", 8)

        builder.addDefine("BUILD_CPU_DEMOS", "OFF")
        builder.addDefine("BUILD_OPENGL3_DEMOS", "OFF")
        builder.addDefine("BUILD_BULLET2_DEMOS", "OFF")
        builder.addDefine("BUILD_UNIT_TESTS", "OFF")
        builder.addDefine("INSTALL_LIBS", "ON")
        builder.addDefine("BUILD_SHARED_LIBS", "OFF")

        builder.buildProject().forEach {
            if (it.toProcess().waitForProcess() != 0) throw IllegalStateException(tr("exception.jmake.env_cmake.fail"))
        }
    }

    fun findFiles() {
        Files.walkFileTree(buildPath.toPath(), object : FileVisitor<Path> {
            override fun preVisitDirectory(dir: Path?, attrs: BasicFileAttributes?): FileVisitResult =
                FileVisitResult.CONTINUE

            override fun visitFile(file: Path?, attrs: BasicFileAttributes?): FileVisitResult {
                file?.fileName?.toString()?.also {
                    if (it.endsWith(".a")) println(file)
                }
                return FileVisitResult.CONTINUE
            }

            override fun visitFileFailed(file: Path?, exc: IOException?): FileVisitResult = FileVisitResult.CONTINUE

            override fun postVisitDirectory(dir: Path?, exc: IOException?): FileVisitResult = FileVisitResult.CONTINUE
        })
    }
}