package com.primogemstudio.engine.bindings.harfbuzz

import com.primogemstudio.engine.foreign.heap.IHeapObject
import java.lang.foreign.MemorySegment

class hb_language_t(data: MemorySegment) : IHeapObject(data)

object HarfBuzzCommonFuncs {
    const val HB_DIRECTION_INVALID = 0
    const val HB_DIRECTION_LTR = 1
    const val HB_DIRECTION_RTL = 2
    const val HB_DIRECTION_TTB = 3
    const val HB_DIRECTION_BTT = 4
    const val HB_SCRIPT_INVALID = 0
    val HB_SCRIPT_COMMON = HB_TAG('Z', 'y', 'y', 'y')
    val HB_SCRIPT_INHERITED = HB_TAG('Z', 'i', 'n', 'h')
    val HB_SCRIPT_UNKNOWN = HB_TAG('Z', 'z', 'z', 'z')
    val HB_SCRIPT_ARABIC = HB_TAG('A', 'r', 'a', 'b')
    val HB_SCRIPT_ARMENIAN = HB_TAG('A', 'r', 'm', 'n')
    val HB_SCRIPT_BENGALI = HB_TAG('B', 'e', 'n', 'g')
    val HB_SCRIPT_CYRILLIC = HB_TAG('C', 'y', 'r', 'l')
    val HB_SCRIPT_DEVANAGARI = HB_TAG('D', 'e', 'v', 'a')
    val HB_SCRIPT_GEORGIAN = HB_TAG('G', 'e', 'o', 'r')
    val HB_SCRIPT_GREEK = HB_TAG('G', 'r', 'e', 'k')
    val HB_SCRIPT_GUJARATI = HB_TAG('G', 'u', 'j', 'r')
    val HB_SCRIPT_GURMUKHI = HB_TAG('G', 'u', 'r', 'u')
    val HB_SCRIPT_HANGUL = HB_TAG('H', 'a', 'n', 'g')
    val HB_SCRIPT_HAN = HB_TAG('H', 'a', 'n', 'i')
    val HB_SCRIPT_HEBREW = HB_TAG('H', 'e', 'b', 'r')
    val HB_SCRIPT_HIRAGANA = HB_TAG('H', 'i', 'r', 'a')
    val HB_SCRIPT_KANNADA = HB_TAG('K', 'n', 'd', 'a')
    val HB_SCRIPT_KATAKANA = HB_TAG('K', 'a', 'n', 'a')
    val HB_SCRIPT_LAO = HB_TAG('L', 'a', 'o', 'o')
    val HB_SCRIPT_LATIN = HB_TAG('L', 'a', 't', 'n')
    val HB_SCRIPT_MALAYALAM = HB_TAG('M', 'l', 'y', 'm')
    val HB_SCRIPT_ORIYA = HB_TAG('O', 'r', 'y', 'a')
    val HB_SCRIPT_TAMIL = HB_TAG('T', 'a', 'm', 'l')
    val HB_SCRIPT_TELUGU = HB_TAG('T', 'e', 'l', 'u')
    val HB_SCRIPT_THAI = HB_TAG('T', 'h', 'a', 'i')
    val HB_SCRIPT_TIBETAN = HB_TAG('T', 'i', 'b', 't')
    val HB_SCRIPT_BOPOMOFO = HB_TAG('B', 'o', 'p', 'o')
    val HB_SCRIPT_BRAILLE = HB_TAG('B', 'r', 'a', 'i')
    val HB_SCRIPT_CANADIAN_SYLLABICS = HB_TAG('C', 'a', 'n', 's')
    val HB_SCRIPT_CHEROKEE = HB_TAG('C', 'h', 'e', 'r')
    val HB_SCRIPT_ETHIOPIC = HB_TAG('E', 't', 'h', 'i')
    val HB_SCRIPT_KHMER = HB_TAG('K', 'h', 'm', 'r')
    val HB_SCRIPT_MONGOLIAN = HB_TAG('M', 'o', 'n', 'g')
    val HB_SCRIPT_MYANMAR = HB_TAG('M', 'y', 'm', 'r')
    val HB_SCRIPT_OGHAM = HB_TAG('O', 'g', 'a', 'm')
    val HB_SCRIPT_RUNIC = HB_TAG('R', 'u', 'n', 'r')
    val HB_SCRIPT_SINHALA = HB_TAG('S', 'i', 'n', 'h')
    val HB_SCRIPT_SYRIAC = HB_TAG('S', 'y', 'r', 'c')
    val HB_SCRIPT_THAANA = HB_TAG('T', 'h', 'a', 'a')
    val HB_SCRIPT_YI = HB_TAG('Y', 'i', 'i', 'i')
    val HB_SCRIPT_DESERET = HB_TAG('D', 's', 'r', 't')
    val HB_SCRIPT_GOTHIC = HB_TAG('G', 'o', 't', 'h')
    val HB_SCRIPT_OLD_ITALIC = HB_TAG('I', 't', 'a', 'l')
    val HB_SCRIPT_BUHID = HB_TAG('B', 'u', 'h', 'd')
    val HB_SCRIPT_HANUNOO = HB_TAG('H', 'a', 'n', 'o')
    val HB_SCRIPT_TAGALOG = HB_TAG('T', 'g', 'l', 'g')
    val HB_SCRIPT_TAGBANWA = HB_TAG('T', 'a', 'g', 'b')
    val HB_SCRIPT_CYPRIOT = HB_TAG('C', 'p', 'r', 't')
    val HB_SCRIPT_LIMBU = HB_TAG('L', 'i', 'm', 'b')
    val HB_SCRIPT_LINEAR_B = HB_TAG('L', 'i', 'n', 'b')
    val HB_SCRIPT_OSMANYA = HB_TAG('O', 's', 'm', 'a')
    val HB_SCRIPT_SHAVIAN = HB_TAG('S', 'h', 'a', 'w')
    val HB_SCRIPT_TAI_LE = HB_TAG('T', 'a', 'l', 'e')
    val HB_SCRIPT_UGARITIC = HB_TAG('U', 'g', 'a', 'r')
    val HB_SCRIPT_BUGINESE = HB_TAG('B', 'u', 'g', 'i')
    val HB_SCRIPT_COPTIC = HB_TAG('C', 'o', 'p', 't')
    val HB_SCRIPT_GLAGOLITIC = HB_TAG('G', 'l', 'a', 'g')
    val HB_SCRIPT_KHAROSHTHI = HB_TAG('K', 'h', 'a', 'r')
    val HB_SCRIPT_NEW_TAI_LUE = HB_TAG('T', 'a', 'l', 'u')
    val HB_SCRIPT_OLD_PERSIAN = HB_TAG('X', 'p', 'e', 'o')
    val HB_SCRIPT_SYLOTI_NAGRI = HB_TAG('S', 'y', 'l', 'o')
    val HB_SCRIPT_TIFINAGH = HB_TAG('T', 'f', 'n', 'g')
    val HB_SCRIPT_BALINESE = HB_TAG('B', 'a', 'l', 'i')
    val HB_SCRIPT_CUNEIFORM = HB_TAG('X', 's', 'u', 'x')
    val HB_SCRIPT_NKO = HB_TAG('N', 'k', 'o', 'o')
    val HB_SCRIPT_PHAGS_PA = HB_TAG('P', 'h', 'a', 'g')
    val HB_SCRIPT_PHOENICIAN = HB_TAG('P', 'h', 'n', 'x')
    val HB_SCRIPT_CARIAN = HB_TAG('C', 'a', 'r', 'i')
    val HB_SCRIPT_CHAM = HB_TAG('C', 'h', 'a', 'm')
    val HB_SCRIPT_KAYAH_LI = HB_TAG('K', 'a', 'l', 'i')
    val HB_SCRIPT_LEPCHA = HB_TAG('L', 'e', 'p', 'c')
    val HB_SCRIPT_LYCIAN = HB_TAG('L', 'y', 'c', 'i')
    val HB_SCRIPT_LYDIAN = HB_TAG('L', 'y', 'd', 'i')
    val HB_SCRIPT_OL_CHIKI = HB_TAG('O', 'l', 'c', 'k')
    val HB_SCRIPT_REJANG = HB_TAG('R', 'j', 'n', 'g')
    val HB_SCRIPT_SAURASHTRA = HB_TAG('S', 'a', 'u', 'r')
    val HB_SCRIPT_SUNDANESE = HB_TAG('S', 'u', 'n', 'd')
    val HB_SCRIPT_VAI = HB_TAG('V', 'a', 'i', 'i')
    val HB_SCRIPT_AVESTAN = HB_TAG('A', 'v', 's', 't')
    val HB_SCRIPT_BAMUM = HB_TAG('B', 'a', 'm', 'u')
    val HB_SCRIPT_EGYPTIAN_HIEROGLYPHS = HB_TAG('E', 'g', 'y', 'p')
    val HB_SCRIPT_IMPERIAL_ARAMAIC = HB_TAG('A', 'r', 'm', 'i')
    val HB_SCRIPT_INSCRIPTIONAL_PAHLAVI = HB_TAG('P', 'h', 'l', 'i')
    val HB_SCRIPT_INSCRIPTIONAL_PARTHIAN = HB_TAG('P', 'r', 't', 'i')
    val HB_SCRIPT_JAVANESE = HB_TAG('J', 'a', 'v', 'a')
    val HB_SCRIPT_KAITHI = HB_TAG('K', 't', 'h', 'i')
    val HB_SCRIPT_LISU = HB_TAG('L', 'i', 's', 'u')
    val HB_SCRIPT_MEETEI_MAYEK = HB_TAG('M', 't', 'e', 'i')
    val HB_SCRIPT_OLD_SOUTH_ARABIAN = HB_TAG('S', 'a', 'r', 'b')
    val HB_SCRIPT_OLD_TURKIC = HB_TAG('O', 'r', 'k', 'h')
    val HB_SCRIPT_SAMARITAN = HB_TAG('S', 'a', 'm', 'r')
    val HB_SCRIPT_TAI_THAM = HB_TAG('L', 'a', 'n', 'a')
    val HB_SCRIPT_TAI_VIET = HB_TAG('T', 'a', 'v', 't')
    val HB_SCRIPT_BATAK = HB_TAG('B', 'a', 't', 'k')
    val HB_SCRIPT_BRAHMI = HB_TAG('B', 'r', 'a', 'h')
    val HB_SCRIPT_MANDAIC = HB_TAG('M', 'a', 'n', 'd')
    val HB_SCRIPT_CHAKMA = HB_TAG('C', 'a', 'k', 'm')
    val HB_SCRIPT_MEROITIC_CURSIVE = HB_TAG('M', 'e', 'r', 'c')
    val HB_SCRIPT_MEROITIC_HIEROGLYPHS = HB_TAG('M', 'e', 'r', 'o')
    val HB_SCRIPT_MIAO = HB_TAG('P', 'l', 'r', 'd')
    val HB_SCRIPT_SHARADA = HB_TAG('S', 'h', 'r', 'd')
    val HB_SCRIPT_SORA_SOMPENG = HB_TAG('S', 'o', 'r', 'a')
    val HB_SCRIPT_TAKRI = HB_TAG('T', 'a', 'k', 'r')
    val HB_SCRIPT_BASSA_VAH = HB_TAG('B', 'a', 's', 's')
    val HB_SCRIPT_CAUCASIAN_ALBANIAN = HB_TAG('A', 'g', 'h', 'b')
    val HB_SCRIPT_DUPLOYAN = HB_TAG('D', 'u', 'p', 'l')
    val HB_SCRIPT_ELBASAN = HB_TAG('E', 'l', 'b', 'a')
    val HB_SCRIPT_GRANTHA = HB_TAG('G', 'r', 'a', 'n')
    val HB_SCRIPT_KHOJKI = HB_TAG('K', 'h', 'o', 'j')
    val HB_SCRIPT_KHUDAWADI = HB_TAG('S', 'i', 'n', 'd')
    val HB_SCRIPT_LINEAR_A = HB_TAG('L', 'i', 'n', 'a')
    val HB_SCRIPT_MAHAJANI = HB_TAG('M', 'a', 'h', 'j')
    val HB_SCRIPT_MANICHAEAN = HB_TAG('M', 'a', 'n', 'i')
    val HB_SCRIPT_MENDE_KIKAKUI = HB_TAG('M', 'e', 'n', 'd')
    val HB_SCRIPT_MODI = HB_TAG('M', 'o', 'd', 'i')
    val HB_SCRIPT_MRO = HB_TAG('M', 'r', 'o', 'o')
    val HB_SCRIPT_NABATAEAN = HB_TAG('N', 'b', 'a', 't')
    val HB_SCRIPT_OLD_NORTH_ARABIAN = HB_TAG('N', 'a', 'r', 'b')
    val HB_SCRIPT_OLD_PERMIC = HB_TAG('P', 'e', 'r', 'm')
    val HB_SCRIPT_PAHAWH_HMONG = HB_TAG('H', 'm', 'n', 'g')
    val HB_SCRIPT_PALMYRENE = HB_TAG('P', 'a', 'l', 'm')
    val HB_SCRIPT_PAU_CIN_HAU = HB_TAG('P', 'a', 'u', 'c')
    val HB_SCRIPT_PSALTER_PAHLAVI = HB_TAG('P', 'h', 'l', 'p')
    val HB_SCRIPT_SIDDHAM = HB_TAG('S', 'i', 'd', 'd')
    val HB_SCRIPT_TIRHUTA = HB_TAG('T', 'i', 'r', 'h')
    val HB_SCRIPT_WARANG_CITI = HB_TAG('W', 'a', 'r', 'a')
    val HB_SCRIPT_AHOM = HB_TAG('A', 'h', 'o', 'm')
    val HB_SCRIPT_ANATOLIAN_HIEROGLYPHS = HB_TAG('H', 'l', 'u', 'w')
    val HB_SCRIPT_HATRAN = HB_TAG('H', 'a', 't', 'r')
    val HB_SCRIPT_MULTANI = HB_TAG('M', 'u', 'l', 't')
    val HB_SCRIPT_OLD_HUNGARIAN = HB_TAG('H', 'u', 'n', 'g')
    val HB_SCRIPT_SIGNWRITING = HB_TAG('S', 'g', 'n', 'w')
    val HB_SCRIPT_ADLAM = HB_TAG('A', 'd', 'l', 'm')
    val HB_SCRIPT_BHAIKSUKI = HB_TAG('B', 'h', 'k', 's')
    val HB_SCRIPT_MARCHEN = HB_TAG('M', 'a', 'r', 'c')
    val HB_SCRIPT_OSAGE = HB_TAG('O', 's', 'g', 'e')
    val HB_SCRIPT_TANGUT = HB_TAG('T', 'a', 'n', 'g')
    val HB_SCRIPT_NEWA = HB_TAG('N', 'e', 'w', 'a')
    val HB_SCRIPT_MASARAM_GONDI = HB_TAG('G', 'o', 'n', 'm')
    val HB_SCRIPT_NUSHU = HB_TAG('N', 's', 'h', 'u')
    val HB_SCRIPT_SOYOMBO = HB_TAG('S', 'o', 'y', 'o')
    val HB_SCRIPT_ZANABAZAR_SQUARE = HB_TAG('Z', 'a', 'n', 'b')
    val HB_SCRIPT_DOGRA = HB_TAG('D', 'o', 'g', 'r')
    val HB_SCRIPT_GUNJALA_GONDI = HB_TAG('G', 'o', 'n', 'g')
    val HB_SCRIPT_HANIFI_ROHINGYA = HB_TAG('R', 'o', 'h', 'g')
    val HB_SCRIPT_MAKASAR = HB_TAG('M', 'a', 'k', 'a')
    val HB_SCRIPT_MEDEFAIDRIN = HB_TAG('M', 'e', 'd', 'f')
    val HB_SCRIPT_OLD_SOGDIAN = HB_TAG('S', 'o', 'g', 'o')
    val HB_SCRIPT_SOGDIAN = HB_TAG('S', 'o', 'g', 'd')
    val HB_SCRIPT_ELYMAIC = HB_TAG('E', 'l', 'y', 'm')
    val HB_SCRIPT_NANDINAGARI = HB_TAG('N', 'a', 'n', 'd')
    val HB_SCRIPT_NYIAKENG_PUACHUE_HMONG = HB_TAG('H', 'm', 'n', 'p')
    val HB_SCRIPT_WANCHO = HB_TAG('W', 'c', 'h', 'o')
    val HB_SCRIPT_CHORASMIAN = HB_TAG('C', 'h', 'r', 's')
    val HB_SCRIPT_DIVES_AKURU = HB_TAG('D', 'i', 'a', 'k')
    val HB_SCRIPT_KHITAN_SMALL_SCRIPT = HB_TAG('K', 'i', 't', 's')
    val HB_SCRIPT_YEZIDI = HB_TAG('Y', 'e', 'z', 'i')
    val HB_SCRIPT_CYPRO_MINOAN = HB_TAG('C', 'p', 'm', 'n')
    val HB_SCRIPT_OLD_UYGHUR = HB_TAG('O', 'u', 'g', 'r')
    val HB_SCRIPT_TANGSA = HB_TAG('T', 'n', 's', 'a')
    val HB_SCRIPT_TOTO = HB_TAG('T', 'o', 't', 'o')
    val HB_SCRIPT_VITHKUQI = HB_TAG('V', 'i', 't', 'h')
    val HB_SCRIPT_MATH = HB_TAG('Z', 'm', 't', 'h')
    val HB_SCRIPT_KAWI = HB_TAG('K', 'a', 'w', 'i')
    val HB_SCRIPT_NAG_MUNDARI = HB_TAG('N', 'a', 'g', 'm')
    val HB_SCRIPT_GARAY = HB_TAG('G', 'a', 'r', 'a')
    val HB_SCRIPT_GURUNG_KHEMA = HB_TAG('G', 'u', 'k', 'h')
    val HB_SCRIPT_KIRAT_RAI = HB_TAG('K', 'r', 'a', 'i')
    val HB_SCRIPT_OL_ONAL = HB_TAG('O', 'n', 'a', 'o')
    val HB_SCRIPT_SUNUWAR = HB_TAG('S', 'u', 'n', 'u')
    val HB_SCRIPT_TODHRI = HB_TAG('T', 'o', 'd', 'r')
    val HB_SCRIPT_TULU_TIGALARI = HB_TAG('T', 'u', 't', 'g')

    fun HB_TAG(a: Char, b: Char, c: Char, d: Char): Int =
        a.code.shl(24).and(b.code.shl(16).and(c.code.shl(8).and(d.code)))
}