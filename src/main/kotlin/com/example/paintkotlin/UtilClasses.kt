package com.example.paintkotlin

import javafx.scene.shape.Polygon
import javafx.scene.shape.Rectangle
import javafx.scene.shape.Shape

data class Point(
    val x: Double, val y: Double
)

class Triangle : Polygon()
class Star : Polygon()

//@Serializable
abstract class ShapeDTO(
    open val stroke: String,
    open val fill: String,
    open val strokeWidth: Double,
    var layoutX: Double? = null,
    var layoutY: Double? = null,
) {
    constructor(shape : Shape) : this(
        shape.stroke.toString(),
        shape.fill.toString(),
        shape.strokeWidth
    )
}

data class RectangleDTO(
    val x: Double,
    val y: Double,
    val width: Double,
    val height: Double,
    override val stroke: String,
    override val fill: String,
    override val strokeWidth: Double,
) : ShapeDTO(
    stroke = stroke,
    fill = fill,
    strokeWidth = strokeWidth,
) {
    constructor(shape: Rectangle) : this(
        x = shape.x,
        y = shape.y,
        width = shape.width,
        height = shape.height,
    )
}

open class PolygonDTO(
    open val points: MutableList<Double>,
    override val stroke: String,
    override val fill: String,
    override val strokeWidth: Double,
) : ShapeDTO(
    stroke = stroke,
    fill = fill,
    strokeWidth = strokeWidth,
)

data class TriangleDTO(
    override val points: MutableList<Double>,
    override val stroke: String,
    override val fill: String,
    override val strokeWidth: Double,
) : PolygonDTO(
    points = points,
    stroke = stroke,
    fill = fill,
    strokeWidth = strokeWidth,
)
data class StarDTO(
    override val points: MutableList<Double>,
    override val stroke: String,
    override val fill: String,
    override val strokeWidth: Double,
) : PolygonDTO(
    points = points,
    stroke = stroke,
    fill = fill,
    strokeWidth = strokeWidth,
)

abstract class RoundDTO(
    open val centerX: Double,
    open val centerY: Double,
    override val stroke: String,
    override val fill: String,
    override val strokeWidth: Double,
) : ShapeDTO(
    stroke = stroke,
    fill = fill,
    strokeWidth = strokeWidth,
)

data class CircleDTO(
    val radius: Double,
    override val stroke: String,
    override val fill: String,
    override val strokeWidth: Double,
    override val centerX: Double,
    override val centerY: Double
) : RoundDTO(
    stroke = stroke,
    fill = fill,
    strokeWidth = strokeWidth,
    centerX = centerX,
    centerY = centerY
)

data class EllipseDTO(
    val radiusX: Double,
    val radiusY: Double,
    override val stroke: String,
    override val fill: String,
    override val strokeWidth: Double,
    override val centerX: Double,
    override val centerY: Double
) : RoundDTO(
    stroke = stroke,
    fill = fill,
    strokeWidth = strokeWidth,
    centerX = centerX,
    centerY = centerY
)

data class LineDTO(
    val startX: Double,
    val startY: Double,
    val endY: Double,
    val endX: Double,
    override val stroke: String,
    override val fill: String,
    override val strokeWidth: Double,
) : ShapeDTO(
    stroke = stroke,
    fill = fill,
    strokeWidth = strokeWidth,
)