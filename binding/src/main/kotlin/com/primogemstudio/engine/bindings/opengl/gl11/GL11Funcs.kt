package com.primogemstudio.engine.bindings.opengl.gl11

import com.primogemstudio.engine.interfaces.NativeMethodCache.callVoidFunc
import com.primogemstudio.engine.interfaces.heap.*

object GL11Funcs {
    const val GL_ACCUM: Int = 256
    const val GL_LOAD: Int = 257
    const val GL_RETURN: Int = 258
    const val GL_MULT: Int = 259
    const val GL_ADD: Int = 260
    const val GL_NEVER: Int = 512
    const val GL_LESS: Int = 513
    const val GL_EQUAL: Int = 514
    const val GL_LEQUAL: Int = 515
    const val GL_GREATER: Int = 516
    const val GL_NOTEQUAL: Int = 517
    const val GL_GEQUAL: Int = 518
    const val GL_ALWAYS: Int = 519
    const val GL_CURRENT_BIT: Int = 1
    const val GL_POINT_BIT: Int = 2
    const val GL_LINE_BIT: Int = 4
    const val GL_POLYGON_BIT: Int = 8
    const val GL_POLYGON_STIPPLE_BIT: Int = 16
    const val GL_PIXEL_MODE_BIT: Int = 32
    const val GL_LIGHTING_BIT: Int = 64
    const val GL_FOG_BIT: Int = 128
    const val GL_DEPTH_BUFFER_BIT: Int = 256
    const val GL_ACCUM_BUFFER_BIT: Int = 512
    const val GL_STENCIL_BUFFER_BIT: Int = 1024
    const val GL_VIEWPORT_BIT: Int = 2048
    const val GL_TRANSFORM_BIT: Int = 4096
    const val GL_ENABLE_BIT: Int = 8192
    const val GL_COLOR_BUFFER_BIT: Int = 16384
    const val GL_HINT_BIT: Int = 32768
    const val GL_EVAL_BIT: Int = 65536
    const val GL_LIST_BIT: Int = 131072
    const val GL_TEXTURE_BIT: Int = 262144
    const val GL_SCISSOR_BIT: Int = 524288
    const val GL_ALL_ATTRIB_BITS: Int = 1048575
    const val GL_POINTS: Int = 0
    const val GL_LINES: Int = 1
    const val GL_LINE_LOOP: Int = 2
    const val GL_LINE_STRIP: Int = 3
    const val GL_TRIANGLES: Int = 4
    const val GL_TRIANGLE_STRIP: Int = 5
    const val GL_TRIANGLE_FAN: Int = 6
    const val GL_QUADS: Int = 7
    const val GL_QUAD_STRIP: Int = 8
    const val GL_POLYGON: Int = 9
    const val GL_ZERO: Int = 0
    const val GL_ONE: Int = 1
    const val GL_SRC_COLOR: Int = 768
    const val GL_ONE_MINUS_SRC_COLOR: Int = 769
    const val GL_SRC_ALPHA: Int = 770
    const val GL_ONE_MINUS_SRC_ALPHA: Int = 771
    const val GL_DST_ALPHA: Int = 772
    const val GL_ONE_MINUS_DST_ALPHA: Int = 773
    const val GL_DST_COLOR: Int = 774
    const val GL_ONE_MINUS_DST_COLOR: Int = 775
    const val GL_SRC_ALPHA_SATURATE: Int = 776
    const val GL_TRUE: Int = 1
    const val GL_FALSE: Int = 0
    const val GL_CLIP_PLANE0: Int = 12288
    const val GL_CLIP_PLANE1: Int = 12289
    const val GL_CLIP_PLANE2: Int = 12290
    const val GL_CLIP_PLANE3: Int = 12291
    const val GL_CLIP_PLANE4: Int = 12292
    const val GL_CLIP_PLANE5: Int = 12293
    const val GL_BYTE: Int = 5120
    const val GL_UNSIGNED_BYTE: Int = 5121
    const val GL_SHORT: Int = 5122
    const val GL_UNSIGNED_SHORT: Int = 5123
    const val GL_INT: Int = 5124
    const val GL_UNSIGNED_INT: Int = 5125
    const val GL_FLOAT: Int = 5126
    const val GL_2_BYTES: Int = 5127
    const val GL_3_BYTES: Int = 5128
    const val GL_4_BYTES: Int = 5129
    const val GL_DOUBLE: Int = 5130
    const val GL_NONE: Int = 0
    const val GL_FRONT_LEFT: Int = 1024
    const val GL_FRONT_RIGHT: Int = 1025
    const val GL_BACK_LEFT: Int = 1026
    const val GL_BACK_RIGHT: Int = 1027
    const val GL_FRONT: Int = 1028
    const val GL_BACK: Int = 1029
    const val GL_LEFT: Int = 1030
    const val GL_RIGHT: Int = 1031
    const val GL_FRONT_AND_BACK: Int = 1032
    const val GL_AUX0: Int = 1033
    const val GL_AUX1: Int = 1034
    const val GL_AUX2: Int = 1035
    const val GL_AUX3: Int = 1036
    const val GL_NO_ERROR: Int = 0
    const val GL_INVALID_ENUM: Int = 1280
    const val GL_INVALID_VALUE: Int = 1281
    const val GL_INVALID_OPERATION: Int = 1282
    const val GL_STACK_OVERFLOW: Int = 1283
    const val GL_STACK_UNDERFLOW: Int = 1284
    const val GL_OUT_OF_MEMORY: Int = 1285
    const val GL_2D: Int = 1536
    const val GL_3D: Int = 1537
    const val GL_3D_COLOR: Int = 1538
    const val GL_3D_COLOR_TEXTURE: Int = 1539
    const val GL_4D_COLOR_TEXTURE: Int = 1540
    const val GL_PASS_THROUGH_TOKEN: Int = 1792
    const val GL_POINT_TOKEN: Int = 1793
    const val GL_LINE_TOKEN: Int = 1794
    const val GL_POLYGON_TOKEN: Int = 1795
    const val GL_BITMAP_TOKEN: Int = 1796
    const val GL_DRAW_PIXEL_TOKEN: Int = 1797
    const val GL_COPY_PIXEL_TOKEN: Int = 1798
    const val GL_LINE_RESET_TOKEN: Int = 1799
    const val GL_EXP: Int = 2048
    const val GL_EXP2: Int = 2049
    const val GL_CW: Int = 2304
    const val GL_CCW: Int = 2305
    const val GL_COEFF: Int = 2560
    const val GL_ORDER: Int = 2561
    const val GL_DOMAIN: Int = 2562
    const val GL_CURRENT_COLOR: Int = 2816
    const val GL_CURRENT_INDEX: Int = 2817
    const val GL_CURRENT_NORMAL: Int = 2818
    const val GL_CURRENT_TEXTURE_COORDS: Int = 2819
    const val GL_CURRENT_RASTER_COLOR: Int = 2820
    const val GL_CURRENT_RASTER_INDEX: Int = 2821
    const val GL_CURRENT_RASTER_TEXTURE_COORDS: Int = 2822
    const val GL_CURRENT_RASTER_POSITION: Int = 2823
    const val GL_CURRENT_RASTER_POSITION_VALID: Int = 2824
    const val GL_CURRENT_RASTER_DISTANCE: Int = 2825
    const val GL_POINT_SMOOTH: Int = 2832
    const val GL_POINT_SIZE: Int = 2833
    const val GL_POINT_SIZE_RANGE: Int = 2834
    const val GL_POINT_SIZE_GRANULARITY: Int = 2835
    const val GL_LINE_SMOOTH: Int = 2848
    const val GL_LINE_WIDTH: Int = 2849
    const val GL_LINE_WIDTH_RANGE: Int = 2850
    const val GL_LINE_WIDTH_GRANULARITY: Int = 2851
    const val GL_LINE_STIPPLE: Int = 2852
    const val GL_LINE_STIPPLE_PATTERN: Int = 2853
    const val GL_LINE_STIPPLE_REPEAT: Int = 2854
    const val GL_LIST_MODE: Int = 2864
    const val GL_MAX_LIST_NESTING: Int = 2865
    const val GL_LIST_BASE: Int = 2866
    const val GL_LIST_INDEX: Int = 2867
    const val GL_POLYGON_MODE: Int = 2880
    const val GL_POLYGON_SMOOTH: Int = 2881
    const val GL_POLYGON_STIPPLE: Int = 2882
    const val GL_EDGE_FLAG: Int = 2883
    const val GL_CULL_FACE: Int = 2884
    const val GL_CULL_FACE_MODE: Int = 2885
    const val GL_FRONT_FACE: Int = 2886
    const val GL_LIGHTING: Int = 2896
    const val GL_LIGHT_MODEL_LOCAL_VIEWER: Int = 2897
    const val GL_LIGHT_MODEL_TWO_SIDE: Int = 2898
    const val GL_LIGHT_MODEL_AMBIENT: Int = 2899
    const val GL_SHADE_MODEL: Int = 2900
    const val GL_COLOR_MATERIAL_FACE: Int = 2901
    const val GL_COLOR_MATERIAL_PARAMETER: Int = 2902
    const val GL_COLOR_MATERIAL: Int = 2903
    const val GL_FOG: Int = 2912
    const val GL_FOG_INDEX: Int = 2913
    const val GL_FOG_DENSITY: Int = 2914
    const val GL_FOG_START: Int = 2915
    const val GL_FOG_END: Int = 2916
    const val GL_FOG_MODE: Int = 2917
    const val GL_FOG_COLOR: Int = 2918
    const val GL_DEPTH_RANGE: Int = 2928
    const val GL_DEPTH_TEST: Int = 2929
    const val GL_DEPTH_WRITEMASK: Int = 2930
    const val GL_DEPTH_CLEAR_VALUE: Int = 2931
    const val GL_DEPTH_FUNC: Int = 2932
    const val GL_ACCUM_CLEAR_VALUE: Int = 2944
    const val GL_STENCIL_TEST: Int = 2960
    const val GL_STENCIL_CLEAR_VALUE: Int = 2961
    const val GL_STENCIL_FUNC: Int = 2962
    const val GL_STENCIL_VALUE_MASK: Int = 2963
    const val GL_STENCIL_FAIL: Int = 2964
    const val GL_STENCIL_PASS_DEPTH_FAIL: Int = 2965
    const val GL_STENCIL_PASS_DEPTH_PASS: Int = 2966
    const val GL_STENCIL_REF: Int = 2967
    const val GL_STENCIL_WRITEMASK: Int = 2968
    const val GL_MATRIX_MODE: Int = 2976
    const val GL_NORMALIZE: Int = 2977
    const val GL_VIEWPORT: Int = 2978
    const val GL_MODELVIEW_STACK_DEPTH: Int = 2979
    const val GL_PROJECTION_STACK_DEPTH: Int = 2980
    const val GL_TEXTURE_STACK_DEPTH: Int = 2981
    const val GL_MODELVIEW_MATRIX: Int = 2982
    const val GL_PROJECTION_MATRIX: Int = 2983
    const val GL_TEXTURE_MATRIX: Int = 2984
    const val GL_ATTRIB_STACK_DEPTH: Int = 2992
    const val GL_CLIENT_ATTRIB_STACK_DEPTH: Int = 2993
    const val GL_ALPHA_TEST: Int = 3008
    const val GL_ALPHA_TEST_FUNC: Int = 3009
    const val GL_ALPHA_TEST_REF: Int = 3010
    const val GL_DITHER: Int = 3024
    const val GL_BLEND_DST: Int = 3040
    const val GL_BLEND_SRC: Int = 3041
    const val GL_BLEND: Int = 3042
    const val GL_LOGIC_OP_MODE: Int = 3056
    const val GL_INDEX_LOGIC_OP: Int = 3057
    const val GL_LOGIC_OP: Int = 3057
    const val GL_COLOR_LOGIC_OP: Int = 3058
    const val GL_AUX_BUFFERS: Int = 3072
    const val GL_DRAW_BUFFER: Int = 3073
    const val GL_READ_BUFFER: Int = 3074
    const val GL_SCISSOR_BOX: Int = 3088
    const val GL_SCISSOR_TEST: Int = 3089
    const val GL_INDEX_CLEAR_VALUE: Int = 3104
    const val GL_INDEX_WRITEMASK: Int = 3105
    const val GL_COLOR_CLEAR_VALUE: Int = 3106
    const val GL_COLOR_WRITEMASK: Int = 3107
    const val GL_INDEX_MODE: Int = 3120
    const val GL_RGBA_MODE: Int = 3121
    const val GL_DOUBLEBUFFER: Int = 3122
    const val GL_STEREO: Int = 3123
    const val GL_RENDER_MODE: Int = 3136
    const val GL_PERSPECTIVE_CORRECTION_HINT: Int = 3152
    const val GL_POINT_SMOOTH_HINT: Int = 3153
    const val GL_LINE_SMOOTH_HINT: Int = 3154
    const val GL_POLYGON_SMOOTH_HINT: Int = 3155
    const val GL_FOG_HINT: Int = 3156
    const val GL_TEXTURE_GEN_S: Int = 3168
    const val GL_TEXTURE_GEN_T: Int = 3169
    const val GL_TEXTURE_GEN_R: Int = 3170
    const val GL_TEXTURE_GEN_Q: Int = 3171
    const val GL_PIXEL_MAP_I_TO_I: Int = 3184
    const val GL_PIXEL_MAP_S_TO_S: Int = 3185
    const val GL_PIXEL_MAP_I_TO_R: Int = 3186
    const val GL_PIXEL_MAP_I_TO_G: Int = 3187
    const val GL_PIXEL_MAP_I_TO_B: Int = 3188
    const val GL_PIXEL_MAP_I_TO_A: Int = 3189
    const val GL_PIXEL_MAP_R_TO_R: Int = 3190
    const val GL_PIXEL_MAP_G_TO_G: Int = 3191
    const val GL_PIXEL_MAP_B_TO_B: Int = 3192
    const val GL_PIXEL_MAP_A_TO_A: Int = 3193
    const val GL_PIXEL_MAP_I_TO_I_SIZE: Int = 3248
    const val GL_PIXEL_MAP_S_TO_S_SIZE: Int = 3249
    const val GL_PIXEL_MAP_I_TO_R_SIZE: Int = 3250
    const val GL_PIXEL_MAP_I_TO_G_SIZE: Int = 3251
    const val GL_PIXEL_MAP_I_TO_B_SIZE: Int = 3252
    const val GL_PIXEL_MAP_I_TO_A_SIZE: Int = 3253
    const val GL_PIXEL_MAP_R_TO_R_SIZE: Int = 3254
    const val GL_PIXEL_MAP_G_TO_G_SIZE: Int = 3255
    const val GL_PIXEL_MAP_B_TO_B_SIZE: Int = 3256
    const val GL_PIXEL_MAP_A_TO_A_SIZE: Int = 3257
    const val GL_UNPACK_SWAP_BYTES: Int = 3312
    const val GL_UNPACK_LSB_FIRST: Int = 3313
    const val GL_UNPACK_ROW_LENGTH: Int = 3314
    const val GL_UNPACK_SKIP_ROWS: Int = 3315
    const val GL_UNPACK_SKIP_PIXELS: Int = 3316
    const val GL_UNPACK_ALIGNMENT: Int = 3317
    const val GL_PACK_SWAP_BYTES: Int = 3328
    const val GL_PACK_LSB_FIRST: Int = 3329
    const val GL_PACK_ROW_LENGTH: Int = 3330
    const val GL_PACK_SKIP_ROWS: Int = 3331
    const val GL_PACK_SKIP_PIXELS: Int = 3332
    const val GL_PACK_ALIGNMENT: Int = 3333
    const val GL_MAP_COLOR: Int = 3344
    const val GL_MAP_STENCIL: Int = 3345
    const val GL_INDEX_SHIFT: Int = 3346
    const val GL_INDEX_OFFSET: Int = 3347
    const val GL_RED_SCALE: Int = 3348
    const val GL_RED_BIAS: Int = 3349
    const val GL_ZOOM_X: Int = 3350
    const val GL_ZOOM_Y: Int = 3351
    const val GL_GREEN_SCALE: Int = 3352
    const val GL_GREEN_BIAS: Int = 3353
    const val GL_BLUE_SCALE: Int = 3354
    const val GL_BLUE_BIAS: Int = 3355
    const val GL_ALPHA_SCALE: Int = 3356
    const val GL_ALPHA_BIAS: Int = 3357
    const val GL_DEPTH_SCALE: Int = 3358
    const val GL_DEPTH_BIAS: Int = 3359
    const val GL_MAX_EVAL_ORDER: Int = 3376
    const val GL_MAX_LIGHTS: Int = 3377
    const val GL_MAX_CLIP_PLANES: Int = 3378
    const val GL_MAX_TEXTURE_SIZE: Int = 3379
    const val GL_MAX_PIXEL_MAP_TABLE: Int = 3380
    const val GL_MAX_ATTRIB_STACK_DEPTH: Int = 3381
    const val GL_MAX_MODELVIEW_STACK_DEPTH: Int = 3382
    const val GL_MAX_NAME_STACK_DEPTH: Int = 3383
    const val GL_MAX_PROJECTION_STACK_DEPTH: Int = 3384
    const val GL_MAX_TEXTURE_STACK_DEPTH: Int = 3385
    const val GL_MAX_VIEWPORT_DIMS: Int = 3386
    const val GL_MAX_CLIENT_ATTRIB_STACK_DEPTH: Int = 3387
    const val GL_SUBPIXEL_BITS: Int = 3408
    const val GL_INDEX_BITS: Int = 3409
    const val GL_RED_BITS: Int = 3410
    const val GL_GREEN_BITS: Int = 3411
    const val GL_BLUE_BITS: Int = 3412
    const val GL_ALPHA_BITS: Int = 3413
    const val GL_DEPTH_BITS: Int = 3414
    const val GL_STENCIL_BITS: Int = 3415
    const val GL_ACCUM_RED_BITS: Int = 3416
    const val GL_ACCUM_GREEN_BITS: Int = 3417
    const val GL_ACCUM_BLUE_BITS: Int = 3418
    const val GL_ACCUM_ALPHA_BITS: Int = 3419
    const val GL_NAME_STACK_DEPTH: Int = 3440
    const val GL_AUTO_NORMAL: Int = 3456
    const val GL_MAP1_COLOR_4: Int = 3472
    const val GL_MAP1_INDEX: Int = 3473
    const val GL_MAP1_NORMAL: Int = 3474
    const val GL_MAP1_TEXTURE_COORD_1: Int = 3475
    const val GL_MAP1_TEXTURE_COORD_2: Int = 3476
    const val GL_MAP1_TEXTURE_COORD_3: Int = 3477
    const val GL_MAP1_TEXTURE_COORD_4: Int = 3478
    const val GL_MAP1_VERTEX_3: Int = 3479
    const val GL_MAP1_VERTEX_4: Int = 3480
    const val GL_MAP2_COLOR_4: Int = 3504
    const val GL_MAP2_INDEX: Int = 3505
    const val GL_MAP2_NORMAL: Int = 3506
    const val GL_MAP2_TEXTURE_COORD_1: Int = 3507
    const val GL_MAP2_TEXTURE_COORD_2: Int = 3508
    const val GL_MAP2_TEXTURE_COORD_3: Int = 3509
    const val GL_MAP2_TEXTURE_COORD_4: Int = 3510
    const val GL_MAP2_VERTEX_3: Int = 3511
    const val GL_MAP2_VERTEX_4: Int = 3512
    const val GL_MAP1_GRID_DOMAIN: Int = 3536
    const val GL_MAP1_GRID_SEGMENTS: Int = 3537
    const val GL_MAP2_GRID_DOMAIN: Int = 3538
    const val GL_MAP2_GRID_SEGMENTS: Int = 3539
    const val GL_TEXTURE_1D: Int = 3552
    const val GL_TEXTURE_2D: Int = 3553
    const val GL_FEEDBACK_BUFFER_POINTER: Int = 3568
    const val GL_FEEDBACK_BUFFER_SIZE: Int = 3569
    const val GL_FEEDBACK_BUFFER_TYPE: Int = 3570
    const val GL_SELECTION_BUFFER_POINTER: Int = 3571
    const val GL_SELECTION_BUFFER_SIZE: Int = 3572
    const val GL_TEXTURE_WIDTH: Int = 4096
    const val GL_TEXTURE_HEIGHT: Int = 4097
    const val GL_TEXTURE_INTERNAL_FORMAT: Int = 4099
    const val GL_TEXTURE_COMPONENTS: Int = 4099
    const val GL_TEXTURE_BORDER_COLOR: Int = 4100
    const val GL_TEXTURE_BORDER: Int = 4101
    const val GL_DONT_CARE: Int = 4352
    const val GL_FASTEST: Int = 4353
    const val GL_NICEST: Int = 4354
    const val GL_LIGHT0: Int = 16384
    const val GL_LIGHT1: Int = 16385
    const val GL_LIGHT2: Int = 16386
    const val GL_LIGHT3: Int = 16387
    const val GL_LIGHT4: Int = 16388
    const val GL_LIGHT5: Int = 16389
    const val GL_LIGHT6: Int = 16390
    const val GL_LIGHT7: Int = 16391
    const val GL_AMBIENT: Int = 4608
    const val GL_DIFFUSE: Int = 4609
    const val GL_SPECULAR: Int = 4610
    const val GL_POSITION: Int = 4611
    const val GL_SPOT_DIRECTION: Int = 4612
    const val GL_SPOT_EXPONENT: Int = 4613
    const val GL_SPOT_CUTOFF: Int = 4614
    const val GL_CONSTANT_ATTENUATION: Int = 4615
    const val GL_LINEAR_ATTENUATION: Int = 4616
    const val GL_QUADRATIC_ATTENUATION: Int = 4617
    const val GL_COMPILE: Int = 4864
    const val GL_COMPILE_AND_EXECUTE: Int = 4865
    const val GL_CLEAR: Int = 5376
    const val GL_AND: Int = 5377
    const val GL_AND_REVERSE: Int = 5378
    const val GL_COPY: Int = 5379
    const val GL_AND_INVERTED: Int = 5380
    const val GL_NOOP: Int = 5381
    const val GL_XOR: Int = 5382
    const val GL_OR: Int = 5383
    const val GL_NOR: Int = 5384
    const val GL_EQUIV: Int = 5385
    const val GL_INVERT: Int = 5386
    const val GL_OR_REVERSE: Int = 5387
    const val GL_COPY_INVERTED: Int = 5388
    const val GL_OR_INVERTED: Int = 5389
    const val GL_NAND: Int = 5390
    const val GL_SET: Int = 5391
    const val GL_EMISSION: Int = 5632
    const val GL_SHININESS: Int = 5633
    const val GL_AMBIENT_AND_DIFFUSE: Int = 5634
    const val GL_COLOR_INDEXES: Int = 5635
    const val GL_MODELVIEW: Int = 5888
    const val GL_PROJECTION: Int = 5889
    const val GL_TEXTURE: Int = 5890
    const val GL_COLOR: Int = 6144
    const val GL_DEPTH: Int = 6145
    const val GL_STENCIL: Int = 6146
    const val GL_COLOR_INDEX: Int = 6400
    const val GL_STENCIL_INDEX: Int = 6401
    const val GL_DEPTH_COMPONENT: Int = 6402
    const val GL_RED: Int = 6403
    const val GL_GREEN: Int = 6404
    const val GL_BLUE: Int = 6405
    const val GL_ALPHA: Int = 6406
    const val GL_RGB: Int = 6407
    const val GL_RGBA: Int = 6408
    const val GL_LUMINANCE: Int = 6409
    const val GL_LUMINANCE_ALPHA: Int = 6410
    const val GL_BITMAP: Int = 6656
    const val GL_POINT: Int = 6912
    const val GL_LINE: Int = 6913
    const val GL_FILL: Int = 6914
    const val GL_RENDER: Int = 7168
    const val GL_FEEDBACK: Int = 7169
    const val GL_SELECT: Int = 7170
    const val GL_FLAT: Int = 7424
    const val GL_SMOOTH: Int = 7425
    const val GL_KEEP: Int = 7680
    const val GL_REPLACE: Int = 7681
    const val GL_INCR: Int = 7682
    const val GL_DECR: Int = 7683
    const val GL_VENDOR: Int = 7936
    const val GL_RENDERER: Int = 7937
    const val GL_VERSION: Int = 7938
    const val GL_EXTENSIONS: Int = 7939
    const val GL_S: Int = 8192
    const val GL_T: Int = 8193
    const val GL_R: Int = 8194
    const val GL_Q: Int = 8195
    const val GL_MODULATE: Int = 8448
    const val GL_DECAL: Int = 8449
    const val GL_TEXTURE_ENV_MODE: Int = 8704
    const val GL_TEXTURE_ENV_COLOR: Int = 8705
    const val GL_TEXTURE_ENV: Int = 8960
    const val GL_EYE_LINEAR: Int = 9216
    const val GL_OBJECT_LINEAR: Int = 9217
    const val GL_SPHERE_MAP: Int = 9218
    const val GL_TEXTURE_GEN_MODE: Int = 9472
    const val GL_OBJECT_PLANE: Int = 9473
    const val GL_EYE_PLANE: Int = 9474
    const val GL_NEAREST: Int = 9728
    const val GL_LINEAR: Int = 9729
    const val GL_NEAREST_MIPMAP_NEAREST: Int = 9984
    const val GL_LINEAR_MIPMAP_NEAREST: Int = 9985
    const val GL_NEAREST_MIPMAP_LINEAR: Int = 9986
    const val GL_LINEAR_MIPMAP_LINEAR: Int = 9987
    const val GL_TEXTURE_MAG_FILTER: Int = 10240
    const val GL_TEXTURE_MIN_FILTER: Int = 10241
    const val GL_TEXTURE_WRAP_S: Int = 10242
    const val GL_TEXTURE_WRAP_T: Int = 10243
    const val GL_CLAMP: Int = 10496
    const val GL_REPEAT: Int = 10497
    const val GL_CLIENT_PIXEL_STORE_BIT: Int = 1
    const val GL_CLIENT_VERTEX_ARRAY_BIT: Int = 2
    const val GL_CLIENT_ALL_ATTRIB_BITS: Int = -1
    const val GL_POLYGON_OFFSET_FACTOR: Int = 32824
    const val GL_POLYGON_OFFSET_UNITS: Int = 10752
    const val GL_POLYGON_OFFSET_POINT: Int = 10753
    const val GL_POLYGON_OFFSET_LINE: Int = 10754
    const val GL_POLYGON_OFFSET_FILL: Int = 32823
    const val GL_ALPHA4: Int = 32827
    const val GL_ALPHA8: Int = 32828
    const val GL_ALPHA12: Int = 32829
    const val GL_ALPHA16: Int = 32830
    const val GL_LUMINANCE4: Int = 32831
    const val GL_LUMINANCE8: Int = 32832
    const val GL_LUMINANCE12: Int = 32833
    const val GL_LUMINANCE16: Int = 32834
    const val GL_LUMINANCE4_ALPHA4: Int = 32835
    const val GL_LUMINANCE6_ALPHA2: Int = 32836
    const val GL_LUMINANCE8_ALPHA8: Int = 32837
    const val GL_LUMINANCE12_ALPHA4: Int = 32838
    const val GL_LUMINANCE12_ALPHA12: Int = 32839
    const val GL_LUMINANCE16_ALPHA16: Int = 32840
    const val GL_INTENSITY: Int = 32841
    const val GL_INTENSITY4: Int = 32842
    const val GL_INTENSITY8: Int = 32843
    const val GL_INTENSITY12: Int = 32844
    const val GL_INTENSITY16: Int = 32845
    const val GL_R3_G3_B2: Int = 10768
    const val GL_RGB4: Int = 32847
    const val GL_RGB5: Int = 32848
    const val GL_RGB8: Int = 32849
    const val GL_RGB10: Int = 32850
    const val GL_RGB12: Int = 32851
    const val GL_RGB16: Int = 32852
    const val GL_RGBA2: Int = 32853
    const val GL_RGBA4: Int = 32854
    const val GL_RGB5_A1: Int = 32855
    const val GL_RGBA8: Int = 32856
    const val GL_RGB10_A2: Int = 32857
    const val GL_RGBA12: Int = 32858
    const val GL_RGBA16: Int = 32859
    const val GL_TEXTURE_RED_SIZE: Int = 32860
    const val GL_TEXTURE_GREEN_SIZE: Int = 32861
    const val GL_TEXTURE_BLUE_SIZE: Int = 32862
    const val GL_TEXTURE_ALPHA_SIZE: Int = 32863
    const val GL_TEXTURE_LUMINANCE_SIZE: Int = 32864
    const val GL_TEXTURE_INTENSITY_SIZE: Int = 32865
    const val GL_PROXY_TEXTURE_1D: Int = 32867
    const val GL_PROXY_TEXTURE_2D: Int = 32868
    const val GL_TEXTURE_PRIORITY: Int = 32870
    const val GL_TEXTURE_RESIDENT: Int = 32871
    const val GL_TEXTURE_BINDING_1D: Int = 32872
    const val GL_TEXTURE_BINDING_2D: Int = 32873
    const val GL_VERTEX_ARRAY: Int = 32884
    const val GL_NORMAL_ARRAY: Int = 32885
    const val GL_COLOR_ARRAY: Int = 32886
    const val GL_INDEX_ARRAY: Int = 32887
    const val GL_TEXTURE_COORD_ARRAY: Int = 32888
    const val GL_EDGE_FLAG_ARRAY: Int = 32889
    const val GL_VERTEX_ARRAY_SIZE: Int = 32890
    const val GL_VERTEX_ARRAY_TYPE: Int = 32891
    const val GL_VERTEX_ARRAY_STRIDE: Int = 32892
    const val GL_NORMAL_ARRAY_TYPE: Int = 32894
    const val GL_NORMAL_ARRAY_STRIDE: Int = 32895
    const val GL_COLOR_ARRAY_SIZE: Int = 32897
    const val GL_COLOR_ARRAY_TYPE: Int = 32898
    const val GL_COLOR_ARRAY_STRIDE: Int = 32899
    const val GL_INDEX_ARRAY_TYPE: Int = 32901
    const val GL_INDEX_ARRAY_STRIDE: Int = 32902
    const val GL_TEXTURE_COORD_ARRAY_SIZE: Int = 32904
    const val GL_TEXTURE_COORD_ARRAY_TYPE: Int = 32905
    const val GL_TEXTURE_COORD_ARRAY_STRIDE: Int = 32906
    const val GL_EDGE_FLAG_ARRAY_STRIDE: Int = 32908
    const val GL_VERTEX_ARRAY_POINTER: Int = 32910
    const val GL_NORMAL_ARRAY_POINTER: Int = 32911
    const val GL_COLOR_ARRAY_POINTER: Int = 32912
    const val GL_INDEX_ARRAY_POINTER: Int = 32913
    const val GL_TEXTURE_COORD_ARRAY_POINTER: Int = 32914
    const val GL_EDGE_FLAG_ARRAY_POINTER: Int = 32915
    const val GL_V2F: Int = 10784
    const val GL_V3F: Int = 10785
    const val GL_C4UB_V2F: Int = 10786
    const val GL_C4UB_V3F: Int = 10787
    const val GL_C3F_V3F: Int = 10788
    const val GL_N3F_V3F: Int = 10789
    const val GL_C4F_N3F_V3F: Int = 10790
    const val GL_T2F_V3F: Int = 10791
    const val GL_T4F_V4F: Int = 10792
    const val GL_T2F_C4UB_V3F: Int = 10793
    const val GL_T2F_C3F_V3F: Int = 10794
    const val GL_T2F_N3F_V3F: Int = 10795
    const val GL_T2F_C4F_N3F_V3F: Int = 10796
    const val GL_T4F_C4F_N3F_V4F: Int = 10797

