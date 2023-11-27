package com.example.paintkotlin.dto

import com.example.paintkotlin.EmptyShape
import com.example.paintkotlin.Star
import com.example.paintkotlin.Triangle
import javafx.scene.shape.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


@Serializable
sealed class ShapeDTO private constructor (
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
    )
}

@Serializable
class RectangleDTO(@Transient var rectangle: Rectangle = Rectangle()) : ShapeDTO(rectangle) {
    val x: Double = rectangle.x
    val y: Double = rectangle.y
    val width: Double = rectangle.width
    val height: Double = rectangle.height

}
@Serializable
sealed class PolygonDTO(@Transient var polygon: Polygon = Polygon()) : ShapeDTO(polygon) {
    val points: List<Double> = polygon.points
}
@Serializable
class TriangleDTO(@Transient var triangle: Triangle = Triangle()) : PolygonDTO(triangle)
@Serializable
class StarDTO(@Transient var star: Star = Star()) : PolygonDTO(star)

@Serializable
sealed class RoundDTO(@Transient var shape: Shape = EmptyShape()) : ShapeDTO(shape) {
    abstract val centerX: Double
    abstract val centerY: Double
}
@Serializable
class CircleDTO(@Transient var circle: Circle = Circle()) : RoundDTO(circle) {
    override val centerX: Double = circle.centerX
    override val centerY: Double = circle.centerY
    val radius: Double = circle.radius
}
@Serializable
class EllipseDTO(@Transient var ellipse: Ellipse = Ellipse()) : RoundDTO(ellipse) {
    override val centerX: Double = ellipse.centerX
    override val centerY: Double = ellipse.centerY
    val radiusX: Double = ellipse.radiusX
    val radiusY: Double = ellipse.radiusY
}
@Serializable
class LineDTO(@Transient var line: Line = Line()) : ShapeDTO(line) {
    val startX: Double = line.startX
    val startY: Double = line.startY
    val endY: Double = line.endY
    val endX: Double = line.endX
}
