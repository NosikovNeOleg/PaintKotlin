package com.example.paintkotlin.controllers

import com.example.paintkotlin.ShapesNames
import com.example.paintkotlin.presenters.MorphPresenter
import javafx.fxml.FXML
import javafx.scene.control.ColorPicker
import javafx.scene.control.ComboBox
import javafx.scene.control.Slider
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.shape.Polygon
import javafx.scene.shape.Shape
import javafx.stage.Stage

class MorphController {
    @FXML
    private var shapesBoxMorph: ComboBox<ShapesNames>? = null

    @FXML
    private var instrumentsBoxMorph: ComboBox<String>? = null

    @FXML
    private var fillColorBoxMorph: ColorPicker? = null

    @FXML
    private var strokeColorBoxMorph: ColorPicker? = null

    @FXML
    private var labelsBoxMorph: HBox? = null

    @FXML
    private var controlsBoxMorph: HBox? = null

    @FXML
    private var paintFieldMorph: Pane? = null

    @FXML
    private var stage: Stage? = null

    @FXML
    private var sliderMorph: Slider? = null
    private var sliderPastValue: Double? = null

    private var drawingController: DrawingController? = null

    private var shapes = HashMap<String, Shape>(2)


    @FXML
    private fun morphMousePressed(mouseEvent: MouseEvent) {
        drawingController?.onPaintFieldMousePressed(mouseEvent)
        val twoShapesIsAlreadyOnThePane = paintFieldMorph?.children?.size == 3
        if (twoShapesIsAlreadyOnThePane) {
            paintFieldMorph?.children?.removeAt(0)
        }
    }

    @FXML
    private fun morphMouseDragged(mouseEvent: MouseEvent) {
        drawingController?.onPaintFieldMouseDragged(mouseEvent)
    }

    @FXML
    private fun morphMouseReleased() {
        val shape = drawingController?.onPaintFieldMouseReleased() ?: return
        if (shape !is Polygon) {
            return
        }
        shapes[FIRST] = shapes[SECOND] ?: shape
        shapes[SECOND] = shape
        val twoShapesIsNotOnThePane = paintFieldMorph?.children?.size?.equals(2) != true
        val shapesIsNotPolygons = shapes[FIRST] !is Polygon || shapes[SECOND] !is Polygon
        if (twoShapesIsNotOnThePane || shapesIsNotPolygons) {
            sliderMorph?.isDisable = true
            return
        }
        sliderMorph?.isDisable = false
    }

    @FXML
    private fun onSliderChanged() {
        val sliderCurrentValue = sliderMorph?.value ?: 0.0
        val isIncrement = if (sliderCurrentValue > (sliderPastValue ?: 0.0)) 1 else -1
        MorphPresenter.morphShapes(shapes, sliderCurrentValue, isIncrement)
        sliderPastValue = sliderCurrentValue
    }

    @FXML
    private fun onChangeFieldButtonClicked() {
        stage?.close()
    }

    @FXML
    fun initialize() {
        drawingController = DrawingController(
            shapesBox = shapesBoxMorph,
            instrumentsBox = instrumentsBoxMorph,
            fillColorBox = fillColorBoxMorph,
            strokeColorBox = strokeColorBoxMorph,
            labelsBox = labelsBoxMorph,
            controlsBox = controlsBoxMorph,
            paintField = paintFieldMorph,
        )
        sliderMorph?.isDisable = true
    }

    companion object {
        const val FIRST = "first"
        const val SECOND = "second"
    }
}