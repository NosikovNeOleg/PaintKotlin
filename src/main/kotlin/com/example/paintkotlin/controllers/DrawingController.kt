package com.example.paintkotlin.controllers

import com.example.paintkotlin.Point
import com.example.paintkotlin.ShapesNames
import com.example.paintkotlin.isBothNotNullDo
import com.example.paintkotlin.presenters.PaintCalculator.calculateShape
import com.example.paintkotlin.presenters.shapeFactory
import com.example.paintkotlin.storage.ShapesStorage
import com.example.paintkotlin.storage.ShapesStorage.getStorage
import javafx.event.EventHandler
import javafx.scene.Cursor
import javafx.scene.control.ColorPicker
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import javafx.scene.layout.GridPane
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Shape

class DrawingController(
    private val shapesBox: ComboBox<ShapesNames>?,
    private val instrumentsBox: ComboBox<String>?,
    private val fillColorBox: ColorPicker?,
    private val strokeColorBox: ColorPicker?,
    private val paintField: Pane?,
    private val instrumentsGridPane: GridPane?
) {

    private var shape: Shape? = null
    private var startPoint: Point? = null
    private var isDrawing: Boolean = false
    private val shapesStorage: ShapesStorage = getStorage()

    fun onPaintFieldMousePressed(mouseEvent: MouseEvent) {
        val isChoose = instrumentsBox?.value == CHOOSE
        if (isDrawing || isChoose) {
            return
        }
        isDrawing = true
        val tempPoint = Point(mouseEvent.x, mouseEvent.y)
        if (shapesBox != null && fillColorBox != null && strokeColorBox != null && paintField != null) {
            shape =
                shapeFactory().getShape(shapeName = shapesBox.value, startPoint = tempPoint, color = fillColorBox.value)
                    .apply {
                        fill = fillColorBox.value
                        stroke = strokeColorBox.value
                        startPoint = tempPoint
                        paintField.children?.add(this)
                    }
        }
    }


    fun onPaintFieldMouseDragged(mouseEvent: MouseEvent) {
        val isChoose = instrumentsBox?.value == CHOOSE
        val mouseNotOnPaintField = paintField?.isHover != true
        if (isChoose || !isDrawing || mouseNotOnPaintField) {
            return
        }
        val x = mouseEvent.x
        val y = mouseEvent.y
        isBothNotNullDo(startPoint, shape) { startPoint, shape ->
            calculateShape(
                startPoint = startPoint, shape = shape, x = x, y = y
            )
        }
    }

    fun onPaintFieldMouseReleased(): Shape? {
        if (!isDrawing || shape == null) {
            return null
        }
        addHandlers(shape)
        shapesStorage.addShape(shape)
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
        if (instrumentsGridPane?.children != null) {
            for (i in 0..instrumentsGridPane.children.size) {
                instrumentsGridPane.add(
                    Label(instrumentsGridPane.children[i].accessibleText), i, 0
                )
            }
        }
    }

    val changeCursor = { shape: Shape, cursor: Cursor -> if (instrumentsBox?.value == CHOOSE) shape.cursor = cursor }
    private operator fun Double.plus(x: Double?): Double {
        return x ?: 0.0
    }

    fun addHandlers(shape: Shape?): Shape? {
        shape?.run {
            var dragDelta: Point? = null
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
        return shape
    }

    private companion object {
        const val CHOOSE = "Выбрать"
        const val FIGURE = "Фигура"
    }
}

