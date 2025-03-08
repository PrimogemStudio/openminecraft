package com.primogemstudio.engine

import com.primogemstudio.engine.bindings.glfw.GLFWBaseFuncs.glfwInit
import com.primogemstudio.engine.bindings.glfw.GLFWBaseFuncs.glfwSetErrorCallback
import com.primogemstudio.engine.bindings.glfw.GLFWBaseFuncs.glfwTerminate
import com.primogemstudio.engine.bindings.glfw.GLFWContextFuncs.glfwMakeContextCurrent
import com.primogemstudio.engine.bindings.glfw.GLFWContextFuncs.glfwSwapInterval
import com.primogemstudio.engine.bindings.glfw.GLFWImage
import com.primogemstudio.engine.bindings.glfw.GLFWInputFuncs.glfwCreateCursor
import com.primogemstudio.engine.bindings.glfw.GLFWInputFuncs.glfwSetCursor
import com.primogemstudio.engine.bindings.glfw.GLFWMonitor
import com.primogemstudio.engine.bindings.glfw.GLFWWindow
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.GLFW_CLIENT_API
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.GLFW_CONTEXT_CREATION_API
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.GLFW_CONTEXT_VERSION_MAJOR
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.GLFW_CONTEXT_VERSION_MINOR
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.GLFW_NATIVE_CONTEXT_API
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.GLFW_OPENGL_API
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.GLFW_TRANSPARENT_FRAMEBUFFER
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwCreateWindow
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwDestroyWindow
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwPollEvents
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwSetFramebufferSizeCallback
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwSwapBuffers
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwWindowHint
import com.primogemstudio.engine.bindings.glfw.GLFWWindowFuncs.glfwWindowShouldClose
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.GL_COLOR_BUFFER_BIT
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.GL_TRIANGLES
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.GL_TRUE
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.glBegin
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.glClear
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.glClearColor
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.glColor4f
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.glEnd
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.glLineWidth
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.glVertex3f
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.glViewport
import com.primogemstudio.engine.foreign.heap.HeapByteArray
import com.primogemstudio.engine.graphics.IRenderer
import com.primogemstudio.engine.graphics.ShaderType
import com.primogemstudio.engine.graphics.backend.vk.BackendRendererVk
import com.primogemstudio.engine.graphics.data.ApplicationInfo
import com.primogemstudio.engine.graphics.data.ApplicationWindowInfo
import com.primogemstudio.engine.resource.Identifier
import com.primogemstudio.engine.types.Version
import java.lang.foreign.MemorySegment

suspend fun main() {
    val re: IRenderer = BackendRendererVk(
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

    val shaderPr = Identifier(namespace = "openmc_graphic", path = "basic_shader")
    re.linkShader(shaderPr, arrayOf(frg, vtx)).await()

    val target = Identifier(namespace = "openmc_graphic", path = "main_pass")
    re.createRenderPass(target)

    val pipetest = Identifier(namespace = "openmc_graphic", path = "main_pipe")
    re.createPipeline(pipetest, shaderPr, target)

    re.bindFramebuffer(target)

    glfwInit()
    glfwSetErrorCallback { err, desc ->
        println("$err $desc")
    }

    // glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_COMPAT_PROFILE)
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 1)
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1)
    glfwWindowHint(GLFW_TRANSPARENT_FRAMEBUFFER, GL_TRUE)
    glfwWindowHint(GLFW_CONTEXT_CREATION_API, GLFW_NATIVE_CONTEXT_API)
    glfwWindowHint(GLFW_CLIENT_API, GLFW_OPENGL_API)
    val window = glfwCreateWindow(
        640,
        480,
        "test!",
        GLFWMonitor(MemorySegment.NULL),
        GLFWWindow(MemorySegment.NULL)
    )
    glfwMakeContextCurrent(window)

    glfwSetCursor(
        window,
        glfwCreateCursor(
            GLFWImage().apply {
                width = 32
                height = 32
                data = HeapByteArray((0..<32 * 32 * 4).map { 0xcc.toByte() }.toByteArray())
            },
            0,
            0
        )
    )

    glfwSetFramebufferSizeCallback(window) { _, width, height ->
        glViewport(0, 0, width, height)
    }
    glfwSwapInterval(0)
    glViewport(0, 0, 640, 480)

    var rotation = 0f
    while (!glfwWindowShouldClose(window)) {
        glClearColor(0f, 0f, 0f, 0f)
        glClear(GL_COLOR_BUFFER_BIT)

        glLineWidth(2f)

        // glRotatef(rotation, 0f, 0f, 1f)

        glBegin(GL_TRIANGLES)
        glColor4f(1f, 1f, 0f, 1f)
        glVertex3f(-0.5f, -0.5f, 0f)
        glColor4f(0f, 1f, 1f, 1f)
        glVertex3f(0.5f, -0.5f, 0f)
        glColor4f(1f, 0f, 1f, 1f)
        glVertex3f(0f, 0.5f, 0f)
        glEnd()

        rotation += 0.00001f

        glfwSwapBuffers(window)
        glfwPollEvents()
    }

    glfwDestroyWindow(window)
    glfwTerminate()
}
