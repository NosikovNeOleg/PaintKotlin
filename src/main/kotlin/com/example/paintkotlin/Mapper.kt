package com.example.paintkotlin

import javafx.scene.paint.Paint
import javafx.scene.shape.*

interface Mapper {

}


object ShapeMapper {
    fun mapShapeToDto(shape: Shape): ShapeDTO? {

        return when (shape) {
            is Rectangle -> ShapeDTO(shape.stroke.toString(), shape.fill.toString(), shape.strokeWidth,ShapesNames.RECTANGLE).apply {
                width = shape.width
                layoutX = shape.layoutX
                layoutY = shape.layoutY
                x = shape.x
                y = shape.y
            }

            is Polygon -> ShapeDTO(shape.stroke.toString(), shape.fill.toString(), shape.strokeWidth,ShapesNames.TRIANGLE).apply {
                points = shape.points
            }

            is Circle -> ShapeDTO(shape.stroke.toString(), shape.fill.toString(), shape.strokeWidth,ShapesNames.CIRCLE).apply {
                centerX = shape.centerX
                radius = shape.radius
                centerY = shape.centerY
            }

            is Line -> ShapeDTO(shape.stroke.toString(), shape.fill.toString(), shape.strokeWidth,ShapesNames.LINE).apply {
                startX = shape.startX
                startY = shape.startY
                endX = shape.endX
                endY = shape.endY
            }

            else -> null
        }
    }
    fun mapDtoToShape(shape: ShapeDTO): Shape? {
        return when (shape.name) {
            ShapesNames.LINE -> Line(shape.startX!!, shape.startY!!, shape.endX!!, shape.endY!!).apply { stroke = Paint.valueOf(shape.stroke) }
            ShapesNames.CIRCLE -> Circle(shape.centerX!!, shape.centerY!!, 0.0, Paint.valueOf(shape.fill)).apply { stroke = Paint.valueOf(shape.stroke) }
            ShapesNames.TRIANGLE -> Polygon().apply { points.addAll(shape.points!!); stroke = Paint.valueOf(shape.stroke); fill = Paint.valueOf(shape.fill)  }
            ShapesNames.RECTANGLE -> Rectangle(shape.x!!, shape.y!!).apply { stroke = Paint.valueOf(shape.stroke); fill = Paint.valueOf(shape.fill); width = shape.width!!; height = shape.height!! }
            else -> null
        }
    }
}