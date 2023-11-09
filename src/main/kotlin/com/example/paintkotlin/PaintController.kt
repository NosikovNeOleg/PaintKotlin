package com.example.paintkotlin

import javafx.fxml.FXML
import javafx.scene.control.ColorPicker
import javafx.scene.control.ComboBox
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.scene.shape.Polygon
import javafx.scene.shape.Rectangle
import javafx.scene.shape.Shape
import kotlin.math.pow
import kotlin.math.sqrt

class PaintController {

    @FXML
    private lateinit var shapesBox: ComboBox<String>
    @FXML
    private lateinit var fillColorBox: ColorPicker
    @FXML
    private lateinit var strokeColorBox : ColorPicker
    @FXML
    private lateinit var paintField : Pane

    private lateinit var shape : Shape
    private lateinit var startPoint : Point

    private var isDrawing : Boolean = false

    private val calculator : PaintCalculator = PaintCalculator()


    @FXML
    private fun onPaintFieldMousePressed(mouseEvent: MouseEvent) {
        if (!isDrawing) {
            isDrawing = true
            val x: Double = mouseEvent.x
            val y: Double = mouseEvent.y
            startPoint = Point(x,y)
             when (shapesBox.value) {
                ShapesNames.LINE.getLabel() -> shape = Line(x,y,x,y)
                ShapesNames.CIRCLE.getLabel() -> shape = Circle(x, y, 0.0, fillColorBox.value)
                ShapesNames.TRIANGLE.getLabel() -> shape = Polygon()
                ShapesNames.RECTANGLE.getLabel() -> shape = Rectangle(0.0,0.0)
                else -> println("Wrong type of shape")
            }
            shape.fill = fillColorBox.value
            shape.stroke = strokeColorBox.value
            paintField.children.add(shape)
        }
    }

    @FXML
    private fun onPaintFieldMouseDragged(mouseEvent: MouseEvent) {
        if (isDrawing) {
            val x: Double = mouseEvent.x
            val y: Double = mouseEvent.y
            if (paintField.isHover){
            when (shapesBox.value) {
                ShapesNames.LINE.getLabel() -> {
                    calculator.calculateLine(shape as Line, x, y)
                }

                ShapesNames.CIRCLE.getLabel() -> {
                    calculator.calculateCircle(shape as Circle, startPoint, x, y)
                }

                ShapesNames.RECTANGLE.getLabel() -> {
                    calculator.calculateRectangle(shape as Rectangle, startPoint, x, y)
                }

                ShapesNames.TRIANGLE.getLabel() -> {
                    calculator.calculateTriangle(shape as Polygon,startPoint, x,y)
                }
            }
            }
        }
    }

    @FXML
    private fun onPaintFieldMouseReleased() {
        if (isDrawing) {
            isDrawing = false
        }
    }


    @FXML
    private fun initialize() {
        shapesBox.items.addAll(
            ShapesNames.values()
                .map { value -> value.getLabel() }
        )
    }
}