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
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.transform.Translate
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
        if (isDrawing) {
            return
        }
        isDrawing = true
        val x = mouseEvent.x
        val y = mouseEvent.y
        startPoint = Point(x, y)
        shape = when (shapesBox?.value) {
            ShapesNames.LINE.label -> Line(x, y, x, y)
            ShapesNames.CIRCLE.label -> Circle(x, y, 0.0, fillColorBox?.value)
            ShapesNames.TRIANGLE.label -> Polygon()
            ShapesNames.RECTANGLE.label -> Rectangle(0.0, 0.0)
            ShapesNames.STAR.label -> Polygon()
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
        if (!isDrawing && paintField?.isHover != true) {
            return
        }
        val x = mouseEvent.x
        val y = mouseEvent.y
        startPoint?.let {
            calculateShape(shape, it, x, y)
        }
    }

    @FXML
    private fun onPaintFieldMouseReleased() {
        if (isDrawing) {
            shape?.run {
                addEventHandler(MouseEvent.MOUSE_ENTERED, EventHandler {
                    if (instrumentsBox?.value == CHOOSE) {
                        cursor = Cursor.HAND
                    }
                })
                addEventHandler(MouseEvent.MOUSE_PRESSED, EventHandler {
                    if (instrumentsBox?.value == CHOOSE) {
                        cursor = Cursor.CLOSED_HAND
                    }
                })
                addEventHandler(MouseEvent.MOUSE_DRAGGED, EventHandler {
                    if (instrumentsBox?.value != CHOOSE) {
                        return@EventHandler
                    }
                    println(String.format("x: ${it.x} | ${it.sceneX} | ${it.screenX}"))
                    println(String.format("layoutX: $layoutX"))
                    val moveTo = Translate(it.x, it.y)
                    transforms.add(moveTo)
//                            this.translateX = it.sceneX
//                            this.translateY = it.sceneY

                })
                addEventHandler(MouseEvent.MOUSE_RELEASED, EventHandler {
                    cursor = Cursor.DEFAULT
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
            extensionFilters.add(FileChooser.ExtensionFilter("JSON files (*.txt)", "*.txt"))
            saveShapes()?.let { ShapesSaver.save(showSaveDialog(stage), it) }
        }
    }

    @FXML
    private fun onLoadButtonClicked() {
        FileChooser().apply {
            title = LOAD_TITLE
            extensionFilters.add(FileChooser.ExtensionFilter("JSON files (*.txt)", "*.txt"))
            paintField = Pane().apply { children?.addAll(ShapesSaver.load(showOpenDialog(stage))) }
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
                Node
            }
        }
    }


    private companion object {
        private const val CHOOSE = "Выбрать"
        private const val FIGURE = "Фигура"
        private const val SAVE_TITLE = "Сохранить файл"
        private const val LOAD_TITLE = "Загрузить файл"
    }
}