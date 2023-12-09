package com.example.paintkotlin.presenters

import com.example.paintkotlin.controllers.MorphController.Companion.FIRST
import com.example.paintkotlin.controllers.MorphController.Companion.SECOND
import javafx.scene.shape.Polygon
import javafx.scene.shape.Shape

object MorphPresenter {
    fun morphShapes(shapes: HashMap<String, Shape>, sliderValue: Double, isIncrement: Boolean) {
        val firstShapeType = shapes[FIRST]
        val secondShapeType = shapes[SECOND]
        if (firstShapeType is Polygon && secondShapeType is Polygon) {
            morphPolygons(shapes, sliderValue, isIncrement)
        }
    }

    private fun morphPolygons(shapes: HashMap<String, Shape>, sliderValue: Double, isIncrement: Boolean) {
        val morphingShape = shapes[FIRST] as Polygon
        val targetShape = shapes[SECOND] as Polygon
        val increment = if (isIncrement) 1 else -1
        with(morphingShape) {
            val tempPoints = points.toList()
            points.clear()
            points.addAll(tempPoints.zip(targetShape.points) { morphPoint, targetPoint ->
                morphPoint + (((targetPoint - morphPoint) * sliderValue / 10) * increment)
            })
        }
    }
}