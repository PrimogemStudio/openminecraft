package com.primogemstudio.engine.jmake

import com.primogemstudio.engine.i18n.Internationalization.tr
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.FileVisitor
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.attribute.BasicFileAttributes

class JMakeProject(
    projPath: File,
    private val target: File?,
    linkLibs: List<File>,
    toolchain: Toolchain,
    resultCallback: (String, Double) -> Unit
) {
    private var rootPath: File = projPath.toPath().resolve(".jmake").toFile()
    private var buildPath: File
    private var builder: ProjectBuilder

    init {
        if (rootPath.exists()) rootPath.deleteRecursively()
        rootPath.mkdirs()
        buildPath = rootPath.toPath().resolve("build").toFile()
        buildPath.mkdirs()

        val jobj = JSONObject(
            Files.newInputStream(projPath.resolve("jmake.json").toPath()).readAllBytes().toString(Charsets.UTF_8)
        )

        builder = if (jobj["type"] == "cmake") CMakeProjectBuilder(projPath, buildPath, toolchain, resultCallback)
        else {
            if (target == null) throw IllegalArgumentException()
            BaseProjectBuilder(
                projPath,
                buildPath,
                toolchain,
                jobj.getJSONArray("files").toList().map { it.toString() },
                jobj.getJSONArray("includes").toList().map { it.toString() },
                target,
                linkLibs,
                resultCallback
            )
        }

        builder.checkEnv()

        if (jobj.has("configs")) jobj.getJSONObject("configs").apply {
            keys().forEach {
                builder.config(
                    it,
                    get(it).toString().replace("\$cpu_cores", Runtime.getRuntime().availableProcessors().toString())
                )
            }
        }

        if (jobj.has("defines")) jobj.getJSONObject("defines").apply {
            keys().forEach {
                builder.addDefine(
                    it,
                    get(it).toString().replace("\$cpu_cores", Runtime.getRuntime().availableProcessors().toString())
                )
            }
        }
    }

    fun build() {
        builder.buildProject().forEach {
            if (it.toProcess(builder::outputProcessor)
                    .waitForProcess() != 0
            ) throw IllegalStateException(tr("exception.jmake.env_make.fail"))
        }
    }

    fun getTargets(): List<File> {
        if (builder is BaseProjectBuilder) {
            return listOf(target!!)
        }

        val targets = mutableListOf<File>()
        Files.walkFileTree(buildPath.toPath(), object : FileVisitor<Path> {
            override fun preVisitDirectory(dir: Path?, attrs: BasicFileAttributes?): FileVisitResult =
                FileVisitResult.CONTINUE

            override fun visitFile(file: Path?, attrs: BasicFileAttributes?): FileVisitResult {
                file?.fileName?.toString()?.also {
                    if (it.endsWith(".a")) targets.add(file.toFile())
                }
                return FileVisitResult.CONTINUE
            }

            override fun visitFileFailed(file: Path?, exc: IOException?): FileVisitResult = FileVisitResult.CONTINUE
            override fun postVisitDirectory(dir: Path?, exc: IOException?): FileVisitResult = FileVisitResult.CONTINUE
        })

        return targets
    }
}