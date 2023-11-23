package com.example.paintkotlin.dto

import com.example.paintkotlin.Star
import com.example.paintkotlin.Triangle
import javafx.scene.shape.*
import kotlinx.serialization.Serializable

@Serializable
abstract class ShapeDTO private constructor(
    val name: String,
    val stroke: String,
    val fill: String,
    val strokeWidth: Double,
    var layoutX: Double? = null,
    var layoutY: Double? = null
) {
    constructor(shape: Shape) : this(
        stroke = shape.stroke.toString(),
        fill = shape.fill.toString(),
        strokeWidth = shape.strokeWidth,
        name = "ser"
    )
}

class RectangleDTO(rectangle: Rectangle) : ShapeDTO(rectangle) {
    val x: Double = rectangle.x
    val y: Double = rectangle.y
    val width: Double = rectangle.width
    val height: Double = rectangle.height
}

abstract class PolygonDTO(polygon: Polygon) : ShapeDTO(polygon) {
    val points: List<Double> = polygon.points
}

class TriangleDTO(triangle: Triangle) : PolygonDTO(triangle)
class StarDTO(star: Star) : PolygonDTO(star)

abstract class RoundDTO(shape: Shape) : ShapeDTO(shape) {
    abstract val centerX: Double
    abstract val centerY: Double
}

class CircleDTO(circle: Circle) : RoundDTO(circle) {
    override val centerX: Double = circle.centerX
    override val centerY: Double = circle.centerY
    val radius: Double = circle.radius
}

class EllipseDTO(ellipse: Ellipse) : RoundDTO(ellipse) {
    override val centerX: Double = ellipse.centerX
    override val centerY: Double = ellipse.centerY
    val radiusX: Double = ellipse.radiusX
    val radiusY: Double = ellipse.radiusY
}

class LineDTO(line: Line) : ShapeDTO(line) {
    val startX: Double = line.startX
    val startY: Double = line.startY
    val endY: Double = line.endY
    val endX: Double = line.endX
}