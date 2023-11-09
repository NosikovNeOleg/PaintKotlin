package com.example.paintkotlin

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.ComboBox
import javafx.stage.Stage

class PaintApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(PaintApplication::class.java.getResource("paint-view.fxml"))
        val scene = Scene(fxmlLoader.load(), 1280.0, 720.0)
        stage.title = "MicroPaint!"
        stage.scene = scene
        stage.show()
    }
}

fun main() {
    Application.launch(PaintApplication::class.java)
}
