package com.primogemstudio.engine.vk.shader

import org.lwjgl.util.shaderc.Shaderc.shaderc_result_release
import java.io.Closeable
import java.nio.ByteBuffer

data class VkShaderData(
    val pResult: Long,
    val buffer: ByteBuffer?
) : Closeable {
    override fun close() {
        shaderc_result_release(pResult)
    }
}
