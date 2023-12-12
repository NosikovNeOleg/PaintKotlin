package com.example.paintkotlin.storage

import javafx.scene.shape.Shape


object ShapesStorage {

    fun getStorage() = this

    private val shapes by lazy {
        ArrayList<Shape>()
    }

    fun addShape(shape: Shape?) {
        if (shape != null) {
            shapes.add(shape)
        }
    }

    fun addShapes(shapes: List<Shape>) {
        shapes.forEach(this::addShape)
    }

    fun getAllShapes(): List<Shape> {
        return shapes
    }
}


