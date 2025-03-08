package com.primogemstudio.engine.graphics.backend.opengl

import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.GL_RENDERER
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.GL_VENDOR
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.GL_VERSION
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.glGetString
import com.primogemstudio.engine.graphics.IRenderer
import com.primogemstudio.engine.graphics.ShaderType
import com.primogemstudio.engine.graphics.data.ApplicationInfo
import com.primogemstudio.engine.graphics.data.ApplicationWindowInfo
import com.primogemstudio.engine.logging.LoggerFactory
import com.primogemstudio.engine.resource.Identifier
import com.primogemstudio.engine.types.Version
import kotlinx.coroutines.Deferred

class BackendRendererOpenGL(
    override val gameInfo: ApplicationInfo,
    override val windowInfo: ApplicationWindowInfo
) : IRenderer {
    private val logger = LoggerFactory.getAsyncLogger()
    override val window: OpenGLWindow = OpenGLWindow(gameInfo, windowInfo) { code, str -> }
    override fun registerShader(shaderId: Identifier, src: Identifier, type: ShaderType): Deferred<Int> {
        TODO("Not yet implemented")
    }

    override fun linkShader(progId: Identifier, progs: Array<Identifier>): Deferred<Int> {
        TODO("Not yet implemented")
    }

    override fun createRenderPass(passId: Identifier) {
        TODO("Not yet implemented")
    }

    override fun createPipeline(pipeId: Identifier, progId: Identifier, passId: Identifier) {
        TODO("Not yet implemented")
    }

    override fun bindFramebuffer(passId: Identifier) {
        TODO("Not yet implemented")
    }

    override fun version(): Version {
        try {
            val vers = glGetString(GL_VERSION).split(" ")[0].split(".").map { it.toUShort() }
            return Version.from(vers[0], vers[1], vers[2])
        } catch (e: Exception) {
            return Version.fromStandard(114u, 514u, 1919u, 810u)
        }
    }

    override fun driver(): String = "${glGetString(GL_RENDERER)} / ${glGetString(GL_VENDOR)}"

    override fun close() {

    }
}