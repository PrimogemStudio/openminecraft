package com.primogemstudio.engine.types

data class Version(
    var version: Long
) : Comparable<Version> {
    companion object {
        fun fromStandard(major: UShort, minor: UShort, patch: UShort, ext: UShort): Version =
            Version((major.toLong() shl 48) + (minor.toLong() shl 32) + (patch.toLong() shl 16) + ext.toLong())

        fun from(major: UShort, minor: UShort, patch: UShort): Version = fromStandard(major, minor, patch, 0.toUShort())

        val MAX_VALUE = fromStandard(UShort.MAX_VALUE, UShort.MAX_VALUE, UShort.MAX_VALUE, UShort.MAX_VALUE)
        val MIN_VALUE = fromStandard(UShort.MIN_VALUE, UShort.MIN_VALUE, UShort.MIN_VALUE, UShort.MIN_VALUE)
    }

    override fun compareTo(other: Version): Int {
        if (version > other.version) return 1
        if (version < other.version) return -1
        return 0
    }

    override fun toString(): String = listOf(
        version.shr(48).and(0xFFFF),
        version.shr(32).and(0xFFFF),
        version.shr(16).and(0xFFFF),
        version.shr(0).and(0xFFFF)
    ).joinToString(".")
}
