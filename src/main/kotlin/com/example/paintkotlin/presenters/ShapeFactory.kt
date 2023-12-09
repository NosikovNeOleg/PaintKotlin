package com.example.paintkotlin.presenters

import com.example.paintkotlin.Point
import com.example.paintkotlin.ShapesNames
import com.example.paintkotlin.Star
import com.example.paintkotlin.Triangle
import javafx.scene.paint.Color
import javafx.scene.shape.*


interface ShapeFactory {

    fun getShape(shapeName: ShapesNames, startPoint : Point, color : Color) : Shape
}

fun shapeFactory() : ShapeFactory = ShapeFactoryImpl()

private class ShapeFactoryImpl : ShapeFactory {
    override fun getShape(shapeName: ShapesNames, startPoint : Point, color : Color): Shape =
        when (shapeName) {
            ShapesNames.LINE -> Line(startPoint.x, startPoint.y, startPoint.x, startPoint.y)
            ShapesNames.CIRCLE -> Circle(startPoint.x, startPoint.y, 0.0, color)
            ShapesNames.TRIANGLE -> Triangle()
            ShapesNames.RECTANGLE -> Rectangle(0.0, 0.0)
            ShapesNames.STAR -> Star()
            ShapesNames.ELLIPSE -> Ellipse(startPoint.x, startPoint.y, 0.0, 0.0)
        }
}