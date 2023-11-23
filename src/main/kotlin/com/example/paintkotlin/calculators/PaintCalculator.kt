package com.example.paintkotlin.calculators

import com.example.paintkotlin.Point
import com.example.paintkotlin.Star
import com.example.paintkotlin.Triangle
import javafx.scene.shape.*
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt


object PaintCalculator {

    fun calculateShape(shape: Shape, startPoint: Point, x: Double, y: Double) {
        startPoint.let {
            when (shape) {
                is Rectangle -> calculateRectangle(shape, it, x, y)
                is Triangle -> calculateTriangle(shape, it, x, y)
                is Circle -> calculateCircle(shape, it, x, y)
                is Line -> calculateLine(shape, x, y)
                is Star -> calculateStar(shape, it, x, y)
                is Ellipse -> calculateEllipse(shape, it, x, y)
            }
        }
    }

    private fun calculateEllipse(ellipse: Ellipse, startPoint: Point, x: Double, y: Double) {
        with(ellipse) {
            radiusX = abs(x - startPoint.x)
            radiusY = abs(y - startPoint.y)
        }
    }

    private fun calculateStar(star: Star, startPoint: Point, x: Double, y: Double) {
        val width = x - startPoint.x
        val height = y - startPoint.y
        with(star.points) {
            clear()
            addAll(
                listOf(
                    startPoint.x + width / 2,
                    startPoint.y,

                    startPoint.x + width / 5 * 3,
                    startPoint.y + height / 3,

                    x,
                    startPoint.y + height / 3,

                    startPoint.x + width / 20 * 14,
                    startPoint.y + height / 2,

                    startPoint.x + width / 5 * 4,
                    y,

                    startPoint.x + width / 2,
                    startPoint.y + height / 15 * 11,

                    startPoint.x + width / 5,
                    y,

                    startPoint.x + width / 20 * 6,
                    startPoint.y + height / 2,

                    startPoint.x,
                    startPoint.y + height / 3,

                    startPoint.x + width / 5 * 2,
                    startPoint.y + height / 3,
                )
            )
        }
    }

    private fun calculateRectangle(rectangle: Rectangle, startPoint: Point, x: Double, y: Double) {
        val tempWidth = x - startPoint.x
        val tempHeight = y - startPoint.y
        with(rectangle) {
            width = abs(tempWidth)
            layoutX = if (tempWidth > 0) x - width else x

            height = abs(tempHeight)
            layoutY = if (tempHeight > 0) y - height else y
        }
    }

    private fun calculateCircle(circle: Circle, startPoint: Point, x: Double, y: Double) {
        circle.radius = sqrt((x - startPoint.x).pow(2) + (y - startPoint.y).pow(2))
    }

    private fun calculateTriangle(shape: Triangle, startPoint: Point, x: Double, y: Double) {
        with(shape.points) {
            clear()
            addAll(
                listOf(
                    startPoint.x,
                    startPoint.y,
                    x,
                    y,
                    x - (x - startPoint.x) / 2 + (y - startPoint.y) / 2,
                    y - (x - startPoint.x) / 2 - (y - startPoint.y) / 2
                )
            )
        }
    }

    private fun calculateLine(line: Line, x: Double, y: Double) {
        with(line) {
            endX = x
            endY = y
        }
    }
}