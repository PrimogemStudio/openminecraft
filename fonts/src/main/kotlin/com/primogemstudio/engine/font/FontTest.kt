package com.primogemstudio.engine.font

import org.lwjgl.system.MemoryUtil
import org.lwjgl.util.harfbuzz.HarfBuzz.*
import org.lwjgl.util.harfbuzz.OpenType.hb_ot_color_palette_get_count

@OptIn(ExperimentalStdlibApi::class)
fun main() {
    System.setProperty("org.lwjgl.harfbuzz.libname", "freetype")
    System.setProperty("org.lwjgl.util.NoFunctionChecks", "true")
    val blob = hb_blob_create_from_file("/home/coder2/extsources/noto-emoji/fonts/Noto-COLRv1.ttf")
    val face = hb_face_create(blob, 0)
    val upem = hb_face_get_upem(face)
    val font = hb_font_create(face)
    hb_font_set_scale(font, upem, upem)
    hb_ft_font_set_funcs(font)

    val paletteCount = hb_ot_color_palette_get_count(face)

    val funcs = hb_paint_funcs_create()
    hb_paint_funcs_set_push_transform_func(funcs, { _, _, xx, yx, xy, yy, dx, dy, _ ->
        println("push_transform $xx, $yx, $xy, $yy, $dx, $dy")
    }, 0, { })
    hb_paint_funcs_set_pop_transform_func(funcs, { _, _, _ ->
        println("pop_transform")
    }, 0, {})
    hb_paint_funcs_set_color_glyph_func(funcs, { _, _, glyph, font, _ ->
        println("color_glyph $glyph, $font")
    }, 0, {})
    hb_paint_funcs_set_push_clip_glyph_func(funcs, { _, _, glyph, font, _ ->
        println("push_clip_glyph $glyph, $font")
    }, 0, {})
    hb_paint_funcs_set_push_clip_rectangle_func(funcs, { _, _, xmin, ymin, xmax, ymax, _ ->
        println("push_clip_rectangle $xmin, $ymin, $xmax, $ymax")
    }, 0, {})
    hb_paint_funcs_set_pop_clip_func(funcs, { _, _, _ ->
        println("pop_clip")
    }, 0, {})
    hb_paint_funcs_set_color_func(funcs, { _, _, fore, c, _ ->
        println("color $fore, 0x${c.toHexString()}")
    }, 0, {})
    hb_paint_funcs_set_image_func(funcs, { _, _, image, width, height, format, slant, extents, _ ->
        println("image $image, $width, $height, $format, $slant, $extents")
        1
    }, 0, {})
    hb_paint_funcs_set_linear_gradient_func(funcs, { _, _, colorLine, x0, y0, x1, y1, x2, y2, _ ->
        println("linear_gradient $colorLine, $x0, $y0, $x1, $y1, $x2, $y2")
    }, 0, {})
    hb_paint_funcs_set_radial_gradient_func(funcs, { _, _, colorLine, x0, y0, r0, x1, y1, r1, _ ->
        println("radial_gradient $colorLine, $x0, $y0, $r0, $x1, $y1, $r1")
    }, 0, {})
    hb_paint_funcs_set_sweep_gradient_func(funcs, { _, _, colorLine, x0, y0, startAngle, endAngle, _ ->
        println("sweep_gradient $colorLine, $x0, $y0, $startAngle, $endAngle")
    }, 0, {})
    hb_paint_funcs_set_push_group_func(funcs, { _, _, _ ->
        println("push_group")
    }, 0, {})
    hb_paint_funcs_set_pop_group_func(funcs, { _, _, mode, _ ->
        println("pop_group $mode")
    }, 0, {})
    hb_paint_funcs_set_custom_palette_color_func(funcs, { _, _, ci, c, _ ->
        println("custom_palette_color $ci, 0x${MemoryUtil.memGetInt(c).toHexString()}")
    }, 0, {})

    for (i in 1200..<1216) {
        println("GLYPH START #$i")
        hb_font_paint_glyph(font, i, funcs, 0, 0, 0)
        println("GLYPH END #$i")
    }
}