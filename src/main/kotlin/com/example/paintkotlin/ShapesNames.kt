package com.example.paintkotlin

enum class ShapesNames(private val label: String) {

    LINE("Линия"),
    RECTANGLE("Прямоугольник"),
    TRIANGLE("Треугольник"),
    STAR("Звезда"),
    CIRCLE("Круг");

    open fun getLabel() = label

}