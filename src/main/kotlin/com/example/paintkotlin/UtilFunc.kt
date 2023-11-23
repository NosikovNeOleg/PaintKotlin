package com.example.paintkotlin

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