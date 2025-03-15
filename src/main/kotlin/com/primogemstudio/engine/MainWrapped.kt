package com.primogemstudio.engine

import com.primogemstudio.engine.bindings.glfw.GLFWInputFuncs.glfwSetCursorPosCallback
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwGetFramebufferSize
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwPollEvents
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwWindowShouldClose
import com.primogemstudio.engine.foreign.heap.HeapInt
import com.primogemstudio.engine.graphics.ShaderType
import com.primogemstudio.engine.graphics.backend.vk.BackendRendererVk
import com.primogemstudio.engine.graphics.data.ApplicationInfo
import com.primogemstudio.engine.graphics.data.ApplicationWindowInfo
import com.primogemstudio.engine.resource.Identifier
import com.primogemstudio.engine.types.Version
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.concurrent.thread

suspend fun main() {
    val re = BackendRendererVk(
        ApplicationInfo(
            "openminecraft",
            Version.from(0u, 0u, 1u),
            "openminecraft",
            Version.from(0u, 0u, 1u),
            Version.fromStandard(1u, 0u, 0u, 0u)
        ),
        ApplicationWindowInfo(
            windowTitle = "OpenMinecraft Dev Preview",
            width = 800,
            height = 600
        ),
        { it.first() },
        { arrayOf() }
    )
    val frg = Identifier(namespace = "openmc_graphic", path = "vtx_shader_frag")
    val vtx = Identifier(namespace = "openmc_graphic", path = "vtx_shader_vert")

    re.registerShader(
        frg,
        Identifier(namespace = "openmc_graphic", path = "shaders/vtx_shader.frag"),
        ShaderType.Fragment
    ).await()
    re.registerShader(
        vtx,
        Identifier(namespace = "openmc_graphic", path = "shaders/vtx_shader.vert"),
        ShaderType.Vertex
    ).await()

    val shaderPr = Identifier(namespace = "openmc_graphic", path = "vtx_shader")
    re.linkShader(shaderPr, arrayOf(frg, vtx)).await()

    val target = Identifier(namespace = "openmc_graphic", path = "main_pass")
    re.createRenderPass(target)

    val pipetest = Identifier(namespace = "openmc_graphic", path = "main_pipe")
    re.createPipeline(pipetest, shaderPr, target)

    re.bindOutputFramebuffer(target)

    val mainVtxBuffer = Identifier(namespace = "openmc_graphic", path = "main_vtxbuffer")
    re.createVertexBuffer(mainVtxBuffer, 60)

    val bb = ByteBuffer.allocate(60).order(ByteOrder.nativeOrder())
    floatArrayOf(
        0.0f, -0.5f, 1.0f, 0.0f, 0.0f,
        0.5f, 0.5f, 0.0f, 1.0f, 0.0f,
        -0.5f, 0.5f, 0.0f, 0.0f, 1.0f
    ).forEach { bb.putFloat(it) }
    re.writeVertexBuffer(mainVtxBuffer, bb.array())

    re.createTestCommandBuffer()

    var xx = 0f
    var yy = 0f
    glfwSetCursorPosCallback(re.window.window) { _, x, y ->
        var xp = HeapInt()
        var yp = HeapInt()
        glfwGetFramebufferSize(re.window.window, xp, yp)

        xx = x.toFloat() / xp.value().toFloat() * 2 - 1
        yy = y.toFloat() / yp.value().toFloat() * 2 - 1
    }

    val thr = thread(start = false) {
        while (!glfwWindowShouldClose(re.window.window)) {
            val bb = ByteBuffer.allocate(60).order(ByteOrder.nativeOrder())
            floatArrayOf(
                xx, yy, 1.0f, 0.0f, 0.0f,
                0.5f, 0.5f, 0.0f, 1.0f, 0.0f,
                -0.5f, 0.5f, 0.0f, 0.0f, 1.0f
            ).forEach { bb.putFloat(it) }
            re.writeVertexBuffer(mainVtxBuffer, bb.array())
        }
    }

    while (!glfwWindowShouldClose(re.window.window)) {
        if (thr.state == Thread.State.NEW) thr.start()
        re.render()

        glfwPollEvents()
    }

    re.close()
}
