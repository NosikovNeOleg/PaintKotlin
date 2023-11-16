package com.example.paintkotlin

import javafx.collections.ObservableList
import javafx.scene.shape.Polygon
import kotlinx.serialization.Serializable

data class Point(
    val x : Double,
    val y : Double
)


class Triangle : Polygon()

class Star : Polygon()

@Serializable
data class ShapeDTO (
    val stroke : String,
    val fill : String,
    val strokeWidth : Double,
    val name : ShapesNames
) {
    var x : Double? = null
    var y : Double? = null
    var startX : Double? = null
    var startY : Double? = null
    var endY : Double? = null
    var endX : Double? = null
    var width : Double? = null
    var height : Double? = null
    var points : ObservableList<Double>? = null
    var centerX : Double? = null
    var centerY : Double? = null
    var layoutX : Double? = null
    var layoutY : Double? = null
    var radius : Double? = null
}
