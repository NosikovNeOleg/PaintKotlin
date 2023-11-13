package com.example.paintkotlin

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class PaintApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(PaintApplication::class.java.getResource(RESOURCE_NAME))
        val scene = Scene(fxmlLoader.load(), WINDOW_WIDTH, WINDOW_HEIGHT)
        with (stage) {
            title = TITLE
            this.scene = scene
            show()
        }
    }

    companion object {
        const val WINDOW_WIDTH = 1280.0
        const val WINDOW_HEIGHT = 720.0
        const val TITLE = "MicroPaint!"
        const val RESOURCE_NAME = "paint-view.fxml"
    }
}

fun main() {
    Application.launch(PaintApplication::class.java)
}
