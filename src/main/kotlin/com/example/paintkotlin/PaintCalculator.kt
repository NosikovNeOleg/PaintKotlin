package com.example.paintkotlin

import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.scene.shape.Polygon
import javafx.scene.shape.Rectangle
import kotlin.math.pow
import kotlin.math.sqrt

class PaintCalculator {

    fun calculateRectangle(rectangle: Rectangle, startPoint : Point, x : Double, y : Double) {
        val tempWidth = x - startPoint.x
        val tempHeight = y - startPoint.y
        if (tempWidth > 0) {
            rectangle.width = tempWidth
            rectangle.layoutX = x - rectangle.width
        } else {
            rectangle.width = -tempWidth
            rectangle.layoutX = x
        }

        if (tempHeight > 0) {
            rectangle.height = tempHeight
            rectangle.layoutY = y - rectangle.height
        } else {
            rectangle.height = -tempHeight
            rectangle.layoutY = y
        }
    }

    fun calculateCircle(circle: Circle, startPoint: Point, x: Double, y: Double) {
        circle.radius = sqrt((x - startPoint.x).pow(2) + (y - startPoint.y).pow(2))
    }

    fun calculateTriangle(triangle : Polygon, startPoint: Point, x: Double, y: Double) {
        triangle.points.clear()
            triangle.points.addAll(
                listOf(
                    startPoint.x, startPoint.y,
                    x, y,
                    x - (x - startPoint.x) / 2 + (y - startPoint.y) / 2, y - (x - startPoint.x) / 2 - (y - startPoint.y) / 2
                )
            )

    }

    fun calculateLine(line : Line, x: Double, y: Double) {
        line.endX = x
        line.endY = y
    }
}