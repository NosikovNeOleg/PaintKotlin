package com.example.paintkotlin

import javafx.scene.paint.Color
import javafx.scene.shape.Shape
import java.io.*

data class Point(
    val x : Double,
    val y : Double
)


data class ShapeDTO(
    val x : Double,
    val y : Double,
    val startX : Double,
    val startY : Double,
    val endY : Double,
    val endX : Double,
    val stroke : Color,
    val strokeWidth : Double,
    val width : Double,
    val height : Double,
    val points : Array<Double>,
    val fill : Color,
    val centerX : Double,
    val centerY : Double
)

object ShapesSaver {

    private val fileOutputStream = null

    fun save(file: File, input: List<ShapeDTO>) {
        ObjectOutputStream(FileOutputStream(file)).use {
                it.write(input.toString().toByteArray())
        }
    }

    fun load(file: File): ArrayList<Shape> {

        FileInputStream(file).use {
            ObjectInputStream(it).use { o ->
                return o.readObject() as ArrayList<Shape>
            }
        }
    }
}