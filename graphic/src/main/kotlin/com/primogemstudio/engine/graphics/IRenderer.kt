package com.primogemstudio.engine.graphics

import com.primogemstudio.engine.graphics.data.ApplicationInfo
import com.primogemstudio.engine.graphics.data.ApplicationWindowInfo
import com.primogemstudio.engine.resource.Identifier
import com.primogemstudio.engine.types.Version
import kotlinx.coroutines.Deferred
import java.io.Closeable

enum class ShaderType {
    Vertex,
    Fragment,
    Compute,
    Geometry,
    TessControl,
    TessEvaluation,
    RayGen,
    AnyHit,
    ClosestHit,
    Miss,
    Intersection,
    Callable
}

interface IRenderer : Closeable {
    fun version(): Version
    fun driver(): String
    val gameInfo: ApplicationInfo
    val windowInfo: ApplicationWindowInfo
    val window: IWindow

    fun registerShader(shaderId: Identifier, src: Identifier, type: ShaderType): Deferred<Int>
    fun linkShader(progId: Identifier, progs: Array<Identifier>): Deferred<Int>
    fun createRenderPass(passId: Identifier)
}