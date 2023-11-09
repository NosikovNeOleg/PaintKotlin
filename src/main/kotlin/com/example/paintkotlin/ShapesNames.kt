package com.example.paintkotlin

enum class ShapesNames(private val label: String) {

    LINE("Линия"),
    RECTANGLE("Прямоугольник"),
    TRIANGLE("Треугольник"),
    CIRCLE("Круг");

    open fun getLabel() = label

}