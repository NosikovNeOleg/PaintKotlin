package com.example.paintkotlin.Controllers

import com.example.paintkotlin.Applications.MorphApplication
import com.example.paintkotlin.Saver
import com.example.paintkotlin.ShapesNames
import com.example.paintkotlin.getShapeSaver
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.ColorPicker
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Shape
import javafx.stage.FileChooser
import javafx.stage.Stage
import java.io.IOException


class PaintController{
    @FXML private var shapesBoxPaint: ComboBox<ShapesNames>? = null
    @FXML private var instrumentsBoxPaint: ComboBox<String>? = null
    @FXML private var fillColorBoxPaint: ColorPicker? = null
    @FXML private var strokeColorBoxPaint: ColorPicker? = null
    @FXML private var labelsBoxPaint: HBox? = null
    @FXML private var controlsBoxPaint: HBox? = null
    @FXML private var paintFieldPaint: Pane? = null
    @FXML private var stage: Stage? = null

    private val drawingController: DrawingController by lazy {
        DrawingController(
        shapesBox = shapesBoxPaint!! ,
        instrumentsBox = instrumentsBoxPaint!!,
        fillColorBox = fillColorBoxPaint!!,
        strokeColorBox = strokeColorBoxPaint!!,
        labelsBox = labelsBoxPaint!!,
        controlsBox = controlsBoxPaint!!,
        paintField = paintFieldPaint!!)
    }

//    private var shape: Shape? = null
//    private var startPoint: Point? = null
//
//    private var isDrawing: Boolean = false

    private val shapeSaver: Saver<Shape> = getShapeSaver()

    @FXML
    private fun paintMousePressed(mouseEvent: MouseEvent){
        drawingController.onPaintFieldMousePressed(mouseEvent)
    }

    @FXML
    private fun paintMouseDragged(mouseEvent: MouseEvent){
        drawingController.onPaintFieldMouseDragged(mouseEvent)
    }

    @FXML
    private fun paintMouseReleased(){
        drawingController.onPaintFieldMouseReleased()
    }


//    @FXML
//    private fun onPaintFieldMousePressed(mouseEvent: MouseEvent) {
//        if (isDrawing || instrumentsBox?.value == CHOOSE) {
//            return
//        }
//        isDrawing = true
//        val x = mouseEvent.x
//        val y = mouseEvent.y
//        startPoint = Point(x, y)
//        shape = when (shapesBox?.value) {
//            ShapesNames.LINE -> Line(x, y, x, y)
//            ShapesNames.CIRCLE -> Circle(x, y, 0.0, fillColorBox?.value)
//            ShapesNames.TRIANGLE -> Triangle()
//            ShapesNames.RECTANGLE -> Rectangle(0.0, 0.0)
//            ShapesNames.STAR -> Star()
//            ShapesNames.ELLIPSE -> Ellipse(x, y, 0.0, 0.0)
//            else -> null
//        }
//        shape?.apply {
//            fill = fillColorBox?.value
//            stroke = strokeColorBox?.value
//            paintField?.children?.add(shape)
//        }
//    }


//    @FXML
//    private fun onPaintFieldMouseDragged(mouseEvent: MouseEvent) {
//        if (instrumentsBox?.value == CHOOSE || !isDrawing || paintField?.isHover != true) {
//            return
//        }
//        val x = mouseEvent.x
//        val y = mouseEvent.y
//        startPoint?.let { point ->
//            shape?.let { shape ->
//                PaintCalculator.calculateShape(
//                    shape,
//                    point,
//                    x,
//                    y
//                )   // лямбду написать чтобы лет для двух сразу скрины есть
//            }
//        }
//    }
//
//    @FXML
//    private fun onPaintFieldMouseReleased() {
//        if (!isDrawing) {
//            return
//        }
//        var dragDelta: Point? = null
//        shape?.run {
//            addEventHandler(MouseEvent.MOUSE_ENTERED) { changeCursor(this, Cursor.HAND) }
//            addEventHandler(MouseEvent.MOUSE_PRESSED) {
//                changeCursor(this, Cursor.CLOSED_HAND)
//                dragDelta = Point(this.layoutX - it.sceneX, this.layoutY - it.sceneY)
//            }
//            addEventHandler(MouseEvent.MOUSE_RELEASED) { changeCursor(this, Cursor.DEFAULT) }
//            addEventHandler(MouseEvent.MOUSE_DRAGGED, EventHandler {
//                if (instrumentsBox?.value != CHOOSE) {
//                    return@EventHandler
//                }
//                dragDelta?.let { delta ->
//                    layoutX = it.sceneX + delta.x
//                    layoutY = it.sceneY + delta.y
//                }
//            })
//        }
//        isDrawing = false
//    }


    @FXML
    private fun onSaveButtonClicked() {
        FileChooser().apply {
            title = SAVE_TITLE
            extensionFilters.add(FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"))
            showSaveDialog(stage)?.let { file ->
                saveShapes()?.let { shapeSaver.save(file, it) }  //
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
        //Application.launch(MorphApplication::class.java
        try {
            val RESOURCE_NAME = "morf-view.fxml"
            val WINDOW_WIDTH = 1280.0
            val WINDOW_HEIGHT = 720.0
            val TITLE = "MicroPaint!"
            val fxmlLoader = FXMLLoader(MorphApplication::class.java.getResource(RESOURCE_NAME))
            val scene = Scene(fxmlLoader.load(), WINDOW_WIDTH, WINDOW_HEIGHT)
            Stage().apply {
                title = TITLE
                this.scene = scene
                show()
            }

        } catch (e: IOException) {
            e.printStackTrace();
            println(e)
        }

    }


    @FXML
    private fun initialize() {
        shapesBoxPaint?.run {
            items.addAll(ShapesNames.values())
            value = ShapesNames.LINE
        }

        instrumentsBoxPaint?.items?.addAll(
            listOf(
                DrawingController.CHOOSE, DrawingController.FIGURE
            )
        )
        strokeColorBoxPaint?.value = Color.BLACK
        controlsBoxPaint?.children?.let {
            it.forEach {
                labelsBoxPaint?.children?.addAll(Label().apply {
                    this.text = it.accessibleText
                })
            }
        }
    }

    private fun saveShapes(): List<Shape>? {
        paintFieldPaint.let { pane ->
            return pane?.children?.map { node ->
                node as Shape // проверочку
            }
        }
    }


    private companion object {
        const val SAVE_TITLE = "Сохранить файл"
        const val LOAD_TITLE = "Загрузить файл"
    }
}