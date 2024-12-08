package com.primogemstudio.engine.font

import org.lwjgl.util.harfbuzz.HarfBuzz.*
import org.lwjgl.util.harfbuzz.OpenType.hb_ot_color_palette_get_count

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
        println("color $fore, $c")
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
        println("custom_palette_color $ci, $c")
    }, 0, {})

    for (i in 1200..<1216) {
        println("GLYPH START #$i")
        hb_font_paint_glyph(font, i, funcs, 0, 0, 0)
        println("GLYPH END #$i")
    }
}

/*
  unsigned palette_count = hb_ot_color_palette_get_count (face);
  for (unsigned palette = 0; palette < palette_count; ++palette)
  {
    unsigned num_colors = hb_ot_color_palette_get_colors (face, palette, 0, nullptr, nullptr);
    if (!num_colors) continue;

    hb_color_t *colors = (hb_color_t*) calloc (num_colors, sizeof (hb_color_t));
    hb_ot_color_palette_get_colors (face, palette, 0, &num_colors, colors);
    if (!num_colors)
    {
      free (colors);
      continue;
    }

    unsigned num_glyphs = hb_face_get_glyph_count (face);
    for (hb_codepoint_t gid = 0; gid < num_glyphs; ++gid)
    {
      unsigned num_layers = hb_ot_color_glyph_get_layers (face, gid, 0, nullptr, nullptr);
      if (!num_layers) continue;

      hb_ot_color_layer_t *layers = (hb_ot_color_layer_t*) malloc (num_layers * sizeof (hb_ot_color_layer_t));

      hb_ot_color_glyph_get_layers (face, gid, 0, &num_layers, layers);
      if (num_layers)
      {
	hb_font_extents_t font_extents;
	hb_font_get_extents_for_direction (font, HB_DIRECTION_LTR, &font_extents);
	hb_glyph_extents_t extents = {0};
	if (!hb_font_get_glyph_extents (font, gid, &extents))
	{
	  printf ("Skip gid: %u\n", gid);
	  continue;
	}

	char output_path[255];
	snprintf (output_path, sizeof output_path, "out/colr-%u-%u-%u.svg", gid, palette, face_index);
	FILE *f = fopen (output_path, "wb");
	fprintf (f, "<svg xmlns=\"http://www.w3.org/2000/svg\""
		    " viewBox=\"%d %d %d %d\">\n",
		    extents.x_bearing, 0,
		    extents.x_bearing + extents.width, -extents.height);
	draw_data_t draw_data;
	draw_data.ascender = extents.y_bearing;
	draw_data.f = f;

	for (unsigned layer = 0; layer < num_layers; ++layer)
	{
	  hb_color_t color = 0x000000FF;
	  if (layers[layer].color_index != 0xFFFF)
	    color = colors[layers[layer].color_index];
	  fprintf (f, "<path fill=\"#%02X%02X%02X\" ",
		   hb_color_get_red (color), hb_color_get_green (color), hb_color_get_green (color));
	  if (hb_color_get_alpha (color) != 255)
	    fprintf (f, "fill-opacity=\"%.3f\"", (double) hb_color_get_alpha (color) / 255.);
	  fprintf (f, "d=\"");
	  hb_font_draw_glyph (font, layers[layer].glyph, funcs, &draw_data);
	  fprintf (f, "\"/>\n");
	}

	fprintf (f, "</svg>");
	fclose (f);
      }
      free (layers);
    }

    free (colors);
  }
}*/