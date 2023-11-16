package com.example.paintkotlin

import javafx.scene.paint.Paint
import javafx.scene.shape.*
import java.lang.NullPointerException

interface Mapper {

}


object ShapeMapper {
    fun mapShapeToDto(shape: Shape): ShapeDTO? {

        return when (shape) {
            is Rectangle -> ShapeDTO(
                shape.stroke.toString(),
                shape.fill.toString(),
                shape.strokeWidth,
                ShapesNames.RECTANGLE
            ).apply {
                width = shape.width
                height = shape.height
                x = shape.x
                y = shape.y
            }

            is Triangle -> ShapeDTO(
                shape.stroke.toString(),
                shape.fill.toString(),
                shape.strokeWidth,
                ShapesNames.TRIANGLE
            ).apply {
                points = ArrayList(shape.points)
            }

            is Circle -> ShapeDTO(
                shape.stroke.toString(),
                shape.fill.toString(),
                shape.strokeWidth,
                ShapesNames.CIRCLE
            ).apply {
                centerX = shape.centerX
                radius = shape.radius
                centerY = shape.centerY
            }

            is Ellipse -> ShapeDTO(
                shape.stroke.toString(),
                shape.fill.toString(),
                shape.strokeWidth,
                ShapesNames.ELLIPSE
            ).apply {
                centerX = shape.centerX
                radiusX = shape.radiusX
                radiusY = shape.radiusY
                centerY = shape.centerY
            }

            is Line -> ShapeDTO(
                shape.stroke.toString(),
                shape.fill.toString(),
                shape.strokeWidth,
                ShapesNames.LINE
            ).apply {
                startX = shape.startX
                startY = shape.startY
                endX = shape.endX
                endY = shape.endY
            }

            is Star -> ShapeDTO(
                shape.stroke.toString(),
                shape.fill.toString(),
                shape.strokeWidth,
                ShapesNames.STAR
            ).apply {
                points = ArrayList(shape.points)
            }

            else -> null
        }?.apply {
            this.layoutX = shape.layoutX
            this.layoutY = shape.layoutY
        }
    }

    fun mapDtoToShape(shape: ShapeDTO): Shape {
            return when (shape.name) {
                ShapesNames.LINE -> {
                    Line(
                        shape.startX ?: throw NullPointerException(),
                        shape.startY ?: throw NullPointerException(),
                        shape.endX ?: throw NullPointerException(),
                        shape.endY ?: throw NullPointerException()
                    ).apply {
                        stroke = Paint.valueOf(shape.stroke)
                    }
                }

                ShapesNames.ELLIPSE -> Ellipse(
                    shape.centerX ?: throw NullPointerException(),
                    shape.centerY ?: throw NullPointerException(),
                    shape.radiusX ?: throw NullPointerException(),
                    shape.radiusY ?: throw NullPointerException(),
                ).apply { fill = Paint.valueOf(shape.fill); stroke = Paint.valueOf(shape.stroke) }

                ShapesNames.STAR -> Star().apply {
                    points.addAll(shape.points ?: throw NullPointerException())
                    stroke = Paint.valueOf(shape.stroke)
                    fill = Paint.valueOf(shape.fill)
                }

                ShapesNames.CIRCLE -> Circle(
                    shape.centerX ?: throw NullPointerException(),
                    shape.centerY ?: throw NullPointerException(),
                    shape.radius ?: throw NullPointerException(),
                    Paint.valueOf(shape.fill)
                ).apply { stroke = Paint.valueOf(shape.stroke) }

                ShapesNames.TRIANGLE -> Polygon().apply {
                    points.addAll(shape.points ?: throw NullPointerException())
                    stroke = Paint.valueOf(shape.stroke)
                    fill = Paint.valueOf(shape.fill)
                }

                ShapesNames.RECTANGLE -> Rectangle(
                    shape.x ?: throw NullPointerException(),
                    shape.y ?: throw NullPointerException()
                ).apply {
                    stroke = Paint.valueOf(shape.stroke)
                    fill = Paint.valueOf(shape.fill)
                    width = shape.width?: throw NullPointerException()
                    height = shape.height?: throw NullPointerException()
                }
            }.apply {
                layoutX = shape.layoutX ?: 0.0
                layoutY = shape.layoutY ?: 0.0
            }
    }


}