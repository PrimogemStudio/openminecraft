package com.primogemstudio.engine.graphics.backend.opengl

import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.GL_RENDERER
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.GL_VENDOR
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.GL_VERSION
import com.primogemstudio.engine.bindings.opengl.gl11.GL11Funcs.glGetString
import com.primogemstudio.engine.graphics.IRenderer
import com.primogemstudio.engine.types.Version

class BackendRendererOpenGL : IRenderer {
    override fun version(): Version {
        try {
            val vers = glGetString(GL_VERSION).split(" ")[0].split(".").map { it.toUShort() }
            return Version.from(vers[0], vers[1], vers[2])
        } catch (e: Exception) {
            return Version.fromStandard(114u, 514u, 1919u, 810u)
        }
    }

    override fun driver(): String = "${glGetString(GL_RENDERER)} / ${glGetString(GL_VENDOR)}"
}