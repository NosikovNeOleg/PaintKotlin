package com.example.paintkotlin.controllers

import com.example.paintkotlin.Saver
import com.example.paintkotlin.ShapesNames
import com.example.paintkotlin.applications.MorphApplication
import com.example.paintkotlin.getShapeSaver
import javafx.fxml.FXML
import javafx.scene.control.ColorPicker
import javafx.scene.control.ComboBox
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.shape.Shape
import javafx.stage.FileChooser
import javafx.stage.Stage


class PaintController {
    @FXML
    private var shapesBoxPaint: ComboBox<ShapesNames>? = null

    @FXML
    private var instrumentsBoxPaint: ComboBox<String>? = null

    @FXML
    private var fillColorBoxPaint: ColorPicker? = null

    @FXML
    private var strokeColorBoxPaint: ColorPicker? = null

    @FXML
    private var labelsBoxPaint: HBox? = null

    @FXML
    private var controlsBoxPaint: HBox? = null

    @FXML
    private var paintFieldPaint: Pane? = null

    @FXML
    private var stage: Stage? = null

    private var drawingController: DrawingController? = null

    private val shapeSaver: Saver<Shape> = getShapeSaver()

    @FXML
    private fun paintMousePressed(mouseEvent: MouseEvent) {
        drawingController?.onPaintFieldMousePressed(mouseEvent)
    }

    @FXML
    private fun paintMouseDragged(mouseEvent: MouseEvent) {
        drawingController?.onPaintFieldMouseDragged(mouseEvent)
    }

    @FXML
    private fun paintMouseReleased() {
        drawingController?.onPaintFieldMouseReleased()
    }

    @FXML
    private fun onSaveButtonClicked() {
        FileChooser().apply {
            title = SAVE_TITLE
            extensionFilters.add(FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"))
            showSaveDialog(stage)?.let { file ->
                saveShapes()?.let { shapeSaver.save(file, it) }
            }
        }
    }

    @FXML
    private fun onLoadButtonClicked() {
        FileChooser().apply {
            title = LOAD_TITLE
            extensionFilters.add(FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"))
            showOpenDialog(stage)?.let {
                paintFieldPaint?.children?.run {
                    clear()
                    addAll(shapeSaver.load(it))
                }
            }
        }
    }

    @FXML
    private fun onChangeFieldButtonClicked() {
        MorphApplication().start(Stage())
    }

    @FXML
    private fun initialize() {
        drawingController = DrawingController(
            shapesBox = shapesBoxPaint,
            instrumentsBox = instrumentsBoxPaint,
            fillColorBox = fillColorBoxPaint,
            strokeColorBox = strokeColorBoxPaint,
            labelsBox = labelsBoxPaint,
            controlsBox = controlsBoxPaint,
            paintField = paintFieldPaint,
        )
    }

    private fun saveShapes(): List<Shape>? {
        paintFieldPaint.let { pane ->
            return pane?.children?.filter { node ->
                node !is Shape
            }?.map { node ->
                node as Shape
            }
        }
    }


    private companion object {
        const val SAVE_TITLE = "Сохранить файл"
        const val LOAD_TITLE = "Загрузить файл"
    }
}