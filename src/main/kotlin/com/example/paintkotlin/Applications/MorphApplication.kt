package com.example.paintkotlin.Applications

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class MorphApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(MorphApplication::class.java.getResource(RESOURCE_NAME))
        val scene = Scene(fxmlLoader.load(), WINDOW_WIDTH, WINDOW_HEIGHT)
        with(stage) {
            title = TITLE
            this.scene = scene
            show()
        }
    }

    private companion object {
        const val WINDOW_WIDTH = 1280.0
        const val WINDOW_HEIGHT = 720.0
        const val TITLE = "Morphing"
        const val RESOURCE_NAME = "morf-view.fxml"
    }
}
