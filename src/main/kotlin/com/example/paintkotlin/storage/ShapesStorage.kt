package com.example.paintkotlin.storage

import javafx.scene.shape.Shape

object ShapesStorage {

    private val shapes : List<Shape> by lazy {
        ArrayList()
    }

    fun addShape(shape : Shape) {

    }

    fun addShapes(shapes : List<Shape>)

    fun getAllShapes() : List<Shape> {
        return shapes
    }
}
