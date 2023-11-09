package com.example.paintkotlin


import javafx.scene.shape.Shape


class Triangle(
    val firstPoint : Point
) : Shape() {
    lateinit var secondPoint : Point
    lateinit var thirdPoint : Point
}