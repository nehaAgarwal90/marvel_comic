package com.example.color_palette.data.model

data class Point(val x : Float, val y : Float) {
    operator fun plus(point: Point): Point {
        return Point(this.x + point.x, this.y + point.y)
    }
}