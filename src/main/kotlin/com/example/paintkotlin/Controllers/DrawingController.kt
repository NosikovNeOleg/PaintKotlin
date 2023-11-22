package com.example.paintkotlin.Controllers

import com.example.paintkotlin.*
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.Cursor
import javafx.scene.control.ColorPicker
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.*
import javafx.stage.Stage

class DrawingController(
    private val shapesBox: ComboBox<ShapesNames>,
    private val instrumentsBox : ComboBox<String>,
    private val fillColorBox : ColorPicker,
    private val strokeColorBox : ColorPicker,
    private val controlsBox : HBox,
    private val labelsBox: HBox,
    private val paintField : Pane,
    //private val stage: Stage
) {

    private var shape: Shape? = null
    private var startPoint: Point? = null
    private var isDrawing: Boolean = false

    public fun onPaintFieldMousePressed(mouseEvent: MouseEvent) {
        val isChoose = instrumentsBox.value == CHOOSE
        if (isDrawing || isChoose) {
            return
        }
        isDrawing = true
        val x = mouseEvent.x
        val y = mouseEvent.y
        startPoint = Point(x, y)
        shape = when (shapesBox.value) {
            ShapesNames.LINE -> Line(x, y, x, y)
            ShapesNames.CIRCLE -> Circle(x, y, 0.0, fillColorBox.value)
            ShapesNames.TRIANGLE -> Triangle()
            ShapesNames.RECTANGLE -> Rectangle(0.0, 0.0)
            ShapesNames.STAR -> Star()
            ShapesNames.ELLIPSE -> Ellipse(x, y, 0.0, 0.0)
            else -> null
        }
        shape?.apply {
            fill = fillColorBox.value
            stroke = strokeColorBox.value
            paintField.children?.add(shape)
        }
    }


    public fun onPaintFieldMouseDragged(mouseEvent: MouseEvent) {
        val isChoose = instrumentsBox.value == CHOOSE
        val mouseNotOnPaintField = !paintField.isHover
        if (isChoose || !isDrawing || mouseNotOnPaintField) {
            return
        }
        val x = mouseEvent.x
        val y = mouseEvent.y
        startPoint?.let { point ->
            shape?.let { shape ->
                PaintCalculator.calculateShape(
                    shape,
                    point,
                    x,
                    y
                )   // лямбду написать чтобы лет для двух сразу скрины есть
            }
        }
    }

    public fun onPaintFieldMouseReleased() {
        if (!isDrawing) {
            return
        }
        var dragDelta: Point? = null
        shape?.run {
            addEventHandler(MouseEvent.MOUSE_ENTERED) { changeCursor(this, Cursor.HAND) }
            addEventHandler(MouseEvent.MOUSE_PRESSED) {
                changeCursor(this, Cursor.CLOSED_HAND)
                dragDelta = Point(this.layoutX - it.sceneX, this.layoutY - it.sceneY)
            }
            addEventHandler(MouseEvent.MOUSE_RELEASED) { changeCursor(this, Cursor.DEFAULT) }
            addEventHandler(MouseEvent.MOUSE_DRAGGED, EventHandler {
                if (instrumentsBox.value != CHOOSE) {
                    return@EventHandler
                }
                dragDelta?.let { delta ->
                    layoutX = it.sceneX + delta.x
                    layoutY = it.sceneY + delta.y
                }
            })
        }
        isDrawing = false
    }

    init {
        shapesBox.run {
            items?.addAll(ShapesNames.values())
            value = ShapesNames.LINE
        }

        instrumentsBox.items?.addAll(
            listOf(
                CHOOSE,
                FIGURE
            )
        )
        strokeColorBox.value = Color.BLACK
        controlsBox.children?.let {
            it.forEach {
                labelsBox.children?.addAll(
                    Label().apply {
                        this.text = it.accessibleText
                    }
                )
            }
        }
    }

    val changeCursor = { shape: Shape, cursor: Cursor -> if (instrumentsBox.value == CHOOSE) shape.cursor = cursor }
    private operator fun Double.plus(x: Double?): Double {
        return x ?: 0.0
    }

    companion object {
        const val CHOOSE = "Выбрать"
        const val FIGURE = "Фигура"
    }
}