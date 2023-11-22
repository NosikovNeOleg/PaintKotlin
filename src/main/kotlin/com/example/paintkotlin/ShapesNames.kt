package com.example.paintkotlin

enum class ShapesNames(private val label: String) {

    LINE("Линия"),
    RECTANGLE("Прямоугольник"),
    TRIANGLE("Треугольник"),
    STAR("Звезда"),
    ELLIPSE("Эллипс"),
    CIRCLE("Круг");

    override fun toString(): String {
        return label
    }

}