package com.example.paintkotlin

import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.scene.shape.Polygon
import javafx.scene.shape.Rectangle
import javafx.scene.shape.Shape
import kotlin.math.pow
import kotlin.math.sqrt

object PaintCalculator {



    fun calculateShape(shape: Shape?, startPoint: Point?, x: Double, y: Double) {
        if (startPoint != null) {
            when (shape) {
                is Rectangle -> calculateRectangle(shape, startPoint, x, y)
                is Polygon -> calculatePolygon(shape, startPoint, x, y)
                is Circle -> calculateCircle(shape, startPoint, x, y)
                is Line -> calculateLine(shape, x, y)
            }
        }
    }

    private fun calculateRectangle(rectangle: Rectangle, startPoint: Point, x: Double, y: Double) {
        val tempWidth = x - startPoint.x
        val tempHeight = y - startPoint.y
        with(rectangle) {
        if (tempWidth > 0) {
            width = tempWidth
            layoutX = x - width
        } else {
            width = -tempWidth
            layoutX = x
        }

        if (tempHeight > 0) {
            height = tempHeight
            layoutY = y - height
        } else {
            height = -tempHeight
            layoutY = y
        }
        }
    }
    private fun calculateCircle(circle: Circle, startPoint: Point, x: Double, y: Double) {
        circle.radius = sqrt((x - startPoint.x).pow(2) + (y - startPoint.y).pow(2))
    }

    private fun calculatePolygon(shape: Polygon, startPoint: Point, x: Double, y: Double) {
            with(shape) {
                points.clear()
                points.addAll(
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
        with(line){
            endX = x
            endY = y
        }
    }
}