package com.example.paintkotlin.controllers

import com.example.paintkotlin.*
import com.example.paintkotlin.calculators.PaintCalculator.calculateShape
import javafx.event.EventHandler
import javafx.scene.Cursor
import javafx.scene.control.ColorPicker
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.*

class DrawingController(
    private val shapesBox: ComboBox<ShapesNames>?,
    private val instrumentsBox: ComboBox<String>?,
    private val fillColorBox: ColorPicker?,
    private val strokeColorBox: ColorPicker?,
    private val controlsBox: HBox?,
    private val labelsBox: HBox?,
    private val paintField: Pane?,
    //private val stage: Stage
) {

    private var shape: Shape? = null
    private var startPoint: Point? = null
    private var isDrawing: Boolean = false

    public fun onPaintFieldMousePressed(mouseEvent: MouseEvent) {
        val isChoose = instrumentsBox?.value == CHOOSE
        if (isDrawing || isChoose) {
            return
        }
        isDrawing = true
        val x = mouseEvent.x
        val y = mouseEvent.y
        startPoint = Point(x, y)
        shape = when (shapesBox?.value) {  // фабрика
            ShapesNames.LINE -> Line(x, y, x, y)
            ShapesNames.CIRCLE -> Circle(x, y, 0.0, fillColorBox?.value)
            ShapesNames.TRIANGLE -> Triangle()
            ShapesNames.RECTANGLE -> Rectangle(0.0, 0.0)
            ShapesNames.STAR -> Star()
            ShapesNames.ELLIPSE -> Ellipse(x, y, 0.0, 0.0)
            else -> null
        }
        shape?.apply {
            fill = fillColorBox?.value
            stroke = strokeColorBox?.value
            paintField?.children?.add(shape)
        }
    }


    public fun onPaintFieldMouseDragged(mouseEvent: MouseEvent) {
        val isChoose = instrumentsBox?.value == CHOOSE
        val mouseNotOnPaintField = paintField?.isHover == false
        if (isChoose || !isDrawing || mouseNotOnPaintField) {
            return
        }
        val x = mouseEvent.x
        val y = mouseEvent.y
        isBothNotNullDo(startPoint, shape) { startPoint, shape ->
            calculateShape(
                startPoint = startPoint,
                shape = shape,
                x = x,
                y = y
            )
        }
    }

    fun onPaintFieldMouseReleased(): Shape? {
        if (!isDrawing) {
            return null
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
                if (instrumentsBox?.value != CHOOSE) {
                    return@EventHandler
                }
                dragDelta?.let { delta ->
                    layoutX = it.sceneX + delta.x
                    layoutY = it.sceneY + delta.y
                }
            })
        }
        isDrawing = false
        return shape
    }

    init {
        shapesBox?.run {
            items.addAll(ShapesNames.values())
            value = ShapesNames.LINE
        }

        instrumentsBox?.items?.addAll(
            listOf(
                CHOOSE, FIGURE
            )
        )
        strokeColorBox?.value = Color.BLACK
        controlsBox?.children?.let {
            it.forEach {
                labelsBox?.children?.addAll(Label().apply {
                    this.text = it.accessibleText
                })
            }
        }
    }

    val changeCursor = { shape: Shape, cursor: Cursor -> if (instrumentsBox?.value == CHOOSE) shape.cursor = cursor }
    private operator fun Double.plus(x: Double?): Double {
        return x ?: 0.0
    }

    private companion object {
        const val CHOOSE = "Выбрать"
        const val FIGURE = "Фигура"
    }
}