package com.example.paintkotlin

import javafx.scene.shape.Polygon

data class Point(
    val x: Double, val y: Double
)

class Triangle : Polygon()
class Star : Polygon()

//@Serializable
abstract class ShapeDTO(
    val stroke: String,
    val fill: String,
    val strokeWidth: Double,
    var layoutX: Double? = null,
    var layoutY: Double? = null,
)

data class RectangleDto(
    val x: Double,
    val y: Double,
    val width: Double,
    val height: Double,
    val rectangleStroke: String,
    val rectangleFill: String,
    val rectangleStrokeWidth: Double,
) : ShapeDTO(
    stroke = rectangleStroke,
    fill = rectangleFill,
    strokeWidth = rectangleStrokeWidth,
)

open class PolygonDTO(
    val points: MutableList<Double>,
    val polygonStroke: String,
    val polygonFill: String,
    val polygonStrokeWidth: Double,
) : ShapeDTO(
    stroke = polygonStroke,
    fill = polygonFill,
    strokeWidth = polygonStrokeWidth,
)

data class TriangleDTO(
    val trianglePoints: MutableList<Double>,
    val triangleStroke: String,
    val triangleFill: String,
    val triangleStrokeWidth: Double,
) : PolygonDTO(
    points = trianglePoints,
    polygonStroke = triangleStroke,
    polygonFill = triangleFill,
    polygonStrokeWidth = triangleStrokeWidth,
)
data class StarDTO(
    val starPoints: MutableList<Double>,
    val starStroke: String,
    val starFill: String,
    val starStrokeWidth: Double,
) : PolygonDTO(
    points = starPoints,
    polygonStroke = starStroke,
    polygonFill = starFill,
    polygonStrokeWidth = starStrokeWidth,
)

abstract class RoundDTO(
    val centerX: Double,
    val centerY: Double,
    roundStroke: String,
    roundFill: String,
    roundStrokeWidth: Double,
) : ShapeDTO(
    stroke = roundStroke,
    fill = roundFill,
    strokeWidth = roundStrokeWidth,
)

data class CircleDTO(
    val radius: Double,
    val roundStroke: String,
    val roundFill: String,
    val circleStrokeWidth: Double,
    val circleCenterX: Double,
    val circleCenterY: Double
) : RoundDTO(
    roundStroke = roundStroke,
    roundFill = roundFill,
    roundStrokeWidth = circleStrokeWidth,
    centerX = circleCenterX,
    centerY = circleCenterY
)

data class EllipseDTO(
    val radiusX: Double,
    val radiusY: Double,
    val ellipseStroke : String,
    val ellipseFill : String,
    val ellipseStrokeWidth : Double,
    val ellipseCenterX : Double,
    val ellipseCenterY : Double
) : RoundDTO(
    roundStroke = ellipseStroke,
    roundFill = ellipseFill,
    roundStrokeWidth = ellipseStrokeWidth,
    centerX = ellipseCenterX,
    centerY = ellipseCenterY
)

data class LineDTO(
    val startX: Double,
    val startY: Double,
    val endY: Double,
    val endX: Double,
    val lineStroke: String,
    val lineFill: String,
    val lineStrokeWidth: Double,
) : ShapeDTO(
    stroke = lineStroke,
    fill = lineFill,
    strokeWidth = lineStrokeWidth,
)