package com.example.paintkotlin

import com.example.paintkotlin.PaintCalculator.calculateShape
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
import javafx.stage.FileChooser
import javafx.stage.Stage


class PaintController {

    @FXML
    private var shapesBox: ComboBox<String>? = null

    @FXML
    private var instrumentsBox: ComboBox<String>? = null

    @FXML
    private var fillColorBox: ColorPicker? = null

    @FXML
    private var strokeColorBox: ColorPicker? = null

    @FXML
    private var labelsBox: HBox? = null

    @FXML
    private var controlsBox: HBox? = null

    @FXML
    private var paintField: Pane? = null

    @FXML
    private var stage: Stage? = null

    private var shape: Shape? = null
    private var startPoint: Point? = null

    private var isDrawing: Boolean = false


    @FXML
    private fun onPaintFieldMousePressed(mouseEvent: MouseEvent) {
        if (isDrawing || instrumentsBox?.value == CHOOSE) {
            return
        }
        isDrawing = true
        val x = mouseEvent.x
        val y = mouseEvent.y
        startPoint = Point(x, y)
        shape = when (shapesBox?.value) {
            ShapesNames.LINE.label -> Line(x, y, x, y)
            ShapesNames.CIRCLE.label -> Circle(x, y, 0.0, fillColorBox?.value)
            ShapesNames.TRIANGLE.label -> Triangle()
            ShapesNames.RECTANGLE.label -> Rectangle(0.0, 0.0)
            ShapesNames.STAR.label -> Star()
            ShapesNames.ELLIPSE.label -> Ellipse(x, y, 0.0, 0.0)
            else -> null
        }
        shape?.run {
            fill = fillColorBox?.value
            stroke = strokeColorBox?.value
            paintField?.children?.add(shape)
        }
    }


    @FXML
    private fun onPaintFieldMouseDragged(mouseEvent: MouseEvent) {
        if (instrumentsBox?.value == CHOOSE || !isDrawing || paintField?.isHover != true) {
            return
        }
        val x = mouseEvent.x
        val y = mouseEvent.y
        startPoint?.let { point ->
            shape?.let { shape ->
                calculateShape(shape, point, x, y)
            }
        }
    }

    @FXML
    private fun onPaintFieldMouseReleased() {
        if (isDrawing) {
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
            saveShapes()
        }
    }


    @FXML
    private fun onSaveButtonClicked() {
        FileChooser().apply {
            title = SAVE_TITLE
            extensionFilters.add(FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"))
            showSaveDialog(stage)?.let { file ->
                saveShapes()?.let { ShapesSaverImpl.save(file, it) }
            }
        }
    }

    @FXML
    private fun onLoadButtonClicked() {
        FileChooser().apply {
            title = LOAD_TITLE
            extensionFilters.add(FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"))
            showOpenDialog(stage)?.let {
                paintField?.children?.run {
                    clear()
                    addAll(ShapesSaverImpl.load(it))
                }
            }
        }
    }


    @FXML
    private fun initialize() {
        shapesBox?.items?.addAll(
            ShapesNames.values()
                .map { value -> value.label }
        )
        instrumentsBox?.items?.addAll(
            listOf(
                CHOOSE,
                FIGURE
            )
        )
        strokeColorBox?.value = Color.BLACK
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

    private fun saveShapes(): List<Shape>? {
        paintField.let {
            return it?.children?.map {
                it as Shape
            }
        }
    }


    private companion object {
        private const val CHOOSE = "Выбрать"
        private const val FIGURE = "Фигура"
        private const val SAVE_TITLE = "Сохранить файл"
        private const val LOAD_TITLE = "Загрузить файл"
    }

    val changeCursor = { shape: Shape, cursor: Cursor -> if (instrumentsBox?.value == CHOOSE) shape.cursor = cursor }
}

private operator fun Double.plus(x: Double?): Double {
    return x ?: 0.0
}
