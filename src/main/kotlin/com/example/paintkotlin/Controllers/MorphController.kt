package com.example.paintkotlin.Controllers

import com.example.paintkotlin.ShapesNames
import javafx.fxml.FXML
import javafx.scene.control.ColorPicker
import javafx.scene.control.ComboBox
import javafx.scene.control.Slider
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.stage.Stage

class MorphController {
    @FXML private var shapesBoxMorph: ComboBox<ShapesNames>? = null
    @FXML private var instrumentsBoxMorph: ComboBox<String>? = null
    @FXML private var fillColorBoxMorph: ColorPicker? = null
    @FXML private var strokeColorBoxMorph: ColorPicker? = null
    @FXML private var labelsBoxMorph: HBox? = null
    @FXML private var controlsBoxMorph: HBox? = null
    @FXML private var paintFieldMorph: Pane? = null
    @FXML private var stage: Stage? = null
    @FXML private var sliderMorph : Slider? = null

    private val drawingController: DrawingController by lazy {
        DrawingController(
            shapesBox = shapesBoxMorph!!,
            instrumentsBox = instrumentsBoxMorph!!,
            fillColorBox = fillColorBoxMorph!!,
            strokeColorBox = strokeColorBoxMorph!!,
            labelsBox = labelsBoxMorph!!,
            controlsBox = controlsBoxMorph!!,
            paintField = paintFieldMorph!!,
            // stage = stage!!)
        )
    }

    @FXML
    private fun morphMousePressed(mouseEvent: MouseEvent) {
        drawingController.onPaintFieldMousePressed(mouseEvent)
        val twoShapesIsAlreadyOnPane = paintFieldMorph?.children?.size?.equals(3) == true
        if (twoShapesIsAlreadyOnPane) {
            paintFieldMorph?.children?.removeAt(0)
        }
    }

    @FXML
    private fun morphMouseDragged(mouseEvent: MouseEvent) {
        drawingController.onPaintFieldMouseDragged(mouseEvent)
    }

    @FXML
    private fun morphMouseReleased(mouseEvent: MouseEvent) {
        drawingController.onPaintFieldMouseReleased()
    }

    @FXML
    private fun onChangeFieldButtonClicked() {
        stage?.close()
    }
}