package com.example.paintkotlin

import com.example.paintkotlin.controllers.DrawingController
import javafx.scene.control.Control
import javafx.scene.layout.Region

fun <T, R> isBothNotNullDo(first: T?, second: R?, action: (T, R) -> Unit) {
    if (first != null && second != null) {
        action(first, second)
    }
}

fun <T, R> isBothNotNullDo(first: T?, second: R?, action: () -> Nothing) {
    if (first != null && second != null) {
        action()
    }
}