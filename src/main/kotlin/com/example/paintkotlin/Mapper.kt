package com.example.paintkotlin

import javafx.scene.paint.Paint
import javafx.scene.shape.*
import java.lang.NullPointerException

interface Mapper<T, E> {
    fun mapToDto(input: T): E?
    fun mapFromDto(input: E): T?
}


object ShapeMapper : Mapper<Shape, ShapeDTO> {
    override fun mapToDto(input: Shape): ShapeDTO? {

        return when (input) {
            is Rectangle -> ShapeDTO(
                input.stroke.toString(),
                input.fill.toString(),
                input.strokeWidth,
                ShapesNames.RECTANGLE
            ).apply {
                width = input.width
                height = input.height
                x = input.x
                y = input.y
            }

            is Triangle -> ShapeDTO(
                input.stroke.toString(),
                input.fill.toString(),
                input.strokeWidth,
                ShapesNames.TRIANGLE
            ).apply {
                points = ArrayList(input.points)
            }

            is Circle -> ShapeDTO(
                input.stroke.toString(),
                input.fill.toString(),
                input.strokeWidth,
                ShapesNames.CIRCLE
            ).apply {
                centerX = input.centerX
                radius = input.radius
                centerY = input.centerY
            }

            is Ellipse -> ShapeDTO(
                input.stroke.toString(),
                input.fill.toString(),
                input.strokeWidth,
                ShapesNames.ELLIPSE
            ).apply {
                centerX = input.centerX
                radiusX = input.radiusX
                radiusY = input.radiusY
                centerY = input.centerY
            }

            is Line -> ShapeDTO(
                input.stroke.toString(),
                input.fill.toString(),
                input.strokeWidth,
                ShapesNames.LINE
            ).apply {
                startX = input.startX
                startY = input.startY
                endX = input.endX
                endY = input.endY
            }

            is Star -> ShapeDTO(
                input.stroke.toString(),
                input.fill.toString(),
                input.strokeWidth,
                ShapesNames.STAR
            ).apply {
                points = ArrayList(input.points)
            }

            else -> null
        }?.apply {
            this.layoutX = input.layoutX
            this.layoutY = input.layoutY
        }
    }

    override fun mapFromDto(input: ShapeDTO): Shape {
        return when (input.name) {
            ShapesNames.LINE -> {
                Line(
                    input.startX ?: throw NullPointerException(),
                    input.startY ?: throw NullPointerException(),
                    input.endX ?: throw NullPointerException(),
                    input.endY ?: throw NullPointerException()
                ).apply {
                    stroke = Paint.valueOf(input.stroke)
                }
            }

            ShapesNames.ELLIPSE -> Ellipse(
                input.centerX ?: throw NullPointerException(),
                input.centerY ?: throw NullPointerException(),
                input.radiusX ?: throw NullPointerException(),
                input.radiusY ?: throw NullPointerException(),
            ).apply { fill = Paint.valueOf(input.fill); stroke = Paint.valueOf(input.stroke) }

            ShapesNames.STAR -> Star().apply {
                points.addAll(input.points ?: throw NullPointerException())
                stroke = Paint.valueOf(input.stroke)
                fill = Paint.valueOf(input.fill)
            }

            ShapesNames.CIRCLE -> Circle(
                input.centerX ?: throw NullPointerException(),
                input.centerY ?: throw NullPointerException(),
                input.radius ?: throw NullPointerException(),
                Paint.valueOf(input.fill)
            ).apply { stroke = Paint.valueOf(input.stroke) }

            ShapesNames.TRIANGLE -> Polygon().apply {
                points.addAll(input.points ?: throw NullPointerException())
                stroke = Paint.valueOf(input.stroke)
                fill = Paint.valueOf(input.fill)
            }

            ShapesNames.RECTANGLE -> Rectangle(
                input.x ?: throw NullPointerException(),
                input.y ?: throw NullPointerException()
            ).apply {
                stroke = Paint.valueOf(input.stroke)
                fill = Paint.valueOf(input.fill)
                width = input.width ?: throw NullPointerException()
                height = input.height ?: throw NullPointerException()
            }
        }.apply {
            layoutX = input.layoutX ?: 0.0
            layoutY = input.layoutY ?: 0.0
        }
    }


}