    fun glEnable(target: Int) = callVoidFunc("glEnable", target)
    fun glDisable(target: Int) = callVoidFunc("glDisable", target)
    fun glAccum(target: Int, data: Float) = callVoidFunc("glAccum", target, data)
    fun glAlphaFunc(target: Int, data: Float) = callVoidFunc("glAlphaFunc", target, data)
    fun glAreTexturesResident(textures: HeapIntArray, residences: HeapBooleanArray) =
        callVoidFunc("glAreTexturesResident", textures.length, textures, residences)

    fun glArrayElement(index: Int) = callVoidFunc("glArrayElement", index)
    fun glBegin(mode: Int) = callVoidFunc("glBegin", mode)
    fun glBindTexture(target: Int, texture: Int) = callVoidFunc("glBindTexture", target, texture)
    fun glBitmap(w: Int, h: Int, xOrig: Float, yOrig: Float, xInc: Float, yInc: Float, data: HeapByteArray) =
        callVoidFunc("glBitmap", w, h, xOrig, yOrig, xInc, yInc, data)
    fun glBlendFunc(sfactor: Int, dfactor: Int) = callVoidFunc("glBlendFunc", sfactor, dfactor)
    fun glCallList(list: Int) = callVoidFunc("glCallList", list)
    fun glCallLists(type: Int, list: IHeapArray<*>) = callVoidFunc("glCallLists", list.length, type, list.ref())
    fun glClear(mask: Int) = callVoidFunc("glClear", mask)
    fun glClearAccum(r: Float, g: Float, b: Float, a: Float) = callVoidFunc("glClearAccum", r, g, b, a)
    fun glClearColor(r: Float, g: Float, b: Float, a: Float) = callVoidFunc("glClearColor", r, g, b, a)
    fun glClearDepth(d: Double) = callVoidFunc("glClearDepth", d)
    fun glClearIndex(c: Float) = callVoidFunc("glClearIndex", c)
    fun glClearStencil(s: Int) = callVoidFunc("glClearStencil", s)
    fun glClipPlane(plane: Int, equation: HeapDoubleArray) = callVoidFunc("glClipPlane", plane, equation)
    fun glColor3b(r: Byte, g: Byte, b: Byte) = callVoidFunc("glColor3b", r, g, b)
    fun glColor3s(r: Short, g: Short, b: Short) = callVoidFunc("glColor3s", r, g, b)
    fun glColor3i(r: Int, g: Int, b: Int) = callVoidFunc("glColor3i", r, g, b)
    fun glColor3f(r: Float, g: Float, b: Float) = callVoidFunc("glColor3f", r, g, b)
    fun glColor3d(r: Double, g: Double, b: Double) = callVoidFunc("glColor3d", r, g, b)
    fun glColor3bv(v: HeapByteArray) = callVoidFunc("glColor3bv", v)
    fun glColor3sv(v: HeapShortArray) = callVoidFunc("glColor3sv", v)
    fun glColor3iv(v: HeapIntArray) = callVoidFunc("glColor3iv", v)
    fun glColor3fv(v: HeapFloatArray) = callVoidFunc("glColor3fv", v)
    fun glColor3dv(v: HeapDoubleArray) = callVoidFunc("glColor3dv", v)
    fun glColor3ubv(v: HeapByteArray) = callVoidFunc("glColor3ubv", v)
    fun glColor3usv(v: HeapShortArray) = callVoidFunc("glColor3usv", v)
    fun glColor3uiv(v: HeapIntArray) = callVoidFunc("glColor3uiv", v)
    fun glColor4b(r: Byte, g: Byte, b: Byte, a: Byte) = callVoidFunc("glColor4b", r, g, b, a)
    fun glColor4s(r: Short, g: Short, b: Short, a: Short) = callVoidFunc("glColor4s", r, g, b, a)
    fun glColor4i(r: Int, g: Int, b: Int, a: Int) = callVoidFunc("glColor4i", r, g, b, a)
    fun glColor4f(r: Float, g: Float, b: Float, a: Float) = callVoidFunc("glColor4f", r, g, b, a)
    fun glColor4d(r: Double, g: Double, b: Double, a: Double) = callVoidFunc("glColor4d", r, g, b, a)
    fun glColor4ub(r: Byte, g: Byte, b: Byte, a: Byte) = callVoidFunc("glColor4ub", r, g, b, a)
    fun glColor4us(r: Short, g: Short, b: Short, a: Short) = callVoidFunc("glColor4us", r, g, b, a)
    fun glColor4ui(r: Int, g: Int, b: Int, a: Int) = callVoidFunc("glColor4ui", r, g, b, a)
    fun glColor4bv(v: HeapByteArray) = callVoidFunc("glColor4bv", v)
    fun glColor4sv(v: HeapShortArray) = callVoidFunc("glColor4sv", v)
    fun glColor4iv(v: HeapIntArray) = callVoidFunc("glColor4iv", v)
    fun glColor4fv(v: HeapFloatArray) = callVoidFunc("glColor4fv", v)
    fun glColor4dv(v: HeapDoubleArray) = callVoidFunc("glColor4dv", v)
    fun glColor4ubv(v: HeapByteArray) = callVoidFunc("glColor4ubv", v)
    fun glColor4usv(v: HeapShortArray) = callVoidFunc("glColor4usv", v)
    fun glColor4uiv(v: HeapIntArray) = callVoidFunc("glColor4uiv", v)
    fun glColorMask(r: Boolean, g: Boolean, b: Boolean, a: Boolean) = callVoidFunc("glColorMask", r, g, b, a)

}