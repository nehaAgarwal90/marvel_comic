package com.example.color_palette.data.model

data class ColorInfoModel(
    val XYZ: XYZ,
    val cmyk: Cmyk,
    val hex: Hex,
    val hsl: Hsl,
    val hsv: Hsv,
    val image: Image,
    val name: Name,
    val rgb: Rgb
)

data class Rgb(
    val b: Int,
    val g: Int,
    val r: Int,
)

data class Hsv(
    val h: Int,
    val s: Int,
    val v: Int,
)

data class XYZ(
    val X: Int,
    val Y: Int,
    val Z: Int
)

data class Cmyk(
    val c: Int,
    val k: Int,
    val m: Int,
    val y: Int
)

data class Hex(
    val clean: String,
    val value: String
)

data class Hsl(
    val h: Int,
    val l: Int,
    val s: Int,
)

data class Name(
    val closest_named_hex: String,
    val distance: Int,
    val exact_match_name: Boolean,
    val value: String
)

data class Image(
    val bare: String,
    val named: String
)