package com.example.paintkotlin.presenters

import com.example.paintkotlin.controllers.MorphController.Companion.FIRST
import com.example.paintkotlin.controllers.MorphController.Companion.SECOND
import javafx.scene.shape.Polygon
import javafx.scene.shape.Shape

object MorphPresenter {
    fun morphShapes(shapes: HashMap<String, Shape>, sliderValue: Double, increment: Int) {
        val firstShapeType = shapes[FIRST]
        val secondShapeType = shapes[SECOND]
        if (firstShapeType is Polygon && secondShapeType is Polygon) {
            morphPolygons(shapes, sliderValue, increment)
        }
    }

    private fun morphPolygons(shapes: HashMap<String, Shape>, sliderValue: Double, increment: Int) {
        val morphingShape = shapes[FIRST] as Polygon
        val targetShape = shapes[SECOND] as Polygon
        with(morphingShape) {
            val tempPoints = points.toList()
            points.clear()
            points.addAll(tempPoints.zip(targetShape.points) { morphPoint, targetPoint ->
                morphPoint + (((targetPoint - morphPoint) * sliderValue / 10) * increment)
            })
        }
    }
}