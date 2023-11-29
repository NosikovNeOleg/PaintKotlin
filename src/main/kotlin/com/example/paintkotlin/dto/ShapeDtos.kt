@file:OptIn(ExperimentalSerializationApi::class)

package com.example.paintkotlin.dto

import com.example.paintkotlin.EmptyShape
import com.example.paintkotlin.Star
import com.example.paintkotlin.Triangle
import javafx.scene.shape.*
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


@Serializable
sealed class ShapeDTO private constructor(
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
    @EncodeDefault
    val x: Double = rectangle.x

    @EncodeDefault
    val y: Double = rectangle.y

    @EncodeDefault
    val width: Double = rectangle.width

    @EncodeDefault
    val height: Double = rectangle.height

}

@Serializable
sealed class PolygonDTO(@Transient var polygon: Polygon = Polygon()) : ShapeDTO(polygon) {
    @EncodeDefault
    val points: List<Double> = polygon.points
}

@Serializable
class TriangleDTO(@Transient var triangle: Triangle = Triangle()) : PolygonDTO(triangle)

@Serializable
class StarDTO(@Transient var star: Star = Star()) : PolygonDTO(star)

@Serializable
sealed class RoundDTO(
    @Transient var shape: Shape = EmptyShape(),
) : ShapeDTO(shape) {
    @EncodeDefault
    abstract val centerX: Double

    @EncodeDefault
    abstract val centerY: Double
}

@Serializable
class CircleDTO(@Transient var circle: Circle = Circle()) : RoundDTO(circle) {
    override val centerX: Double = circle.centerX
    override val centerY: Double = circle.centerY
    val radius: Double = circle.radius
}

@Serializable
class EllipseDTO constructor(
    @Transient var ellipse: Ellipse = Ellipse(),
    override val centerX: Double = ellipse.centerX,
    override val centerY: Double = ellipse.centerY,

    ) : RoundDTO(ellipse) {
    @EncodeDefault
    val radiusX: Double = ellipse.radiusX

    @EncodeDefault
    val radiusY: Double = ellipse.radiusY
}

@Serializable
class LineDTO(
    @Transient var line: Line = Line()
) : ShapeDTO(line) {
    @EncodeDefault
    val startX: Double = line.startX

    @EncodeDefault
    val startY: Double = line.startY

    @EncodeDefault
    val endY: Double = line.endY

    @EncodeDefault
    val endX: Double = line.endX
}
