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
import com.example.paintkotlin.PaintCalculator.calculateShape
import javafx.event.EventHandler
import javafx.scene.Cursor
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.shape.MoveTo

class PaintController {


    @FXML
    private var shapesBox: ComboBox<String>? = null
    @FXML
    private var instrumentsBox: ComboBox<String>? = null
    @FXML
    private var fillColorBox: ColorPicker? = null
    @FXML
    private var strokeColorBox : ColorPicker? = null
    @FXML
    private var labelsBox: HBox? = null
    @FXML
    private var controlsBox: HBox? = null
    @FXML
    private var paintField : Pane? = null

    private var shape : Shape? = null
    private var startPoint : Point? = null

    private var isDrawing : Boolean = false


    @FXML
    private fun onPaintFieldMousePressed(mouseEvent: MouseEvent) {
        if (!isDrawing && instrumentsBox?.value == FIGURE) {
            isDrawing = true
            val x = mouseEvent.x
            val y = mouseEvent.y
            startPoint = Point(x,y)
            when (shapesBox?.value) {
                ShapesNames.LINE.getLabel() -> shape = Line(x,y,x,y)
                ShapesNames.CIRCLE.getLabel() -> shape = Circle(x, y, 0.0, fillColorBox?.value)
                ShapesNames.TRIANGLE.getLabel() -> shape = Polygon()
                ShapesNames.RECTANGLE.getLabel() -> shape = Rectangle(0.0,0.0)
                ShapesNames.STAR.getLabel() -> shape = Polygon()
                else -> println("Wrong type of shape")
            }

            with (shape) {
                this?.fill = fillColorBox?.value
                this?.stroke = strokeColorBox?.value
                paintField?.children?.add(shape)
            }
        }
    }

    @FXML
    private fun onPaintFieldMouseDragged(mouseEvent: MouseEvent) {
        if (isDrawing) {
            val x = mouseEvent.x
            val y = mouseEvent.y
            if (paintField?.isHover == true){
                calculateShape(shape,startPoint,x,y)
            }
        }
    }

    @FXML
    private fun onPaintFieldMouseReleased() {
        if (isDrawing) {
            with (shape) {
                if (this != null) {
                    addEventHandler(MouseEvent.MOUSE_ENTERED, EventHandler {
                        cursor = Cursor.HAND
                    })
                    addEventHandler(MouseEvent.MOUSE_PRESSED, EventHandler {
                        if (instrumentsBox?.value == CHOOSE) {
                            cursor = Cursor.CLOSED_HAND
                        }
                    })
                    addEventHandler(MouseEvent.MOUSE_DRAGGED, EventHandler {
                        if (instrumentsBox?.value == CHOOSE) {
                            if (startPoint != null) {
                                shape?.relocate(it.x,it.y)
                            }
                        }
                    })
                    addEventHandler(MouseEvent.MOUSE_RELEASED, EventHandler {
                        shape?.cursor = Cursor.DEFAULT

                    })
                }
            }
            isDrawing = false
        }
    }


    @FXML
    private fun initialize() {
        shapesBox?.items?.addAll(
            ShapesNames.values()
                .map { value -> value.getLabel() }
        )
        instrumentsBox?.items?.addAll(
                listOf(
                        CHOOSE,
                        FIGURE
                )
        )
        controlsBox?.children?.let {
            for (item in it) {
                labelsBox?.children?.addAll(
                        Label().apply {
                            this.text = item.accessibleText
                        }
                )
            }
        }
    }

    companion object {
        const val CHOOSE = "Выбрать"
        const val FIGURE = "Фигура"
    }
}